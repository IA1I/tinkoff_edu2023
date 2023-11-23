package edu.hw7;

import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task1 {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final AtomicInteger VALUE_FOR_INCREMENT = new AtomicInteger();

    private Task1() {
    }

    public static int increment(final int numberOfIncrements) {
        Thread firstThread = new Thread(() -> {
            LOGGER.info("Thread one started");
            for (int i = 0; i < numberOfIncrements; i++) {
                VALUE_FOR_INCREMENT.incrementAndGet();
            }
            LOGGER.info("Thread one ended");
        });
        Thread secondThread = new Thread(() -> {
            LOGGER.info("Thread two started");
            for (int i = 0; i < numberOfIncrements; i++) {
                VALUE_FOR_INCREMENT.incrementAndGet();
            }
            LOGGER.info("Thread two ended");
        });

        firstThread.start();
        secondThread.start();
        try {
            firstThread.join();
            secondThread.join();
            LOGGER.info("The threads have finished running");
        } catch (InterruptedException e) {
            LOGGER.error("Something went wrong while waiting for threads: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return VALUE_FOR_INCREMENT.get();
    }
}
