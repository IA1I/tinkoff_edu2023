package edu.hw6;

import edu.hw6.task3.AbstractFilter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task3Test {

    @Test
    void shouldThrowIllegalArgumentExceptionForNullInput() {
        assertThrows(IllegalArgumentException.class, () -> AbstractFilter.magicNumber(null));
        assertThrows(IllegalArgumentException.class, () -> AbstractFilter.largerThan(100).and(null));
    }

    @ParameterizedTest
    @MethodSource("testCases")
    void shouldFilterFiles(final AbstractFilter filter, final List<String> expected) {
        List<String> actual = new ArrayList<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(Path.of("src/main/java/edu/hw6"), filter)) {
            entries.forEach(path -> actual.add(path.toString()));
        } catch (IOException e) {

        }

        Assertions.assertThat(actual).containsExactlyElementsOf(expected);
    }

    static Stream<Arguments> testCases() {
        AbstractFilter filter = Files::isDirectory;
        return Stream.of(
            Arguments.of(
                filter,
                List.of(
                    "src\\main\\java\\edu\\hw6\\task1",
                    "src\\main\\java\\edu\\hw6\\task2",
                    "src\\main\\java\\edu\\hw6\\task3",
                    "src\\main\\java\\edu\\hw6\\task4"
                )
            ),
            Arguments.of(
                filter = Files::isRegularFile,
                List.of("src\\main\\java\\edu\\hw6\\Task5.java", "src\\main\\java\\edu\\hw6\\Task6.java")
            ),

            Arguments.of(
                filter.and(AbstractFilter.largerThan(3000)),
                List.of("src\\main\\java\\edu\\hw6\\Task6.java")
            ),
            Arguments.of(
                AbstractFilter.globMatches("*.java"),
                List.of("src\\main\\java\\edu\\hw6\\Task5.java", "src\\main\\java\\edu\\hw6\\Task6.java")
            ),
            Arguments.of(
                AbstractFilter.regexContains("ask"),
                List.of(
                    "src\\main\\java\\edu\\hw6\\task1",
                    "src\\main\\java\\edu\\hw6\\task2",
                    "src\\main\\java\\edu\\hw6\\task3",
                    "src\\main\\java\\edu\\hw6\\task4",
                    "src\\main\\java\\edu\\hw6\\Task5.java",
                    "src\\main\\java\\edu\\hw6\\Task6.java"
                )
            )
        );
    }

    @Test
    void shouldFilterXMLFiles() {
        AbstractFilter filter =
            AbstractFilter.magicNumber((byte) 0x3c, (byte) 0x3f, (byte) 0x78, (byte) 0x6d, (byte) 0x6c, (byte) 0x20);
        List<String> actual = new ArrayList<>();
        List<String> expected = List.of("checkstyle.xml", "pom.xml");
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(Path.of(""), filter)) {
            entries.forEach(path -> actual.add(path.toString()));
        } catch (IOException e) {

        }

        Assertions.assertThat(actual).containsExactlyElementsOf(expected);
    }
}
