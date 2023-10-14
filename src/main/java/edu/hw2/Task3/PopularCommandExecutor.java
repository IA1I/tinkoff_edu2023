package edu.hw2.Task3;

import edu.hw2.Task3.connectionmanagers.ConnectionManager;
import edu.hw2.Task3.connections.Connection;
import edu.hw2.Task3.exceptions.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PopularCommandExecutor {
    public static final Logger LOGGER = LogManager.getLogger();
    private final ConnectionManager manager;
    private final int maxAttempts;

    public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
        this.manager = manager;
        this.maxAttempts = maxAttempts;
    }

    public void updatePackages() {
        tryExecute("apt update && apt upgrade -y");
    }

    void tryExecute(String command){
        Connection connection = manager.getConnection();
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try{
                connection.execute(command);
                break;
            } catch (ConnectionException ce) {
                if (attempt == maxAttempts) {
                    try {
                        connection.close();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    throw new ConnectionException("Number of attempts exceeded", ce.getCause());
                }
            } catch (Exception ignored) {

            }
        }
        try {
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
