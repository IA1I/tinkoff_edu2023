package edu.project4;

import edu.project4.utility.FractalImage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

public class FractalImageTest {

    @ParameterizedTest
    @MethodSource("testCases")
    void shouldThrowIllegalArgumentExceptionForWrongSize(final int inputWidth, final int inputHeight) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> FractalImage.create(inputWidth, inputHeight));
    }

    static Stream<Arguments> testCases() {
        return Stream.of(
            Arguments.of(-100, 100),
            Arguments.of(100, -100),
            Arguments.of(-100, -100)
        );
    }
}
