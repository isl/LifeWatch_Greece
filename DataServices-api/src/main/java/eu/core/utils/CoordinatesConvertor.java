/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.core.utils;

/**
 *
 * @author minadakn
 */
public class CoordinatesConvertor {
    
    public String convertLatitude(String latitude){
       
        if (latitude.contains("E") || latitude.contains("W")) {
            System.out.println("Wrong Entry! You cannot insert E or W in latitude field!");
        } else {

            int posFlag = 1;
            if (latitude.contains("S")) {
                posFlag = 0;
            }

            latitude = latitude.replaceAll("\\s", "");

            latitude = latitude.replace("\u00b0", " ").replace("o", " ").replace("\"", " ").replace("''", " ").replace("'", " ")
                    .replace("N", "").replace("S", "").replace("n", "").replace("s", "");

            String latitude_values[] = latitude.split(" ");

            if (latitude_values.length == 3) {

                String lat_degree = latitude_values[0];
                String lat_mins = latitude_values[1];
                String lat_secs = latitude_values[2];

                double lat_degree_float = Float.parseFloat(lat_degree);
                double lat_mins_float = Float.parseFloat(lat_mins) / 60;
                double lat_secs_float = Float.parseFloat(lat_secs) / 3600;

                double lat_res = lat_degree_float + lat_mins_float + lat_secs_float;

                lat_res = (int)Math.round(lat_res * 10000000)/(double)10000000;

                String lat_result = lat_res + "\u00b0";
                
                if (posFlag == 0) {
                    lat_result = "-" + lat_result;
                }
                latitude = lat_result;

            } else {
                String lat_degree = latitude_values[0];
                String lat_mins = latitude_values[1];
                double lat_degree_float = Float.parseFloat(lat_degree);
                double lat_mins_float = Float.parseFloat(lat_mins) / 60;
  
                double lat_res = lat_degree_float + lat_mins_float;

                lat_res = (int)Math.round(lat_res * 10000000)/(double)10000000;
                
                String lat_result = lat_res + "\u00b0";
                
                if (posFlag == 0) {
                    lat_result = "-" + lat_result;
                }
                latitude = lat_result;
            }
        }
        
        return latitude;
       
    }
    
    public String convertLongitude(String longitude){

        if (longitude.contains("N") || longitude.contains("S")) {
            System.out.println("Wrong Entry! You cannot insert N or S in longitude field!");
        } else {
            int posFlag = 1;
            if (longitude.contains("W")) {
                posFlag = 0;
            }

            longitude = longitude.replaceAll("\\s", "");
 
            longitude = longitude.replace("\u00b0", " ").replace("o", " ").replace("\"", " ").replace("''", " ").replace("'", " ")
                    .replace("E", "").replace("W", "").replace("e", "").replace("w", "");

            String longitude_values[] = longitude.split(" ");

            if (longitude_values.length == 3) {
                String long_degree = longitude_values[0];
                String long_mins = longitude_values[1];
                String long_secs = longitude_values[2];

                double long_degree_float = Float.parseFloat(long_degree);
                double long_mins_float = Float.parseFloat(long_mins) / 60;
                double long_secs_float = Float.parseFloat(long_secs) / 3600;

                double long_res = long_degree_float + long_mins_float + long_secs_float;

                long_res = (int)Math.round(long_res * 10000000)/(double)10000000;
                
                
                String long_result = long_res + "\u00b0";

                if (posFlag == 0) {
                    long_result = "-" + long_result;
                }
                
                longitude = long_result;
            } else {

                String long_degree = longitude_values[0];
                String long_mins = longitude_values[1];

                double long_degree_float = Float.parseFloat(long_degree);
                double long_mins_float = Float.parseFloat(long_mins) / 60;
   
                double long_res = long_degree_float + long_mins_float;

                long_res = (int)Math.round(long_res * 10000000)/(double)10000000;
                
                String long_result = long_res + "\u00b0";

                if (posFlag == 0) {
                    long_result = "-" + long_result;
                }
            
                longitude = long_result;

            }
        }
        
        return longitude;
    }
    
}
