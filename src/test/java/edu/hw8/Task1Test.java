package edu.hw8;

import edu.hw8.task1.Client;
import edu.hw8.task1.Server;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

public class Task1Test {

    @Test
    void shouldGetResponseFromServerFor1Client() throws IOException, InterruptedException {
        String expected = "Не переходи на личности там, где их нет";
        Thread serverThread = new Thread(() -> {
            Server server = new Server(1);
            try {
                server.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.start();
        Thread.sleep(3000);
        Client client = new Client();
        client.start();
        String actual = client.sendMessage("личности");
        serverThread.join();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldGetResponsesFromServerFor3Client() throws IOException, InterruptedException {
        String[] expected = new String[] {"Не переходи на личности там, где их нет",
            "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами",
            " А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма."};
        Thread serverThread = new Thread(() -> {
            Server server = new Server(3);
            try {
                server.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.start();
        Thread.sleep(3000);
        Thread[] clients = new Thread[3];
        String[] messages = new String[] {"личности", "оскорбления", "глупый"};
        String[] actual = new String[3];
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            clients[i] = new Thread(() -> {
                Client client = new Client();
                try {
                    client.start();
                    String response = client.sendMessage(messages[finalI]);
                    actual[finalI] = response;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        for (Thread client : clients) {
            client.start();
        }
        for (Thread client : clients) {
            client.join();
        }
        serverThread.join();
        assertThat(actual).containsExactly(expected);
    }
}
