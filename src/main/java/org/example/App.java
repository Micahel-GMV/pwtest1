package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.config.PropertiesReader;


/**
 * Hello world!
 *
 */
public class App
{
    private static final Logger logger = LogManager.getLogger(App.class);
    public static void main( String[] args )
    {
        System.out.println(PropertiesReader.getProperty("baseURL"));
    }
}
