package helpers;

import ultilities.LogUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;

public class PropertiesHelpers {
    private static Properties properties;
    private static String linkFile;
    private static FileInputStream file;
    private static FileOutputStream out;
    private static String relPropertiesFilePathDefault = "src/test/resources/config/frameworkConfig.properties";

    public static Properties loadAllFiles() {
        LinkedList<String> files = new LinkedList<>();
        files.add("src/test/resources/config/frameworkConfig.properties");
        try {
            properties = new Properties();
            for (String f : files) {
                Properties tempProp = new Properties();
                linkFile = Helpers.getCurrentDir() + f;
                file = new FileInputStream(linkFile);
                tempProp.load(file);
                properties.putAll(tempProp);
            }
            file.close();
            LogUtils.info("Loaded all properties files with properties: "+ getProperties());
            return properties;
        } catch (IOException e) {
            LogUtils.info("Warning !! Can not Load All File.");
            return new Properties();
        }
    }

    private static void overrideProperties(String... propertyNames) {
        for (String propertyName : propertyNames) {
            String commandLineValue = System.getProperty(propertyName);
            if (commandLineValue != null) {
                properties.setProperty(propertyName, commandLineValue);
            }
        }
    }

    public static Properties getProperties() {
        return properties;
    }

    public static void setFile(String relPropertiesFilePath) {
        properties = new Properties();
        try {
            linkFile = Helpers.getCurrentDir() + relPropertiesFilePath;
            file = new FileInputStream(linkFile);
            properties.load(file);
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setDefaultFile() {
        properties = new Properties();
        try {
            linkFile = Helpers.getCurrentDir() + relPropertiesFilePathDefault;
            file = new FileInputStream(linkFile);
            properties.load(file);
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {
        String keyValue = null;
        try {
            if (file == null && properties == null) {
                properties = new Properties();
                linkFile = Helpers.getCurrentDir() + relPropertiesFilePathDefault;
                file = new FileInputStream(linkFile);
                properties.load(file);
                file.close();
            }
            // Get value from file
            keyValue = properties.getProperty(key);
            return keyValue;
            //return keyValue;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return keyValue;
        }
    }

    public static void setValue(String key, String keyValue) {
        try {
            if (file == null) {
                properties = new Properties();
                file = new FileInputStream(Helpers.getCurrentDir() + relPropertiesFilePathDefault);
                properties.load(file);
                file.close();
                out = new FileOutputStream(Helpers.getCurrentDir() + relPropertiesFilePathDefault);
            }
            //Write to the same Prop file as the extracted file
            out = new FileOutputStream(linkFile);
            System.out.println(linkFile);
            properties.setProperty(key, keyValue);
            properties.store(out, "Set value to properties file success.");
            out.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
