package edu.project3;

import edu.project3.fileparsers.FileParser;
import edu.project3.fileparsers.Log;
import edu.project3.fileproviders.LogProvider;
import edu.project3.report_creators.ReportCreator;
import edu.project3.statistics_handlers.StatisticsHandler;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings({"MissingSwitchDefault", "InnerAssignment"})
public class LogAnalyzer {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final DateTimeFormatter DATE_FORMATTER =
        DateTimeFormatter.ISO_DATE;
    private LogProvider logProvider;
    private FileParser fileParser;
    private String path;
    private LocalDate from;
    private LocalDate to;
    private String format;

    public LogAnalyzer(String[] args, FileParser fileParser) {
        handleArguments(args);
        this.logProvider = LogProvider.getInstance(path);
        checkFileParser(fileParser);
        this.fileParser = fileParser;
    }

    private void checkFileParser(FileParser fileParser) {
        if (fileParser == null) {
            LOGGER.error("FileParser is null");
            throw new IllegalArgumentException();
        }
    }

    private void handleArguments(String[] args) {
        checkArgs(args);
        format = "markdown";
        for (int i = 0; i < args.length; i += 2) {
            switch (Argument.valueOfArgument(args[i])) {
                case Argument.PATH -> path = args[i + 1];
                case Argument.FROM -> from = parseDate(args[i + 1]);
                case Argument.TO -> to = parseDate(args[i + 1]);
                case Argument.FORMAT -> format = args[i + 1];
                case null -> {
                    LOGGER.error("Invalid command line arguments");
                    throw new IllegalArgumentException();
                }
            }
        }
        LOGGER.info("Successful argument processing");
    }

    private void checkArgs(String[] args) {
        if (args == null) {
            LOGGER.error("Array of command line arguments is null");
            throw new IllegalArgumentException();
        }
        if (args.length == 0) {
            LOGGER.error("Array of command line arguments is empty");
            throw new IllegalArgumentException();
        }
        if (args.length % 2 != 0) {
            LOGGER.error("Not enough arguments");
            throw new IllegalArgumentException();
        }
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, DATE_FORMATTER);
        } catch (Exception e) {
            LOGGER.error("Error {} parsing date {}", e.getMessage(), date);
            return null;
        }
    }

    public Path analyze() {
        List<Path> files = logProvider.provide(path);
        List<Log> logs = fileParser.parse(files);
        StatisticsHandler statisticsHandler = new StatisticsHandler(from, to, files, logs);
        ReportCreator reportCreator = ReportCreator.getInstance(format);

        return reportCreator.createReport(statisticsHandler);
    }

}
