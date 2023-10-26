package edu.hw3.task5.comparators;

import edu.hw3.task5.Contact;
import java.util.Comparator;

public class ReverseComparator implements Comparator<Contact> {
    @Override
    public int compare(Contact o1, Contact o2) {
        if (o1 == o2) {
            return 0;
        }
        if (o2.getLastName().equals(o1.getLastName())) {
            return o2.getFirstName().compareTo(o1.getFirstName());
        }
        return o2.getLastName().compareTo(o1.getLastName());
    }
}
