package edu.project3;

import edu.project3.fileparsers.FileParser;
import edu.project3.fileparsers.Log;
import edu.project3.fileparsers.SimpleFileParser;
import edu.project3.statistics_handlers.StatisticsHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class StatisticsHandlerTest {
    final static String testLogs1 =
        "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"\n" +
            "93.180.71.3 - - [17/May/2015:08:05:23 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"\n" +
            "80.91.33.133 - - [17/May/2015:08:05:24 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)\"\n" +
            "fnsalf\n" +
            "034lkfs;lmsckc089732ho21;lsmdnv93";
    final static String testLogs2 =
        "m,c;lklck-qi481038hfrslidfs\n" +
            "128.199.51.40 - - [04/Jun/2015:07:06:34 +0000] \"GET /downloads/product_2 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.9.7.9)\"\n" +
            "173.255.199.22 - - [04/Jun/2015:07:06:08 +0000] \"GET /downloads/product_2 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.10.3)\"\n" +
            "94.23.169.246 - - [04/Jun/2015:07:06:43 +0000] \"GET /downloads/product_1 HTTP/1.1\" 404 346 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.22)\"\n" +
            "192.235.75.62 - - [04/Jun/2015:07:06:28 +0000] \"GET /downloads/product_1 HTTP/1.1\" 404 332 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.12)\"\n";
    static List<Path> files;

    @BeforeAll
    static void preparation() {
        files = List.of(Path.of("testlog1.txt"), Path.of("testlog2.txt"));
        try {
            Files.writeString(files.get(0), testLogs1);
            Files.writeString(files.get(1), testLogs2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldReturnEmptyListOfLogsForNullInput() {
        StatisticsHandler statisticsHandler = new StatisticsHandler(null, null, null, null);

        assertThat(statisticsHandler.getTheHeaviestLog()).isNull();
        assertThat(statisticsHandler.getAverageServerResponseSize()).isCloseTo(0.0, Percentage.withPercentage(10));
        assertThat(statisticsHandler.getMostCommonResponseCodes()).isEmpty();
        assertThat(statisticsHandler.getMostFrequentlyIps()).isEmpty();
        assertThat(statisticsHandler.getMostFrequentlyRequestedResources()).isEmpty();
        assertThat(statisticsHandler.getTotalNumberOfRequests()).isEqualTo(0);
    }

    @Test
    void shouldReturnEmptyListOfLogsForEmptyInput() {
        StatisticsHandler statisticsHandler = new StatisticsHandler(null, null, List.of(), List.of());

        assertThat(statisticsHandler.getTheHeaviestLog()).isNull();
        assertThat(statisticsHandler.getAverageServerResponseSize()).isCloseTo(0.0, Percentage.withPercentage(10));
        assertThat(statisticsHandler.getMostCommonResponseCodes()).isEmpty();
        assertThat(statisticsHandler.getMostFrequentlyIps()).isEmpty();
        assertThat(statisticsHandler.getMostFrequentlyRequestedResources()).isEmpty();
        assertThat(statisticsHandler.getTotalNumberOfRequests()).isEqualTo(0);
    }

    @Test
    void shouldReturnStatistic() {
        Log expectedLog = new Log(
            "94.23.169.246",
            "-",
            OffsetDateTime.parse(
                "04/Jun/2015:07:06:43 +0000",
                DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.US)
            ),
            "GET /downloads/product_1 HTTP/1.1",
            404,
            346,
            "-",
            "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.22)"
        );
        double expectedAverageServerResponseSize = 96.85714285714286;
        List<Map.Entry<Integer, Long>> expectedMostCommonResponseCodes = List.of(
            new AbstractMap.SimpleEntry<>(304, 5L),
            new AbstractMap.SimpleEntry<>(404, 2L)
        );
        List<Map.Entry<String, Long>> expectedMostFrequentlyIps = List.of(
            new AbstractMap.SimpleEntry<>("93.180.71.3", 2L),
            new AbstractMap.SimpleEntry<>("80.91.33.133", 1L),
            new AbstractMap.SimpleEntry<>("94.23.169.246", 1L),
            new AbstractMap.SimpleEntry<>("128.199.51.40", 1L),
            new AbstractMap.SimpleEntry<>("173.255.199.22", 1L),
            new AbstractMap.SimpleEntry<>("192.235.75.62", 1L)
        );
        List<Map.Entry<String, Long>> expectedMostFrequentlyRequestedResources = List.of(
            new AbstractMap.SimpleEntry<>("/product_1", 5L),
            new AbstractMap.SimpleEntry<>("/product_2", 2L)
        );
        long expectedTotalNumberOfRequests = 7L;

        FileParser fileParser = new SimpleFileParser();
        List<Log> logs = fileParser.parse(files);
        StatisticsHandler statisticsHandler = new StatisticsHandler(null, null, files, logs);

        assertThat(statisticsHandler.getTheHeaviestLog()).isEqualTo(expectedLog);
        assertThat(statisticsHandler.getAverageServerResponseSize()).isCloseTo(
            expectedAverageServerResponseSize,
            Percentage.withPercentage(10)
        );
        assertThat(statisticsHandler.getMostCommonResponseCodes()).containsExactlyElementsOf(
            expectedMostCommonResponseCodes);
        assertThat(statisticsHandler.getMostFrequentlyIps()).containsExactlyElementsOf(expectedMostFrequentlyIps);
        assertThat(statisticsHandler.getMostFrequentlyRequestedResources()).containsExactlyElementsOf(
            expectedMostFrequentlyRequestedResources);
        assertThat(statisticsHandler.getTotalNumberOfRequests()).isEqualTo(expectedTotalNumberOfRequests);
    }

    @ParameterizedTest
    @MethodSource("testCasesForFilter")
    void shouldFilterLogs(final LocalDate from, final LocalDate to, final long expected) {
        FileParser fileParser = new SimpleFileParser();
        List<Log> logs = fileParser.parse(files);
        StatisticsHandler statisticsHandler = new StatisticsHandler(from, to, files, logs);
        long actual = statisticsHandler.getTotalNumberOfRequests();
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> testCasesForFilter() {
        return Stream.of(
            Arguments.of(LocalDate.parse("2015-05-16", DateTimeFormatter.ISO_DATE), null, 7L),
            Arguments.of(null, LocalDate.parse("2015-06-05", DateTimeFormatter.ISO_DATE), 7L),
            Arguments.of(
                LocalDate.parse("2015-05-30", DateTimeFormatter.ISO_DATE),
                LocalDate.parse("2015-06-05", DateTimeFormatter.ISO_DATE),
                4L
            )
        );
    }

    @AfterAll
    static void ending() {
        for (Path file : files) {
            try {
                Files.delete(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
