package edu.project3;

import edu.project3.fileparsers.SimpleFileParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LogAnalyzerTest {
    static final String testLogs =
        "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"\n" +
            "93.180.71.3 - - [17/May/2015:08:05:23 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"\n" +
            "80.91.33.133 - - [17/May/2015:08:05:24 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)\"\n" +
            "fnsalf\n" +
            "034lkfs;lmsckc089732ho21;lsmdnv93\n" +
            "m,c;lklck-qi481038hfrslidfs\n" +
            "128.199.51.40 - - [04/Jun/2015:07:06:34 +0000] \"GET /downloads/product_2 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.9.7.9)\"\n" +
            "173.255.199.22 - - [04/Jun/2015:07:06:08 +0000] \"GET /downloads/product_2 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.10.3)\"\n" +
            "94.23.169.246 - - [04/Jun/2015:07:06:43 +0000] \"GET /downloads/product_1 HTTP/1.1\" 404 346 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.22)\"\n" +
            "192.235.75.62 - - [04/Jun/2015:07:06:28 +0000] \"GET /downloads/product_1 HTTP/1.1\" 404 332 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.12)\"\n" +
            "79.136.114.202 - - [04/Jun/2015:07:06:04 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.22)\"\n" +
            "192.235.75.62 - - [04/Jun/2015:07:06:41 +0000] \"GET /downloads/product_1 HTTP/1.1\" 404 334 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.12)\"\n" +
            "212.83.148.198 - - [04/Jun/2015:07:06:47 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.9.7.9)\"\n" +
            "94.236.106.132 - - [04/Jun/2015:07:06:03 +0000] \"GET /downloads/product_1 HTTP/1.1\" 404 332 \"-\" \"Debian APT-HTTP/1.3 (0.9.7.9)\"\n" +
            "79.136.114.202 - - [04/Jun/2015:07:06:29 +0000] \"GET /downloads/product_1 HTTP/1.1\" 404 319 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.22)\"\n" +
            "80.91.33.133 - - [04/Jun/2015:07:06:14 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.22)\"\n" +
            "79.136.114.202 - - [04/Jun/2015:07:06:52 +0000] \"GET /downloads/product_1 HTTP/1.1\" 200 490 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.22)\"\n" +
            "79.136.114.202 - - [04/Jun/2015:07:06:50 +0000] \"GET /downloads/product_1 HTTP/1.1\" 404 334 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.22)\"\n" +
            "80.91.33.133 - - [04/Jun/2015:07:06:16 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.16)\"\n" +
            "80.91.33.133 - - [04/Jun/2015:07:06:30 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.16)\"\n" +
            "192.235.75.62 - - [04/Jun/2015:07:06:45 +0000] \"GET /downloads/product_1 HTTP/1.1\" 404 331 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.12)\"\n" +
            "141.138.90.60 - - [04/Jun/2015:07:06:46 +0000] \"GET /downloads/product_2 HTTP/1.1\" 200 3316 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.15)\"\n" +
            "141.138.90.60 - - [04/Jun/2015:07:06:31 +0000] \"GET /downloads/product_2 HTTP/1.1\" 200 490 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.15)\"\n" +
            "173.255.199.22 - - [04/Jun/2015:07:06:04 +0000] \"GET /downloads/product_2 HTTP/1.1\" 404 339 \"-\" \"Debian APT-HTTP/1.3 (0.8.10.3)\"\n" +
            "54.186.10.255 - - [04/Jun/2015:07:06:05 +0000] \"GET /downloads/product_2 HTTP/1.1\" 200 2582 \"-\" \"urlgrabber/3.9.1 yum/3.4.3\"\n" +
            "80.91.33.133 - - [04/Jun/2015:07:06:16 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.16)\"\n" +
            "144.76.151.58 - - [04/Jun/2015:07:06:05 +0000] \"GET /downloads/product_2 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.9.7.9)\"\n" +
            "79.136.114.202 - - [04/Jun/2015:07:06:35 +0000] \"GET /downloads/product_1 HTTP/1.1\" 404 334 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.22)\"\n";
    static String expectedDefault =
        "#### Общая информация|Метрика|Значение||:-:|-:||Файл(-ы)|\\'log.txt\\' ||Начальная дата|-||Конечная дата|-||Количество запросов|25||Средний размер ответа|395.16b|#### Запрашиваемые ресурсы|Ресурс|Количество||:-:|-:||\\'/product_1\\'|18||\\'/product_2\\'|7|#### Коды ответа|Код|Имя|Количество||:-:|:-:|-:||304|Not Modified|12||404|Not Found|9||200|OK|4|#### Удаленные адреса|Адрес|Количество обращений||-:|-:||80.91.33.133|5||79.136.114.202|5||192.235.75.62|3||93.180.71.3|2||141.138.90.60|2||173.255.199.22|2||144.76.151.58|1||94.236.106.132|1||94.23.169.246|1||128.199.51.40|1||54.186.10.255|1||212.83.148.198|1|#### Самый тяжелый ответ|Поле|Значение||:-:|-:||remote address|141.138.90.60||remote user|-||local time|2015-06-04T07:06:46Z||request|GET /downloads/product_2 HTTP/1.1||status|200||body bytes sent|3316||http referer|-||http user agent|Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.15)|";
    static String expectedADOC =
        "==== Общая информация[cols=2]|====|Метрика|Значение|Файл|'log.txt'|Начальная дата|-|Конечная дата|-|Количество запросов25|Средний размер ответа395.16b|======== Запрашиваемые ресурсы[cols=2]|====|Ресурс|Количество|'/product_1'|18|'/product_2'|7|======== Коды ответа[cols=3]|====|Код|Имя|Количество|304|Not Modified|12|404|Not Found|9|200|OK|4|======== Удаленные адреса[cols=2]|====|Адрес|Количество обращений|80.91.33.133|5|79.136.114.202|5|192.235.75.62|3|93.180.71.3|2|141.138.90.60|2|173.255.199.22|2|144.76.151.58|1|94.236.106.132|1|94.23.169.246|1|128.199.51.40|1|54.186.10.255|1|212.83.148.198|1|======== Самый тяжелый ответ[cols=2]|====|Поле|Значение|remote address|141.138.90.60|remote user|-|local time|2015-06-04T07:06:46Z|request|GET /downloads/product_2 HTTP/1.1|status|200|body bytes sent|3316|http referer|-|http user agent|Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.15)";
    static Path file;

    @BeforeAll
    static void preparation() {
        file = Path.of("log.txt");
        try {
            Files.writeString(file, testLogs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForNullArgs() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new LogAnalyzer(null, null));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForEmptyArgs() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new LogAnalyzer(new String[] {}, null));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForWrongArgs() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new LogAnalyzer(new String[] {"--wrong"}, null));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForWrongNumberOfArgs() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new LogAnalyzer(new String[] {"--path"}, null));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForNullFileParser() {
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new LogAnalyzer(new String[] {"--path", "log.txt"}, null)
        );
    }

    @Test
    void shouldReturnMDLogFile() throws IOException {
        String[] args = new String[] {"--path", "log.txt"};
        LogAnalyzer logAnalyzer = new LogAnalyzer(args, new SimpleFileParser());
        Path actual = logAnalyzer.analyze();

        String expectedFileName = "log report.md";

        assertThat(Files.exists(actual)).isTrue();
        assertThat(Files.isRegularFile(actual)).isTrue();
        assertThat(actual.getFileName().toString()).isEqualTo(expectedFileName);
        List<String> lines = Files.readAllLines(actual);
        StringBuilder actualText = new StringBuilder();
        for (String line : lines) {
            actualText.append(line);
        }
        assertThat(actualText.toString()).isEqualTo(expectedDefault);
    }

    @Test
    void shouldReturnADOCLogFile() throws IOException {
        String[] args = new String[] {"--path", "log.txt", "--format", "adoc"};
        LogAnalyzer logAnalyzer = new LogAnalyzer(args, new SimpleFileParser());
        Path actual = logAnalyzer.analyze();

        String expectedFileName = "log report.adoc";

        assertThat(Files.exists(actual)).isTrue();
        assertThat(Files.isRegularFile(actual)).isTrue();
        assertThat(actual.getFileName().toString()).isEqualTo(expectedFileName);
        List<String> lines = Files.readAllLines(actual);
        StringBuilder actualText = new StringBuilder();
        for (String line : lines) {
            actualText.append(line);
        }
        assertThat(actualText.toString()).isEqualTo(expectedADOC);
    }

    @AfterAll
    static void ending() {
        try {
            Files.delete(file);
            Files.delete(Path.of("log report.md"));
            Files.delete(Path.of("log report.adoc"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
