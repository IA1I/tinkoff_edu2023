package edu.project1;

import edu.project1.dictionaries.SimpleDictionary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

public class SimpleDictionaryTest {

    @ParameterizedTest
    @MethodSource("testCases")
    void shouldThrowIllegalArgumentExceptionForBadInputDictionary(List<String> input) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new SimpleDictionary(input));
    }

    static Stream<Arguments> testCases() {
        return Stream.of(
            Arguments.of(List.of("1235sad")),
            Arguments.of(List.of()),
            Arguments.of(List.of("d")),
            Arguments.of(List.of(".x,mxzc-32")),
            Arguments.of(List.of("word", "a")),
            Arguments.of(List.of("word", "12435"))
        );
    }
}
