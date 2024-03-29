package org.example.config;

import java.io.FileInputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PropertiesReader {
    private static final Logger logger = LogManager.getLogger(PropertiesReader.class);
    private static final String DEFAULT_PROPERTIES_FILE = "default.properties";
    private static final Properties properties = new Properties();

    static {
        LogManager.getLogger();
        try {
            properties.load(new FileInputStream(DEFAULT_PROPERTIES_FILE));
            logger.info("Default properties loaded from " + DEFAULT_PROPERTIES_FILE);
            logger.info("Properties number:" + properties.size());
            properties.entrySet().forEach(property -> logger.info("Property: " + property.getKey() + " = " + property.getValue()));
        } catch (Exception e) {
            logger.error("Error loading properties from " + DEFAULT_PROPERTIES_FILE, e);
        }
    }
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}
