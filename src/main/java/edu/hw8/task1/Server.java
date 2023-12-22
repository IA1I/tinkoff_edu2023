package edu.hw8.task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int NUMBER_OF_THREADS = 5;
    private static final long TIMEOUT = 10L;
    private final ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static final int PORT = 18080;
    private int numberOfResponses;
    private static final Map<String, String> CAROUS_ANSWERS = new HashMap<>();

    static {
        CAROUS_ANSWERS.put("личности", "Не переходи на личности там, где их нет");
        CAROUS_ANSWERS.put(
            "оскорбления",
            "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами"
        );
        CAROUS_ANSWERS.put(
            "глупый",
            " А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма."
        );
        CAROUS_ANSWERS.put("интеллект", "Чем ниже интеллект, тем громче оскорбления");
    }

    public Server(int numberOfResponses) {
        if (numberOfResponses < 1) {
            LOGGER.error("Wrong number of responses {}", numberOfResponses);
            throw new IllegalArgumentException("Wrong number of responses");
        }
        this.numberOfResponses = numberOfResponses;
    }

    public void start() throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        LOGGER.info("Server is bound to a socket: address {}, port {}", server.getInetAddress(), server.getLocalPort());
        while (numberOfResponses > 0) {
            LOGGER.info("Waiting for a client connection");
            Socket client = server.accept();
            numberOfResponses--;
            executorService.submit(() -> sendAnswer(client));
        }
        try {
            executorService.awaitTermination(TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executorService.close();
        server.close();
    }

    private void sendAnswer(Socket client) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        ) {
            LOGGER.info("New client connected: {}", client.getInetAddress());
            String word = in.readLine();
            out.write(CAROUS_ANSWERS.get(word));
            LOGGER.info("Get the request: {}", word);
        } catch (IOException e) {
            LOGGER.error("Something went wrong: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
