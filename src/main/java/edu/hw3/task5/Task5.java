package edu.hw3.task5;

import edu.hw3.task5.comparators.ComparatorManager;
import java.util.Arrays;

public class Task5 {

    private Task5() {
    }

    public static Contact[] parseContacts(String[] names, String type) {
        Contact[] contacts = parseContacts(names);
        SortType sortType = getSortType(type);
        Arrays.sort(contacts, ComparatorManager.getComparator(sortType));
        return contacts;
    }

    private static Contact[] parseContacts(String[] names) {
        if (names == null) {
            return new Contact[] {};
        }
        Contact[] contacts = new Contact[names.length];
        for (int index = 0; index < names.length; index++) {
            contacts[index] = Contact.of(names[index]);
        }

        return contacts;
    }

    private static SortType getSortType(String type) {
        SortType sortType;
        try {
            sortType = SortType.valueOf(type);
        } catch (NullPointerException exception) {
            throw new IllegalArgumentException();
        }

        return sortType;
    }
}
