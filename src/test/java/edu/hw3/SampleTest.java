package edu.hw3;

import edu.hw3.ask7.NullableComparator;
import edu.hw3.task5.Contact;
import edu.hw3.task5.Task5;
import edu.hw3.task6.SimpleStockMarket;
import edu.hw3.task6.Stock;
import edu.hw3.task6.StockMarket;
import edu.hw3.task8.BackwardIterator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class SampleTest {

    @Nested
    @DisplayName("1. Шифр Атбаш")
    class TestTask1 {

        @Test
        void shouldThrowIllegalArgumentExceptionForNull() {
            assertThrows(IllegalArgumentException.class, () -> Task1.atbash(null));
        }

        @ParameterizedTest
        @CsvSource({
            "Hello world!, Svool dliow!",
            "Any fool can write code that a computer can understand. Good programmers write code that humans can understand. ― Martin Fowler, Zmb ullo xzm dirgv xlwv gszg z xlnkfgvi xzm fmwvihgzmw. Tllw kiltiznnvih dirgv xlwv gszg sfnzmh xzm fmwvihgzmw. ― Nzigrm Uldovi",
            "1234567890-=.!?/'[]{}()\\;:<>*&^%$#@, 1234567890-=.!?/'[]{}()\\;:<>*&^%$#@"
        })
        void shouldCipherString(final String input, final String expected) {
            String actual = Task1.atbash(input);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("2. Кластеризация скобок")
    class TestTask2 {

        @Test
        void shouldThrowIllegalArgumentExceptionForNullInput() {
            assertThrows(IllegalArgumentException.class, () -> Task2.clusterize(null));
        }

        @ParameterizedTest
        @MethodSource("testCasesForBalancedInput")
        void shouldReturnClusterForBalancedInput(final String input, final List<String> expected) {
            List<String> actual = Task2.clusterize(input);

            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> testCasesForBalancedInput() {
            return Stream.of(
                Arguments.of("()()()", List.of("()", "()", "()")),
                Arguments.of("((()))", List.of("((()))")),
                Arguments.of("((()))(())()()(()())", List.of("((()))", "(())", "()", "()", "(()())")),
                Arguments.of("((())())(()(()()))", List.of("((())())", "(()(()()))"))
            );
        }

        @ParameterizedTest
        @MethodSource("testCasesForUnbalancedInput")
        void shouldThrowIllegalArgumentExceptionForUnbalancedInput(final String input) {
            assertThrows(IllegalArgumentException.class, () -> Task2.clusterize(input));
        }

        static Stream<Arguments> testCasesForUnbalancedInput() {
            return Stream.of(
                Arguments.of("(()"),
                Arguments.of("()))")
            );
        }

        @ParameterizedTest
        @MethodSource("testCasesForBadInput")
        void shouldThrowIllegalArgumentExceptionForBadInput(final String input) {
            assertThrows(IllegalArgumentException.class, () -> Task2.clusterize(input));
        }

        static Stream<Arguments> testCasesForBadInput() {
            return Stream.of(
                Arguments.of("((213)5.sfsa"),
                Arguments.of("()))kmf.2/40-(*")
            );
        }
    }

    @Nested
    @DisplayName("3. Частота слов")
    class TestTask3 {
        @Test
        void shouldThrowIllegalArgumentExceptionForNull() {
            assertThrows(IllegalArgumentException.class, () -> Task3.freqDict(null));
        }

        @ParameterizedTest
        @MethodSource("testCasesForListStrings")
        void shouldReturnFrequencyDictionaryForListStrings(
            final List<String> input,
            final Map<String, Integer> expected
        ) {
            Map<String, Integer> actual = Task3.freqDict(input);

            assertThat(actual).containsAllEntriesOf(expected);
        }

        static Stream<Arguments> testCasesForListStrings() {
            return Stream.of(
                Arguments.of(List.of("a", "bb", "a", "bb"), Map.of("bb", 2, "a", 2)),
                Arguments.of(List.of("this", "and", "that", "and"), Map.of("that", 1, "and", 2, "this", 1)),
                Arguments.of(List.of("код", "код", "код", "bug"), Map.of("код", 3, "bug", 1))
            );
        }

        @ParameterizedTest
        @MethodSource("testCasesForListIntegers")
        void shouldReturnFrequencyDictionaryForListIntegers(
            final List<Integer> input,
            final Map<Integer, Integer> expected
        ) {
            Map<Integer, Integer> actual = Task3.freqDict(input);

            assertThat(actual).containsAllEntriesOf(expected);
        }

        static Stream<Arguments> testCasesForListIntegers() {
            return Stream.of(
                Arguments.of(List.of(1, 1, 2, 2), Map.of(1, 2, 2, 2))
            );
        }
    }

    @Nested
    @DisplayName("4. Римские цифры")
    class TestTask4 {

        @ParameterizedTest
        @CsvSource({
            "0",
            "4000",
            "-1",
            "5000"
        })
        void shouldThrowIllegalArgumentExceptionForBadInput(final int input) {
            assertThrows(IllegalArgumentException.class, () -> Task4.convertToRoman(input));
        }

        @ParameterizedTest
        @CsvSource({
            "2, II",
            "12, XII",
            "16, XVI",
            "3999, MMMCMXCIX",
            "1988, MCMLXXXVIII",
            "283, CCLXXXIII"
        })
        void shouldConvertDecimalNumberToRoman(final int input, final String expected) {
            String actual = Task4.convertToRoman(input);

            assertThat(actual).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("5. Список контактов")
    class TestTask5 {
        @ParameterizedTest
        @MethodSource("correctTestCases")
        void shouldReturnArrayOfContactsForCorrectInput(
            final String[] inputNames,
            final String inputSortType,
            final Contact[] expected
        ) {
            Contact[] actual = Task5.parseContacts(inputNames, inputSortType);
            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> correctTestCases() {
            return Stream.of(
                Arguments.of(
                    new String[] {"John Locke", "Thomas Aquinas", "David Hume", "Rene Descartes"},
                    "ASC",
                    new Contact[] {new Contact("Thomas", "Aquinas"), new Contact("Rene", "Descartes"),
                        new Contact("David", "Hume"), new Contact("John", "Locke")}
                ),
                Arguments.of(
                    new String[] {"Paul Erdos", "Leonhard Euler", "Carl Gauss"},
                    "DESC",
                    new Contact[] {new Contact("Carl", "Gauss"), new Contact("Leonhard", "Euler"),
                        new Contact("Paul", "Erdos")}
                ),
                Arguments.of(new String[] {}, "DESC", new Contact[] {}),
                Arguments.of(null, "DESC", new Contact[] {})
            );
        }

        @ParameterizedTest
        @MethodSource("correctTestCasesWithoutFullName")
        void shouldReturnArrayOfContactsForInputWithoutFullName(
            final String[] inputNames,
            final String inputSortType,
            final Contact[] expected
        ) {
            Contact[] actual = Task5.parseContacts(inputNames, inputSortType);
            assertThat(actual).isEqualTo(expected);
        }

        static Stream<Arguments> correctTestCasesWithoutFullName() {
            return Stream.of(
                Arguments.of(
                    new String[] {"Ivan", "Egor", "Lina"},
                    "ASC",
                    new Contact[] {new Contact("Egor", ""), new Contact("Ivan", ""), new Contact("Lina", "")}
                ),
                Arguments.of(
                    new String[] {"Ivan", "Egor", "Lina"},
                    "DESC",
                    new Contact[] {new Contact("Lina", ""), new Contact("Ivan", ""), new Contact("Egor", "")}
                ),
                Arguments.of(
                    new String[] {"Ivan Portnov", "Egor", "Lina"},
                    "ASC",
                    new Contact[] {new Contact("Egor", ""), new Contact("Lina", ""), new Contact("Ivan", "Portnov")}
                ),
                Arguments.of(
                    new String[] {"Ivan Portnov", "Egor Portnov", "Lina", "", "Ilya Komarov"},
                    "ASC",
                    new Contact[] {new Contact("", ""), new Contact("Lina", ""), new Contact("Ilya", "Komarov"),
                        new Contact("Egor", "Portnov"), new Contact("Ivan", "Portnov")}
                )
            );
        }

        @Test
        void shouldThrowIllegalArgumentExceptionForNullSortTypeInput() {
            assertThrows(IllegalArgumentException.class, () -> Task5.parseContacts(new String[] {}, null));
        }

        @Test
        void shouldThrowIllegalArgumentExceptionForIncorrectSortTypeInput() {
            assertThrows(IllegalArgumentException.class, () -> Task5.parseContacts(new String[] {}, "wrong type"));
        }
    }

    @Nested
    @DisplayName("6. Биржа")
    class TestTask6 {
        static StockMarket market;
        static Stock stock1;
        static Stock stock2;
        static Stock stock3;

        @BeforeAll
        static void setup() {
            market = new SimpleStockMarket(new PriorityQueue<>());
            stock1 = new Stock(10.0);
            stock2 = new Stock(5.0);
            stock3 = new Stock(777.0);
            market.add(stock1);
            market.add(stock2);
            market.add(stock3);
        }

        @Test
        void shouldAddStockToMarket() {
            List<Stock> expected = new ArrayList<>();
            expected.add(stock1);
            expected.add(stock2);
            expected.add(stock3);

            List<Stock> actual = market.getAllStocks();

            assertThat(actual).hasSameElementsAs(expected);
        }

        @Test
        void shouldRemoveStockFromMarket() {
            List<Stock> expected = new ArrayList<>();
            expected.add(stock1);
            expected.add(stock3);

            market.remove(stock2);

            List<Stock> actual = market.getAllStocks();

            assertThat(actual).hasSameElementsAs(expected);
            market.add(stock2);
        }

        @Test
        void shouldReturnTheMostExpensiveStock() {
            Stock actual = market.mostValuableStock();
            assertThat(actual).isEqualTo(stock3);
        }

        @Test
        void shouldThrowIllegalArgumentExceptionForNegativeStock() {
            assertThrows(IllegalArgumentException.class, () -> new Stock(-3.4));
        }
    }

    @Nested
    @DisplayName("7. Дерево и null")
    class TestTask7 {
        @Test
        void shouldWorkWithNullKey() {
            TreeMap<String, String> tree = new TreeMap<>(new NullableComparator());

            tree.put(null, "test");
            assertThat(tree.containsKey(null)).isTrue();
        }
    }

    @Nested
    @DisplayName("8. Обратный итератор")
    class TestTask8 {
        @Test
        void shouldReturnIntegerElementsInReverseOrder() {
            List<Integer> list = new ArrayList<>();
            int elementsNumber = 5;
            for (int i = 0; i < elementsNumber; i++) {
                list.add(i);
            }
            Iterator<Integer> integerIterator = new BackwardIterator<>(list);

            for (int i = 0; i < elementsNumber; i++) {
                assertThat(integerIterator.next()).isEqualTo(list.get(elementsNumber - i - 1));
            }
        }

        @Test
        void shouldReturnStringElementsInReverseOrder() {
            List<String> list = new ArrayList<>();
            int elementsNumber = 5;
            for (int i = 0; i < elementsNumber; i++) {
                list.add(String.valueOf(i));
            }
            Iterator<String> integerIterator = new BackwardIterator<>(list);

            for (int i = 0; i < elementsNumber; i++) {
                assertThat(integerIterator.next()).isEqualTo(list.get(elementsNumber - i - 1));
            }
        }

        @Test
        void shouldReturnFalseForEmptyList() {
            List<String> list = new ArrayList<>();
            Iterator<String> integerIterator = new BackwardIterator<>(list);

            assertFalse(integerIterator.hasNext());
        }
    }
}
