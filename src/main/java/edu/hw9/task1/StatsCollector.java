package edu.hw9.task1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StatsCollector {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int NUMBER_OF_METRICS = 4;
    private final List<List<Double>> stats = new ArrayList<>(NUMBER_OF_METRICS);
    private final ExecutorService executor;

    public StatsCollector(int threadsNumber) {
        if (threadsNumber < 1) {
            LOGGER.error("Wrong threads number {}", threadsNumber);
            throw new IllegalArgumentException();
        }
        this.executor = Executors.newFixedThreadPool(threadsNumber);
        initStats();
        LOGGER.info("Created new StatsCollector with {} threads", threadsNumber);
    }

    private void initStats() {
        for (int i = 0; i < NUMBER_OF_METRICS; i++) {
            stats.add(new ArrayList<>());
        }
    }

    public void push(String metric, double[] data) {
        push(Metric.valueOf(metric), data);
    }

    public void push(Metric metric, double[] data) {
        executor.submit(() -> calculateStat(metric, data));
        LOGGER.info("Submitted new task with metric {} and data {}", metric, Arrays.toString(data));
    }

    private void calculateStat(Metric metric, double[] data) {
        Double result = switch (metric) {
            case SUM -> Arrays.stream(data).sum();
            case AVG -> Arrays.stream(data).average().orElse(0.0);
            case MAX -> Arrays.stream(data).max().orElse(0.0);
            case MIN -> Arrays.stream(data).min().orElse(0.0);
        };
        synchronized (stats) {
            stats.get(metric.ordinal()).add(result);
        }
        LOGGER.info("Added result {} for metric {} and data {}", result, metric, Arrays.toString(data));
    }

    public List<List<Double>> getStats() {
        return stats;
    }
}
