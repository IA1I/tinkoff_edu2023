package edu.hw5;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Task1 {
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");
    private static final String SEPARATOR = " - ";
    private static final int MINUTES_IN_HOUR = 60;

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
            throw new IllegalArgumentException();
        }
        String[] intervals = getIntervals(session);
        long duration = getDuration(intervals);
        if (duration <= 0) {
            throw new IllegalArgumentException("Wrong interval, negative duration");
        }
        return duration;
    }

    private static long getDuration(String[] intervals) {
        String left = intervals[0];
        String right = intervals[1];
        try {
            LocalDateTime leftDate = LocalDateTime.parse(left, DATE_TIME_FORMAT);
            LocalDateTime rightDate = LocalDateTime.parse(right, DATE_TIME_FORMAT);
            Duration duration = Duration.between(leftDate, rightDate);
            return duration.toMinutes();
        } catch (DateTimeParseException dateTimeParseException) {
            throw new IllegalArgumentException("Wrong date time format");
        }
    }

    private static String[] getIntervals(String session) {
        String[] intervals = session.split(SEPARATOR);
        if (intervals.length != 2) {
            throw new IllegalArgumentException("Wrong session interval");
        }
        return intervals;
    }

    private static String getAverageTime(long averageMinutes) {
        if (averageMinutes < MINUTES_IN_HOUR) {
            return averageMinutes + "м";
        } else {
            long hours = averageMinutes / MINUTES_IN_HOUR;
            long minutes = averageMinutes - hours * MINUTES_IN_HOUR;
            return hours + "ч " + minutes + "м";
        }
    }

    private static void checkInput(String[] sessions) {
        if (sessions == null || sessions.length == 0) {
            throw new IllegalArgumentException("Empty input");
        }
    }
}
