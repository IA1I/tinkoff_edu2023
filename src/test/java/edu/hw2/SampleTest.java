package edu.hw2;

import edu.hw2.Task1.Expr;
import edu.hw2.Task2.Rectangle;
import edu.hw2.Task2.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
            return new Arguments[]{
                Arguments.of(new Rectangle(0, 0)),
                Arguments.of(new Square(0, 0))
            };
        }

        @ParameterizedTest
        @MethodSource("rectangles")
        void rectangleArea(Rectangle rect) {
            rect = rect.setWidth(20);
            rect = rect.setHeight(10);

            assertThat(rect.area()).isEqualTo(200.0);
        }
    }
}
