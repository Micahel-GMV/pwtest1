package org.example.tools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.base.BaseTest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class ResourcesLoader {
    private static final Logger logger = LogManager.getLogger(ResourcesLoader.class);
    public static String loadResource(String resourceName) {
        logger.info("Loading from resource folder:" + resourceName);
        try (InputStream inputStream = ResourcesLoader.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (inputStream == null) {
                return null;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                return reader.lines().collect(Collectors.joining("\n"));
            }
        } catch (Exception e) {
            logger.error("Couldn`t load resource: " + resourceName, e);
            return null;
        }
    }
}
