package edu.project3.statistics_handlers;

import edu.project3.fileparsers.Log;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StatisticsHandler {
    private List<Path> files;
    private LocalDate from;
    private LocalDate to;
    private long totalNumberOfRequests;
    private List<Map.Entry<String, Long>> mostFrequentlyRequestedResources;
    private List<Map.Entry<Integer, Long>> mostCommonResponseCodes;
    private double averageServerResponseSize;
    private List<Map.Entry<String, Long>> mostFrequentlyIps;
    private Log theHeaviestLog;
    private Predicate<? super Log> dateFilter;

    public StatisticsHandler(LocalDate from, LocalDate to, List<Path> files, List<Log> logs) {
        this.files = files;
        this.from = from;
        this.to = to;
        this.dateFilter = getDateFilter();
        handle(logs);
    }

    private void handle(List<Log> logs) {
        this.totalNumberOfRequests = countTotalNumberOfRequests(logs);
        this.mostFrequentlyRequestedResources = getTheMostFrequentlyRequestedResources(logs);
        this.mostCommonResponseCodes = getTheMostCommonResponseCodes(logs);
        this.averageServerResponseSize = getAverageServerResponseSize(logs);
        this.mostFrequentlyIps = getTheMostFrequentlyIps(logs);
        this.theHeaviestLog = getTheHeaviestLog(logs);
    }

    private Predicate<? super Log> getDateFilter() {
        return log -> {
            if (from == null && to == null) {
                return true;
            } else if (from == null) {
                return to.isAfter(log.getDate().toLocalDate());
            } else if (to == null) {
                return from.isBefore(log.getDate().toLocalDate());
            }
            return from.isBefore(log.getDate().toLocalDate()) && to.isAfter(log.getDate().toLocalDate());
        };
    }

    private long countTotalNumberOfRequests(List<Log> logs) {
        if (logs == null) {
            return 0;
        }
        return logs.stream()
            .filter(dateFilter)
            .count();
    }

    private List<Map.Entry<String, Long>> getTheMostFrequentlyRequestedResources(List<Log> logs) {
        if (logs == null) {
            return List.of();
        }
        return logs.stream()
            .filter(dateFilter)
            .collect(Collectors.groupingBy(Log::getResource, Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .toList();
    }

    private List<Map.Entry<Integer, Long>> getTheMostCommonResponseCodes(List<Log> logs) {
        if (logs == null) {
            return List.of();
        }
        return logs.stream()
            .filter(dateFilter)
            .collect(Collectors.groupingBy(Log::getStatus, Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .toList();
    }

    private double getAverageServerResponseSize(List<Log> logs) {
        if (logs == null) {
            return 0.0;
        }
        return logs.stream()
            .filter(dateFilter)
            .mapToInt(Log::getBodyBytesSent)
            .average().orElse(0.0);
    }

    private List<Map.Entry<String, Long>> getTheMostFrequentlyIps(List<Log> logs) {
        if (logs == null) {
            return List.of();
        }
        return logs.stream()
            .filter(dateFilter)
            .collect(Collectors.groupingBy(Log::getRemoteAddress, Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .toList();
    }

    private Log getTheHeaviestLog(List<Log> logs) {
        if (logs == null) {
            return null;
        }
        return logs.stream()
            .filter(dateFilter)
            .max(Comparator.comparing(Log::getBodyBytesSent))
            .orElse(null);
    }

    public List<Path> getFiles() {
        return files;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    public long getTotalNumberOfRequests() {
        return totalNumberOfRequests;
    }

    public List<Map.Entry<String, Long>> getMostFrequentlyRequestedResources() {
        return mostFrequentlyRequestedResources;
    }

    public List<Map.Entry<Integer, Long>> getMostCommonResponseCodes() {
        return mostCommonResponseCodes;
    }

    public double getAverageServerResponseSize() {
        return averageServerResponseSize;
    }

    public List<Map.Entry<String, Long>> getMostFrequentlyIps() {
        return mostFrequentlyIps;
    }

    public Log getTheHeaviestLog() {
        return theHeaviestLog;
    }
}
