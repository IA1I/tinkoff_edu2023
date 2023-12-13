package edu.hw10.task2;

public interface IntInterface {
    @Cache(persist = false)
    int get(int a);

    @Cache(persist = true)
    int getWithCacheOnDisk(int a);
}
