package edu.hw5.task3.date_parser;

import java.time.LocalDate;
import java.util.Optional;

public class TodayDateParser extends DateParser {
    private static final String FORMATTER = "today";

    public TodayDateParser() {
    }

    public TodayDateParser(DateParser nextDateParser) {
        super(nextDateParser);
    }

    @Override
    public Optional<LocalDate> parseDate(String format) {
        if (format.equals(FORMATTER)) {
            return Optional.of(LocalDate.now());
        } else if (nextDateParser != null) {
            return nextDateParser.parseDate(format);
        } else {
            return Optional.empty();
        }
    }
}
