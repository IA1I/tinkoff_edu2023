package edu.hw2.Task3.connectionmanagers;

import edu.hw2.Task3.connections.Connection;
import edu.hw2.Task3.connections.FaultyConnection;
import edu.hw2.Task3.connections.StableConnection;
import java.util.Random;
import static edu.hw2.Task3.PopularCommandExecutor.MAXIMUM_VALUE;

public class DefaultConnectionManager implements ConnectionManager {
    private final Random random;

    public DefaultConnectionManager(Random random) {
        this.random = random;
    }

    @Override
    public Connection getConnection() {
        if (random.nextInt(0, MAXIMUM_VALUE) > 1) {
            return new StableConnection();
        } else {
            return new FaultyConnection();
        }
    }
}
