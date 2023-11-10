package edu.hw5.task3;

import edu.hw5.task3.date_parser.DateParser;
import edu.hw5.task3.date_parser.DaysAgoDateParser;
import edu.hw5.task3.date_parser.FirstDateParser;
import edu.hw5.task3.date_parser.FourthDateParser;
import edu.hw5.task3.date_parser.SecondDateParser;
import edu.hw5.task3.date_parser.ThirdDateParser;
import edu.hw5.task3.date_parser.TodayDateParser;
import edu.hw5.task3.date_parser.TomorrowDateParser;
import edu.hw5.task3.date_parser.YesterdayDateParser;
import java.time.LocalDate;
import java.util.Optional;

public class Task3 {
    private Task3() {
    }

    public static Optional<LocalDate> parseDate(String string) {
        if (isNullInput(string)) {
            return Optional.empty();
        }
        DateParser parser =
            new FirstDateParser(new SecondDateParser(new ThirdDateParser(new FourthDateParser(new TomorrowDateParser(
                new TodayDateParser(new YesterdayDateParser(new DaysAgoDateParser(null))))))));
        return parser.parseDate(string);
    }

    private static boolean isNullInput(String string) {
        return string == null;
    }
}
