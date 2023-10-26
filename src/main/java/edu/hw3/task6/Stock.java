package edu.hw3.task6;

import org.jetbrains.annotations.NotNull;

public class Stock implements Comparable<Stock> {
    private double price;

    public Stock(double price) {
        if (isPriceNegative(price)) {
            throw new IllegalArgumentException();
        }
        this.price = price;
    }

    @Override
    public int compareTo(@NotNull Stock o) {
        if (this == o) {
            return 0;
        }

        return (int) (o.price - this.price);
    }

    private boolean isPriceNegative(double price) {
        return price < 0;
    }
}
