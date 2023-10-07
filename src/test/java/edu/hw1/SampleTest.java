package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SampleTest {


    @Nested
    @DisplayName("1. Длина видео")
    class TestTask1{
        @ParameterizedTest
        @DisplayName("Перевод из минут в секунды")
        @CsvSource({
            "01:00, 60",
            "00:00, 0",
            "000:000, 0",
            "001:002, 62",
            "13:56, 836",
            "999:59, 59999"
        })
        void testRegularInput(final String input, final int expected){
            int actual = Task1.minutesToSeconds(input);

            assertThat(actual).isEqualTo(expected);
        }
        @ParameterizedTest
        @DisplayName("Неправильный формат ввода")
        @CsvSource({
            "01:60, -1",
            "00:234, -1",
            "131:234, -1",
            "21:ad, -1",
            "we:ad, -1",
            "wrong, -1",
            ", -1",
            " , -1",
            "ac:59, -1",
            "2147483649:2, -1",
            "10:2:2, -1"
        })
        void testWrongInputFormat(final String input, final int expected){
            int actual = Task1.minutesToSeconds(input);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("2. Количество цифр")
    class TestTask2{
        @ParameterizedTest
        @DisplayName("Количество цифр в числе")
        @MethodSource("testCases")
        void testRegularInput(final int input, final int expected){
            int actual = Task2.countDigits(input);

            assertThat(actual).isEqualTo(expected);
        }
        static Stream<Arguments> testCases(){
            return Stream.of(
                Arguments.of(12345, 5),
                Arguments.of(1234, 4),
                Arguments.of(123, 3),
                Arguments.of(12, 2),
                Arguments.of(1, 1),
                Arguments.of(0, 1),
                Arguments.of(-1, 1),
                Arguments.of(-12, 2),
                Arguments.of(-123, 3),
                Arguments.of(-1234, 4),
                Arguments.of(-12345, 5)
            );
        }
    }
    
}
