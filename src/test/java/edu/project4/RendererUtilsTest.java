package edu.project4;

import edu.project4.renderer.RendererUtils;
import edu.project4.transformation.Linear;
import edu.project4.transformation.Transformation;
import edu.project4.transformation.affine.Affine;
import edu.project4.utility.Point;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.assertThat;

public class RendererUtilsTest {
    @Test
    void shouldReturnRotatedPoint() {
        Point expected = new Point(-17.809, 13.14675);
        Point actual = RendererUtils.getRotatedPoint(new Point(7.0, 21), 2 * Math.PI / 5);
        assertThat(actual.getX()).isCloseTo(expected.getX(), Percentage.withPercentage(1));
        assertThat(actual.getY()).isCloseTo(expected.getY(), Percentage.withPercentage(1));
    }

    @ParameterizedTest
    @MethodSource("pointsWhichInArea")
    void shouldReturnTrueForPointsWithCoordinatesInArea(final double x, final double y) {
        assertThat(RendererUtils.isPointInRightArea(new Point(x, y))).isTrue();
    }

    static Stream<Arguments> pointsWhichInArea() {
        return Stream.of(
            Arguments.of(0.0, 0.0),
            Arguments.of(-1.0, -1.0),
            Arguments.of(1.0, -1.0),
            Arguments.of(-1.0, 1.0),
            Arguments.of(1.0, 1.0),
            Arguments.of(1.777, 1.0),
            Arguments.of(-1.777, -1.0),
            Arguments.of(0.68, -0.87),
            Arguments.of(-0.77, 0.516)
        );
    }

    @ParameterizedTest
    @MethodSource("pointsWhichNotInArea")
    void shouldReturnFalseForPointsWithCoordinatesNotInArea(final double x, final double y) {
        assertThat(RendererUtils.isPointInRightArea(new Point(x, y))).isFalse();
    }

    static Stream<Arguments> pointsWhichNotInArea() {
        return Stream.of(
            Arguments.of(1.78, 0.0),
            Arguments.of(-1.78, 0.0),
            Arguments.of(-1.0, -1.1),
            Arguments.of(-1.0, 1.1),
            Arguments.of(1.8, 1.1),
            Arguments.of(-1.8, -1.1)
        );
    }

    @RepeatedTest(10)
    void shouldReturnRandomPoint() {
        Point actual = RendererUtils.getRandomPoint();
        assertThat(actual.getX()).isBetween(-1.777, 1.777);
        assertThat(actual.getY()).isBetween(-1.0, 1.0);
    }

    @Test
    void shouldReturnTransformationFromListOf1Element() {
        Transformation actual = RendererUtils.getRandomTransformation(List.of(new Linear()));

        assertThat(actual).isInstanceOf(Linear.class);
    }

    @Test
    void shouldReturnAffineTransformationFromListOf1Element() {
        Affine actual = RendererUtils.getRandomAffine(List.of(new Affine(0, 0, 0, 0, 0, 0, 0, 0, 0)));

        assertThat(actual).isInstanceOf(Affine.class);
    }
}
