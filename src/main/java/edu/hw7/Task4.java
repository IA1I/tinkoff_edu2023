package edu.hw7;

import java.util.concurrent.ThreadLocalRandom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task4 {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int NANO_SEC_IN_ONE_SECOND = 1_000_000_000;
    private static final int RATIO_OF_AREAS_OF_CIRCLE_AND_SQUARE = 4;

    private Task4() {
    }

    public static double countPI(int iterations) {
        if (iterations < 1) {
            throw new IllegalArgumentException();
        }
        int circleCount = 0;
        long start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            double x = Math.random();
            double y = Math.random();
            if ((x * x) + (y * y) < 1) {
                circleCount++;
            }
        }
        double pi = RATIO_OF_AREAS_OF_CIRCLE_AND_SQUARE * ((double) circleCount / iterations);
        long end = System.nanoTime() - start;
        LOGGER.info(
            "Calculating pi:{} with {} iterations took {} seconds, error: {}",
            pi,
            iterations,
            (double) end / NANO_SEC_IN_ONE_SECOND,
            Math.abs(Math.PI - pi)
        );
        return pi;
    }

    public static double countPIParallel(int iterations, int numberOfThreads) {
        if (iterations < 1 || numberOfThreads < 1) {
            throw new IllegalArgumentException();
        }
        int circleCount = 0;
        long start = System.nanoTime();
        MyThread[] threads = initialize(iterations, numberOfThreads);
        for (var thread : threads) {
            thread.start();
        }
        try {
            for (var thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (var thread : threads) {
            circleCount += thread.getCircleCount();
        }
        double pi = RATIO_OF_AREAS_OF_CIRCLE_AND_SQUARE * ((double) circleCount / iterations);
        long end = System.nanoTime() - start;
        LOGGER.info(
            "Calculating pi:{} with {} iterations and {} threads took {} seconds, error: {}",
            pi,
            iterations,
            numberOfThreads,
            (double) end / NANO_SEC_IN_ONE_SECOND,
            Math.abs(Math.PI - pi)
        );
        return pi;
    }

    private static MyThread[] initialize(int iterations, int numberOfThreads) {
        MyThread[] threads = new MyThread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new MyThread(iterations / numberOfThreads);
        }

        return threads;
    }

    private static class MyThread extends Thread {
        private final int iterations;
        private int circleCount;

        MyThread(int iterations) {
            this.iterations = iterations;
        }

        @Override
        public void run() {
            for (int i = 0; i < iterations; i++) {
                double x = ThreadLocalRandom.current().nextDouble();
                double y = ThreadLocalRandom.current().nextDouble();
                if ((x * x) + (y * y) < 1) {
                    circleCount++;
                }
            }
        }

        public int getCircleCount() {
            return circleCount;
        }
    }
}
