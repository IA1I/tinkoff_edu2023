package edu.hw3.task5;

import java.util.Objects;

public class Contact {
    public static final String SEPARATOR_CHARACTER = " ";
    private String firstName;
    private String lastName;

    public Contact(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Contact(String firstName) {
        this(firstName, "");
    }

    public static Contact of(String name) {
        if (name.isEmpty()) {
            return new Contact("", "");
        }
        String[] fullName = name.split(SEPARATOR_CHARACTER);
        return switch (fullName.length) {
            case 2 -> new Contact(fullName[0], fullName[1]);
            case 1 -> new Contact(fullName[0], "");
            default -> throw new IllegalArgumentException();
        };
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        return firstName.equals(contact.getFirstName()) && lastName.equals(contact.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
