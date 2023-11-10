package edu.hw5.task3.date_parser;

import java.time.LocalDate;
import java.util.Optional;

public class DaysAgoDateParser extends DateParser {

    public static final String FORMAT_PATTERN = "^(\\d)+ days ago$";
    public static final String SEPARATOR = " ";
    public static final int INDEX_OF_NUMBER = 0;

    public DaysAgoDateParser(DateParser nextDateParser) {
        super(nextDateParser);
    }

    @Override
    public Optional<LocalDate> parseDate(String format) {
        if (format.matches(FORMAT_PATTERN)) {
            long minusDays = getNumber(format);
            return Optional.of(LocalDate.now().minusDays(minusDays));
        } else {
            return Optional.empty();
        }
    }

    private long getNumber(String format) {
        String[] parts = format.split(SEPARATOR);
        return Long.parseLong(parts[INDEX_OF_NUMBER]);
    }
}
