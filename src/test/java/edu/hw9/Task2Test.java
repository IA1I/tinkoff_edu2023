package edu.hw9;

import edu.hw9.task2.DirectoriesFinder;
import edu.hw9.task2.FilesFinder;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class Task2Test {
    static String separator = System.getProperty("file.separator");

    @Test
    void shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> new DirectoriesFinder(null));
        assertThrows(IllegalArgumentException.class, () -> new FilesFinder(null));
    }

    @Test
    void shouldReturnListOfDirectories() {

        List<Path> expected =
            List.of(Path.of("src" + separator + "main" + separator + "java" + separator + "edu" + separator + "hw1"));
        DirectoriesFinder finder = new DirectoriesFinder(Path.of("src"));
        List<Path> actual = finder.invoke();

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void shouldReturnListOfFiles() {
        List<Path> expected = List.of(Path.of(
            "src" + separator + "main" + separator + "java" + separator + "edu" + separator + "hw9" + separator +
                "task1" + separator + "Metric.java"));
        FilesFinder finder = new FilesFinder(1, 100, ".java", Path.of("src"));
        List<Path> actual = finder.invoke();

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
