package edu.hw5.task3.date_parser;

import java.time.LocalDate;
import java.util.Optional;

public class YesterdayDateParser extends DateParser {
    private static final String FORMATTER_YESTERDAY = "yesterday";
    private static final String FORMATTER_DAY_AGO = "1 day ago";

    public YesterdayDateParser() {
    }

    public YesterdayDateParser(DateParser nextDateParser) {
        super(nextDateParser);
    }

    @Override
    public Optional<LocalDate> parseDate(String format) {
        if (format.equals(FORMATTER_YESTERDAY) || format.equals(FORMATTER_DAY_AGO)) {
            return Optional.of(LocalDate.now().minusDays(1L));
        } else if (nextDateParser != null) {
            return nextDateParser.parseDate(format);
        } else {
            return Optional.empty();
        }
    }
}
