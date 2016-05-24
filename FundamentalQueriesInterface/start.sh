#!/bin/sh
cd ./jetty-distribution-9.2.11.v20150529
java -jar start.jar -Djetty.port=xxx -DbackendServerUrl=xxx -Dcom.bigdata.rdf.sail.webapp.ConfigParams.propertyFile=../RWStore.properties  -DrepositoryType=sparql -Dendpoint=xxx -DstartPage=Help:Start -Dcom.metaphacts.config.location=../config/config.prop -DworkingDir=../ &

