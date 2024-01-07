package edu.project3.report_creators;

import edu.project3.statistics_handlers.StatisticsHandler;
import java.nio.file.Path;
import java.util.Map;

public interface ReportCreator {
    Map<Integer, String> STATUS_CODE =
        Map.of(
            200,
            "OK",
            206,
            "Partial Content",
            304,
            "Not Modified",
            403,
            "Forbidden",
            404,
            "Not Found",
            416,
            "Range Not Satisfiable"
        );

    Path createReport(StatisticsHandler statisticsHandler);

    static ReportCreator getInstance(String format) {
        if (format.equals("markdown")) {
            return new MarkdownReportCreator();
        }
        if (format.equals("adoc")) {
            return new AdocReportCreator();
        }

        return null;
    }
}
