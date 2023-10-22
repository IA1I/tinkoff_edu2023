package edu.hw2.Task3.connections;

import edu.hw2.Task3.exceptions.ConnectionException;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static edu.hw2.Task3.PopularCommandExecutor.MAXIMUM_VALUE;

public class FaultyConnection implements Connection {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void execute(String command, Random random) {
        if (random.nextInt(0, MAXIMUM_VALUE) > 1) {
            LOGGER.info(command);
        } else {
            throw new ConnectionException();
        }
    }

    @Override
    public void close() throws Exception {
        LOGGER.info("Faulty connection closed");
    }
}
