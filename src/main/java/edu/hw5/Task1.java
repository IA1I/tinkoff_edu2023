package edu.hw5;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task1 {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");
    private static final String SEPARATOR = " - ";
    private static final int MINUTES_IN_HOUR = 60;
    private static final String SYMBOL_FOR_MINUTES = "м";
    private static final String SYMBOL_FOR_HOURS_AND_SPACE = "ч ";
    private static final String WRONG_INTERVAL_NEGATIVE_DURATION = "Wrong interval, negative duration";
    private static final String WRONG_DATE_TIME_FORMAT = "Wrong date time format";
    private static final String WRONG_SESSION_INTERVAL = "Wrong session interval";
    private static final String EMPTY_INPUT = "Empty input";

    private Task1() {
    }

    public static String getAverageSessionTime(String[] sessions) {
        checkInput(sessions);
        long totalMinutes = 0;
        for (String session : sessions) {
            totalMinutes += getSessionDuration(session);
        }

        return getAverageTime(totalMinutes / sessions.length);
    }

    private static long getSessionDuration(String session) {
        if (session == null) {
            LOGGER.error("Interval is null");
            throw new IllegalArgumentException();
        }
        String[] intervals = getIntervals(session);
        long duration = getDuration(intervals);
        if (duration <= 0) {
            LOGGER.error(WRONG_INTERVAL_NEGATIVE_DURATION);
            throw new IllegalArgumentException(WRONG_INTERVAL_NEGATIVE_DURATION);
        }

        LOGGER.info("Successful calculation of session duration");
        return duration;
    }

    private static long getDuration(String[] intervals) {
        String left = intervals[0];
        String right = intervals[1];
        try {
            LocalDateTime leftDate = LocalDateTime.parse(left, DATE_TIME_FORMAT);
            LocalDateTime rightDate = LocalDateTime.parse(right, DATE_TIME_FORMAT);
            Duration duration = Duration.between(leftDate, rightDate);
            LOGGER.info("Successful calculation of interval duration");
            return duration.toMinutes();
        } catch (DateTimeParseException dateTimeParseException) {
            LOGGER.error(WRONG_DATE_TIME_FORMAT);
            throw new IllegalArgumentException(WRONG_DATE_TIME_FORMAT);
        }
    }

    private static String[] getIntervals(String session) {
        String[] intervals = session.split(SEPARATOR);
        if (intervals.length != 2) {
            LOGGER.error(WRONG_SESSION_INTERVAL);
            throw new IllegalArgumentException(WRONG_SESSION_INTERVAL);
        }

        LOGGER.info("Successful interval parsing");
        return intervals;
    }

    private static String getAverageTime(long averageMinutes) {
        if (averageMinutes < MINUTES_IN_HOUR) {
            return averageMinutes + SYMBOL_FOR_MINUTES;
        } else {
            long hours = averageMinutes / MINUTES_IN_HOUR;
            long minutes = averageMinutes - hours * MINUTES_IN_HOUR;

            LOGGER.info("Successful calculation of average session duration");
            return hours + SYMBOL_FOR_HOURS_AND_SPACE + minutes + SYMBOL_FOR_MINUTES;
        }
    }

    private static void checkInput(String[] sessions) {
        if (sessions == null || sessions.length == 0) {
            LOGGER.error(EMPTY_INPUT);
            throw new IllegalArgumentException(EMPTY_INPUT);
        }

        LOGGER.info("Successful input validation");
    }
}
