package edu.hw6;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task5 {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String TASK_URL = "https://hacker-news.firebaseio.com/v0/topstories.json";
    private static final int TIMEOUT = 10;
    private static final String RESPONSE_FROM_SERVER = "Received response from server";
    private static final String SOMETHING_WENT_WRONG = "Something went wrong: {}";

    private Task5() {
    }

    public static long[] hackerNewsTopStories() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(TASK_URL))
                .GET()
                .timeout(Duration.of(TIMEOUT, ChronoUnit.SECONDS))
                .build();
            var response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
            LOGGER.info(RESPONSE_FROM_SERVER);
            return parseResponse(response.body());
        } catch (IOException | InterruptedException | URISyntaxException e) {
            LOGGER.error(SOMETHING_WENT_WRONG, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static String news(long id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://hacker-news.firebaseio.com/v0/item/" + id + ".json"))
                .GET()
                .timeout(Duration.of(TIMEOUT, ChronoUnit.SECONDS))
                .build();
            var response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());
            LOGGER.info(RESPONSE_FROM_SERVER);
            return getTitleFromResponse(response.body());
        } catch (IOException | InterruptedException | URISyntaxException e) {
            LOGGER.error(SOMETHING_WENT_WRONG, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static long[] parseResponse(String response) {
        String[] ids = response.substring(1, response.length() - 1).split(",");
        long[] decimalIds = new long[ids.length];
        for (int i = 0; i < decimalIds.length; i++) {
            decimalIds[i] = Long.parseLong(ids[i]);
        }

        return decimalIds;
    }

    private static String getTitleFromResponse(String response) {
        Pattern pattern = Pattern.compile("^.*\"title\":\"([\\w ]+)\".*$");
        Matcher matcher = pattern.matcher(response);
        matcher.find();
        LOGGER.info("Article title received");
        return matcher.group(1);
    }
}
