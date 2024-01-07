package edu.project3;

import edu.project3.fileproviders.HttpLogProvider;
import edu.project3.fileproviders.LocalFileLogProvider;
import edu.project3.fileproviders.LogProvider;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class FileProvidersTest {

    @ParameterizedTest
    @MethodSource("logProviders")
    void shouldReturnEmptyListOfPathsForNullInput(final LogProvider logProvider) {
        List<Path> actual = logProvider.provide(null);

        assertThat(actual).isEmpty();
    }

    static Stream<Arguments> logProviders() {
        return Stream.of(
            Arguments.of(new LocalFileLogProvider()),
            Arguments.of(new HttpLogProvider())
        );
    }

    @ParameterizedTest
    @MethodSource("testCasesForWrongPath")
    void shouldReturnEmptyListOfPathsForNonExistentPath(final LogProvider logProvider, final String path) {
        List<Path> actual = logProvider.provide(path);

        assertThat(actual).isEmpty();
    }

    static Stream<Arguments> testCasesForWrongPath() {
        return Stream.of(
            Arguments.of(new LocalFileLogProvider(), "portnov"),
            Arguments.of(new HttpLogProvider(), "")
        );
    }

    @Test
    void shouldReturnListOfPathsForCorrectInputForLocalFileLogProvider() {
        LogProvider logProvider = new LocalFileLogProvider();
        List<Path> expected = List.of(Path.of("pom.xml"));
        List<Path> actual = logProvider.provide("pom.xml");

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Disabled
    @Test
    void shouldReturnListOfPathsForCorrectInputForHttpLogProvider() {
        LogProvider logProvider = new HttpLogProvider();
        List<Path> expected = List.of(Path.of("logs.txt"));
        List<Path> actual = logProvider.provide(
            "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs");

        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @ParameterizedTest
    @MethodSource("testCasesForGetInstance")
    void shouldReturnLogProvider(final String path, final Class<?> expected) {
        LogProvider logProvider = LogProvider.getInstance(path);

        assertThat(logProvider).isInstanceOf(expected);
    }

    static Stream<Arguments> testCasesForGetInstance() {
        return Stream.of(
            Arguments.of(
                "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs",
                HttpLogProvider.class
            ),
            Arguments.of("portnov.txt", LocalFileLogProvider.class),
            Arguments.of(null, LocalFileLogProvider.class)
        );
    }

    @Test
    void shouldReturnListOfFilesForDirectoryInput() {
        LogProvider logProvider = new LocalFileLogProvider();
        List<Path> actual = logProvider.provide("");

        assertThat(actual).isNotEmpty();
    }
}
