package edu.project3;

import edu.project3.fileparsers.FileParser;
import edu.project3.fileparsers.Log;
import edu.project3.fileparsers.SimpleFileParser;
import edu.project3.report_creators.MarkdownReportCreator;
import edu.project3.report_creators.ReportCreator;
import edu.project3.statistics_handlers.StatisticsHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MarkdownReportCreatorTest {
    final static String expectedText = "#### Общая информация" +
        "|Метрика|Значение|" +
        "|:-:|-:|" +
        "|Файл(-ы)|\\'testlog1.txt\\' \\'testlog2.txt\\' |" +
        "|Начальная дата|-|" +
        "|Конечная дата|-|" +
        "|Количество запросов|7|" +
        "|Средний размер ответа|96.85714285714286b|" +
        "#### Запрашиваемые ресурсы" +
        "|Ресурс|Количество|" +
        "|:-:|-:|" +
        "|\\'/product_1\\'|5|" +
        "|\\'/product_2\\'|2|" +
        "#### Коды ответа" +
        "|Код|Имя|Количество|" +
        "|:-:|:-:|-:|" +
        "|304|Not Modified|5|" +
        "|404|Not Found|2|" +
        "#### Удаленные адреса" +
        "|Адрес|Количество обращений|" +
        "|-:|-:|" +
        "|93.180.71.3|2|" +
        "|80.91.33.133|1|" +
        "|94.23.169.246|1|" +
        "|128.199.51.40|1|" +
        "|173.255.199.22|1|" +
        "|192.235.75.62|1|" +
        "#### Самый тяжелый ответ" +
        "|Поле|Значение|" +
        "|:-:|-:|" +
        "|remote address|94.23.169.246|" +
        "|remote user|-|" +
        "|local time|2015-06-04T07:06:43Z|" +
        "|request|GET /downloads/product_1 HTTP/1.1|" +
        "|status|404|" +
        "|body bytes sent|346|" +
        "|http referer|-|" +
        "|http user agent|Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.22)|";
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
    static StatisticsHandler statisticsHandler;

    @BeforeAll
    static void preparation() {
        files = List.of(Path.of("testlog1.txt"), Path.of("testlog2.txt"));
        try {
            Files.writeString(files.get(0), testLogs1);
            Files.writeString(files.get(1), testLogs2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FileParser fileParser = new SimpleFileParser();
        List<Log> logs = fileParser.parse(files);
        statisticsHandler = new StatisticsHandler(null, null, files, logs);
    }

    @Test
    void shouldReturnPathForNullInput() {
        ReportCreator reportCreator = new MarkdownReportCreator();
        Path actual = reportCreator.createReport(null);
        String expectedFileName = "log report.md";

        assertThat(Files.exists(actual)).isTrue();
        assertThat(Files.isRegularFile(actual)).isTrue();
        assertThat(actual.getFileName().toString()).isEqualTo(expectedFileName);
    }

    @Test
    void shouldReturnPathForCorrectInput() throws IOException {
        ReportCreator reportCreator = new MarkdownReportCreator();
        Path actual = reportCreator.createReport(statisticsHandler);
        String expectedFileName = "log report.md";

        assertThat(Files.exists(actual)).isTrue();
        assertThat(Files.isRegularFile(actual)).isTrue();
        assertThat(actual.getFileName().toString()).isEqualTo(expectedFileName);
        List<String> lines = Files.readAllLines(actual);
        StringBuilder actualText = new StringBuilder();
        for (String line : lines) {
            actualText.append(line);
        }
        assertThat(actualText.toString()).isEqualTo(expectedText);
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

        try {
            Files.delete(Path.of("log report.md"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
