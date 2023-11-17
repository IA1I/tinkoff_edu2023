package edu.hw5.task3.date_parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class FirstDateParser extends DateParser {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public FirstDateParser() {
    }

    public FirstDateParser(DateParser nextDateParser) {
        super(nextDateParser);
    }

    @Override
    public Optional<LocalDate> parseDate(String format) {
        try {
            LocalDate date = LocalDate.parse(format, FORMATTER);
            return Optional.of(date);
        } catch (DateTimeParseException dateTimeParseException) {
            if (nextDateParser != null) {
                return nextDateParser.parseDate(format);
            } else {
                return Optional.empty();
            }
        }
    }
}
