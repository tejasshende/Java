package com.sm.api.utils;

import java.io.FileInputStream;
import java.text.DecimalFormat;
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

                if (propFileName.contains("twitter")) {
                    //setting up the values in HashMap
                    propHashMap.put("consumerKey", prop.getProperty("consumerKey"));
                    propHashMap.put("consumerKeySecret", prop.getProperty("consumerKeySecret"));
                    propHashMap.put("accessTokenKey", prop.getProperty("accessTokenKey"));
                    propHashMap.put("accessTokenSecret", prop.getProperty("accessTokenSecret"));
                } else if (propFileName.contains("fb")) {
                    propHashMap.put("accessToken", prop.getProperty("accessToken"));
                    propHashMap.put("appID", prop.getProperty("appID"));
                    propHashMap.put("appSecret", prop.getProperty("appSecret"));
                } else if (propFileName.contains("insta")) {
                    propHashMap.put("accessToken", prop.getProperty("accessToken"));
                } else {
                    System.out.println("property file '" + propFileName + "' not found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // returning the HashMap
        return propHashMap;
    }

    //Format the number like 1000 to 1K, 1000000 to 1M
    public String formatNumber(int inpNumber){
        String[] suffix = new String[]{"","K", "M", "B", "T"};
        int MAX_LENGTH = 4;

        String formattedNumber = new DecimalFormat("##0E0").format(inpNumber);
        formattedNumber = formattedNumber.replaceAll("E[0-9]", suffix[Character.getNumericValue(formattedNumber.charAt(formattedNumber.length() - 1)) / 3]);
        while(formattedNumber.length() > MAX_LENGTH || formattedNumber.matches("[0-9]+\\.[a-z]")){
            formattedNumber = formattedNumber.substring(0, formattedNumber.length()-2) + formattedNumber.substring(formattedNumber.length() - 1);
        }
        // retuning the formatted number
        return formattedNumber;
    }

}
