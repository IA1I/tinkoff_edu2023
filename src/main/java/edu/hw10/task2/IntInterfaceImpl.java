package edu.hw10.task2;

public class IntInterfaceImpl implements IntInterface {

    private static final int CONST = 5;

    @Override
    public int get(int a) {
        return a + CONST;
    }

    @Override
    public int getWithCacheOnDisk(int a) {
        return a - CONST;
    }
}
