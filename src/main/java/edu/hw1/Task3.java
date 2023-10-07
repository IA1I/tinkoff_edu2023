package edu.hw1;

import java.util.Arrays;

public class Task3 {
    public static final int ZERO_LENGTH = 0;
    public static boolean isNestable(int[] firstArray, int[] secondArray){
        if(!isValidInput(firstArray, secondArray)){
            return false;
        }
        int firstMin = Integer.MAX_VALUE, secondMin = Integer.MAX_VALUE;
        int firstMax = Integer.MIN_VALUE, secondMax = Integer.MIN_VALUE;
        for(var number : firstArray){
            firstMin = Math.min(firstMin, number);
            firstMax = Math.max(firstMax, number);
        }
        for(var number : secondArray){
            secondMin = Math.min(secondMin, number);
            secondMax = Math.max(secondMax, number);
        }

        return firstMin > secondMin && firstMax < secondMax;
    }

    private static boolean isValidInput(int[] firstArray, int[] secondArray){
        if(firstArray == null || secondArray == null){
            return false;
        }
        return firstArray.length != ZERO_LENGTH && secondArray.length != ZERO_LENGTH;
    }
}
