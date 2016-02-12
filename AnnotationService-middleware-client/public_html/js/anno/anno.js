/*
 *
 * Copyright 2015 FORTH-ICS-ISL (http://www.ics.forth.gr/isl/)
 * Foundation for Research and Technology - Hellas (FORTH)
 * Institute of Computer Science (ICS)
 * Information Systems Laboratory (ISL)
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 *
 */
var anno = (function () {
// Object that holds annotation related information
    var annoConfig = {
        domain: 'http://localhost', // server offering the REST API
        port: 8080, // port# of the server
        service: 'annotationService/annotations', // the service runs at
        // filter this out from the URIs we want to annotate
        //filterOutFromURIs: 'metacatalogue.portal.lifewatch.gr.eu/search/browse?uri='
    };
// function that returns the url according to the configuration of annotation
// e.g. http://localhost:8084/LifeWatchAnnotation/annotations/
    function getServiceURL() {
        return annoConfig.domain + ':'
                + annoConfig.port + '/'
                + annoConfig.service + '/';
    }

// Create the constructor for storing the state
    var State = function () {
        this.enabled = false, // is annotation enabled?
        this.containedAnchorElements = []; // array of available anchors inside an element
        this.annotatedURIs = {}; // holds the annotated URIs, and their counts, etc.,
        this.annotatedAnchoredElements = []; // holds the elements that are annotated
        this.hideTimer = null; // timer for tooltips
        this.activeTooltip = null; // holds the acrive tooltip
        this.annoBar = null; // holds the annotation bar
        this.elementID = null;  // holds the elementID we want to annotate
    };
// This object holds internal information for supporting
// annotation. Exists only if annotation is enabled.
    var state;

    /**
     * Code for enabling/disabling the annotation slide bar
     */
    function initAnnotationBar() {
        // Code for the annotation bar with id annoBar
        var barCode = "<div id=\"annoBar\">" +
                "<div class=\"handle\">" +
                "<span class=\"handle-left\"></span>" +
                "</div>" +
                "<h4>Annotation Panel<br/>" +
                "<small><span class=\"\">" +
                "</span>Press <kbd>ESC</kbd> to close </small>" +
                "</h4>" +
                "<div id=\"annoInfo\">" +
                "</div>" +
                "<div id=\"annoConsole\">" +
                "</div></div>" +
                "</div>";
        $(document.body).append(barCode);
        // Button toggles slider
        $("#annoBar a").click(function () {
            var id = $(this).attr("href").substring(1);
            $("html, body").animate({scrollTop: $("#" + id).offset().top}, 1000, function () {
                $("#annoBar").slideReveal("hide");
            });
        });
        //
        state.annoBar = $("#annoBar").slideReveal({
            width: 300,
            push: false,
            position: "right",
            zIndex: 1000000,    // Make sure that panel is not hidden by bootstrap nav bar 
            // speed: 600,
            trigger: $(".handle"),
            // autoEscape: false,
            shown: function (obj) {
                obj.find(".handle").html('<span class="handle-right"></span>');
                obj.addClass("left-shadow-overlay");
            },
            hidden: function (obj) {
                obj.find(".handle").html('<span class="handle-left"></span>');
                obj.removeClass("left-shadow-overlay");
            }
        });
        // Add callbacks for tooltips
        addCallbacks();
        // Function that add callbacks when we mouse enter or mouseleave a tooltip
        function addCallbacks() {
            /* When entering the annotation tooltip with the buttons, clear timeout,
             * so that the tooltip is not destroyed */
            $(document).on('mouseenter', '.ui-tooltip', function () {
                // cancel tooltip closing on hover
                if (state && state !== null) {
                    clearTimeout(state.hideTimer);
                }
            });
            /* When entering the annotation tooltip with the buttons, clear timeout,
             * so that the tooltip is not destroyed */
            $(document).on('mouseleave', '.ui-tooltip', function () {
                // close the tooltip later (maybe ...)
                if (state && state !== null) {
                    state.hideTimer = setTimeout(function () {
                        $('.annoWrapperActive').uitooltip('close');
                        $('.annoWrapperHighlightActive').uitooltip('close');
                    }, 100);
                }
            });
        }
    }

    /**
     * Remove the annotation bar
     * @returns {undefined}
     */
    function removeAnnotationBar() {
        $('#annoBar').remove();
    }

    /**
     * Function that updates the color and tooltips of the annotated elements
     *
     * @returns {undefined}
     */
    function updateAnnotatedAnchorElements() {
        // if annotation is enabled
        if (state.enabled === true) {
            for (var i = 0; i < state.containedAnchorElements.length; i++) {
                // Current element
                var elem = state.containedAnchorElements[i];
                var targetURI = getQueryParameterOfURL('uri', elem.href);
                // If the href of this element is annotated
                if (isAnnotated(targetURI)) {
                    // Now wrap it around a span and highlight it
                    annotateElement(elem, true);
                    var annoWrapper = $(elem).parent();
                    // Also add tooltip to the wrapper
                    // Shows number of annotations, insert annotation targeting URI
                    // and search annotation targeting URI
                    setClickableTooltipHighLight(
                            annoWrapper,
                            '<div>'
                            + '<span class=\"label label-primary\" style=\"font-size: 100%\" title="Number of annotations">'
                            + state.annotatedURIs[targetURI] + '</span>'
                            + '<button onclick="anno.createAnnotation(\''
                            + targetURI + '\');" class="button button-anno" title="New Annotation">'
                            +'<span class="glyphicon glyphicon-pencil"></span></button>'
                            + '<button onclick="anno.showAnnotation(\''
                            + targetURI + '\');" class="button button-show" title="Show Annotations">'
                            + '<span class="glyphicon glyphicon-search"></span></button>'
                            + '</div>'
                            );
                } else {
                    // No annotated URI
                    // Now wrap it around a span and highlight it
                    annotateElement(elem, false);
                    var annoWrapper = $(elem).parent();
                    // Also add tooltip to the wrapper
                    setClickableTooltipNonHighLight(
                            annoWrapper,
                            '<div>'
                            + '<button onclick="anno.createAnnotation(\''
                            + targetURI + '\');" class="button button-anno" title="New Annotation">'
                            + '<span class="glyphicon glyphicon-pencil"></span></button>'
                            + '</div>'
                            );
                }
            }
        } else {
            // Remove the annotations
            for (var i = 0; i < state.containedAnchorElements.length; i++) {
                // Current element
                var elem = state.containedAnchorElements[i];
                deAnnotateElement(elem);
            }
        }

        // Method that adds clickable tooltips to annotated elements
        // 2 Buttons
        function setClickableTooltipHighLight(target, cont) {
            $(target).uitooltip({
                content: cont, // use the content provided
                show: null, // show immediately
                items: "span",
                position: {
                    my: 'center bottom',
                    at: 'center top-15',
                    collision: 'none'
                },
                open: function () {
                    // Close previous active tooltip
                    if (state.activeTooltip !== null)
                        $(state.activeTooltip).uitooltip('close');
                    clearTimeout(state.hideTimer);
                    $(target).removeClass("annoWrapperHighlight");
                    $(target).addClass("annoWrapperHighlightActive");
                    // Set current active tool tip
                    state.activeTooltip = this;
                },
                close: function (event) {
                    /*
                     if ($(event.currentTarget).is('a')) {
                     event.stopPropagation();
                     $(this).stop(true);
                     }*/
                    // Set active tooltip to null
                    state.activeTooltip = null;
                    $(target).removeClass("annoWrapperHighlightActive");
                    $(target).addClass("annoWrapperHighlight");
                }
            }).on('mouseleave', function (e) {
                // prevent tooltip widget to close the tooltip now
                e.stopImmediatePropagation();
                var that = this;
                // close the tooltip later (maybe ...)
                state.hideTimer = setTimeout(function () {
                    $(that).uitooltip('close');
                    $(target).removeClass("annoWrapperHighlightActive");
                    $(target).addClass("annoWrapper");
                }, 500);
            }).off('mouseout').off('focusout');
        }

        // Method that adds tooltips to non annotated elements
        // 1 Button
        function setClickableTooltipNonHighLight(target, cont) {
            $(target).uitooltip({
                content: cont, // use the content provided
                show: null, // show immediately
                items: "span",
                position: {
                    my: 'center bottom',
                    at: 'center top-15',
                    collision: 'none'
                },
                open: function () {
                    // Close previous active tooltip
                    if (state.activeTooltip !== null)
                        $(state.activeTooltip).uitooltip('close');
                    clearTimeout(state.hideTimer);
                    $(target).removeClass("annoWrapper");
                    $(target).addClass("annoWrapperHighlightActive");
                    // Set current active tool tip
                    state.activeTooltip = this;
                },
                close: function (event) {
                    /*
                     if ($(event.currentTarget).is('a')) {
                     event.stopPropagation();
                     $(this).stop(true);
                     }*/
                    // Set active tooltip to null
                    state.activeTooltip = null;
                    $(target).removeClass("annoWrapperHighlightActive");
                    $(target).addClass("annoWrapper");
                }
            }).on('mouseleave', function (e) {
                // prevent tooltip widget to close the tooltip now
                e.stopImmediatePropagation();
                var that = this;
                // close the tooltip later (maybe ...)
                state.hideTimer = setTimeout(function () {
                    $(that).uitooltip('close');
                    $(target).removeClass("annoWrapperHighlightActive");
                    $(target).addClass("annoWrapper");
                }, 500);
            }).off('mouseout').off('focusout');
        }

        // Function that wrap the element around a span and
        // highlights a specific element with color
        function annotateElement(elem, highlight) {
            // If already annotated, deannotate it
            if ($(elem).parent().is("span")) {
                if ($(elem).parent().hasClass("annoWrapperHighlight")
                        || $(elem).parent().hasClass("annoWrapper"))
                    deAnnotateElement(elem);
            }
            // Wrap element around a span element with annotation class
            if (highlight === true)
                $(elem).wrap('<span class="annoWrapperHighlight"></span>');
            else
                $(elem).wrap('<span class="annoWrapper"></span>');
        }

        // Function that highlights a specific element with color
        function deAnnotateElement(elem) {
            if ($(elem).parent().is("span")) {
                // Remove the span that we wrapped the element into
                $(elem).unwrap();
            }
        }
    }

    /**
     * returns if a URI is
     * @param {type} URI
     * @returns {Boolean}
     */
    function isAnnotated(URI) {
        if (state && state !== null)
            // If message URI is not null
            if (state.annotatedURIs[URI]) {
                // If number of annotations > 0
                return state.annotatedURIs[URI] > 0;
            } else
                return false;
    }

    /**
     * function that shows bar
     *
     * @returns {undefined}
     */
    function showAnnoBar() {
        if (state && state !== null) {
            state.annoBar.slideReveal('show');
        }
    }


    /**
     * Function that adds in an array all URLs contained in a specific
     * element.
     *
     * These elements will be used to make the REST call for getting available
     * annotations * @param {type} elementID
     * @returns {undefined}
     */
    function findURLs(elementID) {
        if (state && state !== null) {
            var intro = document.getElementById(elementID);
            // Get all anchor elements in elementID
            var anchors = Array.prototype.slice.call(intro.getElementsByTagName('a'));

            // Keep only lifewatch species URIs
            function species(value, index, ar) {
                var contains = "http://www.lifewatchgreece.eu/entity/species/";
                // values are anchors
                var filtered = getQueryParameterOfURL('uri', value.href);
                return filtered.indexOf(contains) === 0;
            }
            state.containedAnchorElements = anchors.filter(species);
        }
    }

    /**
     * Function that loads to the annotation panel the UI for the creation of the
     * annotation
     * @param {type} targetURI
     * @returns {undefined}
     */
    function createAnnotationUI(targetURI) {
        var createUI = "";
        var anno = {};
        // Add header
        createUI += "<div id=\"annoBarHeader\" class=\"list-group-item\"><b><h6>Create annotation targeting</h6>";
        createUI += "<a href=\"" + targetURI + "\" target=\"_blank\">" + targetURI + "</a></b></div>";
        // Annotation Type
        createUI += "<div class=\"annoCreationContainer\">";
        createUI += "<div class=\"anno-list-group-item\" id=\"annoCreationType\">";
        createUI += "<h6><b>Annotation type</b></h6>";
        // Here is the form
        createUI += "<form class=\"form-horizontal\" role=\"form\">";
        createUI += "<div class=\"form-group\">";
        createUI += "<label class=\"col-lg-3 control-label\" for=\"annotationType\">Type:</label>";
        createUI += "<div class=\"col-lg-9\">";
        createUI += "<select id=\"annotationTypeForm\" class=\"js-example-basic-single form-control\" data-live-search=\"true\" title=\"Please select a type...\">";
        createUI += "<option selected disabled value=\"\"></option>";
        createUI += "<option>POLYTRAITS</option>";
        createUI += "<option>TEXT</option>";
        createUI += "</select>";
        createUI += "</div></div>";
        createUI += "</form>";
        createUI += "</div>";
        // Create divs for the rest annotation elements
        createUI += "<div id=\"annoCreationBody\"></div>";
        createUI += "<div id=\"annoCreationTarget\"></div>";
        createUI += "<div id=\"annoCreationMotivatedBy\"></div>";
        createUI += "<div id=\"annoCreationProv\"></div>";
        createUI += "<div id=\"annoCreateButton\"></div>";
        createUI += "</div>";
        // Now add dom elements
        $("#annoInfo").html(createUI);
        // Add the events for selecting the type of the annotation
        addEventsToCreateAnnotationMenusInitial(anno, targetURI);
    }

    /**
     * Get query parameter of url
     * @param {type} name
     * @param {type} url
     * @returns {Array|anno_L7.getQueryParameterOfURL.results}
     */
    function getQueryParameterOfURL(name, url) {
        if (!url)
            url = location.href;
        name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
        var regexS = "[\\?&]" + name + "=([^&#]*)";
        var regex = new RegExp(regexS);
        var results = regex.exec(url);
        return results === null ? url : results[1];
    }

    /**
     * Adds events for managing the drop down menus of the type of the annotation
     * @param {type} anno
     * @param {type} targetURI
     * @returns {undefined}
     */
    function addEventsToCreateAnnotationMenusInitial(anno, targetURI) {
        // Update the menus with options
        $("#annotationTypeForm").change(function () {
            var selText = $("#annotationTypeForm").val();
            anno = {}; // reset annotation object
            anno.target = {};
            // Remove from the URI the filter out
            anno.target.id = targetURI;

            // set the type of the annotation
            anno.type = selText;
            // Draw the rest part of the annotation
            createAnnoUIRest(anno);
            // Check if we should enable the button
            checkAnnotationCreateButton(anno);
        });
        // For select2
        $.fn.select2.amd.require(['select2/compat/matcher'], function (oldMatcher) {
            $("#annotationTypeForm").select2({
                minimumResultsForSearch: 0,
                placeholder: "Select an option",
                matcher: oldMatcher(matchStart)
            });
        });
    }


    /**
     * After selecting the type of the annotation, create the rest of
     * @param {type} anno
     * @returns {undefined}
     */
    function createAnnoUIRest(anno) {
        // Create anno body
        anno.body = {};

        // Annotation Type
        var bodyUI = "";
        bodyUI += "<div class=\"anno-list-group-item\" id=\"annoBodyType\">";
        bodyUI += "<h6><b>Body</b></h6>";
        // Here is the form for the body type
        bodyUI += "<form class=\"form-horizontal\" role=\"form\">";
        bodyUI += "<div class=\"form-group\">";
        bodyUI += "<label class=\"col-lg-3 control-label\" for=\"annotationType\">Type:</label>";
        bodyUI += "<div class=\"col-lg-9\">";
        bodyUI += "<select id=\"annotationBodyTypeForm\" class=\"js-example-basic-single form-control\" title=\"Please select a type...\">";
        // If it is text type
        if (anno.type === 'TEXT') {
            // Maybe in the future we additionally support the ao:TextArea
            bodyUI += "<option disabled value=\"\"></option>";
            bodyUI += "<option selected value=\"dctypes:Text\">DCTYPES - TEXT</option>";
            // Set default body type to DCTYPES - TEXT
            anno.body.type = $('#annotationBodyTypeForm').val();
        } else {
            // Call a function that returns all available options for Polytraits
            bodyUI += "<option selected disabled value=\"\"></option>";
            bodyUI += polytraitTypes();
        }
        bodyUI += "</select>";
        bodyUI += "</div></div>";
        bodyUI += "</form>";

        // If this a text anno, we have to show the value, language and format
        if (anno.type === 'TEXT') {
            // Text input for value
            bodyUI += "<form class=\"form-horizontal\" role=\"form\">";
            bodyUI += "<div class=\"form-group\">";
            bodyUI += "<label class=\"col-lg-3 control-label\"for=\"annotationBodyValueForm\">Value:</label>";
            bodyUI += "<div class=\"col-lg-9\">";
            bodyUI += "<input class=\"form-control\" id=\"annotationBodyValueForm\" type=\"text\" title = \"Please insert text...\">";
            bodyUI += "</div></div>";
            bodyUI += "</form>";

            // format
            bodyUI += "<form class=\"form-horizontal\" role=\"form\">";
            bodyUI += "<div class=\"form-group\">";
            bodyUI += "<label class=\"col-lg-3 control-label\" for=\"annotationBodyFormatForm\">Format:</label>";
            bodyUI += "<div class=\"col-lg-9\">";
            bodyUI += "<select id=\"annotationBodyFormatForm\" class=\"js-example-basic-single form-control\" title=\"Please select a format...\">";
            bodyUI += "<option disabled value=\"\"></option>";
            bodyUI += mimeTypes();
            bodyUI += "</select>";
            bodyUI += "</div></div>";
            bodyUI += "</form>";

            // language
            bodyUI += "<form class=\"form-horizontal\" role=\"form\">";
            bodyUI += "<div class=\"form-group\">";
            bodyUI += "<label class=\"col-lg-3 control-label\" for=\"annotationBodyLanguageForm\">Language:</label>";
            bodyUI += "<div class=\"col-lg-9\">";
            bodyUI += "<select id=\"annotationBodyLanguageForm\" class=\"js-example-basic-single form-control\" title=\"Please select a language...\">";
            bodyUI += "<option disabled value=\"\"></option>";
            bodyUI += languageTypes();
            bodyUI += "</select>";
            bodyUI += "</div></div>";
            bodyUI += "</form>";

        } else {
            // Here is the form for the body value of Polytraits
            bodyUI += "<form class=\"form-horizontal\" role=\"form\">";
            bodyUI += "<div class=\"form-group\">";
            bodyUI += "<label class=\"col-lg-3 control-label\" for=\"annotationBodyValueForm\">Modality:</label>";
            bodyUI += "<div class=\"col-lg-9\">";
            bodyUI += "<select id=\"annotationBodyValueForm\" class=\"js-example-basic-single form-control\" title=\"Please select a modality...\">";
            bodyUI += "<option selected disabled value=\"\"></option>";
            bodyUI += "</select>";
            bodyUI += "</div></div>";
            bodyUI += "</form>";
        }
        bodyUI += "</div>"; // Closes the body
        $("#annoCreationBody").html(bodyUI);

        // Target
        // target in anno object has already been initialized

        var targetUI = "";
        // First show the target
        targetUI += "<div class=\"anno-list-group-item\" id=\"annoTargetType\">";
        targetUI += "<h6><b>Target</b></h6>";
        targetUI += "<label class=\"col-lg-3 control-label\" for=\"annotationTargetID\">ID:</label>";
        targetUI += "<a href=\"" + anno.target.id + "\" target=\"_blank\" class=\"annoPanelRef\">" + anno.target.id + "</a>";

        // type selection
        targetUI += "<form class=\"form-horizontal\" role=\"form\">";
        targetUI += "<div class=\"form-group\">";
        targetUI += "<label class=\"col-lg-3 control-label\" for=\"annotationTargetTypeForm\">Type:</label>";
        targetUI += "<div class=\"col-lg-9\">";
        targetUI += "<select id=\"annotationTargetTypeForm\" class=\"js-example-basic-single form-control\" title=\"Please select a target type...\">";
        targetUI += "<option disabled value=\"\"></option>";
        targetUI += "<option selected value=\"http://umbel.org/umbel/rc/Animal\">http://umbel.org/umbel/rc/Animal</option>";
        targetUI += "</select>";
        targetUI += "</div></div>";
        targetUI += "</form>";
        targetUI += "</div>";

        // Add the motivation
        $("#annoCreationTarget").html(targetUI);

        // Motivation
        anno.motivated = {};
        var motivationUI = "";
        // First show the target
        motivationUI += "<div class=\"anno-list-group-item\" id=\"annoMotivationType\">";
        motivationUI += "<h6><b>Motivation</b></h6>";

        // type selection
        motivationUI += "<form class=\"form-horizontal\" role=\"form\">";
        motivationUI += "<div class=\"form-group\">";
        motivationUI += "<label class=\"col-lg-3 control-label\" for=\"annotationMotivatedByForm\">Type:</label>";
        motivationUI += "<div class=\"col-lg-9\">";
        motivationUI += "<select id=\"annotationMotivatedByForm\" class=\"js-example-basic-single form-control\" title=\"Please select a motivation type...\">";
        motivationUI += "<option disabled value=\"\"></option>";
        // Have to create appropriate method for this
        motivationUI += "<option selected value=\"http://www.w3.org/ns/oa#describing\">Describing</option>";
        motivationUI += "</select>";
        motivationUI += "</div></div>";
        motivationUI += "</form>";
        motivationUI += "</div>";

        // Add the motivation
        $("#annoCreationMotivatedBy").html(motivationUI);

        // Provenance
        // Until we start to use the authorization, just sent the default lifewatch user
        anno.prov = {};
        var provUI = "";
        provUI += "<div class=\"anno-list-group-item\" id=\"annoProvType\">";
        provUI += "<h6><b>Provenance</b></h6>";
        provUI += "<label class=\"col-lg-3 control-label\">user:</label>";
        provUI += "<label class=\"col-lg-9 control-label\">lifewatch</label><br>";
        provUI += "</div>";

        // Add the prov
        $("#annoCreationProv").html(provUI);

        // Button for submit
        var submitButton = "";
        submitButton += "<div class=\"anno-list-group-item\" id=\"annoCreateButtonType\">";
        submitButton += "<button id=\"annotationCreateButton\" class=\"btn btn-success btn-block\" disabled>Create</button>";
        submitButton += "</div>";

        // Add the button code
        $("#annoCreateButton").html(submitButton);

        // Add Events for the rest annotations of menu
        addEventsToCreateAnnotationMenusRest(anno);

        // Set default values after enabling the selections
        if (anno.type === 'TEXT') {
            // Set target type
            anno.body.type = $("#annotationBodyTypeForm").val();
            // Set default language to english
            anno.body.language = $('#annotationBodyLanguageForm').val();
            // Set default format to text
            anno.body.format = $('#annotationBodyFormatForm').val();
        }
        // Set target type
        anno.target.type = $("#annotationTargetTypeForm").val();
        // Set motivated type
        anno.motivated.type = $("#annotationMotivatedByForm").val();

    }

    /**
     * Adds events for managing the drop down menus during the creations of the annotations
     * @param {type} anno
     * @returns {undefined}
     */
    function addEventsToCreateAnnotationMenusRest(anno) {
        // If we click the type
        $("#annotationBodyTypeForm").change(function () {
            var selText = $("#annotationBodyTypeForm").val();

            // set the type of the annotation
            anno.body.type = selText;

            // If it is text type
            if (anno.type === 'TEXT') {
                var select = "<option disabled value=\"\"></option>";
                select += "<option selected value=\"dctypes:Text\">DCTYPES - TEXT</option>";
                $("#annotationBodyValueForm").html(select);
            } else {
                // Call a function that returns all available modalities
                // for the selected type
                $("#annotationBodyValueForm").html(polytraitsModalities(anno));
                // reset the value of the select box using select2
                $('#annotationBodyValueForm').select2("val", "");
            }
            // Check if we should enable the button
            checkAnnotationCreateButton(anno);
        });

        // If we click the value
        $("#annotationBodyValueForm").change(function () {
            var selText = $("#annotationBodyValueForm").val();
            // set the type of the annotation
            anno.body.value = selText;
            // Check if we should enable the button
            checkAnnotationCreateButton(anno);
        });

        // If we click the target type
        $("#annotationTargetTypeForm").change(function () {
            var selText = $("#annotationTargetTypeForm").val();
            // set the type of the annotation
            anno.target.type = selText;
            // Check if we should enable the button
            checkAnnotationCreateButton(anno);
        });

        // If we click the motivation type
        $("#annotationMotivatedByForm").change(function () {
            var selText = $("#annotationMotivatedByForm").val();
            // set the type of the annotation
            anno.motivated = {};
            anno.motivated.type = selText;
            // Check if we should enable the button
            checkAnnotationCreateButton(anno);
        });

        // If the create button is enabled and clicked!
        $("#annoCreateButton").click(function () {
            // Create the jsonld annotation
            var jsonldAnno = createJSONLD(anno);
            // The send it to the server!
            ajaxRESTCreateAnnotation(jsonldAnno, anno.target.id);
        });

        // If we click the format
        $("#annotationBodyFormatForm").change(function () {
            var selText = $("#annotationBodyFormatForm").val();
            // set the type of the annotation
            anno.body.format = selText;
            // Check if we should enable the button
            checkAnnotationCreateButton(anno);
        });

        // If we click the language
        $("#annotationBodyLanguageForm").change(function () {
            var selText = $("#annotationBodyLanguageForm").val();
            // set the type of the annotation
            anno.body.language = selText;
            // Check if we should enable the button
            checkAnnotationCreateButton(anno);
        });

        // For select2
        $.fn.select2.amd.require(['select2/compat/matcher'], function (oldMatcher) {
            $(".js-example-basic-single").select2({
                minimumResultsForSearch: 0,
                placeholder: "Select an option",
                matcher: oldMatcher(matchStart)
            });
        });
    }

    /**
     * Function used for matching elements
     * @param {type} term
     * @param {type} text
     * @param {type} option
     * @returns {Boolean}
     */
    function matchStart(term, text, option) {
        // Search also the value of the option
        return text.toUpperCase().indexOf(term.toUpperCase()) >= 0
                || option.element.value.toUpperCase().indexOf(term.toUpperCase()) >= 0;
    }

    /**
     * Function that returns a strong with the available polytrait types
     * @returns {String}
     */
    function polytraitTypes() {
        var options = "";
        // 47 options
        for (var i = 0; i < polytraitsMap.length; i++) {
            options += "<option value=\"" + polytraitsMap[i].type + "\">" +
                    polytraitsMap[i].desc + "</option>";
        }
        return options;
    }

    /**
     * Selects available values for polytraits
     * In the future it could be substituted by appropriate ajax calls to
     * the server side?
     * @param {type} anno
     * @returns {String}
     */
    function polytraitsModalities(anno) {
        var modalitiesHTML = "";
        modalitiesHTML += "<option selected disabled value=\"\"></option>";

        // Get modalities
        var result = $.grep(polytraitsMap, function (e) {
            return e.type === anno.body.type;
        });

        var modalities;
        if (result.length === 0) {
            // not found
            // We should never be here
            alert("Something went wrong! Type was not found!");
        } else if (result.length === 1) {
            // access the foo property using result[0].foo
            modalities = result[0].modalities;
        } else {
            // multiple items found
            // We should never be here
            alert("Something went wrong! Many types were found!");
        }

        for (var modality in modalities) {
            modalitiesHTML += "<option value=\""
                    + modality + "\">"
                    + modalities[modality]
                    + "</option>";
        }

        return modalitiesHTML;
    }

    /**
     * Function that returns a string with the available polytrait types
     * @returns {String}
     */
    function languageTypes() {
        var languages = "";
        for (var isoLang in isoLangs) {
            // skip loop if the property is from prototype
            if (!isoLangs.hasOwnProperty(isoLang))
                continue;
            var lang = isoLangs[isoLang];
            // Auto select english
            if (isoLang === 'en')
                languages += "<option selected value=\"" + isoLang + "\">" +
                        lang.name + "</option>";
            else
                languages += "<option value=\"" + isoLang + "\">" +
                        lang.name + "</option>";
        }
        return languages;
    }

    /**
     * Function that returns a strong with the available polytrait types
     * @returns {String}
     */
    function mimeTypes() {
        var mimes = "";
        for (var mime in supportedMimeTypes) {
            // Auto select english
            if (supportedMimeTypes[mime] === 'text/plain')
                mimes += "<option selected value=\"" + supportedMimeTypes[mime] + "\">" +
                        supportedMimeTypes[mime] + "</option>";
            else
                mimes += "<option value=\"" + supportedMimeTypes[mime] + "\">" +
                        supportedMimeTypes[mime] + "</option>";
        }
        return mimes;
    }

    /**
     *
     * @param {type} anno
     * @returns {undefined}
     */
    function checkAnnotationCreateButton(anno) {
        // if object is ok
        if (checkAnnoObjectReady(anno)) {
            // Should enable the button
            $('#annotationCreateButton').removeAttr('disabled');
            return;
        } else {
            // Disable button
            $('#annotationCreateButton').attr('disabled', true);
        }
    }

    /**
     * Check if all aspects of an annotation object have been filled in
     * @param {type} anno
     * @returns {Boolean}
     */
    function checkAnnoObjectReady(anno) {
        if (anno) {
            if (anno.body && anno.body.type && anno.body.value) {
                if (anno.target && anno.target.id && anno.target.type) {
                    if (anno.motivated && anno.motivated.type) {
                        // annotation is ok
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Construct basic ajax call
     * @returns {XMLHttpRequest|ActiveXObject}
     */
    function getRequestObject() {
        if (window.XMLHttpRequest) {
            return(new XMLHttpRequest());
        } else if (window.ActiveXObject) {
            return(new ActiveXObject("Microsoft.XMLHTTP"));
        } else {
            return(null);
        }
    }

    /**
     * AJAX request for creating a specific annotation
     * @param {type} anno
     * @param {type} targetURI
     * @returns {undefined}
     */
    function ajaxRESTCreateAnnotation(anno, targetURI) {
        var request = getRequestObject();
        request.onreadystatechange = function () {
            if (request.readyState === 4) {
                handleRESTCreateAnnotation(request, targetURI);
            }
        };
        request.open("POST", getServiceURL(), true);
        // Set header to ld+json
        request.setRequestHeader("Content-Type", "application/ld+json;charset=UTF-8");
        var parameters = anno;
        request.send(parameters);
        showConsoleMessage("Creating annotation...");
    }

    /**
     * function for handling the ajax creationg request
     * @param {type} request
     * @param {type} targetURI
     * @returns {undefined}
     */
    function handleRESTCreateAnnotation(request, targetURI) {
        // If annotation was created successfully
        if (request.status === 201 && state && state.enabled) {
            // addConsoleMessage
            showConsoleMessage("Annotation created!");
            // Now update the annotated URIs and also update the bar
            ajaxAnnotatedURIs();
            // Update the annotations in the annotation bar
            ajaxRESTRetrieveAnnotation(targetURI);
        }
    }

    /**
     * AJAX request for retrieving an annotation
     * @param {type} targetURI
     * @returns {undefined}
     */
    function ajaxRESTRetrieveAnnotation(targetURI) {
        var request = getRequestObject();
        request.onreadystatechange = function () {
            handleRESTRetrieveAnnotation(request, targetURI);
        };
        // Set header to ld+json
        var retrieveURL = getServiceURL() + "target/" + "?uri=" + encodeURIComponent(targetURI);
        request.open("GET", retrieveURL, true);
        request.setRequestHeader("Content-Type", "application/ld+json;charset=UTF-8");
        var parameters = null;
        request.send(parameters);
        showConsoleMessage("Retrieving annotations...");
    }

    /**
     * handler for retrieval of annotations of specific target
     * @param {type} request
     * @param {type} targetURI
     * @returns {undefined}
     */
    function handleRESTRetrieveAnnotation(request, targetURI) {
        if (request.readyState === 4 && state && state.enabled) {
            if (request.status === 200) {
                // Show the results of the ajax request to this specific div
                document.getElementById("annoInfo").innerHTML =
                        prettyJSONLDAnnotationSpecificTarget(request.responseText, targetURI);
            } else if (request.status === 404) {
                document.getElementById("annoInfo").innerHTML =
                        "<div id=\"annoBarHeader\" class=\"anno-list-group-item\"><h6><a href=\""
                        + targetURI + "\">" + targetURI + "</a><br><b>No annotations found!</b></h6></div>";
            }
            showConsoleMessage("Annotations retrieved!");
        }
    }
    /**
     * ajax request that deletes annotation with specific uri
     * @param {type} annoID
     * @param {type} targetURI
     * @returns {undefined}
     */
    function ajaxRESTDeleteAnnotation(annoID, targetURI) {
        var request = getRequestObject();
        var uri = getServiceURL() + encodeURIComponent(annoID);
        request.onreadystatechange = function () {
            if ((request.readyState === 4)) {
                handleRESTDeleteAnnotation(request, targetURI);
            }
        };
        request.open("DELETE", uri, true);
        // Set header to ld+json
        request.setRequestHeader("Content-Type", "application/ld+json;charset=UTF-8");
        var parameters = null;
        request.send(parameters);
        showConsoleMessage("Removing annotation...");

    }

    /**
     * handling function for deleting annotations
     * @param {type} request
     * @param {type} targetURI
     * @returns {undefined}
     */
    function handleRESTDeleteAnnotation(request, targetURI) {
        if (request.status === 200 && state && state.enabled) {
            showConsoleMessage("Annotation removed!");
            // Now update the annotated URIs and also update the bar
            ajaxAnnotatedURIs();
            // Update the annotations in the annotation bar
            ajaxRESTRetrieveAnnotation(targetURI);
        }
    }

    /**
     * AJAX request get annotated URIs and their counts from the server
     * @returns {undefined}
     */
    function ajaxAnnotatedURIs() {
        var request = getRequestObject();
        request.onreadystatechange = function () {
            if (request.readyState === 4) {
                handleAnnotatedURIs(request);
            }
        };
        // The request is a POST request, since for optimization reasons we would
        // like the client to send which URIs should be queried if they are annotated
        request.open("POST", getServiceURL() + "targetsOnly", true);
        // Set header to json
        request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

        var parameters = null;
        request.send(parameters);
    }

    /**
     * Handling function for getting the annotated uris
     * @param {type} request
     * @returns {undefined}
     */
    function handleAnnotatedURIs(request) {
        if (request.readyState === 4) {
            if (request.status === 200) {
                // The response text holds all the annotated URIs from the server
                // Parse the JSON and hold it in annotatedURIs
                state.annotatedURIs = JSON.parse(request.responseText);
                // Add annotations to annotated URIs
                updateAnnotatedAnchorElements();
            } else if (request.status === 404) {
                // The response text holds all the annotated URIs from the server
                // Parse the JSON and hold it in annotatedURIs
                state.annotatedURIs = JSON.parse(request.responseText);
                // Add annotations to annotated URIs
            }
        }
    }

    /**
     * Show the annotations of a specific target in a pretty print
     * for HTML rendering
     * @param {String} jsonld
     * @param {String} targetURI
     * @returns {undefined}
     */
    function prettyJSONLDAnnotationSpecificTarget(jsonld, targetURI) {
        var parsedJSON = JSON.parse(jsonld);
        var pretty = "";

        pretty += "<div id=\"annoBarHeader\" class=\"anno-list-group-item\"><h5><a class=\"wrap\" href=\""
                + targetURI + "\">" + targetURI + "</a><br>";
        if (parsedJSON.length === 1)
            pretty += "<br><b>" + parsedJSON.length + "</b> annotation available</h5></div>";
        else
            pretty += "<br><b>" + parsedJSON.length + "</b> annotations available</h5></div>";
        pretty += "<br>";

        // Bootstrap list group
        // 1st the header
        pretty += "<hr></hr><ul class=\"anno-list-group\">";

        for (var i = 0; i < parsedJSON.length; i++) {
            // read the annotation
            var currentAnno = {};
            // Be careful where we search to find the annotation properties!!!
            readAnnotation(parsedJSON[i]["@graph"][0], currentAnno);

            // Currently type either simple Text or Polytraits
            var annoHeader = "";
            if (currentAnno.body["@type"] === "dctypes:Text") {
                annoHeader = "DCTYPE - TEXT ";
                // Add value of text
                annoHeader += "[" + currentAnno.body["value"] + "]";
            } else {
                // Get description of this specific URL
                var result = $.grep(polytraitsMap, function (e) {
                    return e.type === currentAnno.body["@type"];
                });

                var description;
                var modality;
                if (result.length === 0) {
                    // not found
                    // We should never be here
                    alert("Something went wrong! Type was not found!");
                } else if (result.length === 1) {
                    // access the foo property using result[0].foo
                    description = result[0].desc;
                    modality = result[0].modalities[currentAnno.body["value"]];
                } else {
                    // multiple items found
                    // We should never be here
                    alert("Something went wrong! Many types were found!");
                }
                // Show information of annotation type and value
                annoHeader = "POLYTRAITS - "
                        + description + " [" + modality + "]";
            }
            pretty += "<li class=\"anno-list-group-item\">";
            pretty += "<a class=\"btn btn-primary btn-block wrap\" href=\"#annoPanelCollapse"
                    + (i + 1) + "\" data-toggle=\"collapse\">";
            pretty += "<h5>" + (i + 1) + "." + annoHeader + "</h5></a>\n";
            pretty += "<div id=\"annoPanelCollapse" + (i + 1) + "\" class=\"panel-collapse collapse\">";
            // List with annotation elements
            pretty += "<ul class=\"list-group\" style=\"margin: 2px\">";

            // Body
            pretty += "<li class=\"anno-list-group-item\">";
            pretty += "<a href=\"#annoPanelCollapseBody" + (i + 1) + "\" data-toggle=\"collapse\">";
            pretty += "<h6><u>Body</u></h6></a>";
            pretty += "<div id=\"annoPanelCollapseBody" + (i + 1) + "\" class=\"panel-collapse collapse wrap annoParts\">";
            // If this is not a dctype text
            if (currentAnno.body["@type"] !== "dctypes:Text") {
                pretty += "<b><i>type</i>:</b> <b><a class=\"annoPanelRef\" target=\"_blank\" href=\""
                        + currentAnno.body["@type"] + "\">" + description + "</a></b><br>";
                pretty += "<b><i>value</i>:</b> <b><a class=\"annoPanelRef\" target=\"_blank\" href=\""
                        + currentAnno.body["value"] + "\">" + modality + "</a></b><br>";
            } else {
                // Use the full dctype:Text uri
                pretty += "<b><i>type</i>:</b> <b><a class=\"annoPanelRef\"target=\"_blank\" href=\""
                        + "http://dublincore.org/documents/dcmi-terms/#dcmitype-Text" + "\">"
                        + "http://dublincore.org/documents/dcmi-terms/#dcmitype-Text" + "</a></b><br>";
                pretty += "<b><i>value</i>:</b> <b>\"" + currentAnno.body["value"] + "\"</b><br>";
                //http://dublincore.org/documents/dcmi-terms/#dcmitype-Text
                        // If text show also format and language
                        if (currentAnno.body["format"])
                    pretty += "<b><i>format</i>:</b> <b>" + currentAnno.body["format"] + "</b><br>";
                if (currentAnno.body["language"])
                    pretty += "<b><i>language</i>:</b> <b>" + currentAnno.body["language"] + "</b><br>";
            }
            pretty += "</div>";
            pretty += "</li>";

            // Target
            pretty += "<li class=\"anno-list-group-item\">";
            pretty += "<a href=\"#annoPanelCollapseTarget" + (i + 1) + "\" data-toggle=\"collapse\">";
            pretty += "<h6><u>Target</u></h6></a>";
            pretty += "<div id=\"annoPanelCollapseTarget" + (i + 1) + "\" class=\"panel-collapse collapse wrap annoParts\">";
            // Check the namespace
            // TODO: Have to find a nice way to do this by using the context of the jsonld
            // Currently we only have an umbelrc:Animal
            var targetURL = "";
            if (currentAnno.target["@type"].substring(0, "umbelrc".length)) {
                targetURL = "http://umbel.org/umbel/rc/" + currentAnno.target["@type"]
                        .substring(currentAnno.target["@type"].indexOf(":") + 1);
            }

            pretty += "<b><i>id</i>:</b> <b><a class=\"annoPanelRef\" target=\"_blank\" href=\""
                    + currentAnno.target["@id"] + "\">" + currentAnno.target["@id"] + "</a></b><br>";
            pretty += "<b><i>type</i>:</b> <b><a class=\"annoPanelRef\" target=\"_blank\" href=\""
                    + targetURL + "\">" + currentAnno.target["@type"] + "</a></b><br>";
            pretty += "</div>";
            pretty += "</li>";

            // MotivatedBy
            pretty += "<li class=\"anno-list-group-item\">";
            pretty += "<a href=\"#annoPanelCollapseMotivated" + (i + 1) + "\" data-toggle=\"collapse\">";
            pretty += "<h6><u>Motivated By</u></h6></a>";
            pretty += "<div id=\"annoPanelCollapseMotivated" + (i + 1) + "\" class=\"panel-collapse collapse wrap annoParts\">";
            // If motivations is oa:describing
            if (currentAnno.motivated.by === "oa:describing") {
                pretty += "<b><i>motivation</i>:</b> <b><a target=\"_blank\" href=\""
                        + "http://www.w3.org/ns/oa#describing" + "\">"
                        + "Describing" + "</a></b><br>";
            } else
                pretty += "<b><i>motivation</i>: " + currentAnno.motivated.by + "</b>";

            pretty += "</div>";
            pretty += "</li>";

            // Provenance
            pretty += "<li class=\"anno-list-group-item\">";
            pretty += "<a href=\"#annoPanelCollapseProv" + (i + 1) + "\" data-toggle=\"collapse\">";
            pretty += "<h6>Provenance</h6></a>";
            pretty += "<div id=\"annoPanelCollapseProv" + (i + 1) + "\" class=\"panel-collapse collapse wrap annoParts\">";
            pretty += "<b><i>annotated by</i>: " + currentAnno.annotatedBy.nick + "</b><br>";
            pretty += "<b><i>annotated at</i>: " + currentAnno.annotatedAt + "</b><br>";
            pretty += "<b><i>serialized by</i>: " + currentAnno.serializedBy.name + "</b><br>";
            pretty += "<b><i>serialized at</i>: " + currentAnno.serializedAt + "</b><br>";
            pretty += "</div>";
            pretty += "</li>";

            // JSON
            pretty += "<li class=\"anno-list-group-item\">";
            pretty += "<a href=\"#annoPanelCollapseJSON" + (i + 1) + "\" data-toggle=\"collapse\">";
            pretty += "<h6><u>JSON-LD representation</u></h6></a>";
            pretty += "<div id=\"annoPanelCollapseJSON" + (i + 1) + "\" class=\"panel-collapse collapse wrap annoParts\">";
            pretty += jsonldMarkup(parsedJSON[i], parsedJSON[i]["@context"]);
            pretty += "</div>";
            pretty += "</li>";

            // Add delete button
            pretty += "<button type=\"button\" class=\"btn btn-danger btn-block wrap\" onclick=\"anno.deleteAnnotation(\'"
                    + currentAnno.id + "\',\'" + targetURI + "\');\">Remove</button><br>";

            pretty += "</ul>";
            pretty += "</li>";
        }
        pretty += "</ul>";
        pretty += "</div>";

        return pretty;
    }

    /**
     * Read the jsonld specific bits of the annotations and fill in anno
     * @param {Object} jsonld annotation
     * @param {Object} anno
     * @returns {anno}
     */
    function readAnnotation(jsonld, anno) {

        // if this is an annotation object, get all relevant information
        // Have to be rather careful with the names!
        console.log(jsonld);
        if (jsonld["@type"] && jsonld["@type"] === "oa:Annotation") {
            anno.id = jsonld["@id"].substr(jsonld["@id"].lastIndexOf('/') + 1);    // id of anno
            anno.URI = jsonld["@id"];   // URI of anno
            anno.annotatedBy = jsonld["oa:annotatedBy"] || jsonld["annotatedBy"];
            anno.annotatedAt = jsonld["oa:annotatedAt"] || jsonld["annotatedAt"];
            anno.serializedBy = jsonld["oa:serializedBy"] || jsonld["serializedBy"];
            anno.serializedAt = jsonld["oa:serializedAt"] || jsonld["serializedAt"];
            anno.body = jsonld["oa:hasBody"] || jsonld["body"];
            anno.body.id = anno.body["@id"];
            anno.target = jsonld["oa:hasTarget"] || jsonld["target"];
            anno.target.id = anno.target["@id"];
            anno.motivated = jsonld["oa:motivatedBy"] || jsonld["motivation"];
            anno.motivated.by = anno.motivated["@id"];
        }
    }

    /**
     *
     * @param {type} message
     * @returns {undefined}
     */
    function showConsoleMessage(message) {
        document.getElementById("annoConsole").innerHTML =
                '<div id=\"consoleDiv\" class=\"annoConsole\"><h6><b>' + message + '</b></h6></div>';
        // Autohide after 3 secs
        $('#consoleDiv').delay(3000).fadeOut('xslow');
    }

    /**
     * Function that creates a JSON representation using the anno object
     * cretaed through the panel
     * @param {type} anno
     * @returns {undefined}
     */
    function createJSONLD(anno) {
        if (!checkAnnoObjectReady(anno))
            return null;

        var annoJSONLD = "{\n";
        // Object is ok. Now create the JSONLD representation
        // This is the context.
        annoJSONLD += "\"@context\": {\n"
                + "    \"oa\" :     \"http://www.w3.org/ns/oa#\",\n"
                + "    \"dc\" :     \"http://purl.org/dc/elements/1.1/\",\n"
                + "    \"dcterms\": \"http://purl.org/dc/terms/\",\n"
                + "    \"dctypes\": \"http://purl.org/dc/dcmitype/\",\n"
                + "    \"foaf\" :   \"http://xmlns.com/foaf/0.1/\",\n"
                + "    \"rdf\" :    \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\",\n"
                + "    \"rdfs\" :   \"http://www.w3.org/2000/01/rdf-schema#\",\n"
                + "    \"skos\" :   \"http://www.w3.org/2004/02/skos/core#\",\n"
                + "    \"umbelrc\": \"http://umbel.org/umbel/rc/\",\n"
                + "\n"
                + "    \"body\" :         {\"@id\" : \"oa:hasBody\"},\n"
                + "    \"target\" :       {\"@type\":\"@id\", \"@id\" : \"oa:hasTarget\"},\n"
                + "    \"source\" :       {\"@type\":\"@id\", \"@id\" : \"oa:hasSource\"},\n"
                + "    \"selector\" :     {\"@type\":\"@id\", \"@id\" : \"oa:hasSelector\"},\n"
                + "    \"state\" :        {\"@type\":\"@id\", \"@id\" : \"oa:hasState\"},\n"
                + "    \"scope\" :        {\"@type\":\"@id\", \"@id\" : \"oa:hasScope\"},\n"
                + "    \"annotatedBy\" :  {\"@type\":\"@id\", \"@id\" : \"oa:annotatedBy\"},\n"
                + "    \"serializedBy\" : {\"@type\":\"@id\", \"@id\" : \"oa:serializedBy\"},\n"
                + "    \"motivation\" :   {\"@type\":\"@id\", \"@id\" : \"oa:motivatedBy\"},\n"
                + "    \"stylesheet\" :   {\"@type\":\"@id\", \"@id\" : \"oa:styledBy\"},\n"
                + "    \"cached\" :       {\"@type\":\"@id\", \"@id\" : \"oa:cachedSource\"},\n"
                + "    \"conformsTo\" :   {\"@type\":\"@id\", \"@id\" : \"dcterms:conformsTo\"},\n"
                + "    \"members\" :      {\"@type\":\"@id\", \"@id\" : \"oa:membershipList\", \"@container\": \"@list\"},\n"
                + "    \"item\" :         {\"@type\":\"@id\", \"@id\" : \"oa:item\"},\n"
                + "    \"related\" :      {\"@type\":\"@id\", \"@id\" : \"skos:related\"},\n"
                + "\n"
                + "    \"format\" :       \"dc:format\",\n"
                + "    \"language\":      \"dc:language\",\n"
                + "    \"annotatedAt\" :  \"oa:annotatedAt\",\n"
                + "    \"serializedAt\" : \"oa:serializedAt\",\n"
                + "    \"when\" :         \"oa:when\",\n"
                + "    \"value\" :        \"rdf:value\",\n"
                + "    \"start\" :        \"oa:start\",\n"
                + "    \"end\" :          \"oa:end\",\n"
                + "    \"exact\" :        \"oa:exact\",\n"
                + "    \"prefix\" :       \"oa:prefix\",\n"
                + "    \"suffix\" :       \"oa:suffix\",\n"
                + "    \"label\" :        \"rdfs:label\",\n"
                + "    \"name\" :         \"foaf:name\",\n"
                + "    \"mbox\" :         \"foaf:mbox\",\n"
                + "    \"nick\" :         \"foaf:nick\",\n"
                + "    \"styleClass\" :   \"oa:styleClass\"\n"
                + "  },\n";

        // Get date
        var date = new Date();

        ///Now create the body, target, motivation and provenance part of the annotation
        // Ids except for targers, are going to be created by the service.
        // Even if the client send its own our service is responsible for
        // the annotations and the bodies
        annoJSONLD += " \"@type\":\"oa:Annotation\", \n"
                // Add the body. Different kind of bodies
                + " \"body\": { \n";
        // If this is a POLYTRAIT
        if (anno.type === 'POLYTRAITS') {
            annoJSONLD += "   \"@type\":\"" + anno.body.type + "\", \n"
                    + "   \"value\":\"" + anno.body.value + "\" \n";
        } else if (anno.type === 'TEXT') {
            // Else if this is a dctype:text
            annoJSONLD += "   \"@type\":\"" + anno.body.type + "\", \n"
                    + "   \"value\":\"" + anno.body.value + "\", \n"
                    + "   \"language\":\"" + anno.body.language + "\", \n"
                    + "   \"format\":\"" + anno.body.format + "\" \n";
        }
        annoJSONLD += " }, \n"
                // Add the target
                + " \"target\": { \n"
                + "   \"@id\": \"" + anno.target.id + "\", \n"
                + "   \"@type\": \"" + anno.target.type + "\" \n"
                + " },\n"
                // Add motivation
                + " \"oa:motivatedBy\":\"" + anno.motivated.type + "\",\n"
                // Add motivation
                + " \"oa:annotatedAt\":\"" + date.toISOString() + "\"\n"
                + "}";

        // Now return the jsonld string
        return annoJSONLD;
    }

    /**
     * A Map that holds the ids of the supported ontologies and their descriptions
     * @type type
     */
    var polytraitsMap = [{
            type: "http://polytraits.lifewatchgreece.eu/terms/MAT",
            desc: "Age at first reproduction",
            modalities: {
                // 7 modalities
                "http://polytraits.lifewatchgreece.eu/terms/MAT_2M": "&le; 2 months",
                "http://polytraits.lifewatchgreece.eu/terms/MAT_6M": "2 - 6 months",
                "http://polytraits.lifewatchgreece.eu/terms/MAT_1Y": "6 months - 1 year",
                "http://polytraits.lifewatchgreece.eu/terms/MAT_2Y": "1 year - 2 years",
                "http://polytraits.lifewatchgreece.eu/terms/MAT_3Y": "2 year - 3 years",
                "http://polytraits.lifewatchgreece.eu/terms/MAT_4Y": "&ge; 4 years"
            }
        }, {
            type: "http://purl.obolibrary.org/obo/CMO_0000013",
            desc: "Body size (max)",
            modalities: {
                // 7 modalities
                "http://polytraits.lifewatchgreece.eu/terms/BS_1": "&lt; 2.5mm",
                "http://polytraits.lifewatchgreece.eu/terms/BS_2": "2.5mm - 10mm",
                "http://polytraits.lifewatchgreece.eu/terms/BS_3": "11mm - 20mm",
                "http://polytraits.lifewatchgreece.eu/terms/BS_4": "21mm - 50mm",
                "http://polytraits.lifewatchgreece.eu/terms/BS_5": "51mm - 80mm",
                "http://polytraits.lifewatchgreece.eu/terms/BS_6": "81mm - 100mm",
                "http://polytraits.lifewatchgreece.eu/terms/BS_7": "&gt; 100mm"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/CPLX",
            desc: "Complex species",
            modalities: {
                // 2 modalities
                "http://polytraits.lifewatchgreece.eu/terms/CPLX_YES": "yes",
                "http://polytraits.lifewatchgreece.eu/terms/CPLX_NO": "no"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/DEV",
            desc: "Developmental mechanism",
            modalities: {
                // 2 modalities
                "http://purl.bioontology.org/ontology/MESH/D052287": "oviparus",
                "http://purl.bioontology.org/ontology/MESH/D052286": "viviparus"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/DZ",
            desc: "Depth zonation (benthos)",
            modalities: {
                // 6 modalities
                "http://polytraits.lifewatchgreece.eu/terms/DZ_SUP": "supralittoral zone",
                "http://purl.obolibrary.org/obo/ENVO_00000316": "littoral zone",
                "http://polytraits.lifewatchgreece.eu/terms/DZ_SUB": "sublittoral zone",
                "http://polytraits.lifewatchgreece.eu/terms/DZ_BAT": "bathyal zone",
                "http://polytraits.lifewatchgreece.eu/terms/DZ_ABY": "abyssal zone",
                "http://purl.obolibrary.org/obo/ENVO_01000028": "hadal zone"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/DZP",
            desc: "Depth zonation (pelagic)",
            modalities: {
                // 5 modalities
                "http://purl.obolibrary.org/obo/ENVO_00000209": "epipelagic",
                "http://polytraits.lifewatchgreece.eu/terms/DZP_MESO": "mesopelagic",
                "http://polytraits.lifewatchgreece.eu/terms/DZP_BATH": "bathypelagic ",
                "http://purl.obolibrary.org/obo/ENVO_00000212": "abyssopelagic",
                "http://purl.obolibrary.org/obo/ENVO_00000214": "hadalpelagic"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/EGG",
            desc: "Egg size",
            modalities: {
                // 3 modalities
                "http://polytraits.lifewatchgreece.eu/terms/EGG_S": "0&#956;m -100&#956;m",
                "http://polytraits.lifewatchgreece.eu/terms/EGG_M": "100&#956;m -200&#956;m",
                "http://polytraits.lifewatchgreece.eu/terms/EGG_L": "&gt;200&#956;m"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/RW",
            desc: "Ecosystem engineering",
            modalities: {
                // 9 modalities
                "http://polytraits.lifewatchgreece.eu/terms/RW_YES": "yes",
                "http://polytraits.lifewatchgreece.eu/terms/RW_NO": "no",
                "http://polytraits.lifewatchgreece.eu/terms/RW_DIFF": "biodiffusor",
                "http://polytraits.lifewatchgreece.eu/terms/RW_UC": "upward conveyor",
                "http://polytraits.lifewatchgreece.eu/terms/RW_DC": "downward conveyour",
                "http://polytraits.lifewatchgreece.eu/terms/RW_REG": "regenerator",
                "http://polytraits.lifewatchgreece.eu/terms/RW_BEV": "blind-ended ventilation",
                "http://polytraits.lifewatchgreece.eu/terms/RW_OEV": "open-ended ventilation",
                "http://polytraits.lifewatchgreece.eu/terms/RW_HAB": "habitat-building"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/EP",
            desc: "Environmental position",
            modalities: {
                // 12 modalities
                "http://polytraits.lifewatchgreece.eu/terms/EP_EPIB": "Epibenthic",
                "http://polytraits.lifewatchgreece.eu/terms/EP_ENDOB": "Endobenthic",
                "http://polytraits.lifewatchgreece.eu/terms/EP_IST": "Interstitial",
                "http://polytraits.lifewatchgreece.eu/terms/EP_HYP": "Hyperbenthic",
                "http://purl.obolibrary.org/obo/ENVO_01000023": "Pelagic",
                "http://polytraits.lifewatchgreece.eu/terms/EP_EPIL": "Epilithic",
                "http://polytraits.lifewatchgreece.eu/terms/EP_EL": "Endolithic",
                "http://polytraits.lifewatchgreece.eu/terms/EP_LITH": "Lithotomous",
                "http://polytraits.lifewatchgreece.eu/terms/EP_BB": "Boring in biogenic substrate",
                "http://polytraits.lifewatchgreece.eu/terms/EP_EPIZ": "Epizoic",
                "http://polytraits.lifewatchgreece.eu/terms/EP_EPIP": "Epiphytic",
                "http://polytraits.lifewatchgreece.eu/terms/EP_PAR": "Parasitic"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/EPKY",
            desc: "Epitoky",
            modalities: {
                // 2 modalities
                "http://polytraits.lifewatchgreece.eu/terms/EPKY_YES": "yes",
                "http://polytraits.lifewatchgreece.eu/terms/EPKY_NO": "no"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/FAC",
            desc: "Factors triggering reproduction",
            modalities: {
                // 5 modalities
                "http://polytraits.lifewatchgreece.eu/terms/FAC_LUN": "lunar cycle",
                "http://polytraits.lifewatchgreece.eu/terms/FAC_HOR": "pheromones / hormones",
                "http://polytraits.lifewatchgreece.eu/terms/FAC_PHO": "photoperiod",
                "http://polytraits.lifewatchgreece.eu/terms/FAC_TMP": "temperature",
                "http://polytraits.lifewatchgreece.eu/terms/FAC_SAL": "salinity"
            }
        }, {
            type: "http://purl.obolibrary.org/obo/PATO_0000273",
            desc: "Fecundity",
            modalities: {
                // 7 modalities
                "http://polytraits.lifewatchgreece.eu/terms/FEC_50": "1 - 50",
                "http://polytraits.lifewatchgreece.eu/terms/FEC_500": "50 - 500",
                "http://polytraits.lifewatchgreece.eu/terms/FEC_2500": "500 - 2500",
                "http://polytraits.lifewatchgreece.eu/terms/FEC_10000": "2500 - 10000",
                "http://polytraits.lifewatchgreece.eu/terms/FEC_20000": "10000 - 20000",
                "http://polytraits.lifewatchgreece.eu/terms/FEC_100000": "20000 - 100000",
                "http://polytraits.lifewatchgreece.eu/terms/FEC_100000+": "&gt;100000"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/STRUCT",
            desc: "Feeding structure",
            modalities: {
                // 6 modalities
                "http://polytraits.lifewatchgreece.eu/terms/STRUCT_SAP": "simple axial pharynx",
                "http://polytraits.lifewatchgreece.eu/terms/STRUCT_VBO": "ventral buccal organ (simple)",
                "http://polytraits.lifewatchgreece.eu/terms/STRUCT_VMP": "ventral muscular pharynx",
                "http://polytraits.lifewatchgreece.eu/terms/STRUCT_MAP": "muscular axial pharynx",
                "http://polytraits.lifewatchgreece.eu/terms/STRUCT_ABS": "buccal organ absent or occluded",
                "http://polytraits.lifewatchgreece.eu/terms/STRUCT_ACC": "accessory feeding structures",
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/FEED",
            desc: "Feeding type",
            modalities: {
                // 8 modalities
                "http://eol.org/schema/terms/predator": "predator",
                "http://eol.org/schema/terms/suspensionFeeder": "predator",
                "http://polytraits.lifewatchgreece.eu/terms/FEED_NSD": "non-selective deposit feeder",
                "http://polytraits.lifewatchgreece.eu/terms/FEED_SD": "selective deposit feeder",
                "http://polytraits.lifewatchgreece.eu/terms/FEED_D": "deposit feeder (selective or non selective)",
                "http://polytraits.lifewatchgreece.eu/terms/FEED_O": "omnivore",
                "http://polytraits.lifewatchgreece.eu/terms/FEED_S": "scavenger",
                "http://purl.bioontology.org/ontology/MESH/D060434": "herbivore"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/FER",
            desc: "Fertilization type",
            modalities: {
                // 3 modalities
                "http://polytraits.lifewatchgreece.eu/terms/FER_INT": "internal",
                "http://polytraits.lifewatchgreece.eu/terms/FER_EXT": "external (broadcast spawner)",
                "http://polytraits.lifewatchgreece.eu/terms/FER_PSEU": "external (pseudocopulation)"
            }
        }, {
            type: "http://rs.tdwg.org/dwc/terms/habitat",
            desc: "Habitat type",
            modalities: {
                // 14 modalities
                "http://polytraits.lifewatchgreece.eu/terms/HAB_ALG": "algae",
                "http://polytraits.lifewatchgreece.eu/terms/HAB_REEF": "biogenic reef",
                "http://polytraits.lifewatchgreece.eu/terms/HAB_CAVE": "caves",
                "http://polytraits.lifewatchgreece.eu/terms/HAB_CREV": "crevices/ fissures",
                "http://polytraits.lifewatchgreece.eu/terms/HAB_MAERL": "maerl/ coralligenous habitats",
                "http://purl.obolibrary.org/obo/ENVO_00002032": "other species",
                "http://polytraits.lifewatchgreece.eu/terms/HAB_OH": "overhangs",
                "http://purl.obolibrary.org/obo/ENVO_00000317": "rockpools",
                "http://purl.obolibrary.org/obo/ENVO_00000054": "salt marsh",
                "http://purl.obolibrary.org/obo/ENVO_01000059": "seagrass",
                "http://polytraits.lifewatchgreece.eu/terms/HAB_STRAND": "strandline",
                "http://polytraits.lifewatchgreece.eu/terms/HAB_UB": "under boulders",
                "http://purl.obolibrary.org/obo/ENVO_01000023": "water column",
                "http://purl.obolibrary.org/obo/ENVO_00002007": "soft sediments"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/HSET",
            desc: "Habitat type of settlement / early development",
            modalities: {
                // 14 modalities
                "http://polytraits.lifewatchgreece.eu/terms/HAB_ALG": "algae",
                "http://polytraits.lifewatchgreece.eu/terms/HAB_REEF": "biogenic reef",
                "http://polytraits.lifewatchgreece.eu/terms/HAB_CAVE": "caves",
                "http://polytraits.lifewatchgreece.eu/terms/HAB_CREV": "crevices/ fissures",
                "http://polytraits.lifewatchgreece.eu/terms/HAB_MAERL": "maerl/ coralligenous habitats",
                "http://purl.obolibrary.org/obo/ENVO_00002032": "other species",
                "http://polytraits.lifewatchgreece.eu/terms/HAB_OH": "overhangs",
                "http://purl.obolibrary.org/obo/ENVO_00000317": "rockpools",
                "http://purl.obolibrary.org/obo/ENVO_00000054": "salt marsh",
                "http://purl.obolibrary.org/obo/ENVO_01000059": "seagrass",
                "http://polytraits.lifewatchgreece.eu/terms/HAB_STRAND": "strandline",
                "http://polytraits.lifewatchgreece.eu/terms/HAB_UB": "under boulders",
                "http://purl.obolibrary.org/obo/ENVO_01000023": "water column",
                "http://purl.obolibrary.org/obo/ENVO_00002007": "soft sediments"
            }

        }, {
            type: "http://purl.obolibrary.org/obo/GO_0044402",
            desc: "Intra- and interspecific competition",
            modalities: {
                // 5 modalities
                "http://polytraits.lifewatchgreece.eu/terms/COMP_AA": "annelida (adults)",
                "http://polytraits.lifewatchgreece.eu/terms/COMP_CA": "crustacea (adults)",
                "http://polytraits.lifewatchgreece.eu/terms/COMP_AL": "annelida (larvae)",
                "http://polytraits.lifewatchgreece.eu/terms/COMP_CL": "crustacea (larvae)",
                "http://polytraits.lifewatchgreece.eu/terms/COMP_MOLL": "mollusca"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/JMOB",
            desc: "Juvenile mobility",
            modalities: {
                // 4 modalities
                "http://polytraits.lifewatchgreece.eu/terms/MOB_CRAWL": "crawler",
                "http://polytraits.lifewatchgreece.eu/terms/MOB_BUR": "burrower",
                "http://polytraits.lifewatchgreece.eu/terms/MOB_SWIM": "swimmer",
                "http://polytraits.lifewatchgreece.eu/terms/MOB_SESS": "non-motile / semi-motile"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/LDEV",
            desc: "Larval development",
            modalities: {
                // 2 modalities
                "http://eol.org/schema/terms/directDeveloper": "direct development",
                "http://polytraits.lifewatchgreece.eu/terms/LDEV_I": "indirect development"
            }
        }, {
            type: "http://eol.org/schema/terms/MarineLarvalDevelopmentStrategy",
            desc: "Larval feeding type",
            modalities: {
                // 2 modalities
                "http://eol.org/schema/terms/planktotrophic": "planktotrophic",
                "http://polytraits.lifewatchgreece.eu/terms/LFT_M": "maternally derived nutrition"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/LM",
            desc: "Larval mode of development",
            modalities: {
                // 2 modalities
                "http://polytraits.lifewatchgreece.eu/terms/LM_B": "benthic",
                "http://polytraits.lifewatchgreece.eu/terms/LM_P": "pelagic"
            }
        }, {
            type: "http://purl.obolibrary.org/obo/PATO_0000050",
            desc: "Lifespan",
            modalities: {
                // 5 modalities
                "http://polytraits.lifewatchgreece.eu/terms/LIFE_1": "&le; 1 year",
                "http://polytraits.lifewatchgreece.eu/terms/LIFE_1-3": "1 - 3 years",
                "http://polytraits.lifewatchgreece.eu/terms/LIFE_3-5": "3 - 5 years",
                "http://polytraits.lifewatchgreece.eu/terms/LIFE_5+": "&ge; 5 years"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/PC",
            desc: "Location of parental care",
            modalities: {
                // 2 modalities
                "http://polytraits.lifewatchgreece.eu/terms/PC_FAR": "outside microenvironment of the parent",
                "http://polytraits.lifewatchgreece.eu/terms/PC_NEAR": "within microenvironment of the parent"
            }
        }, {
            type: "http://purl.obolibrary.org/obo/GO_0007552",
            desc: "Metamorphosis type",
            modalities: {
                // 2 modalities
                "http://polytraits.lifewatchgreece.eu/terms/MV_CAT": "catastrophic",
                "http://polytraits.lifewatchgreece.eu/terms/MV_NCAT": "non-catastrophic"
            }
        }, {
            type: "http://purl.obolibrary.org/obo/IDOMAL_0002084",
            desc: "Migrations of adult",
            modalities: {
                // 2 modalities
                "http://polytraits.lifewatchgreece.eu/terms/MIGR_YES": "yes",
                "http://polytraits.lifewatchgreece.eu/terms/MIGR_NO": "no"
            }
        }, {
            type: "http://purl.obolibrary.org/obo/GO_0040011",
            desc: "Mobility of adult",
            modalities: {
                // 4 modalities
                "http://polytraits.lifewatchgreece.eu/terms/MOB_CRAWL": "crawler",
                "http://polytraits.lifewatchgreece.eu/terms/MOB_BUR": "burrower",
                "http://polytraits.lifewatchgreece.eu/terms/MOB_SWIM": "swimmer",
                "http://polytraits.lifewatchgreece.eu/terms/MOB_SESS": "non-motile / semi-motile"
            }
        }, {
            type: "http://purl.obolibrary.org/obo/GO_0000003",
            desc: "Mode of reproduction",
            modalities: {
                // 4 modalities
                "http://purl.obolibrary.org/obo/HAO_0000048": "gonochoristic",
                "http://purl.obolibrary.org/obo/HAO_0000046": "simultaneous hermaphrodite",
                "http://purl.obolibrary.org/obo/HAO_0000045": "sequential hermaphrodite",
                "http://purl.obolibrary.org/obo/GO_0019954": "asexual reproduction"
            }
        }, {
            type: "http://purl.obolibrary.org/obo/GO_0060746",
            desc: "Parental care / Brood protection",
            modalities: {
                // 2 modalities
                "http://polytraits.lifewatchgreece.eu/terms/BP_YES": "yes",
                "http://polytraits.lifewatchgreece.eu/terms/BP_NO": "no"
            }
        }, {
            type: "http://purl.bioontology.org/ontology/CSP/1138-4873",
            desc: "Pattern of oogenesis",
            modalities: {
                // 2 modalities
                "http://polytraits.lifewatchgreece.eu/terms/OOG_INTRA": "intraovarian",
                "http://polytraits.lifewatchgreece.eu/terms/OOG_EXTRA": "extraovarian"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/PHF",
            desc: "Physiographic feature",
            modalities: {
                // 9 modalities
                "http://polytraits.lifewatchgreece.eu/terms/PHF_COAST": "open coast",
                "http://polytraits.lifewatchgreece.eu/terms/PHF_OFF": "offshore seabed",
                "http://purl.obolibrary.org/obo/ENVO_00000394": "strait",
                "http://purl.obolibrary.org/obo/ENVO_00000039": "fjord",
                "http://purl.obolibrary.org/obo/ENVO_00000418": "ria",
                "http://purl.obolibrary.org/obo/ENVO_00000045": "estuary",
                "http://purl.obolibrary.org/obo/ENVO_00000032": "enclosed coast / embayment",
                "http://polytraits.lifewatchgreece.eu/terms/PHF_LAG": "lagoon",
                "http://purl.obolibrary.org/obo/ENVO_01000122": "Hydrothermal vents"
            }

        }, {
            type: "http://www.ebi.ac.uk/efo/EFO_0004820",
            desc: "Population sex ratio",
            modalities: {
                // 3 modalities
                "http://polytraits.lifewatchgreece.eu/terms/PSR_EQ": "1:1",
                "http://polytraits.lifewatchgreece.eu/terms/PSR_F": "female > male",
                "http://polytraits.lifewatchgreece.eu/terms/PSR_M": "male > female"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/PRED",
            desc: "Predated by",
            modalities: {
                // 6 modalities
                "http://polytraits.lifewatchgreece.eu/terms/PRED_ANN": "annelids",
                "http://polytraits.lifewatchgreece.eu/terms/PRED_CRUS": "crustaceans",
                "http://polytraits.lifewatchgreece.eu/terms/PRED_FISH": "fish",
                "http://polytraits.lifewatchgreece.eu/terms/PRED_BIRD": "birds",
                "http://polytraits.lifewatchgreece.eu/terms/PRED_MOLL": "mollusks",
                "http://polytraits.lifewatchgreece.eu/terms/PRED_ECHI": "echinoderms"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/STRAT",
            desc: "Reproduction strategy of the individual",
            modalities: {
                // 2 modalities
                "http://polytraits.lifewatchgreece.eu/terms/STRAT_ITER": "iteroparous",
                "http://polytraits.lifewatchgreece.eu/terms/STRAT_SEM": "semelparous"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/RT",
            desc: "Reproduction temperature",
            modalities: {
                // 3 modalities
                "http://polytraits.lifewatchgreece.eu/terms/RT_COLD": "cold waters",
                "http://polytraits.lifewatchgreece.eu/terms/RT_TEMP": "warm/ temperate/ subtropical waters",
                "http://polytraits.lifewatchgreece.eu/terms/RT_WARM": "tropical waters"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/RESORP",
            desc: "Resorption of eggs",
            modalities: {
                // 2 modalities
                "http://polytraits.lifewatchgreece.eu/terms/RESORP_YES": "yes",
                "http://polytraits.lifewatchgreece.eu/terms/RESORP_NO": "no"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/SM",
            desc: "Sexual metamorphosis",
            modalities: {
                // 2 modalities
                "http://polytraits.lifewatchgreece.eu/terms/SM_YES": "yes",
                "http://polytraits.lifewatchgreece.eu/terms/SM_NO": "no"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/FREQ",
            desc: "Spawning frequency of the population",
            modalities: {
                // 3 modalities
                "http://polytraits.lifewatchgreece.eu/terms/FREQ_MULTI": "multiple events / year",
                "http://polytraits.lifewatchgreece.eu/terms/FREQ_CONT": "continuous or semi-continuous",
                "http://polytraits.lifewatchgreece.eu/terms/FREQ_ANNU": "annually / seasonally"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/SPERM",
            desc: "Sperm type",
            modalities: {
                // 3 modalities
                "http://polytraits.lifewatchgreece.eu/terms/SETTL_HARD": "hard substrates",
                "http://polytraits.lifewatchgreece.eu/terms/SPERM_ENT": "ent - aquasperm",
                "http://polytraits.lifewatchgreece.eu/terms/SPERM_INTRO": "introsperm"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/SETTL",
            desc: "Substrate type of settlement",
            modalities: {
                // 10 modalities
                "http://polytraits.lifewatchgreece.eu/terms/SETTL_HARD": "hard substrates",
                "http://polytraits.lifewatchgreece.eu/terms/SETTL_SAND": "sand",
                "http://polytraits.lifewatchgreece.eu/terms/MUD": "mud",
                "http://purl.obolibrary.org/obo/ENVO_01000120": "clay",
                "http://purl.obolibrary.org/obo/ENVO_01000119": "silt",
                "http://purl.obolibrary.org/obo/ENVO_01000018": "gravel",
                "http://purl.obolibrary.org/obo/ENVO_01000116": "pebbles",
                "http://purl.obolibrary.org/obo/ENVO_01000115": "cobbles",
                "http://purl.obolibrary.org/obo/ENVO_01000114": "boulders",
                "http://purl.obolibrary.org/obo/ENVO_00002034": "bacterial/ organic biofilm"}
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/SYNC",
            desc: "Synchronization of spawning",
            modalities: {
                // 2 modalities
                "http://polytraits.lifewatchgreece.eu/terms/SYNC_YES": "yes",
                "http://polytraits.lifewatchgreece.eu/terms/SYNC_NO": "no"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/SOC",
            desc: "Sociability",
            modalities: {
                // 14 modalities
                "http://polytraits.lifewatchgreece.eu/terms/SOC_ALG": "algae",
                "http://polytraits.lifewatchgreece.eu/terms/SOC_PHAN": "seagrasses",
                "http://polytraits.lifewatchgreece.eu/terms/SOC_ANN": "annelids",
                "http://polytraits.lifewatchgreece.eu/terms/SOC_BACT": "bacteria",
                "http://polytraits.lifewatchgreece.eu/terms/SOC_CRUS": "crustaceans",
                "http://polytraits.lifewatchgreece.eu/terms/SOC_FISH": "fish",
                "http://polytraits.lifewatchgreece.eu/terms/SOC_MOLL": "mollusks",
                "http://polytraits.lifewatchgreece.eu/terms/SOC_NEM": "nematodes",
                "http://polytraits.lifewatchgreece.eu/terms/SOC_BRAN": "branchiostomids",
                "http://polytraits.lifewatchgreece.eu/terms/SOC_ECHI": "echinoderms",
                "http://polytraits.lifewatchgreece.eu/terms/SOC_ANTH": "cnidarians",
                "http://polytraits.lifewatchgreece.eu/terms/SOC_POR": "poriferans",
                "http://polytraits.lifewatchgreece.eu/terms/SOC_BRY": "bryozoans",
                "http://polytraits.lifewatchgreece.eu/terms/SOC_ENT": "entoproctans"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/SUBST",
            desc: "Substrate type",
            modalities: {
                // 22 modalities
                "http://polytraits.lifewatchgreece.eu/terms/SUBST_ROCK": "bedrock",
                "http://polytraits.lifewatchgreece.eu/terms/SUBST_LB": "large to very large boulders",
                "http://polytraits.lifewatchgreece.eu/terms/SUBST_SB": "small boulders",
                "http://purl.obolibrary.org/obo/ENVO_01000115": "cobbles",
                "http://purl.obolibrary.org/obo/ENVO_01000116": "pebbles",
                "http://purl.obolibrary.org/obo/ENVO_01000018": "gravel",
                "http://polytraits.lifewatchgreece.eu/terms/SUBST_SG": "sandy gravel",
                "http://polytraits.lifewatchgreece.eu/terms/SUBST_MG": "muddy gravel",
                "http://polytraits.lifewatchgreece.eu/terms/SUBST_MSG": "muddy sandy gravel",
                "http://polytraits.lifewatchgreece.eu/terms/SUBST_CS": "coarse clean sand",
                "http://polytraits.lifewatchgreece.eu/terms/SUBST_FS": "fine clean sand",
                "http://polytraits.lifewatchgreece.eu/terms/SUBST_GS": "gravelly sand",
                "http://polytraits.lifewatchgreece.eu/terms/SUBST_MGS": "muddy gravelly sand",
                "http://polytraits.lifewatchgreece.eu/terms/SUBST_MS": "muddy sand",
                "http://polytraits.lifewatchgreece.eu/terms/SUBST_SM": "sandy mud",
                "http://polytraits.lifewatchgreece.eu/terms/SUBST_SGM": "sandy gravelly mud",
                "http://polytraits.lifewatchgreece.eu/terms/SUBST_GM": "gravelly mud",
                "http://polytraits.lifewatchgreece.eu/terms/MUD": "mud",
                "http://purl.obolibrary.org/obo/ENVO_01000119": "silt",
                "http://purl.obolibrary.org/obo/ENVO_01000120": "clay",
                "http://polytraits.lifewatchgreece.eu/terms/SUBST_MIX": "mixed",
                "http://polytraits.lifewatchgreece.eu/terms/SUBST_ART": "artificial"
            }
        }, {
            type: "http://purl.bioontology.org/ontology/MSH/D054712",
            desc: "Survival salinity",
            modalities: {
                // 4 modalities
                "http://polytraits.lifewatchgreece.eu/terms/SAL_FULL": "full salinity",
                "http://polytraits.lifewatchgreece.eu/terms/SAL_VAR": "variable salinity",
                "http://polytraits.lifewatchgreece.eu/terms/SAL_RCD": "reduced salinity",
                "http://polytraits.lifewatchgreece.eu/terms/SAL_LOW": "low salinity"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/TEMP",
            desc: "Survival temperature",
            modalities: {
                // 3 modalities
                "http://polytraits.lifewatchgreece.eu/terms/TEMP_COLD": "cold waters",
                "http://polytraits.lifewatchgreece.eu/terms/TEMP_WARM": "warm/ temperate/ subtropical waters",
                "http://polytraits.lifewatchgreece.eu/terms/TEMP_HOT": "tropical waters"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/TOL",
            desc: "Tolerance (AMBI index)",
            modalities: {
                // 5 modalities
                "http://polytraits.lifewatchgreece.eu/terms/TOL_I": "group I",
                "http://polytraits.lifewatchgreece.eu/terms/TOL_II": "group II",
                "http://polytraits.lifewatchgreece.eu/terms/TOL_III": "group III",
                "http://polytraits.lifewatchgreece.eu/terms/TOL_IV": "group IV",
                "http://polytraits.lifewatchgreece.eu/terms/TOL_V": "group V"
            }
        }, {
            type: "http://polytraits.lifewatchgreece.eu/terms/TUBE",
            desc: "Tube / burrow material",
            modalities: {
                // 7 modalities
                "http://purl.obolibrary.org/obo/ENVO_01000120": "clay",
                "http://purl.obolibrary.org/obo/ENVO_01000018": "gravel",
                "http://purl.obolibrary.org/obo/ENVO_01000017": "sand",
                "http://polytraits.lifewatchgreece.eu/terms/TUBE_BIO": "biogenic detritus",
                "http://purl.obolibrary.org/obo/ENVO_02000040": "secretions",
                "http://polytraits.lifewatchgreece.eu/terms/TUBE_CALC": "calcium carbonate",
                "http://polytraits.lifewatchgreece.eu/terms/MUD": "mud"
            }
        }, {
            type: "http://eol.org/schema/terms/preysUpon",
            desc: "Typically feeds on",
            modalities: {
                // 15 modalities
                "http://polytraits.lifewatchgreece.eu/terms/TF_ALG": "algae",
                "http://polytraits.lifewatchgreece.eu/terms/TF_BACT": "bacteria",
                "http://polytraits.lifewatchgreece.eu/terms/TF_ANN": "annelids",
                "http://polytraits.lifewatchgreece.eu/terms/TF_CIL": "ciliates",
                "http://polytraits.lifewatchgreece.eu/terms/TF_CRUS": "crustaceans",
                "http://polytraits.lifewatchgreece.eu/terms/TF_DIAT": "diatoms",
                "http://polytraits.lifewatchgreece.eu/terms/TF_FLAG": "flagellates",
                "http://polytraits.lifewatchgreece.eu/terms/TF_FORAM": "foraminiferans",
                "http://polytraits.lifewatchgreece.eu/terms/TF_MOLL": "mollusks",
                "http://polytraits.lifewatchgreece.eu/terms/TF_OM": "detritus",
                "http://polytraits.lifewatchgreece.eu/terms/TF_SED": "sediment",
                "http://polytraits.lifewatchgreece.eu/terms/TF_FISH": "fish",
                "http://polytraits.lifewatchgreece.eu/terms/TF_ASC": "ascidians",
                "http://polytraits.lifewatchgreece.eu/terms/TF_ECHI": "echinoderms",
                "http://polytraits.lifewatchgreece.eu/terms/TF_CNID": "cnidarians"
            }
        }
    ];


// Following code from https://gist.github.com/mirontoli/4722797

    /**
     * @author Anatoly Mironov (mirontoli)
     * http://sharepointkunskap.wordpress.com
     * http://www.bool.se
     *
     * http://stackoverflow.com/questions/3605495/generate-a-list-of-localized-language-names-with-links-to-google-translate/14800384#14800384
     * http://stackoverflow.com/questions/10997128/language-name-from-iso-639-1-code-in-javascript/14800499#14800499
     *
     * using Phil Teare's answer on stackoverflow
     * http://stackoverflow.com/questions/3217492/list-of-language-codes-in-yaml-or-json/4900304#4900304
     * Just for testing only. Incorporate in your own javascript namespace
     * Example: getLanguageName("cv-RU") --> Chuvash
     */
    /**
     * @author Phil Teare
     * using wikipedia data
     */
    var isoLangs = {
        "ab": {
            "name": "Abkhaz",
            "nativeName": "аҧсуа"
        },
        "aa": {
            "name": "Afar",
            "nativeName": "Afaraf"
        },
        "af": {
            "name": "Afrikaans",
            "nativeName": "Afrikaans"
        },
        "ak": {
            "name": "Akan",
            "nativeName": "Akan"
        },
        "sq": {
            "name": "Albanian",
            "nativeName": "Shqip"
        },
        "am": {
            "name": "Amharic",
            "nativeName": "አማርኛ"
        },
        "ar": {
            "name": "Arabic",
            "nativeName": "العربية"
        },
        "an": {
            "name": "Aragonese",
            "nativeName": "Aragonés"
        },
        "hy": {
            "name": "Armenian",
            "nativeName": "Հայերեն"
        },
        "as": {
            "name": "Assamese",
            "nativeName": "অসমীয়া"
        },
        "av": {
            "name": "Avaric",
            "nativeName": "авар мацӀ, магӀарул мацӀ"
        },
        "ae": {
            "name": "Avestan",
            "nativeName": "avesta"
        },
        "ay": {
            "name": "Aymara",
            "nativeName": "aymar aru"
        },
        "az": {
            "name": "Azerbaijani",
            "nativeName": "azərbaycan dili"
        },
        "bm": {
            "name": "Bambara",
            "nativeName": "bamanankan"
        },
        "ba": {
            "name": "Bashkir",
            "nativeName": "башҡорт теле"
        },
        "eu": {
            "name": "Basque",
            "nativeName": "euskara, euskera"
        },
        "be": {
            "name": "Belarusian",
            "nativeName": "Беларуская"
        },
        "bn": {
            "name": "Bengali",
            "nativeName": "বাংলা"
        },
        "bh": {
            "name": "Bihari",
            "nativeName": "भोजपुरी"
        },
        "bi": {
            "name": "Bislama",
            "nativeName": "Bislama"
        },
        "bs": {
            "name": "Bosnian",
            "nativeName": "bosanski jezik"
        },
        "br": {
            "name": "Breton",
            "nativeName": "brezhoneg"
        },
        "bg": {
            "name": "Bulgarian",
            "nativeName": "български език"
        },
        "my": {
            "name": "Burmese",
            "nativeName": "ဗမာစာ"
        },
        "ca": {
            "name": "Catalan; Valencian",
            "nativeName": "Català"
        },
        "ch": {
            "name": "Chamorro",
            "nativeName": "Chamoru"
        },
        "ce": {
            "name": "Chechen",
            "nativeName": "нохчийн мотт"
        },
        "ny": {
            "name": "Chichewa; Chewa; Nyanja",
            "nativeName": "chiCheŵa, chinyanja"
        },
        "zh": {
            "name": "Chinese",
            "nativeName": "中文 (Zhōngwén), 汉语, 漢語"
        },
        "cv": {
            "name": "Chuvash",
            "nativeName": "чӑваш чӗлхи"
        },
        "kw": {
            "name": "Cornish",
            "nativeName": "Kernewek"
        },
        "co": {
            "name": "Corsican",
            "nativeName": "corsu, lingua corsa"
        },
        "cr": {
            "name": "Cree",
            "nativeName": "ᓀᐦᐃᔭᐍᐏᐣ"
        },
        "hr": {
            "name": "Croatian",
            "nativeName": "hrvatski"
        },
        "cs": {
            "name": "Czech",
            "nativeName": "česky, čeština"
        },
        "da": {
            "name": "Danish",
            "nativeName": "dansk"
        },
        "dv": {
            "name": "Divehi; Dhivehi; Maldivian;",
            "nativeName": "ދިވެހި"
        },
        "nl": {
            "name": "Dutch",
            "nativeName": "Nederlands, Vlaams"
        },
        "en": {
            "name": "English",
            "nativeName": "English"
        },
        "eo": {
            "name": "Esperanto",
            "nativeName": "Esperanto"
        },
        "et": {
            "name": "Estonian",
            "nativeName": "eesti, eesti keel"
        },
        "ee": {
            "name": "Ewe",
            "nativeName": "Eʋegbe"
        },
        "fo": {
            "name": "Faroese",
            "nativeName": "føroyskt"
        },
        "fj": {
            "name": "Fijian",
            "nativeName": "vosa Vakaviti"
        },
        "fi": {
            "name": "Finnish",
            "nativeName": "suomi, suomen kieli"
        },
        "fr": {
            "name": "French",
            "nativeName": "français, langue française"
        },
        "ff": {
            "name": "Fula; Fulah; Pulaar; Pular",
            "nativeName": "Fulfulde, Pulaar, Pular"
        },
        "gl": {
            "name": "Galician",
            "nativeName": "Galego"
        },
        "ka": {
            "name": "Georgian",
            "nativeName": "ქართული"
        },
        "de": {
            "name": "German",
            "nativeName": "Deutsch"
        },
        "el": {
            "name": "Greek, Modern",
            "nativeName": "Ελληνικά"
        },
        "gn": {
            "name": "Guaraní",
            "nativeName": "Avañeẽ"
        },
        "gu": {
            "name": "Gujarati",
            "nativeName": "ગુજરાતી"
        },
        "ht": {
            "name": "Haitian; Haitian Creole",
            "nativeName": "Kreyòl ayisyen"
        },
        "ha": {
            "name": "Hausa",
            "nativeName": "Hausa, هَوُسَ"
        },
        "he": {
            "name": "Hebrew (modern)",
            "nativeName": "עברית"
        },
        "hz": {
            "name": "Herero",
            "nativeName": "Otjiherero"
        },
        "hi": {
            "name": "Hindi",
            "nativeName": "हिन्दी, हिंदी"
        },
        "ho": {
            "name": "Hiri Motu",
            "nativeName": "Hiri Motu"
        },
        "hu": {
            "name": "Hungarian",
            "nativeName": "Magyar"
        },
        "ia": {
            "name": "Interlingua",
            "nativeName": "Interlingua"
        },
        "id": {
            "name": "Indonesian",
            "nativeName": "Bahasa Indonesia"
        },
        "ie": {
            "name": "Interlingue",
            "nativeName": "Originally called Occidental; then Interlingue after WWII"
        },
        "ga": {
            "name": "Irish",
            "nativeName": "Gaeilge"
        },
        "ig": {
            "name": "Igbo",
            "nativeName": "Asụsụ Igbo"
        },
        "ik": {
            "name": "Inupiaq",
            "nativeName": "Iñupiaq, Iñupiatun"
        },
        "io": {
            "name": "Ido",
            "nativeName": "Ido"
        },
        "is": {
            "name": "Icelandic",
            "nativeName": "Íslenska"
        },
        "it": {
            "name": "Italian",
            "nativeName": "Italiano"
        },
        "iu": {
            "name": "Inuktitut",
            "nativeName": "ᐃᓄᒃᑎᑐᑦ"
        },
        "ja": {
            "name": "Japanese",
            "nativeName": "日本語 (にほんご／にっぽんご)"
        },
        "jv": {
            "name": "Javanese",
            "nativeName": "basa Jawa"
        },
        "kl": {
            "name": "Kalaallisut, Greenlandic",
            "nativeName": "kalaallisut, kalaallit oqaasii"
        },
        "kn": {
            "name": "Kannada",
            "nativeName": "ಕನ್ನಡ"
        },
        "kr": {
            "name": "Kanuri",
            "nativeName": "Kanuri"
        },
        "ks": {
            "name": "Kashmiri",
            "nativeName": "कश्मीरी, كشميري‎"
        },
        "kk": {
            "name": "Kazakh",
            "nativeName": "Қазақ тілі"
        },
        "km": {
            "name": "Khmer",
            "nativeName": "ភាសាខ្មែរ"
        },
        "ki": {
            "name": "Kikuyu, Gikuyu",
            "nativeName": "Gĩkũyũ"
        },
        "rw": {
            "name": "Kinyarwanda",
            "nativeName": "Ikinyarwanda"
        },
        "ky": {
            "name": "Kirghiz, Kyrgyz",
            "nativeName": "кыргыз тили"
        },
        "kv": {
            "name": "Komi",
            "nativeName": "коми кыв"
        },
        "kg": {
            "name": "Kongo",
            "nativeName": "KiKongo"
        },
        "ko": {
            "name": "Korean",
            "nativeName": "한국어 (韓國語), 조선말 (朝鮮語)"
        },
        "ku": {
            "name": "Kurdish",
            "nativeName": "Kurdî, كوردی‎"
        },
        "kj": {
            "name": "Kwanyama, Kuanyama",
            "nativeName": "Kuanyama"
        },
        "la": {
            "name": "Latin",
            "nativeName": "latine, lingua latina"
        },
        "lb": {
            "name": "Luxembourgish, Letzeburgesch",
            "nativeName": "Lëtzebuergesch"
        },
        "lg": {
            "name": "Luganda",
            "nativeName": "Luganda"
        },
        "li": {
            "name": "Limburgish, Limburgan, Limburger",
            "nativeName": "Limburgs"
        },
        "ln": {
            "name": "Lingala",
            "nativeName": "Lingála"
        },
        "lo": {
            "name": "Lao",
            "nativeName": "ພາສາລາວ"
        },
        "lt": {
            "name": "Lithuanian",
            "nativeName": "lietuvių kalba"
        },
        "lu": {
            "name": "Luba-Katanga",
            "nativeName": ""
        },
        "lv": {
            "name": "Latvian",
            "nativeName": "latviešu valoda"
        },
        "gv": {
            "name": "Manx",
            "nativeName": "Gaelg, Gailck"
        },
        "mk": {
            "name": "Macedonian",
            "nativeName": "македонски јазик"
        },
        "mg": {
            "name": "Malagasy",
            "nativeName": "Malagasy fiteny"
        },
        "ms": {
            "name": "Malay",
            "nativeName": "bahasa Melayu, بهاس ملايو‎"
        },
        "ml": {
            "name": "Malayalam",
            "nativeName": "മലയാളം"
        },
        "mt": {
            "name": "Maltese",
            "nativeName": "Malti"
        },
        "mi": {
            "name": "Māori",
            "nativeName": "te reo Māori"
        },
        "mr": {
            "name": "Marathi (Marāṭhī)",
            "nativeName": "मराठी"
        },
        "mh": {
            "name": "Marshallese",
            "nativeName": "Kajin M̧ajeļ"
        },
        "mn": {
            "name": "Mongolian",
            "nativeName": "монгол"
        },
        "na": {
            "name": "Nauru",
            "nativeName": "Ekakairũ Naoero"
        },
        "nv": {
            "name": "Navajo, Navaho",
            "nativeName": "Diné bizaad, Dinékʼehǰí"
        },
        "nb": {
            "name": "Norwegian Bokmål",
            "nativeName": "Norsk bokmål"
        },
        "nd": {
            "name": "North Ndebele",
            "nativeName": "isiNdebele"
        },
        "ne": {
            "name": "Nepali",
            "nativeName": "नेपाली"
        },
        "ng": {
            "name": "Ndonga",
            "nativeName": "Owambo"
        },
        "nn": {
            "name": "Norwegian Nynorsk",
            "nativeName": "Norsk nynorsk"
        },
        "no": {
            "name": "Norwegian",
            "nativeName": "Norsk"
        },
        "ii": {
            "name": "Nuosu",
            "nativeName": "ꆈꌠ꒿ Nuosuhxop"
        },
        "nr": {
            "name": "South Ndebele",
            "nativeName": "isiNdebele"
        },
        "oc": {
            "name": "Occitan",
            "nativeName": "Occitan"
        },
        "oj": {
            "name": "Ojibwe, Ojibwa",
            "nativeName": "ᐊᓂᔑᓈᐯᒧᐎᓐ"
        },
        "cu": {
            "name": "Old Church Slavonic, Church Slavic, Church Slavonic, Old Bulgarian, Old Slavonic",
            "nativeName": "ѩзыкъ словѣньскъ"
        },
        "om": {
            "name": "Oromo",
            "nativeName": "Afaan Oromoo"
        },
        "or": {
            "name": "Oriya",
            "nativeName": "ଓଡ଼ିଆ"
        },
        "os": {
            "name": "Ossetian, Ossetic",
            "nativeName": "ирон æвзаг"
        },
        "pa": {
            "name": "Panjabi, Punjabi",
            "nativeName": "ਪੰਜਾਬੀ, پنجابی‎"
        },
        "pi": {
            "name": "Pāli",
            "nativeName": "पाऴि"
        },
        "fa": {
            "name": "Persian",
            "nativeName": "فارسی"
        },
        "pl": {
            "name": "Polish",
            "nativeName": "polski"
        },
        "ps": {
            "name": "Pashto, Pushto",
            "nativeName": "پښتو"
        },
        "pt": {
            "name": "Portuguese",
            "nativeName": "Português"
        },
        "qu": {
            "name": "Quechua",
            "nativeName": "Runa Simi, Kichwa"
        },
        "rm": {
            "name": "Romansh",
            "nativeName": "rumantsch grischun"
        },
        "rn": {
            "name": "Kirundi",
            "nativeName": "kiRundi"
        },
        "ro": {
            "name": "Romanian, Moldavian, Moldovan",
            "nativeName": "română"
        },
        "ru": {
            "name": "Russian",
            "nativeName": "русский язык"
        },
        "sa": {
            "name": "Sanskrit (Saṁskṛta)",
            "nativeName": "संस्कृतम्"
        },
        "sc": {
            "name": "Sardinian",
            "nativeName": "sardu"
        },
        "sd": {
            "name": "Sindhi",
            "nativeName": "सिन्धी, سنڌي، سندھی‎"
        },
        "se": {
            "name": "Northern Sami",
            "nativeName": "Davvisámegiella"
        },
        "sm": {
            "name": "Samoan",
            "nativeName": "gagana faa Samoa"
        },
        "sg": {
            "name": "Sango",
            "nativeName": "yângâ tî sängö"
        },
        "sr": {
            "name": "Serbian",
            "nativeName": "српски језик"
        },
        "gd": {
            "name": "Scottish Gaelic; Gaelic",
            "nativeName": "Gàidhlig"
        },
        "sn": {
            "name": "Shona",
            "nativeName": "chiShona"
        },
        "si": {
            "name": "Sinhala, Sinhalese",
            "nativeName": "සිංහල"
        },
        "sk": {
            "name": "Slovak",
            "nativeName": "slovenčina"
        },
        "sl": {
            "name": "Slovene",
            "nativeName": "slovenščina"
        },
        "so": {
            "name": "Somali",
            "nativeName": "Soomaaliga, af Soomaali"
        },
        "st": {
            "name": "Southern Sotho",
            "nativeName": "Sesotho"
        },
        "es": {
            "name": "Spanish; Castilian",
            "nativeName": "español, castellano"
        },
        "su": {
            "name": "Sundanese",
            "nativeName": "Basa Sunda"
        },
        "sw": {
            "name": "Swahili",
            "nativeName": "Kiswahili"
        },
        "ss": {
            "name": "Swati",
            "nativeName": "SiSwati"
        },
        "sv": {
            "name": "Swedish",
            "nativeName": "svenska"
        },
        "ta": {
            "name": "Tamil",
            "nativeName": "தமிழ்"
        },
        "te": {
            "name": "Telugu",
            "nativeName": "తెలుగు"
        },
        "tg": {
            "name": "Tajik",
            "nativeName": "тоҷикӣ, toğikī, تاجیکی‎"
        },
        "th": {
            "name": "Thai",
            "nativeName": "ไทย"
        },
        "ti": {
            "name": "Tigrinya",
            "nativeName": "ትግርኛ"
        },
        "bo": {
            "name": "Tibetan Standard, Tibetan, Central",
            "nativeName": "བོད་ཡིག"
        },
        "tk": {
            "name": "Turkmen",
            "nativeName": "Türkmen, Түркмен"
        },
        "tl": {
            "name": "Tagalog",
            "nativeName": "Wikang Tagalog, ᜏᜒᜃᜅ᜔ ᜆᜄᜎᜓᜄ᜔"
        },
        "tn": {
            "name": "Tswana",
            "nativeName": "Setswana"
        },
        "to": {
            "name": "Tonga (Tonga Islands)",
            "nativeName": "faka Tonga"
        },
        "tr": {
            "name": "Turkish",
            "nativeName": "Türkçe"
        },
        "ts": {
            "name": "Tsonga",
            "nativeName": "Xitsonga"
        },
        "tt": {
            "name": "Tatar",
            "nativeName": "татарча, tatarça, تاتارچا‎"
        },
        "tw": {
            "name": "Twi",
            "nativeName": "Twi"
        },
        "ty": {
            "name": "Tahitian",
            "nativeName": "Reo Tahiti"
        },
        "ug": {
            "name": "Uighur, Uyghur",
            "nativeName": "Uyƣurqə, ئۇيغۇرچە‎"
        },
        "uk": {
            "name": "Ukrainian",
            "nativeName": "українська"
        },
        "ur": {
            "name": "Urdu",
            "nativeName": "اردو"
        },
        "uz": {
            "name": "Uzbek",
            "nativeName": "zbek, Ўзбек, أۇزبېك‎"
        },
        "ve": {
            "name": "Venda",
            "nativeName": "Tshivenḓa"
        },
        "vi": {
            "name": "Vietnamese",
            "nativeName": "Tiếng Việt"
        },
        "vo": {
            "name": "Volapük",
            "nativeName": "Volapük"
        },
        "wa": {
            "name": "Walloon",
            "nativeName": "Walon"
        },
        "cy": {
            "name": "Welsh",
            "nativeName": "Cymraeg"
        },
        "wo": {
            "name": "Wolof",
            "nativeName": "Wollof"
        },
        "fy": {
            "name": "Western Frisian",
            "nativeName": "Frysk"
        },
        "xh": {
            "name": "Xhosa",
            "nativeName": "isiXhosa"
        },
        "yi": {
            "name": "Yiddish",
            "nativeName": "ייִדיש"
        },
        "yo": {
            "name": "Yoruba",
            "nativeName": "Yorùbá"
        },
        "za": {
            "name": "Zhuang, Chuang",
            "nativeName": "Saɯ cueŋƅ, Saw cuengh"
        }
    };

    /*
     * Holds mime types
     */
    var supportedMimeTypes = [
        "text/plain",
        "text/html"
    ];

    /*
     * This is the public API
     * @param {type} elementID
     * @returns {undefined}
     */
    return {
        /**
         * function that enables the annotation service
         * This is the starting point!
         * The elementID is the DOM element the URLs of which we are going to annotate
         *
         * @param {type} elementID
         * @returns {undefined}
         */
        toggleAnnotation: function (elementID) {
            // First time we come here
            if (state === undefined)
                // Init state
                state = new State();

            // If annotation is not enabled
            if (state.enabled === false) {
                // Set annotation to true
                state.enabled = true;
                // Init the annotation bar
                initAnnotationBar();
                // find urls in this specific element
                findURLs(elementID);
                // Update of URIs is responsibility of the function updateAnnotatedAnchorElements
                // which is called by the handler of the ajax request
                // Send the Ajax request
                ajaxAnnotatedURIs();
                // Hold the element ID
                state.elementID = elementID;
            } else {
                // disable
                state.enabled = false;
                // Remove annotations from annotated URIs
                updateAnnotatedAnchorElements();
                // Now remove also the bar
                removeAnnotationBar();
                // reset state
                //state = null;
            }
        },
        /**
         * Retrieves the annotation for a specific URI, parses the json-ld info
         * and shows it in the annotation window
         * @param {type} URI
         * @returns {undefined}
         */
        showAnnotation: function (URI) {
            // open the annotation bar
            showAnnoBar();
            if (state && state !== null && state.enabled) {
                // Get annotations for this specific URL
                // This uris are the clean ones, so we could possibly not use
                // getQueryParameterOfURL
                var filtered = getQueryParameterOfURL('uri', URI);
                ajaxRESTRetrieveAnnotation(filtered);
            }
        },
        /**
         * Function responsible for creating an annotation for a specific URI
         * We have to pass the jsonld string of the annotation we want to create
         *
         * @param {type} URI
         * @returns {undefined}
         */
        createAnnotation: function (URI) {
            // open the annotation bar
            showAnnoBar();
            if (state && state !== null && state.enabled) {
                // Get annotations for this specific URL
                var filtered = getQueryParameterOfURL('uri', URI);
                createAnnotationUI(filtered);
            }
        },
        /**
         * Function responsible for deleting an annotation for a specific URI
         * We have to pass the jsonld string of the annotation we want to create
         *
         * @param {type} URI
         * @returns {undefined}
         */
        deleteAnnotation: function (annoID, URI) {
            if (state && state !== null && state.enabled) {
                // Get annotations for this specific URL
                var filtered = getQueryParameterOfURL('uri', URI);
                ajaxRESTDeleteAnnotation(annoID, filtered);
            }
        },

        /**
         * Method that updates the available URIs
         * @returns {undefined}
         */
        update: function () {
            if (state && state !== null) {
                findURLs(state.elementID);
                ajaxAnnotatedURIs();
            }
        },
        /**
         * Method that checks if anno is on
         * returns boolean
         */
        isOn: function () {
            if (state && state !== null) {
                return state.enabled;
            } else
                return false;
        },
        /**
         * Method that checks if anno is off
         * returns boolean
         */
        isOff: function () {
            if (state && state !== null) {
                return !state.enabled;
            } else
                return true;
        }
    };
})();
