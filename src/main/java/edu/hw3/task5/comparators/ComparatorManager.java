package edu.hw3.task5.comparators;

import edu.hw3.task5.Contact;
import edu.hw3.task5.SortType;
import java.util.Comparator;

public class ComparatorManager {
    private ComparatorManager() {
    }

    public static Comparator<Contact> getComparator(SortType sortType) {
        return switch (sortType) {
            case ASC -> new NaturalsOrderComparator();
            case DESC -> new ReverseComparator();
        };
    }
}
