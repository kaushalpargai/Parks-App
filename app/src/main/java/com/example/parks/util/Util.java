package com.example.parks.util;

public class Util {
    public static final String  PARKS_URL = "https://developer.nps.gov/api/v1/parks?stateCode=AZ&api_key=6XTd2xHL0HeF5UjkHtlr0lEJhe0RJHJ8bD44LqOU";
    public static String getParksUrl(String stateCode){
        return "https://developer.nps.gov/api/v1/parks?stateCode="+ stateCode +"&api_key=6XTd2xHL0HeF5UjkHtlr0lEJhe0RJHJ8bD44LqOU";
    }
}
