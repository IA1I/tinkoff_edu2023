package edu.hw5.task3.date_parser;

import java.time.LocalDate;
import java.util.Optional;

public abstract class DateParser {
    public DateParser nextDateParser;

    public DateParser(DateParser nextDateParser) {
        this.nextDateParser = nextDateParser;
    }

    public abstract Optional<LocalDate> parseDate(String format);
}
