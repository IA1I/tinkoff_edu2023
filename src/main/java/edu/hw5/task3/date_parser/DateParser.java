package edu.hw5.task3.date_parser;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public abstract class DateParser {
    public DateParser nextDateParser;

    public DateParser() {
    }

    public DateParser(DateParser nextDateParser) {
        this.nextDateParser = nextDateParser;
    }

    public abstract Optional<LocalDate> parseDate(String format);

    public static DateParser getParser(List<DateParser> parsers) {
        if (parsers == null || parsers.isEmpty()) {
            throw new IllegalArgumentException();
        }
        DateParser parser = parsers.get(0);
        DateParser currentParser = parser;
        for (int i = 1; i < parsers.size(); i++) {
            currentParser.nextDateParser = parsers.get(i);
            currentParser = currentParser.nextDateParser;
        }

        return parser;
    }
}
