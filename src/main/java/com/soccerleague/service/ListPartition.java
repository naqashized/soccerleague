package com.soccerleague.service;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public final class ListPartition<T> extends AbstractList<List<T>> {

    private final List<T> list;
    private final int chunkSize;

    public ListPartition(List<T> list, int chunkSize) {
        this.list = new ArrayList<>(list);
        this.chunkSize = chunkSize;
    }

    public static <T> ListPartition<T> ofSize(List<T> list, int chunkSize) {
        return new ListPartition<>(list, chunkSize);
    }

    @Override
    public List<T> get(int index) {
        int start = index * chunkSize;
        int end = Math.min(start + chunkSize, list.size());

        if (start > end) {
            throw new IndexOutOfBoundsException("Invalid start and end");
        }

        return new ArrayList<>(list.subList(start, end));
    }

    @Override
    public int size() {
        return (int) Math.ceil((double) list.size() / (double) chunkSize);
    }
}
