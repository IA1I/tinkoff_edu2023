package edu.hw1;

import java.util.stream.Stream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SampleTest {

    @Nested
    @DisplayName("0. Привет, мир!")
    class TestTask0 {

        @Test
        @Disabled
        void applicationWritesHelloWordToSystemOut() throws Exception {
            String actual = tapSystemOut(Task0::sayHelloToWorld);
            String expected = "Привет, мир!";
            assertThat(actual).contains(expected);
        }
    }

    @Nested
    @DisplayName("1. Длина видео")
    class TestTask1 {
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
        void testRegularInput(final String input, final int expected) {
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
        void testWrongInputFormat(final String input, final int expected) {
            int actual = Task1.minutesToSeconds(input);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("2. Количество цифр")
    class TestTask2 {
        @ParameterizedTest
        @DisplayName("Количество цифр в числе")
        @MethodSource("testCases")
        void testRegularInput(final int input, final int expected) {
            int actual = Task2.countDigits(input);

            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> testCases() {
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
    class TestTask3 {
        @ParameterizedTest
        @DisplayName("Вложенный массив")
        @MethodSource("testCases")
        void testRegularInput(final int[] firstInputArray, final int[] secondInputArray, final boolean expected) {
            boolean actual = Task3.isNestable(firstInputArray, secondInputArray);

            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> testCases() {
            return Stream.of(
                Arguments.of((Object) new int[] {1, 2, 3, 4}, (Object) new int[] {0, 6}, true),
                Arguments.of((Object) new int[] {3, 1}, (Object) new int[] {4, 0}, true),
                Arguments.of((Object) new int[] {9, 9, 8}, (Object) new int[] {8, 9}, false),
                Arguments.of((Object) new int[] {1, 2, 3, 4}, (Object) new int[] {2, 3}, false),
                Arguments.of((Object) new int[] {}, (Object) new int[] {2, 3}, false),
                Arguments.of((Object) new int[] {1, 2}, (Object) new int[] {}, false),
                Arguments.of((Object) new int[] {}, (Object) new int[] {}, false),
                Arguments.of((Object) new int[] {}, null, false),
                Arguments.of(null, (Object) new int[] {}, false),
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

    @Nested
    @DisplayName("5. Особый палиндром")
    class TestTask5 {
        @ParameterizedTest
        @DisplayName("Особый палиндром")
        @MethodSource("testCases")
        void testRegularInput(final int input, final boolean expected) {
            boolean actual = Task5.isPalindromeDescendant(input);

            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> testCases() {
            return Stream.of(
                Arguments.of(11211230, true),
                Arguments.of(13001120, true),
                Arguments.of(23336014, true),
                Arguments.of(11, true),
                Arguments.of(5, true),
                Arguments.of(14, false),
                Arguments.of(1234, false),
                Arguments.of(12345, false)
            );
        }
    }

    @Nested
    @DisplayName("6. Постоянная Капрекара")
    class TestTask6 {
        @ParameterizedTest
        @DisplayName("Постоянная Капрекара")
        @MethodSource("testCases")
        void testRegularInput(final int input, final int expected) {
            int actual = Task6.countK(input);

            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> testCases() {
            return Stream.of(
                Arguments.of(3524, 3),
                Arguments.of(6621, 5),
                Arguments.of(6554, 4),
                Arguments.of(1234, 3),
                Arguments.of(2221, 5)
            );
        }

        @ParameterizedTest
        @DisplayName("Неправильный формат ввода")
        @CsvSource({
            "123",
            "10000",
            "1111",
            "2222",
            "-1234",
            "1000"
        })
        void testWrongInputFormat(final int input) {
            assertThrows(IllegalArgumentException.class, () -> Task6.countK(input));
        }

    }

    @Nested
    @DisplayName("7. Циклический битовый сдвиг")
    class TaskTask7 {
        @ParameterizedTest
        @DisplayName("Сдвиг влево")
        @MethodSource("testCasesForLeft")
        void testRegularInputForLeft(final int inputNumber, final int inputShift, final int expected) {
            int actual = Task7.rotateLeft(inputNumber, inputShift);

            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> testCasesForLeft() {
            return Stream.of(
                Arguments.of(16, 1, 1),
                Arguments.of(16, 6, 1),
                Arguments.of(17, 2, 6)
            );
        }

        @ParameterizedTest
        @DisplayName("Неправильный формат ввода для сдвига влево и вправо")
        @CsvSource({
            "-10, 1",
            "10, -1",
            "-100, -3",
        })
        void testWrongInputFormatForBothShift(final int inputNumber, final int inputShift) {
            assertThrows(IllegalArgumentException.class, () -> Task7.rotateLeft(inputNumber, inputShift));
            assertThrows(IllegalArgumentException.class, () -> Task7.rotateRight(inputNumber, inputShift));
        }

        @ParameterizedTest
        @DisplayName("Сдвиг вправо")
        @MethodSource("testCasesForRight")
        void testRegularInputForRight(final int inputNumber, final int inputShift, final int expected) {
            int actual = Task7.rotateRight(inputNumber, inputShift);

            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> testCasesForRight() {
            return Stream.of(
                Arguments.of(8, 1, 4),
                Arguments.of(8, 5, 4)
            );
        }
    }

    @Nested
    @DisplayName("8. Кони на доске")
    class TestTask8 {
        @ParameterizedTest
        @DisplayName("Кони на доске")
        @MethodSource("testCases")
        void testRegularInput(final byte[][] input, final boolean expected) {
            boolean actual = Task8.knightBoardCapture(input);

            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> testCases() {
            return Stream.of(
                Arguments.of(new byte[][] {
                    {0, 0, 0, 1, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 0, 0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 1, 0, 1, 0},
                    {0, 1, 0, 0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 0, 0, 0, 0, 0, 1},
                    {0, 0, 0, 0, 1, 0, 0, 0}
                }, true),
                Arguments.of(new byte[][] {
                    {1, 0, 1, 0, 1, 0, 1, 0},
                    {0, 1, 0, 1, 0, 1, 0, 1},
                    {0, 0, 0, 0, 1, 0, 1, 0},
                    {0, 0, 1, 0, 0, 1, 0, 1},
                    {1, 0, 0, 0, 1, 0, 1, 0},
                    {0, 0, 0, 0, 0, 1, 0, 1},
                    {1, 0, 0, 0, 1, 0, 1, 0},
                    {0, 0, 0, 1, 0, 1, 0, 1}
                }, false),
                Arguments.of(new byte[][] {
                    {0, 0, 0, 0, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0, 1, 0, 0},
                    {0, 0, 0, 1, 0, 0, 0, 0},
                    {1, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 0, 1, 0, 0},
                    {1, 0, 0, 0, 0, 0, 0, 0}
                }, false)
            );
        }

        @ParameterizedTest
        @DisplayName("Неправильный формат ввода")
        @MethodSource("wrongTestCases")
        void testWrongInput(final byte[][] input) {
            assertThrows(IllegalArgumentException.class, () -> Task8.knightBoardCapture(input));
        }

        static Stream<Arguments> wrongTestCases() {
            return Stream.of(
                Arguments.of((Object) null),
                Arguments.of((Object) new byte[][] {
                    null,
                    {0, 1, 0, 1, 0, 1, 0, 1},
                    {0, 0, 0, 0, 1, 0, 1, 0},
                    {0, 0, 1, 0, 0, 1, 0, 1},
                    {1, 0, 0, 0, 1, 0, 1, 0},
                    {0, 0, 0, 0, 0, 1, 0, 1},
                    {1, 0, 0, 0, 1, 0, 1, 0},
                    {0, 0, 0, 1, 0, 1, 0, 1}
                }),
                Arguments.of((Object) new byte[][] {
                    {0, 0, 0, 0, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0, 1, 0, 0},
                    {0, 0, 0, 1, 0, 0, 0, 0},
                    {1, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 0, 1, 0, 0},
                    {1, 0, 0, 0, 0, 0, 0, 0}
                }),
                Arguments.of((Object) new byte[][] {
                    {0, 0, 0, 0, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0, 1, 0, 0},
                    {0, 0, 0, 1, 0, 0, 0, 0},
                    {1, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 0, 1, 0, 0},
                    {1, 0, 0, 0, 0, 0, 0, 0}
                })
            );
        }
    }
}
