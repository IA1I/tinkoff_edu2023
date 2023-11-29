package edu.hw8.task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int PORT = 18080;
    private Socket client;

    public void start() throws IOException {
        client = new Socket("localhost", PORT);
        LOGGER.info("Created a new client");
    }

    public String sendMessage(String msg) throws IOException {
        try (
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        ) {
            out.println(msg);
            LOGGER.info("Wrote to OutputStream: {}", msg);

            String response = in.readLine();
            LOGGER.info("Got a response: {}", response);
            client.close();
            return response;
        }
    }
}
