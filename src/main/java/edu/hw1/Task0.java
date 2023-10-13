package edu.hw1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task0 {
    private static final Logger LOGGER = LogManager.getLogger();

    public Task0() {
    }

    public static void sayHelloToWorld() {
        LOGGER.info("Привет, мир!");
    }
}
