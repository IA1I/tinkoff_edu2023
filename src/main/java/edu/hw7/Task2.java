package edu.hw7;

import java.math.BigInteger;
import java.util.stream.IntStream;

public class Task2 {
    private Task2() {
    }

    public static BigInteger factorial(int n) {
        if (n < 2) {
            return BigInteger.ONE;
        }
        return IntStream.rangeClosed(2, n)
            .parallel()
            .mapToObj(BigInteger::valueOf)
            .reduce(BigInteger::multiply)
            .orElse(BigInteger.ONE);
    }
}
