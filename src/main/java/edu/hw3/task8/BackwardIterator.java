package edu.hw3.task8;

import java.util.Iterator;
import java.util.List;

public class BackwardIterator<T> implements Iterator<T> {
    private final List<T> list;
    private int currentIndex;

    public BackwardIterator(List<T> list) {
        this.list = list;
        this.currentIndex = list.size();

    }

    @Override
    public boolean hasNext() {
        return currentIndex > 0;
    }

    @Override
    public T next() {
        return list.get(--currentIndex);
    }
}
