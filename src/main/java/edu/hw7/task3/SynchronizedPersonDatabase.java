package edu.hw7.task3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SynchronizedPersonDatabase implements PersonDatabase {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Map<Integer, Person> storage = new HashMap<>();

    @Override
    public void add(Person person) {
        synchronized (storage) {
            storage.put(person.id(), person);
            LOGGER.info("Added new person: {}", person);
        }
    }

    @Override
    public void delete(int id) {
        synchronized (storage) {
            Person removed = storage.remove(id);
            LOGGER.info("Removed person: {}", removed);
        }
    }

    @Override
    public List<Person> findByName(String name) {
        List<Person> personsByName;
        synchronized (storage) {
            personsByName = storage.values().stream()
                .filter(person -> person.name().equals(name))
                .toList();
            LOGGER.info("Returned a list of people by name: {}", name);
        }

        return personsByName;
    }

    @Override
    public List<Person> findByAddress(String address) {
        List<Person> personsByAddress;
        synchronized (storage) {
            personsByAddress = storage.values().stream()
                .filter(person -> person.address().equals(address))
                .toList();
            LOGGER.info("Returned a list of people by address: {}", address);
        }

        return personsByAddress;
    }

    @Override
    public List<Person> findByPhone(String phone) {
        List<Person> personsByPhone;
        synchronized (storage) {
            personsByPhone = storage.values().stream()
                .filter(person -> person.phoneNumber().equals(phone))
                .toList();
            LOGGER.info("Returned a list of people by phone: {}", phone);
        }

        return personsByPhone;
    }
}
