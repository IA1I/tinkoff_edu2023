package edu.hw2.Task3.connections;

import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StableConnection implements Connection {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void execute(String command, Random random) {
        LOGGER.info(command);
    }

    @Override
    public void close() throws Exception {
        LOGGER.info("Stable connection closed");
    }
}
