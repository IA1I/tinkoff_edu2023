package edu.hw2.Task3.connections;

import java.util.Random;

public interface Connection extends AutoCloseable {
    void execute(String command, Random random);
}
