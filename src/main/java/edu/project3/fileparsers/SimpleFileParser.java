package edu.project3.fileparsers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleFileParser implements FileParser {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String REGEX_FOR_IP = "((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}";
    private static final String REGEX_FOR_LOG = "^(.*) - (.*) \\[(.*)] \"(.*)\" (\\d{3}) (\\d+) \"(.*)\" \"(.*)\"$";
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);
    private static final int GROUP_FOR_REMOTE_ADDRESS = 1;
    private static final int GROUP_FOR_REMOTE_USER = 2;
    private static final int GROUP_FOR_DATE = 3;
    private static final int GROUP_FOR_REQUEST = 4;
    private static final int GROUP_FOR_STATUS = 5;
    private static final int GROUP_FOR_BODY_BYTE_SENT = 6;
    private static final int GROUP_FOR_HTTP_REFERER = 7;
    private static final int GROUP_FOR_HTTP_USER_AGENT = 8;

    @Override
    public List<Log> parse(List<Path> files) {
        List<Log> logs = new ArrayList<>();
        if (files == null) {
            LOGGER.info("Incoming file list is null");
            return logs;
        }
        for (Path file : files) {
            List<String> currentFile = readFromFile(file);
            LOGGER.info("Successful reading from file {}", file.getFileName().toString());
            for (String log : currentFile) {
                if (log.matches(REGEX_FOR_LOG)) {
                    logs.add(parseLog(log));
                }
            }
        }

        return logs;
    }

    private List<String> readFromFile(Path file) {
        try {
            return Files.readAllLines(file);
        } catch (IOException e) {
            LOGGER.error("Error {} reading from file {}", e.getMessage(), file.getFileName().toString());
            return List.of();
        }
    }

    private Log parseLog(String log) {
        Pattern pattern = Pattern.compile(REGEX_FOR_LOG);
        Matcher matcher = pattern.matcher(log);
        matcher.find();

        String remoteAddress = matcher.group(GROUP_FOR_REMOTE_ADDRESS);
        String remoteUser = matcher.group(GROUP_FOR_REMOTE_USER);
        OffsetDateTime date = parseDate(matcher.group(GROUP_FOR_DATE));
        String request = matcher.group(GROUP_FOR_REQUEST);
        int status = Integer.parseInt(matcher.group(GROUP_FOR_STATUS));
        int bodyBytesSent = Integer.parseInt(matcher.group(GROUP_FOR_BODY_BYTE_SENT));
        String httpReferer = matcher.group(GROUP_FOR_HTTP_REFERER);
        String httpUserAgent = matcher.group(GROUP_FOR_HTTP_USER_AGENT);

        return new Log(remoteAddress, remoteUser, date, request, status, bodyBytesSent, httpReferer, httpUserAgent);
    }

    private OffsetDateTime parseDate(String date) {
        try {
            return OffsetDateTime.parse(date, DATE_TIME_FORMATTER);
        } catch (Exception e) {
            LOGGER.error("Error {} parsing date {}", e.getMessage(), date);
            return null;
        }
    }
}
