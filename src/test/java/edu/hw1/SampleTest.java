package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SampleTest {


    @Nested
    class TestTask1{
        @ParameterizedTest
        @DisplayName("Перевод из минут в секунды")
        @CsvSource({
            "01:00, 60",
            "00:00, 0",
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
            "ac:59, -1"
        })
        void testWrongInputFormat(final String input, final int expected){
            int actual = Task1.minutesToSeconds(input);

            assertThat(actual).isEqualTo(expected);
        }
    }
}
