package edu.hw3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task3 {

    private static final int DEFAULT_VALUE = 0;
    private static final int INCREMENT = 1;

    private Task3() {
    }

    public static <T> Map<T, Integer> freqDict(List<T> listObjects) {
        if (listObjects == null) {
            throw new IllegalArgumentException();
        }
        Map<T, Integer> freqDict = new HashMap<>();
        for (var object : listObjects) {
            freqDict.put(object, freqDict.getOrDefault(object, DEFAULT_VALUE) + INCREMENT);
        }

        return freqDict;
    }
}
