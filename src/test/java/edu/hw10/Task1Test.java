package edu.hw10;

import edu.hw10.task1.MyClass;
import edu.hw10.task1.MyRecord;
import edu.hw10.task1.RandomObjectGenerator;
import java.util.Random;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task1Test {

    @Test
    void shouldCreateMyClassObjectWithRandomParameters() {
        RandomObjectGenerator rog = new RandomObjectGenerator(new Random(1L));
        MyClass actual = (MyClass) rog.nextObject(MyClass.class);
        assertThat(actual.getIntValue()).isEqualTo(-1155869325);
        assertThat(actual.getDoubleValue())
            .isCloseTo(-1.7159544698553011E9, Percentage.withPercentage(0.01));
        assertThat(actual.getBooleanValue()).isFalse();
        assertThat(actual.getCharValue()).isEqualTo('쳼');
        assertThat(actual.getIntArray()).containsExactly(1429008869, -1465154083, -138487339, -1242363800);
    }

    @Test
    void shouldCreateMyClassObjectWithRandomParametersWithMethod() {
        RandomObjectGenerator rog = new RandomObjectGenerator(new Random(1L));
        MyClass actual = (MyClass) rog.nextObject(MyClass.class, "create");
        assertThat(actual.getIntValue()).isEqualTo(-1155869325);
        assertThat(actual.getDoubleValue())
            .isCloseTo(-1.7159544698553011E9, Percentage.withPercentage(0.01));
        assertThat(actual.getBooleanValue()).isFalse();
        assertThat(actual.getCharValue()).isEqualTo('쳼');
        assertThat(actual.getIntArray()).containsExactly(1429008869, -1465154083, -138487339, -1242363800);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForNonExistingCreatingMethod() {
        RandomObjectGenerator rog = new RandomObjectGenerator(new Random(1L));
        assertThrows(
            IllegalArgumentException.class,
            () -> rog.nextObject(MyClass.class, "notExistingCreate")
        );
    }

    @Test
    void shouldThrowIllegalArgumentExceptionForNonCreatingMethod() {
        RandomObjectGenerator rog = new RandomObjectGenerator(new Random(1L));
        assertThrows(
            IllegalArgumentException.class,
            () -> rog.nextObject(MyClass.class, "getIntValue")
        );
    }

    @Test
    void shouldCreateMyRecordWithAnnotation() {
        RandomObjectGenerator rog = new RandomObjectGenerator(new Random(1L));
        MyRecord actual = (MyRecord) rog.nextObject(MyRecord.class);
        assertThat(actual.intValue()).isEqualTo(100);
        assertThat(actual.doubleValue())
            .isCloseTo(163.56252578129124, Percentage.withPercentage(0.01));
        assertThat(actual.booleanValue()).isFalse();
    }
}
