package edu.hw2.Task3;

import edu.hw2.Task3.connectionmanagers.ConnectionManager;
import edu.hw2.Task3.connections.Connection;
import edu.hw2.Task3.exceptions.ConnectionException;
import java.util.Random;

public class PopularCommandExecutor {
    private final ConnectionManager manager;
    private final int maxAttempts;
    public static final int MAXIMUM_VALUE = 10;

    public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
        this.manager = manager;
        this.maxAttempts = maxAttempts;
    }

    public void updatePackages(Random random) {
        tryExecute("apt update && apt upgrade -y", random);
    }

    void tryExecute(String command, Random random) {
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try (Connection connection = manager.getConnection()) {
                connection.execute(command, random);
                break;
            } catch (ConnectionException connectionException) {
                if (attempt == maxAttempts) {
                    throw new ConnectionException("Number of attempts exceeded", connectionException.getCause());
                }
            } catch (Exception ignored) {

            }
        }
    }

}
