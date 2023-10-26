package edu.hw3.task6;

import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class SimpleStockMarket implements StockMarket {
    private PriorityQueue<Stock> stocks;

    public SimpleStockMarket(PriorityQueue<Stock> stocks) {
        this.stocks = stocks;
    }

    @Override
    public void add(Stock stock) {
        stocks.add(stock);
    }

    @Override
    public void remove(Stock stock) {
        stocks.remove(stock);
    }

    @Override
    public Stock mostValuableStock() {
        return stocks.peek();
    }

    @Override
    public List<Stock> getAllStocks() {
        return List.copyOf(stocks);
    }
}
