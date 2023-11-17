package edu.hw5;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class Task2 {

    public static final int DAY_TO_FIND = 13;
    public static final int NUMBER_OF_MONTHS = 12;

    private Task2() {
    }

    public static List<LocalDate> getAllFriday13th(int year) {
        checkInput(year);
        List<LocalDate> fridays13th = new ArrayList<>();
        LocalDate date = LocalDate.of(year, 1, DAY_TO_FIND);
        for (int month = 1; month <= NUMBER_OF_MONTHS; month++) {
            if (date.getDayOfWeek() == DayOfWeek.FRIDAY) {
                fridays13th.add(date);
            }
            date = date.plusMonths(1L);
        }

        return fridays13th;
    }

    public static LocalDate getNextFriday13th(LocalDate date) {
        isNullDate(date);
        LocalDate nextDate = LocalDate.from(date);
        do {
            nextDate = nextDate.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        } while (nextDate.getDayOfMonth() != DAY_TO_FIND);

        return nextDate;
    }

    private static void isNullDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date is null");
        }
    }

    private static void checkInput(int year) {
        if (year < 1) {
            throw new IllegalArgumentException("Wrong year");
        }
    }
}
