package edu.hw9;

import edu.hw9.task1.StatsCollector;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class Task1Test {
    static double[] data1 = new double[] {1.5, 2.5, 3.25, -4.75, 5.5};
    static double[] data2 = new double[] {1.5, 2.5, 3.25, -4.75};
    static double[] data3 = new double[] {1.5, 2.5, 3.25};
    static double[] data4 = new double[] {1.5, 2.5};

    @Test
    void shouldThrowIllegalArgumentExceptionForWrongThreadsNumber() {
        assertThrows(IllegalArgumentException.class, () -> new StatsCollector(0));
        assertThrows(IllegalArgumentException.class, () -> new StatsCollector(-3));
    }

    @Test
    void shouldCalculateStatsFor1Thread() throws InterruptedException {
        List<List<Double>> expected = new ArrayList<>();
        expected.add(List.of(8.0));
        expected.add(List.of(1.6));
        expected.add(List.of(5.5));
        expected.add(List.of(-4.75));
        StatsCollector statsCollector = new StatsCollector(1);
        statsCollector.push("SUM", data1);
        statsCollector.push("AVG", data1);
        statsCollector.push("MAX", data1);
        statsCollector.push("MIN", data1);
        Thread.sleep(1000);
        List<List<Double>> actual = statsCollector.getStats();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void shouldCalculateStatsFor4Thread() throws InterruptedException {
        List<List<Double>> expected = new ArrayList<>();
        expected.add(List.of(2.5, 7.25, 4.0, 8.0));
        expected.add(List.of(1.6, 0.625, 2.4166666666666665, 2.0));
        expected.add(List.of(3.25, 3.25, 2.5, 5.5));
        expected.add(List.of(-4.75, -4.75, 1.5, 1.5));
        StatsCollector statsCollector = new StatsCollector(4);
        Thread[] threads = initThreads(statsCollector);
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        Thread.sleep(1000);
        List<List<Double>> actual = statsCollector.getStats();
        for (int i = 0; i < 4; i++) {
            assertThat(actual.get(i)).containsExactlyInAnyOrderElementsOf(expected.get(i));
        }
    }

    Thread[] initThreads(StatsCollector statsCollector) {

        Thread[] threads = new Thread[4];
        threads[0] = new Thread(() -> {
            statsCollector.push("SUM", data1);
            statsCollector.push("SUM", data2);
            statsCollector.push("SUM", data3);
            statsCollector.push("SUM", data4);
        });

        threads[1] = new Thread(() -> {
            statsCollector.push("AVG", data1);
            statsCollector.push("AVG", data2);
            statsCollector.push("AVG", data3);
            statsCollector.push("AVG", data4);
        });

        threads[2] = new Thread(() -> {
            statsCollector.push("MAX", data1);
            statsCollector.push("MAX", data2);
            statsCollector.push("MAX", data3);
            statsCollector.push("MAX", data4);
        });

        threads[3] = new Thread(() -> {
            statsCollector.push("MIN", data1);
            statsCollector.push("MIN", data2);
            statsCollector.push("MIN", data3);
            statsCollector.push("MIN", data4);
        });

        return threads;
    }
}
