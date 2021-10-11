package com.salmon.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class BaseLoaderTest {

    static Properties propLocal = null;

    BaseLoaderTest(String marketplace) {

        loadPropetiesFile(marketplace);

    }

    public static void loadPropetiesFile(String marketPlace) {

        if (marketPlace.equalsIgnoreCase("Taiwan")) {
            propLocal = new Properties();
            InputStream is = null;

            String path = System.getProperty("user.dir") + "/src/main/resources/Localizaion/Local_TW.properties";
            try {
                is = new FileInputStream(path);
                propLocal.load(is);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static String getValueFromLocalization(String localeKey) throws UnsupportedEncodingException {

         return propLocal.getProperty(localeKey);

    }
}
