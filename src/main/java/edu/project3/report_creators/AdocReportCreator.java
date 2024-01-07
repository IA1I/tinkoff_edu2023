package edu.project3.report_creators;

import edu.project3.fileparsers.Log;
import edu.project3.statistics_handlers.StatisticsHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@SuppressWarnings("MultipleStringLiterals")
public class AdocReportCreator implements ReportCreator {
    @Override
    public Path createReport(StatisticsHandler statisticsHandler) {
        Path file = Path.of("log report.adoc");
        if (statisticsHandler == null) {
            return file;
        }
        StringBuilder report = new StringBuilder();
        createGeneralInformation(statisticsHandler, report);
        createInformationAboutRequestedResources(statisticsHandler, report);
        createInformationAboutResponseCodes(statisticsHandler, report);
        createInformationAboutMostFrequentlyIps(statisticsHandler, report);
        createInformationAboutTheHeaviestLog(statisticsHandler, report);
        writeToFile(file, report);
        return file;
    }

    private static void createInformationAboutTheHeaviestLog(
        StatisticsHandler statisticsHandler,
        StringBuilder report
    ) {
        Log log = statisticsHandler.getTheHeaviestLog();
        report.append("==== Самый тяжелый ответ\n\n[cols=2]\n|====\n|Поле\n|Значение\n");
        report.append("|remote address\n")
            .append("|")
            .append(log.getRemoteAddress())
            .append("\n");
        report.append("|remote user\n")
            .append("|")
            .append(log.getRemoteUser())
            .append("\n");
        report.append("|local time\n")
            .append("|")
            .append(log.getDate().toString())
            .append("\n");
        report.append("|request\n")
            .append("|")
            .append(log.getMethod())
            .append(" ")
            .append(log.getPathToResource())
            .append(log.getResource())
            .append(" ")
            .append(log.getProtocol())
            .append("\n");
        report.append("|status\n")
            .append("|")
            .append(log.getStatus())
            .append("\n");
        report.append("|body bytes sent\n")
            .append("|")
            .append(log.getBodyBytesSent())
            .append("\n");
        report.append("|http referer\n")
            .append("|")
            .append(log.getHttpReferer())
            .append("\n");
        report.append("|http user agent\n")
            .append("|")
            .append(log.getHttpUserAgent())
            .append("\n");
    }

    private void createInformationAboutMostFrequentlyIps(
        StatisticsHandler statisticsHandler,
        StringBuilder report
    ) {
        report.append("==== Удаленные адреса\n\n[cols=2]\n|====\n|Адрес\n|Количество обращений\n");
        List<Map.Entry<String, Long>> mostFrequentlyIps =
            statisticsHandler.getMostFrequentlyIps();
        for (var entry : mostFrequentlyIps) {
            report.append("|")
                .append(entry.getKey())
                .append("\n|")
                .append(entry.getValue())
                .append("\n");
        }
        report.append("|====\n");
    }

    private static void createInformationAboutResponseCodes(StatisticsHandler statisticsHandler, StringBuilder report) {
        report.append("==== Коды ответа\n\n[cols=3]\n|====\n|Код\n|Имя\n|Количество\n");
        List<Map.Entry<Integer, Long>> mostCommonResponseCodes = statisticsHandler.getMostCommonResponseCodes();
        for (var entry : mostCommonResponseCodes) {
            report.append("|")
                .append(entry.getKey())
                .append("\n|")
                .append(STATUS_CODE.get(entry.getKey()))
                .append("\n|")
                .append(entry.getValue())
                .append("\n");
        }
        report.append("|====\n");
    }

    private static void createInformationAboutRequestedResources(
        StatisticsHandler statisticsHandler,
        StringBuilder report
    ) {
        report.append("==== Запрашиваемые ресурсы\n\n[cols=2]\n|====\n|Ресурс\n|Количество\n");
        List<Map.Entry<String, Long>> mostFrequentlyRequestedResources =
            statisticsHandler.getMostFrequentlyRequestedResources();
        for (var entry : mostFrequentlyRequestedResources) {
            report.append("|'")
                .append(entry.getKey())
                .append("'\n|")
                .append(entry.getValue())
                .append("\n");
        }
        report.append("|====\n");
    }

    private static void createGeneralInformation(StatisticsHandler statisticsHandler, StringBuilder report) {
        report.append("==== Общая информация\n\n[cols=2]\n|====\n|Метрика\n|Значение\n");
        report.append("|Файл\n");
        for (Path path : statisticsHandler.getFiles()) {
            report.append("|'")
                .append(path.getFileName().toString())
                .append("'\n");
        }
        report.append("|Начальная дата\n");
        if (statisticsHandler.getFrom() == null) {
            report.append("|-\n");
        } else {
            report.append("|")
                .append(statisticsHandler.getFrom())
                .append("\n");
        }
        report.append("|Конечная дата\n");
        if (statisticsHandler.getTo() == null) {
            report.append("|-\n");
        } else {
            report.append("|")
                .append(statisticsHandler.getTo())
                .append("\n");
        }

        report.append("|Количество запросов\n")
            .append(statisticsHandler.getTotalNumberOfRequests())
            .append("\n");

        report.append("|Средний размер ответа\n")
            .append(statisticsHandler.getAverageServerResponseSize())
            .append("b\n");

        report.append("|====\n");
    }

    private static void writeToFile(Path file, StringBuilder report) {
        try {
            Files.writeString(file, report);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
