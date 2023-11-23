package edu.hw7;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {

    @Test
    void shouldReturnFactorialOf7() {
        BigInteger expected = new BigInteger("5040");
        BigInteger actual = Task2.factorial(7);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldReturnFactorialIf0() {
        BigInteger expected = BigInteger.ONE;
        BigInteger actual = Task2.factorial(1);
        assertThat(actual).isEqualTo(expected);
    }
}
