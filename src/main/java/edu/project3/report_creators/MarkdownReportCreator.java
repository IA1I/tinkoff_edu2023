package edu.project3.report_creators;

import edu.project3.fileparsers.Log;
import edu.project3.statistics_handlers.StatisticsHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@SuppressWarnings("MultipleStringLiterals")
public class MarkdownReportCreator implements ReportCreator {
    @Override
    public Path createReport(StatisticsHandler statisticsHandler) {
        Path file = Path.of("log report.md");
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

    private void writeToFile(Path file, StringBuilder report) {
        try {
            Files.writeString(file, report);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createInformationAboutTheHeaviestLog(
        StatisticsHandler statisticsHandler,
        StringBuilder report
    ) {
        Log log = statisticsHandler.getTheHeaviestLog();
        report.append("#### Самый тяжелый ответ\n\n|Поле|Значение|\n|:-:|-:|\n");
        report.append("|remote address|")
            .append(log.getRemoteAddress())
            .append("|\n");
        report.append("|remote user|")
            .append(log.getRemoteUser())
            .append("|\n");
        report.append("|local time|")
            .append(log.getDate().toString())
            .append("|\n");
        report.append("|request|")
            .append(log.getMethod())
            .append(" ")
            .append(log.getPathToResource())
            .append(log.getResource())
            .append(" ")
            .append(log.getProtocol())
            .append("|\n");
        report.append("|status|")
            .append(log.getStatus())
            .append("|\n");
        report.append("|body bytes sent|")
            .append(log.getBodyBytesSent())
            .append("|\n");
        report.append("|http referer|")
            .append(log.getHttpReferer())
            .append("|\n");
        report.append("|http user agent|")
            .append(log.getHttpUserAgent())
            .append("|\n");
    }

    private void createInformationAboutMostFrequentlyIps(
        StatisticsHandler statisticsHandler,
        StringBuilder report
    ) {
        report.append("#### Удаленные адреса\n\n|Адрес|Количество обращений|\n|-:|-:|\n");
        List<Map.Entry<String, Long>> mostFrequentlyIps =
            statisticsHandler.getMostFrequentlyIps();
        for (var entry : mostFrequentlyIps) {
            report.append("|")
                .append(entry.getKey())
                .append("|")
                .append(entry.getValue())
                .append("|\n");
        }
        report.append("\n");
    }

    private void createInformationAboutResponseCodes(StatisticsHandler statisticsHandler, StringBuilder report) {
        report.append("#### Коды ответа\n\n|Код|Имя|Количество|\n|:-:|:-:|-:|\n");
        List<Map.Entry<Integer, Long>> mostCommonResponseCodes = statisticsHandler.getMostCommonResponseCodes();
        for (var entry : mostCommonResponseCodes) {
            report.append("|")
                .append(entry.getKey())
                .append("|")
                .append(STATUS_CODE.get(entry.getKey()))
                .append("|")
                .append(entry.getValue())
                .append("|\n");
        }
        report.append("\n");
    }

    private void createInformationAboutRequestedResources(
        StatisticsHandler statisticsHandler,
        StringBuilder report
    ) {
        report.append("#### Запрашиваемые ресурсы\n\n|Ресурс|Количество|\n|:-:|-:|\n");
        List<Map.Entry<String, Long>> mostFrequentlyRequestedResources =
            statisticsHandler.getMostFrequentlyRequestedResources();
        for (var entry : mostFrequentlyRequestedResources) {
            report.append("|\\'")
                .append(entry.getKey())
                .append("\\'|")
                .append(entry.getValue())
                .append("|\n");
        }
        report.append("\n");
    }

    private void createGeneralInformation(StatisticsHandler statisticsHandler, StringBuilder report) {
        report.append("#### Общая информация\n\n|Метрика|Значение|\n|:-:|-:|\n");
        report.append("|Файл(-ы)|");
        for (Path path : statisticsHandler.getFiles()) {
            report.append("\\'")
                .append(path.getFileName().toString())
                .append("\\' ");
        }
        report.append("|\n");
        report.append("|Начальная дата|");
        if (statisticsHandler.getFrom() == null) {
            report.append("-");
        } else {
            report.append(statisticsHandler.getFrom());
        }
        report.append("|\n");
        report.append("|Конечная дата|");
        if (statisticsHandler.getTo() == null) {
            report.append("-");
        } else {
            report.append(statisticsHandler.getTo());
        }
        report.append("|\n");

        report.append("|Количество запросов|")
            .append(statisticsHandler.getTotalNumberOfRequests())
            .append("|\n");

        report.append("|Средний размер ответа|")
            .append(statisticsHandler.getAverageServerResponseSize())
            .append("b|\n");

        report.append("\n");
    }
}
