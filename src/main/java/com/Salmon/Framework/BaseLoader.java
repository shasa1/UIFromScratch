package com.Salmon.Framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class BaseLoader {
    static Properties propLocal = null;

    public static void loadPropetiesFile(String marketPlace){

        if(marketPlace.equalsIgnoreCase("Taiwan")){
            propLocal = new Properties();
            InputStream is = null;

            String path = System.getProperty("user.dir")+"/src/main/resources/Localizaion/Local_TW.properties";
            try{
                is = new FileInputStream(path);
            }catch (IOException e){
                e.printStackTrace();
            }

        }

    }

    public static String getValueFromLocalization(String localeKey){

        byte[] bytes=  propLocal.getProperty(localeKey).getBytes(StandardCharsets.ISO_8859_1);
        String iso = "";
        for (byte b: bytes) {

            iso = iso + b;

        }
        return iso;
    }
}
