package edu.hw3.task5.comparators;

import edu.hw3.task5.Contact;
import java.util.Comparator;

public class NaturalsOrderComparator implements Comparator<Contact> {
    @Override
    public int compare(Contact o1, Contact o2) {
        if (o1 == o2) {
            return 0;
        }
        if (o1.getLastName().equals(o2.getLastName())) {
            return o1.getFirstName().compareTo(o2.getFirstName());
        }
        return o1.getLastName().compareTo(o2.getLastName());
    }
}
