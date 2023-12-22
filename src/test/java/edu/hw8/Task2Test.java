package edu.hw8;

import edu.hw8.task2.FixedThreadPool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Comparator;
import java.util.OptionalInt;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {

    @Test
    void shouldThrowIllegalArgumentExceptionForWrongThreadsNumber() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> FixedThreadPool.create(-1));
    }

    @Test
    void shouldSOUTMessage() {
        try (FixedThreadPool threadPool = FixedThreadPool.create(2)) {
            threadPool.start();
            threadPool.execute(() -> System.out.println("working"));
            threadPool.execute(() -> System.out.println("working"));
            threadPool.execute(() -> System.out.println("working"));
            threadPool.execute(() -> System.out.println("working"));
            threadPool.execute(() -> System.out.println("working"));
            threadPool.execute(() -> System.out.println("working"));
            threadPool.execute(() -> System.out.println("working"));
            threadPool.execute(() -> System.out.println("working"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldCountFibonacciNumbersParallel() {
        int[] expected = new int[] {0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144};
        int[] actual = new int[expected.length];
        try (FixedThreadPool threadPool = FixedThreadPool.create(3)) {
            threadPool.start();
            for (int i = 0; i < expected.length; i++) {
                int finalI = i;
                threadPool.execute(() -> {
                    int max = Stream.iterate(new int[] {0, 1}, arr -> new int[] {arr[1], arr[0] + arr[1]})
                        .limit(finalI)
                        .mapToInt(value -> value[1])
                        .max().orElse(0);
                    actual[finalI] = max;
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertThat(actual).containsExactly(expected);
    }
}
