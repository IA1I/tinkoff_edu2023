package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Nested
    @DisplayName("3. Вложенный массив")
    class TestTask3{
        @ParameterizedTest
        @DisplayName("Вложенный массив")
        @MethodSource("testCases")
        void testRegularInput(final int[] firstInputArray, final int[] secondInputArray, final boolean expected){
            boolean actual = Task3.isNestable(firstInputArray, secondInputArray);

            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> testCases(){
            return Stream.of(
                Arguments.of((Object) new int[]{1, 2, 3, 4}, (Object) new int[]{0, 6}, true),
                Arguments.of((Object) new int[]{3, 1}, (Object) new int[]{4, 0}, true),
                Arguments.of((Object) new int[]{9, 9, 8}, (Object) new int[]{8, 9}, false),
                Arguments.of((Object) new int[]{1, 2, 3, 4}, (Object) new int[]{2, 3}, false),
                Arguments.of((Object) new int[]{}, (Object) new int[]{2, 3}, false),
                Arguments.of((Object) new int[]{1, 2}, (Object) new int[]{}, false),
                Arguments.of((Object) new int[]{}, (Object) new int[]{}, false),
                Arguments.of((Object) new int[]{}, null, false),
                Arguments.of(null, (Object) new int[]{}, false),
                Arguments.of(null, null, false)
            );
        }
    }

    @Nested
    @DisplayName("4. Сломанная строка")
    class TestTask4 {
        @ParameterizedTest
        @DisplayName("Исправление строки")
        @CsvSource({
            "123456, 214365",
            "hTsii  s aimex dpus rtni.g, This is a mixed up string.",
            "badce, abcde",
            "a, a",
            "ba, ab"
        })
        void testRegularInput(final String input, final String expected) {
            String actual = Task4.fixString(input);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("Неправильный формат ввода: null")
        void testWrongInputFormat() {
            assertThrows(IllegalArgumentException.class, () -> Task4.fixString(null));
        }

        @Test
        @DisplayName("Пустая строка")
        void testEmptyString() {
            String input = "";
            String expected = "";
            String actual = Task4.fixString(input);

            assertThat(actual).isEqualTo(expected);
        }
    }
}
