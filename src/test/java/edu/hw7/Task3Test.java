package edu.hw7;

import edu.hw7.task3.Person;
import edu.hw7.task3.PersonDatabase;
import edu.hw7.task3.ReadWriteLockPersonDatabase;
import edu.hw7.task3.SynchronizedPersonDatabase;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class Task3Test {

    @ParameterizedTest
    @MethodSource("personDatabases")
    void shouldPutToDBAndDeleteFromDB(final PersonDatabase personDatabase) {
        Person person1 = new Person(1, "name 1", "address1", "phone number 1");
        Person person2 = new Person(2, "name 1", "address2", "phone number 2");
        Person person3 = new Person(3, "name 1", "address3", "phone number 3");
        Person person4 = new Person(1, "name 1", "address4", "phone number 4");
        personDatabase.add(person1);
        personDatabase.add(person2);
        personDatabase.add(person3);
        personDatabase.add(person4);

        assertThat(personDatabase.findByName("name 1")).containsExactlyInAnyOrder(person2, person3, person4);
        personDatabase.delete(1);

        assertThat(personDatabase.findByName("name 1")).containsExactlyInAnyOrder(person2, person3);
    }

    static Stream<Arguments> personDatabases() {
        return Stream.of(
            Arguments.of(new SynchronizedPersonDatabase()),
            Arguments.of(new ReadWriteLockPersonDatabase())
        );
    }

    @ParameterizedTest
    @MethodSource("personDatabases")
    void shouldWorkParallel(final PersonDatabase personDatabase) {
        personDatabase.add(new Person(1, "name 1", "address1", "phone number 1"));
        personDatabase.add(new Person(2, "name 2", "address2", "phone number 2"));
        personDatabase.add(new Person(3, "name 3", "address3", "phone number 3"));
        personDatabase.add(new Person(4, "name 4", "address4", "phone number 4"));
        Thread addingThread = new Thread(() -> {
            personDatabase.add(new Person(5, "name 1", "address1", "phone number 1"));
            personDatabase.add(new Person(6, "name 2", "address2", "phone number 2"));
            personDatabase.add(new Person(7, "name 3", "address3", "phone number 3"));
            personDatabase.add(new Person(8, "name 4", "address4", "phone number 4"));
        });

        Thread findByName = new Thread(() -> {
            personDatabase.findByName("name 1");
        });

        Thread findByAddress = new Thread(() -> {
            personDatabase.findByAddress("address2");

        });

        Thread findByPhone = new Thread(() -> {
            personDatabase.findByPhone("phone number 3");

        });
        addingThread.start();
        findByName.start();
        findByAddress.start();
        findByPhone.start();

        try {
            addingThread.join();
            findByName.join();
            findByAddress.join();
            findByPhone.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
