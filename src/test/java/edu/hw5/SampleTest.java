package edu.hw5;

import edu.hw5.task3.Task3;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SampleTest {

    @Nested
    @DisplayName("Задание 1")
    class Task1Test {
        @ParameterizedTest
        @MethodSource("correctTestCases")
        void shouldReturnAverageSessionTime(
            final String[] input,
            String expected
        ) {
            String actual = Task1.getAverageSessionTime(input);
            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> correctTestCases() {
            return Stream.of(
                Arguments.of(new String[] {"2022-03-12, 20:20 - 2022-03-12, 23:50",
                    "2022-04-01, 21:30 - 2022-04-02, 01:20"}, "3ч 40м"),
                Arguments.of(new String[] {"2022-03-12, 20:20 - 2022-03-12, 23:50"}, "3ч 30м"),
                Arguments.of(new String[] {"2022-03-12, 20:20 - 2022-03-12, 20:50"}, "30м")
            );
        }

        @ParameterizedTest
        @MethodSource("incorrectTestCases")
        void shouldThrowIllegalArgumentExceptionForIncorrectInput(final String[] input) {
            assertThrows(IllegalArgumentException.class, () -> Task1.getAverageSessionTime(input));
        }

        static Stream<Arguments> incorrectTestCases() {
            return Stream.of(
                Arguments.of((Object) new String[] {"2022-03, 20:20 - 2022-03-12, 23:50",
                    "2022-04-01, 21:30 - 2022-04-02, 01:20"}),
                Arguments.of((Object) new String[] {"2022-03-34, 20:20 - 2022-03-12, 23:50",
                    "2022-04-01, 21:30 - 2022-04-02, 01:20"}),
                Arguments.of((Object) new String[] {"2022-03-12, 20:20"}),
                Arguments.of((Object) new String[] {"2022-03-12, 20:20 - 2022-03-12, 23:50 - 2022-03-12, 23:50"}),
                Arguments.of((Object) new String[] {null}),
                Arguments.of((Object) new String[] {"null", null}),
                Arguments.of((Object) new String[] {}),
                Arguments.of((Object) new String[] {""}),
                Arguments.of(new String[] {"2022-03-12, 20:50 - 2022-03-12, 20:20"}, "30м")
            );
        }
    }

    @Nested
    @DisplayName("Задание 2")
    class Task2Test {
        @ParameterizedTest
        @MethodSource("testCases")
        void shouldReturnListOfLocalDatesOfFridays13th(final int input, final List<LocalDate> expected) {
            List<LocalDate> actual = Task2.getAllFriday13th(input);
            assertThat(actual).containsExactlyElementsOf(expected);
        }

        static Stream<Arguments> testCases() {
            return Stream.of(
                Arguments.of(
                    1925,
                    List.of(
                        LocalDate.of(1925, 2, 13),
                        LocalDate.of(1925, 3, 13),
                        LocalDate.of(1925, 11, 13)
                    )
                ),
                Arguments.of(
                    2024,
                    List.of(
                        LocalDate.of(2024, 9, 13),
                        LocalDate.of(2024, 12, 13)
                    )
                )
            );
        }

        @ParameterizedTest
        @CsvSource({
            "0",
            "-15",
            "-346"
        })
        void shouldThrowIllegalArgumentException(final int input) {
            assertThrows(IllegalArgumentException.class, () -> Task2.getAllFriday13th(input));
        }

        @ParameterizedTest
        @MethodSource("testCasesForNextFriday13th")
        void shouldReturnNextFriday13th(final LocalDate input, final LocalDate expected) {
            LocalDate actual = Task2.getNextFriday13th(input);

            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> testCasesForNextFriday13th() {
            return Stream.of(
                Arguments.of(LocalDate.of(2023, 11, 9), LocalDate.of(2024, 9, 13))
            );
        }

        @Test
        void shouldThrowIllegalArgumentExceptionForNextFriday13th() {
            assertThrows(IllegalArgumentException.class, () -> Task2.getNextFriday13th(null));
        }
    }

    @Nested
    @DisplayName("Задание 3")
    class Task3Test {

        @ParameterizedTest
        @MethodSource("correctTestCases")
        void shouldReturnParsedDate(final String input, final Optional<LocalDate> expected) {
            Optional<LocalDate> actual = Task3.parseDate(input);

            assertThat(actual.isPresent()).isEqualTo(expected.isPresent());
            assertThat(actual.get()).isEqualTo(expected.get());
        }

        static Stream<Arguments> correctTestCases() {
            return Stream.of(
                Arguments.of("2020-10-10", Optional.of(LocalDate.of(2020, 10, 10))),
                Arguments.of("2020-12-2", Optional.of(LocalDate.of(2020, 12, 2))),
                Arguments.of("1/3/1976", Optional.of(LocalDate.of(1976, 3, 1))),
                Arguments.of("1/3/20", Optional.of(LocalDate.of(2020, 3, 1))),
                Arguments.of("tomorrow", Optional.of(LocalDate.now().plusDays(1L))),
                Arguments.of("today", Optional.of(LocalDate.now())),
                Arguments.of("yesterday", Optional.of(LocalDate.now().minusDays(1L))),
                Arguments.of("1 day ago", Optional.of(LocalDate.now().minusDays(1L))),
                Arguments.of("2 days ago", Optional.of(LocalDate.now().minusDays(2L))),
                Arguments.of("2234 days ago", Optional.of(LocalDate.now().minusDays(2234L)))
            );
        }

        @Test
        void shouldReturnEmptyOptionalForNull() {
            Optional<LocalDate> actual = Task3.parseDate(null);

            assertThat(actual.isEmpty()).isTrue();
        }

        @ParameterizedTest
        @MethodSource("incorrectTestCases")
        void shouldReturnEmptyOptional(final String input) {
            Optional<LocalDate> actual = Task3.parseDate(input);

            assertThat(actual.isEmpty()).isTrue();
        }

        static Stream<Arguments> incorrectTestCases() {
            return Stream.of(
                Arguments.of("asd"),
                Arguments.of("2022-10"),
                Arguments.of("null"),
                Arguments.of("1/1"),
                Arguments.of("2022/35/40"),
                Arguments.of("")
            );
        }
    }

    @Nested
    @DisplayName("Задание 4")
    class Task4Test {
        @ParameterizedTest
        @CsvSource({
            "abc~abs",
            "123ewr!abs",
            "@",
            "asd#",
            "$yrey",
            "sxzvzx123%safr324g",
            "sda^sad",
            "sda&sad",
            "sda*sad",
            "sd|asad",
        })
        void shouldReturnTrueForCorrectPassword(final String input) {
            boolean actual = Task4.isRegexMatches(input);

            assertThat(actual).isTrue();
        }

        @ParameterizedTest
        @CsvSource({
            "abcabs",
            "123324",
            "sflsfls34234sdda",
            "asd##",
            "$yrey!",
            "sxzvzx123%safr324g$",
            "!@#$%^&*"
        })
        void shouldReturnFalseForIncorrectPassword(final String input) {
            boolean actual = Task4.isRegexMatches(input);

            assertThat(actual).isFalse();
        }

        @Test
        void shouldReturnFalseForNull() {
            boolean actual = Task4.isRegexMatches(null);

            assertThat(actual).isFalse();
        }
    }

    @Nested
    @DisplayName("Задание 5")
    class Task5Test {
        @ParameterizedTest
        @CsvSource({
            "A123BE777",
            "O777OO177",
            "K145TO48"
        })
        void shouldReturnTrueForCorrectPassword(final String input) {
            boolean actual = Task5.isRegexMatches(input);

            assertThat(actual).isTrue();
        }

        @ParameterizedTest
        @CsvSource({
            "abcabs",
            "123АВЕ777",
            "А123ВГ77",
            "А123ВЕ7777"
        })
        void shouldReturnFalseForIncorrectPassword(final String input) {
            boolean actual = Task5.isRegexMatches(input);

            assertThat(actual).isFalse();
        }

        @Test
        void shouldReturnFalseForNull() {
            boolean actual = Task5.isRegexMatches(null);

            assertThat(actual).isFalse();
        }
    }

    @Nested
    @DisplayName("Задание 6")
    class Task6Test {
        @ParameterizedTest
        @CsvSource({
            "abc, achfdbaabgabcaabg",
            "abc, abc",
            "or, word",
            "or, wordwordword",
            "word123, sacsavas2312.zx][-=word123sfvcoi76",
            "0-9], abs[0-9]sfsd",
            "[0-9], abs[0-9]sfsd",
            "[, abs[0-9]sfsd",
            "*, ab*c",
            "\\, ab\\c",
            "\\d{3}, \\d{3}",
            "123, a1b2c3",
            "\\, a1b\\2c3"
        })
        void shouldReturnTrueForCorrectInput(final String inputS, final String inputT) {
            boolean actual = Task6.isSSubsequenceOfT(inputS, inputT);

            assertThat(actual).isTrue();
        }

        @ParameterizedTest
        @CsvSource({
            "123, achfdbaabgabcaabg",
            "%, abc",
            "?, word",
            "., wordwordword",
            "+, sacsavas2312.zx][-=word123sfvcoi76",
            "[0-9], 123",
            "\n, abssfsd",
            "\\d{3}, 123",
            "*, abs[0-9]sfsd"
        })
        void shouldReturnFalseForSIsNotSubsequence(final String inputS, final String inputT) {
            boolean actual = Task6.isSSubsequenceOfT(inputS, inputT);

            assertThat(actual).isFalse();
        }

        @Test
        void shouldReturnFalseForNull() {
            boolean actual = Task6.isSSubsequenceOfT(null, null);

            assertThat(actual).isFalse();
        }
    }

    @Nested
    @DisplayName("Задание 7")
    class Task7Test {
        @Nested
        @DisplayName("Содержит не менее 3 символов, причем третий символ равен 0")
        class Task71Test {
            @ParameterizedTest
            @CsvSource({
                "010",
                "0101100101010",
                "000000000",
                "110111111",
                "000"
            })
            void shouldReturnTrueForCorrectInput(final String input) {
                boolean actual = Task7.isContainsAtLeast3CharactersWithThird0(input);

                assertThat(actual).isTrue();
            }

            @ParameterizedTest
            @CsvSource({
                "11",
                "111",
                "001000000",
                "111111111",
                "001100101010",
                "word"
            })
            void shouldReturnFalseForNotMatchedString(final String input) {
                boolean actual = Task7.isContainsAtLeast3CharactersWithThird0(input);

                assertThat(actual).isFalse();
            }

            @Test
            void shouldReturnFalseForNull() {
                boolean actual = Task7.isContainsAtLeast3CharactersWithThird0(null);

                assertThat(actual).isFalse();
            }

            @Test
            void shouldReturnFalseForEmptyString() {
                boolean actual = Task7.isContainsAtLeast3CharactersWithThird0("");

                assertThat(actual).isFalse();
            }
        }

        @Nested
        @DisplayName("Начинается и заканчивается одним и тем же символом")
        class Task72Test {
            @ParameterizedTest
            @CsvSource({
                "010",
                "0101100101010",
                "000000000",
                "110111111",
                "000",
                "11",
                "110000001",
                "101001"
            })
            void shouldReturnTrueForCorrectInput(final String input) {
                boolean actual = Task7.isStartsAndEndsWithSameCharacter(input);

                assertThat(actual).isTrue();
            }

            @ParameterizedTest
            @CsvSource({
                "10",
                "011",
                "01010000001",
                "0111111111",
                "1001100101010",
                "word"
            })
            void shouldReturnFalseForNotMatchedString(final String input) {
                boolean actual = Task7.isStartsAndEndsWithSameCharacter(input);

                assertThat(actual).isFalse();
            }

            @Test
            void shouldReturnFalseForNull() {
                boolean actual = Task7.isStartsAndEndsWithSameCharacter(null);

                assertThat(actual).isFalse();
            }

            @Test
            void shouldReturnFalseForEmptyString() {
                boolean actual = Task7.isStartsAndEndsWithSameCharacter("");

                assertThat(actual).isFalse();
            }
        }

        @Nested
        @DisplayName("Длина не менее 1 и не более 3")
        class Task73Test {
            @ParameterizedTest
            @CsvSource({
                "0",
                "1",
                "00",
                "01",
                "10",
                "11",
                "000",
                "001",
                "010",
                "011",
                "100",
                "101",
                "110",
                "111"
            })
            void shouldReturnTrueForCorrectInput(final String input) {
                boolean actual = Task7.isLengthBetween1And3(input);

                assertThat(actual).isTrue();
            }

            @ParameterizedTest
            @CsvSource({
                "0111",
                "0110000",
                "01010000001",
                "0111111111",
                "1001100101010",
                "word",
                "123"
            })
            void shouldReturnFalseForNotMatchedString(final String input) {
                boolean actual = Task7.isLengthBetween1And3(input);

                assertThat(actual).isFalse();
            }

            @Test
            void shouldReturnFalseForNull() {
                boolean actual = Task7.isLengthBetween1And3(null);

                assertThat(actual).isFalse();
            }

            @Test
            void shouldReturnFalseForEmptyString() {
                boolean actual = Task7.isLengthBetween1And3("");

                assertThat(actual).isFalse();
            }
        }
    }

    @Nested
    @DisplayName("Задание 8")
    class Task8Test {
        @Nested
        @DisplayName("Нечетной длины")
        class Task81Test {
            @ParameterizedTest
            @CsvSource({
                "010",
                "010110010101011",
                "1",
                "0",
                "110111111",
                "0001010"
            })
            void shouldReturnTrueForCorrectInput(final String input) {
                boolean actual = Task8.isOddLength(input);

                assertThat(actual).isTrue();
            }

            @ParameterizedTest
            @CsvSource({
                "11",
                "00",
                "0010000001",
                "1111111110",
                "001100101010",
                "word"
            })
            void shouldReturnFalseForNotMatchedString(final String input) {
                boolean actual = Task8.isOddLength(input);

                assertThat(actual).isFalse();
            }

            @Test
            void shouldReturnFalseForNull() {
                boolean actual = Task8.isOddLength(null);

                assertThat(actual).isFalse();
            }

            @Test
            void shouldReturnFalseForEmptyString() {
                boolean actual = Task8.isOddLength("");

                assertThat(actual).isFalse();
            }
        }

        @Nested
        @DisplayName("Начинается с 0 и имеет нечетную длину, или начинается с 1 и имеет четную длину")
        class Task82Test {
            @ParameterizedTest
            @CsvSource({
                "010",
                "1010",
                "0",
                "10",
                "1111",
                "000"
            })
            void shouldReturnTrueForCorrectInput(final String input) {
                boolean actual = Task8.isStartsWith0AndOddOrStartsWith1And(input);

                assertThat(actual).isTrue();
            }

            @ParameterizedTest
            @CsvSource({
                "111",
                "00",
                "100",
                "0111",
                "1",
                "word"
            })
            void shouldReturnFalseForNotMatchedString(final String input) {
                boolean actual = Task8.isStartsWith0AndOddOrStartsWith1And(input);

                assertThat(actual).isFalse();
            }

            @Test
            void shouldReturnFalseForNull() {
                boolean actual = Task8.isStartsWith0AndOddOrStartsWith1And(null);

                assertThat(actual).isFalse();
            }

            @Test
            void shouldReturnFalseForEmptyString() {
                boolean actual = Task8.isStartsWith0AndOddOrStartsWith1And("");

                assertThat(actual).isFalse();
            }
        }

        @Nested
        @DisplayName("Количество 0 кратно 3")
        class Task83Test {
            @ParameterizedTest
            @CsvSource({
                "000",
                "1010101",
                "0100",
                "001011",
                "111011101110111",
                "111000111001110111",
                "111011101101010101",
                "00111111110111111"
            })
            void shouldReturnTrueForCorrectInput(final String input) {
                boolean actual = Task8.isQuantity0IsAMultipleOf3(input);

                assertThat(actual).isTrue();
            }

            @ParameterizedTest
            @CsvSource({
                "111",
                "00",
                "1001",
                "0110100",
                "1",
                "00",
                "1000100",
                "0",
                "word"
            })
            void shouldReturnFalseForNotMatchedString(final String input) {
                boolean actual = Task8.isQuantity0IsAMultipleOf3(input);

                assertThat(actual).isFalse();
            }

            @Test
            void shouldReturnFalseForNull() {
                boolean actual = Task8.isQuantity0IsAMultipleOf3(null);

                assertThat(actual).isFalse();
            }

            @Test
            void shouldReturnFalseForEmptyString() {
                boolean actual = Task8.isQuantity0IsAMultipleOf3("");

                assertThat(actual).isFalse();
            }
        }

        @Nested
        @DisplayName("Любая строка, кроме 11 или 111")
        class Task84Test {
            @ParameterizedTest
            @CsvSource({
                "000",
                "1010101",
                "0100",
                "001011",
                "1000",
                "0",
                "1",
                "110001",
                "1110111",
                "111000"
            })
            void shouldReturnTrueForCorrectInput(final String input) {
                boolean actual = Task8.isAnyStringOtherThan11Or111(input);

                assertThat(actual).isTrue();
            }

            @ParameterizedTest
            @CsvSource({
                "11",
                "111",
                "word"
            })
            void shouldReturnFalseForNotMatchedString(final String input) {
                boolean actual = Task8.isAnyStringOtherThan11Or111(input);

                assertThat(actual).isFalse();
            }

            @Test
            void shouldReturnFalseForNull() {
                boolean actual = Task8.isAnyStringOtherThan11Or111(null);

                assertThat(actual).isFalse();
            }

            @Test
            void shouldReturnFalseForEmptyString() {
                boolean actual = Task8.isAnyStringOtherThan11Or111("");

                assertThat(actual).isFalse();
            }
        }

        @Nested
        @DisplayName("Каждый нечетный символ равен 1")
        class Task85Test {
            @ParameterizedTest
            @CsvSource({
                "010",
                "010101",
                "0101",
                "111111111111",
                "0"
            })
            void shouldReturnTrueForCorrectInput(final String input) {
                boolean actual = Task8.isEveryOddCharacterIs1(input);

                assertThat(actual).isTrue();
            }

            @ParameterizedTest
            @CsvSource({
                "0100",
                "001",
                "00",
                "word"
            })
            void shouldReturnFalseForNotMatchedString(final String input) {
                boolean actual = Task8.isEveryOddCharacterIs1(input);

                assertThat(actual).isFalse();
            }

            @Test
            void shouldReturnFalseForNull() {
                boolean actual = Task8.isEveryOddCharacterIs1(null);

                assertThat(actual).isFalse();
            }
        }

        @Nested
        @DisplayName("Содержит не менее двух 0 и не более одной 1")
        class Task86Test {
            @ParameterizedTest
            @CsvSource({
                "010",
                "000",
                "010000000",
                "000001",
                "00"
            })
            void shouldReturnTrueForCorrectInput(final String input) {
                boolean actual = Task8.isContainsAtLeastTwo0AndAtMostOne1(input);

                assertThat(actual).isTrue();
            }

            @ParameterizedTest
            @CsvSource({
                "11",
                "00000010001",
                "00000011",
                "01",
                "1",
                "word"
            })
            void shouldReturnFalseForNotMatchedString(final String input) {
                boolean actual = Task8.isContainsAtLeastTwo0AndAtMostOne1(input);

                assertThat(actual).isFalse();
            }

            @Test
            void shouldReturnFalseForNull() {
                boolean actual = Task8.isContainsAtLeastTwo0AndAtMostOne1(null);

                assertThat(actual).isFalse();
            }

            @Test
            void shouldReturnFalseForEmptyString() {
                boolean actual = Task8.isContainsAtLeastTwo0AndAtMostOne1("");

                assertThat(actual).isFalse();
            }
        }

        @Nested
        @DisplayName("Нет последовательных 1")
        class Task87Test {
            @ParameterizedTest
            @CsvSource({
                "010",
                "1",
                "1010101010",
                "000001",
                "0100000"
            })
            void shouldReturnTrueForCorrectInput(final String input) {
                boolean actual = Task8.isNoConsecutive1(input);

                assertThat(actual).isTrue();
            }

            @ParameterizedTest
            @CsvSource({
                "11",
                "11111",
                "00000011",
                "01000010111110",
                "word"
            })
            void shouldReturnFalseForNotMatchedString(final String input) {
                boolean actual = Task8.isNoConsecutive1(input);

                assertThat(actual).isFalse();
            }

            @Test
            void shouldReturnFalseForNull() {
                boolean actual = Task8.isNoConsecutive1(null);

                assertThat(actual).isFalse();
            }
        }
    }
}
