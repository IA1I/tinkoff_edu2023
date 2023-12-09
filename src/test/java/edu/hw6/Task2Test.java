package edu.hw6;

import edu.hw6.task2.Task2;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {
    static Path file;
    static String path;
    static String filename;
    static String extension;
    static byte[] expected;

    @BeforeAll
    static void preparation() throws IOException {
        path = "task2.txt";
        filename = "task2";
        extension = ".txt";
        file = Path.of(path);
        Files.createFile(file);
        Files.writeString(file, "Tinkoff homework6 task2 text!!!");
        expected = Files.readAllBytes(file);
    }

    @Test
    void shouldCreateFirstCopy() throws IOException {
        Path copiedFile = Task2.cloneFile(path);

        assertThat(Files.exists(copiedFile)).isTrue();
        assertThat(copiedFile.getFileName().toString()).isEqualTo(filename + " - копия" + extension);

        byte[] actual = Files.readAllBytes(copiedFile);
        assertThat(actual).isEqualTo(expected);

        Files.delete(copiedFile);
    }

    @Test
    void shouldCreateSecondCopy() throws IOException {
        Path firstCopy = Task2.cloneFile(path);
        Path secondCopy = Task2.cloneFile(path);

        assertThat(Files.exists(secondCopy)).isTrue();
        assertThat(secondCopy.getFileName().toString()).isEqualTo(filename + " - копия (1)" + extension);

        byte[] actual = Files.readAllBytes(secondCopy);
        assertThat(actual).isEqualTo(expected);

        Files.delete(firstCopy);
        Files.delete(secondCopy);
    }

    @Test
    void shouldCreateThirdCopy() throws IOException {
        Path firstCopy = Task2.cloneFile(path);
        Path secondCopy = Task2.cloneFile(path);
        Path thirdCopy = Task2.cloneFile(path);

        assertThat(Files.exists(thirdCopy)).isTrue();
        assertThat(thirdCopy.getFileName().toString()).isEqualTo(filename + " - копия (2)" + extension);

        byte[] actual = Files.readAllBytes(thirdCopy);
        assertThat(actual).isEqualTo(expected);

        Files.delete(firstCopy);
        Files.delete(secondCopy);
        Files.delete(thirdCopy);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForNullPath() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Task2.cloneFile((Path) null));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForIncorrectPath() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Task2.cloneFile(Path.of("Portnov.txt")));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForNullString() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Task2.cloneFile((String) null));
    }

    @AfterAll
    static void ending() throws IOException {
        Files.delete(file);
    }
}
