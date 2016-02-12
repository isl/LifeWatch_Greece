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
package eu.lifewatchgreece.annotations.controllers.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author papadako
 */
@WebServlet(name = "OAuth", urlPatterns = {"/oauth"})
public class OAuth extends HttpServlet {

    // Key and secret from annotateit.org
    String CONSUMER_KEY = "ba97bf25406a4a95aa9edc9ca1f65c1e";
    String CONSUMER_SECRET = "9fd408f4-2044-4d1f-b260-41113b170000";

    // Only change this if you're sure you know what you're doing. 86400 = 1 day
    String CONSUMER_TTL = "86400";

    /**
     * Uses JSON Web Token Generator to create a token
     *
     * @return
     */
    String getToken(String username) {
        // Use time for expiration
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());
        
        Claims claims = Jwts.claims();

        claims.put("consumerKey", CONSUMER_KEY);// consumer key
        claims.put("userId", username);       // account Id
        claims.put("issuedAt", nowAsISO);       // time as ISO8601 UTC timezone
        claims.put("ttl", CONSUMER_TTL);        // expire in seconds (one day)
                

        // Now create the token
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")   // Not sure if it is needed
                .setHeaderParam("cty", "text/plain")   // Again not sure
                .setIssuer("LifeWatchAnnotationAuthentication")              // iss
                .setClaims(claims) // Add the necessary claims for annotateit
                .setId(UUID.randomUUID().toString())    // Set UUID
                .signWith(SignatureAlgorithm.HS256, CONSUMER_SECRET).compact();
        
        // userRole and userGroups might also be supported by annotateit SP
        // Assertion that the sign and unsign is correct
        assert Jwts.parser().setSigningKey(CONSUMER_SECRET)
                .parseClaimsJws(token).getBody()
                .get("consumerKey").equals(CONSUMER_KEY);
        
        return token;
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String username = "";
        if(request.getAttribute("user") != null) {
            username = (String) request.getAttribute("user");
        }
        
        try (PrintWriter out = response.getWriter()) {

            /* Return the token */
            out.println(getToken(username));
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
