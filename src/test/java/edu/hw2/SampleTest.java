package edu.hw2;

import edu.hw2.Task1.Expr;
import edu.hw2.Task2.Rectangle;
import edu.hw2.Task2.Square;
import edu.hw2.Task3.PopularCommandExecutor;
import edu.hw2.Task3.connectionmanagers.DefaultConnectionManager;
import edu.hw2.Task3.connectionmanagers.FaultyConnectionManager;
import edu.hw2.Task3.exceptions.ConnectionException;
import edu.hw2.Task4.CallingInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import java.util.Random;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class SampleTest {

    @Nested
    @DisplayName("1. Калькулятор выражений")
    class TestTask1 {
        @Test
        @DisplayName("Тест из задания")
        void testFromTask() {
            var two = new Expr.Constant(2);
            var four = new Expr.Constant(4);
            var negOne = new Expr.Negate(new Expr.Constant(1));
            var sumTwoFour = new Expr.Addition(two, four);
            var mult = new Expr.Multiplication(sumTwoFour, negOne);
            var exp = new Expr.Exponent(mult, 2);
            var res = new Expr.Addition(exp, new Expr.Constant(1));
            double expectedSumTwoFour = 6;
            double expectedMultSumTwoFourAndNegOne = -6;
            double expectedNegSixPowerTwo = 36;
            double expectedExpSumOne = 37;

            assertThat(sumTwoFour.evaluate()).isEqualTo(expectedSumTwoFour);
            assertThat(mult.evaluate()).isEqualTo(expectedMultSumTwoFourAndNegOne);
            assertThat(exp.evaluate()).isEqualTo(expectedNegSixPowerTwo);
            assertThat(res.evaluate()).isEqualTo(expectedExpSumOne);
        }

        @ParameterizedTest
        @DisplayName("Проверка сложения")
        @CsvSource({
            "1, 2, 3",
            "-1, 4, 3",
            "-2, -3, -5"
        })
        void testAddition(final double expression1, final double expression2, final double expected) {
            var sum = new Expr.Addition(expression1, expression2);
            assertThat(sum.evaluate()).isEqualTo(expected);

            sum = new Expr.Addition(new Expr.Constant(expression1), expression2);
            assertThat(sum.evaluate()).isEqualTo(expected);

            sum = new Expr.Addition(expression1, new Expr.Constant(expression2));
            assertThat(sum.evaluate()).isEqualTo(expected);

            sum = new Expr.Addition(new Expr.Constant(expression1), new Expr.Constant(expression2));
            assertThat(sum.evaluate()).isEqualTo(expected);
        }

    }

    @Nested
    @DisplayName("2. Квадрат и прямоугольник")
    class TestTask2 {
        static Arguments[] rectangles() {
            return new Arguments[] {
                Arguments.of(new Rectangle(0, 0)),
                Arguments.of(new Square(0))
            };
        }

        @ParameterizedTest
        @MethodSource("rectangles")
        void rectangleArea(Rectangle rect) {
            rect = rect.setWidth(20);
            rect = rect.setHeight(10);

            assertThat(rect.area()).isEqualTo(200);
        }
    }

    @Nested
    @DisplayName("3. Удаленный сервер")
    class TestTask3 {
        @Test
        @DisplayName("Успешное выполнение команды")
        void testSuccessfulCommandExecutionForStableConnection() {
            Random random = mock(Random.class);
            PopularCommandExecutor executor = new PopularCommandExecutor(new DefaultConnectionManager(random), 1);
            Mockito.when(random.nextInt(0, 10)).thenReturn(2);
            assertDoesNotThrow(() -> executor.updatePackages(random));
        }

        @Test
        @DisplayName("Успешное выполнение команды")
        void testSuccessfulCommandExecutionForFaultyConnection() {
            Random random = mock(Random.class);
            PopularCommandExecutor executor = new PopularCommandExecutor(new FaultyConnectionManager(), 1);
            Mockito.when(random.nextInt(0, 10)).thenReturn(2);
            assertDoesNotThrow(() -> executor.updatePackages(random));
        }

        @Test
        @DisplayName("Превышение количества попыток")
        void testExceedingNumberOfAttemptsForFaultyConnection() {
            PopularCommandExecutor executor = new PopularCommandExecutor(new FaultyConnectionManager(), 5);
            Random random = mock(Random.class);
            Mockito.when(random.nextInt(0, 10)).thenReturn(0);
            Assertions.assertThrows(ConnectionException.class, () -> executor.updatePackages(random));
        }
    }

    @Nested
    @DisplayName("4. Кто вызвал функцию?")
    class TestTask4 {
        @Test
        @DisplayName("Кто вызвал метод callingInfo")
        void testStackTrace() {
            String expectedClassName = "edu.hw2.SampleTest$TestTask4";
            String expectedMethodName = "testStackTrace";
            CallingInfo callingInfo = CallingInfo.callingInfo();
            String actualClassName = callingInfo.className();
            String actualMethodName = callingInfo.methodName();

            assertThat(actualClassName).isEqualTo(expectedClassName);
            assertThat(actualMethodName).isEqualTo(expectedMethodName);
        }

        @Test
        @DisplayName("Тест для проверки вызова конкретного метода")
        void testStackTraceWithSpecificMethod() {
            String expectedClassName = "edu.hw2.SampleTest$TestTask4";
            String expectedMethodName = "testStackTraceWithSpecificMethod";

            CallingInfo callingInfo = CallingInfo.callingInfo("testStackTraceWithSpecificMethod");
            String actualClassName = callingInfo.className();
            String actualMethodName = callingInfo.methodName();

            assertThat(actualClassName).isEqualTo(expectedClassName);
            assertThat(actualMethodName).isEqualTo(expectedMethodName);
        }

        @Test
        @DisplayName("Тест для проверки вызова конкретного метода, который не использовали")
        void testStackTraceWithSpecificMethodThatWasNotCalled() {
            String expectedClassName = "Никто не вызывал";
            String expectedMethodName = "getChance";

            CallingInfo callingInfo = CallingInfo.callingInfo("getChance");
            String actualClassName = callingInfo.className();
            String actualMethodName = callingInfo.methodName();

            assertThat(actualClassName).isEqualTo(expectedClassName);
            assertThat(actualMethodName).isEqualTo(expectedMethodName);
        }
    }
}
