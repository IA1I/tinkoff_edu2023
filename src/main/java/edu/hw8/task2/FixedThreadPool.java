package edu.hw8.task2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FixedThreadPool implements ThreadPool {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Thread[] threads;
    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();

    private FixedThreadPool(int threadsNumber) {
        if (threadsNumber < 1) {
            LOGGER.error("Wrong threads number {}", threadsNumber);
            throw new IllegalArgumentException("Wrong threads number");
        }
        this.threads = new Thread[threadsNumber];
        for (int i = 0; i < threadsNumber; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> {
                while (!threads[finalI].isInterrupted()) {
                    try {
                        Runnable task = tasks.take();
                        LOGGER.info("Running task {}", task);
                        task.run();
                    } catch (InterruptedException e) {
                        LOGGER.error("Thread interrupted");
                        break;
                    }
                }
            });
        }
    }

    public static FixedThreadPool create(int threadNumber) {
        LOGGER.info("Creating new FixedThreadPool with {} threads", threadNumber);
        return new FixedThreadPool(threadNumber);
    }

    @Override
    public void start() {
        LOGGER.info("Beginning of work");
        for (Thread thread : threads) {
            thread.start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        LOGGER.info("Adding a new task {}", runnable);
        tasks.add(runnable);
    }

    @Override
    public void close() throws Exception {
        while (!tasks.isEmpty()) {
        }
        for (Thread thread : threads) {
            thread.interrupt();
        }
        LOGGER.info("Closing");
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        LOGGER.info("All threads have joined");
    }
}
