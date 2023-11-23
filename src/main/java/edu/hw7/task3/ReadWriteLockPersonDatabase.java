package edu.hw7.task3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReadWriteLockPersonDatabase implements PersonDatabase {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Map<Integer, Person> storage = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    @Override
    public void add(Person person) {
        try {
            writeLock.lock();
            storage.put(person.id(), person);
            LOGGER.info("Added new person: {}", person);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void delete(int id) {
        try {
            writeLock.lock();
            Person removed = storage.remove(id);
            LOGGER.info("Removed person: {}", removed);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public List<Person> findByName(String name) {
        List<Person> personsByName;
        try {
            readLock.lock();
            personsByName = storage.values().stream()
                .filter(person -> person.name().equals(name))
                .toList();
            LOGGER.info("Returned a list of people by name: {}", name);
        } finally {
            readLock.unlock();
        }
        return personsByName;
    }

    @Override
    public List<Person> findByAddress(String address) {
        List<Person> personsByAddress;
        try {
            readLock.lock();
            personsByAddress = storage.values().stream()
                .filter(person -> person.address().equals(address))
                .toList();
            LOGGER.info("Returned a list of people by address: {}", address);
        } finally {
            readLock.unlock();
        }
        return personsByAddress;
    }

    @Override
    public List<Person> findByPhone(String phone) {
        List<Person> personsByPhone;
        try {
            readLock.lock();
            personsByPhone = storage.values().stream()
                .filter(person -> person.phoneNumber().equals(phone))
                .toList();
            LOGGER.info("Returned a list of people by phone: {}", phone);
        } finally {
            readLock.unlock();
        }
        return personsByPhone;
    }
}
