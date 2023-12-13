package edu.hw10.task1;

public class MyClass {
    private int intValue;
    private double doubleValue;
    private boolean booleanValue;
    private char charValue;

    private int[] intArray;

    public MyClass() {
    }

    public MyClass(int intValue, double doubleValue, boolean booleanValue, char charValue) {
        this.intValue = intValue;
        this.doubleValue = doubleValue;
        this.booleanValue = booleanValue;
        this.charValue = charValue;
    }

    public MyClass(int intValue, double doubleValue, boolean booleanValue, char charValue, int[] intArray) {
        this.intValue = intValue;
        this.doubleValue = doubleValue;
        this.booleanValue = booleanValue;
        this.charValue = charValue;
        this.intArray = intArray;
    }

    public static MyClass create(
        int intValue,
        double doubleValue,
        boolean booleanValue,
        char charValue,
        int[] intArray
    ) {
        return new MyClass(intValue, doubleValue, booleanValue, charValue, intArray);
    }

    public int getIntValue() {
        return intValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public boolean getBooleanValue() {
        return booleanValue;
    }

    public char getCharValue() {
        return charValue;
    }

    public int[] getIntArray() {
        return intArray;
    }
}
