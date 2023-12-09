package edu.project4;

import edu.project4.transformation.Ex;
import edu.project4.transformation.Exponential;
import edu.project4.transformation.Eyefish;
import edu.project4.transformation.Fisheye;
import edu.project4.transformation.Handkerchief;
import edu.project4.transformation.Heart;
import edu.project4.transformation.Julia;
import edu.project4.transformation.Linear;
import edu.project4.transformation.Polar;
import edu.project4.transformation.Sinusoidal;
import edu.project4.transformation.Spherical;
import edu.project4.transformation.Spiral;
import edu.project4.transformation.Swirl;
import edu.project4.transformation.Transformation;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TransformationTest {
    @Test
    void shouldReturnListOfAllTransformations() {
        List expected = new ArrayList<>();
        expected.add(Linear.class);
        expected.add(Sinusoidal.class);
        expected.add(Spherical.class);
        expected.add(Swirl.class);
        expected.add(Polar.class);
        expected.add(Handkerchief.class);
        expected.add(Heart.class);
        expected.add(Spiral.class);
        expected.add(Ex.class);
        expected.add(Julia.class);
        expected.add(Fisheye.class);
        expected.add(Exponential.class);
        expected.add(Eyefish.class);

        List<Transformation> actual = Transformation.getAllTransformations();
        assertThat(actual).map(Transformation::getClass).containsExactlyElementsOf(expected);
    }

    @Test
    void shouldReturnRandomListOfTransformations() {
        List expected = new ArrayList<>();
        expected.add(Linear.class);
        expected.add(Julia.class);
        expected.add(Exponential.class);
        List<Transformation> actual = Transformation.getRandomTransformation(1L);

        assertThat(actual).map(Transformation::getClass).containsExactlyElementsOf(expected);
    }

}
