package edu.hw7;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class Task1Test {

    @Test
    void shouldReturnIncrementedValue() {
        int input = 10000000;
        int expected = 20000000;
        int actual = Task1.increment(input);
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
