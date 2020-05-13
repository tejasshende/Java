package com.sm.api.utils;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;

public class Utils {

    //This method will read the .properties file and store the values in linkedHashMap
    public HashMap<String, String> readPropertyFile(String propFileName) {
        HashMap<String, String> propHashMap = new HashMap<String, String>();
        Properties prop = new Properties();

        try {
            FileInputStream fis = new FileInputStream(propFileName);

            if (fis != null) {
                prop.load(fis);

                //setting up the values in HashMap
                propHashMap.put("consumerKey", prop.getProperty("consumerKey"));
                propHashMap.put("consumerKeySecret", prop.getProperty("consumerKeySecret"));
                propHashMap.put("accessTokenKey", prop.getProperty("accessTokenKey"));
                propHashMap.put("accessTokenSecret", prop.getProperty("accessTokenSecret"));
            } else {
                System.out.println("property file '" + propFileName + "' not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // returning the HashMap
        return propHashMap;
    }
}
