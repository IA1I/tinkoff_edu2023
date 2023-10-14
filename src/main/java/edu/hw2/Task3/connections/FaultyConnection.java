package edu.hw2.Task3.connections;

import edu.hw2.Task3.Chance;
import edu.hw2.Task3.PopularCommandExecutor;
import edu.hw2.Task3.exceptions.ConnectionException;

public class FaultyConnection implements Connection {

    @Override
    public void execute(String command) {
        if (Chance.getChance()) {
            PopularCommandExecutor.LOGGER.info(command);
        } else {
            throw new ConnectionException();
        }
    }

    @Override
    public void close() throws Exception {
        PopularCommandExecutor.LOGGER.info("Faulty connection closed");
    }
}
