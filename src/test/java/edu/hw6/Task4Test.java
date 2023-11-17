package edu.hw6;

import edu.hw6.task4.Task4;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task4Test {
    static Path file;
    static Path nonExistentFile;

    @BeforeAll
    static void preparation() {
        try {
            file = Files.createFile(Path.of("task4.txt"));
            nonExistentFile = Path.of("portnov.im");
        } catch (IOException e) {

        }
    }

    @Test
    void shouldWriteStringToFile() throws IOException {
        Task4.writeToFile(file);
        String expected = "Programming is learned by writing programs. ― Brian Kernighan";
        String actual = Files.readString(file);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldWriteStringToNonExistentFile() throws IOException {
        Task4.writeToFile(nonExistentFile);
        String expected = "Programming is learned by writing programs. ― Brian Kernighan";
        String actual = Files.readString(nonExistentFile);

        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("testCases")
    void shouldThrowIllegalArgumentExceptionForIncorrectPath(final Path input) {
        assertThrows(IllegalArgumentException.class, () -> Task4.writeToFile(input));
    }

    static Stream<Arguments> testCases() {
        return Stream.of(
            Arguments.of((Object) null),
            Arguments.of(Path.of("src"))
        );
    }

    @AfterAll
    static void ending() {
        try {
            Files.delete(file);
            Files.delete(nonExistentFile);
        } catch (IOException e) {

        }
    }
}
