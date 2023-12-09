package edu.project4;

import edu.project4.transformation.affine.Affine;
import edu.project4.transformation.affine.AffineUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class AffineUtilsTest {
    @Test
    void shouldReturnListOfAffineTransformations(){
        List<Affine> actual = AffineUtils.getAffineTransformations();
        assertThat(actual.size()).isEqualTo(7);
    }
}
