package edu.hw2.Task3.connections;

import edu.hw2.Task3.PopularCommandExecutor;

public class StableConnection implements Connection {
    @Override
    public void execute(String command) {
        PopularCommandExecutor.LOGGER.info(command);
    }

    @Override
    public void close() throws Exception {
        PopularCommandExecutor.LOGGER.info("Stable connection closed");
    }
}
