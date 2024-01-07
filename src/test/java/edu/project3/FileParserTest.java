package edu.project3;

import edu.project3.fileparsers.FileParser;
import edu.project3.fileparsers.Log;
import edu.project3.fileparsers.SimpleFileParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FileParserTest {
    final static String testLogs1 =
        "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"\n";
    final static String testLogs2 = "m,c;lklck-qi481038hfrslidfs\n";
    static List<Path> files;
    static List<Log> expected;

    @BeforeAll
    static void preparation() {
        files = List.of(Path.of("testlog1.txt"), Path.of("testlog2.txt"));
        expected = getExpected();
        try {
            Files.writeString(files.get(0), testLogs1);
            Files.writeString(files.get(1), testLogs2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static List<Log> getExpected() {
        List<Log> list = new ArrayList<>();
        list.add(new Log(
            "93.180.71.3",
            "-",
            OffsetDateTime.parse(
                "17/May/2015:08:05:32 +0000",
                DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.US)
            ),
            "GET /downloads/product_1 HTTP/1.1",
            304,
            0,
            "-",
            "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
        ));

        return list;
    }

    @Test
    void shouldReturnEmptyListOfLogsForNullInput() {
        FileParser fileParser = new SimpleFileParser();
        List<Log> actual = fileParser.parse(null);

        assertThat(actual).isEmpty();
    }

    @Test
    void shouldReturnEmptyListOfLogsForEmptyList() {
        FileParser fileParser = new SimpleFileParser();
        List<Log> actual = fileParser.parse(List.of());

        assertThat(actual).isEmpty();
    }

    @Test
    void shouldReturnListOfLogs() {
        FileParser fileParser = new SimpleFileParser();
        List<Log> actual = fileParser.parse(files);

        assertThat(actual).containsExactlyElementsOf(expected);
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
