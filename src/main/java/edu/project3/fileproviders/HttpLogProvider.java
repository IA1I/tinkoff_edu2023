package edu.project3.fileproviders;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static java.net.http.HttpClient.newHttpClient;

public class HttpLogProvider implements LogProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int TIMEOUT = 10;

    @Override
    public List<Path> provide(String path) {
        Path filePath = Path.of("logs.txt");
        try {
            var request = HttpRequest.newBuilder()
                .uri(new URI(path))
                .GET()
                .timeout(Duration.of(TIMEOUT, ChronoUnit.SECONDS))
                .build();
            var response = newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofFile(filePath));
            LOGGER.info("Successful writing of logs to a file {}", filePath.toString());
            return List.of(filePath);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            LOGGER.error("Something went wrong when trying to contact {}. {}", path, e.getMessage());
        } catch (NullPointerException | IllegalArgumentException e) {
            LOGGER.info("An empty path was passed: {}", e.getMessage());
        }

        return List.of();
    }
}
