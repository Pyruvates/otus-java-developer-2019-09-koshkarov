package ru.koshkarovvitaliy;

import java.util.*;

public class DIYarrayList<T> implements List<T> {
    private final static int DEFAULT_SIZE = 10;
    private final static Object[] EMPTY_OBJECT = {};
    private transient Object[] elements;
    private int size;
    private final static int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    public DIYarrayList() {
        this.elements = EMPTY_OBJECT;
    }

    public DIYarrayList(int capacity) {
        if (capacity > 0) {
            this.elements = new Object[capacity];
        } else if (capacity == 0) {
            this.elements = EMPTY_OBJECT;
        } else {
            throw new IllegalArgumentException("Illegal capacity : " + capacity);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean add(T t) {
        add(t, elements, size);
        return true;
    }

    private void add(T t, Object[] elements, int s) {
        if (s == elements.length)
            elements = grow();
        elements[s] = t;
        size = s + 1;
    }

    private Object[] grow() {
        return grow(size + 1);
    }

    private Object[] grow(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity > 0 || elements != EMPTY_OBJECT) {
            int newSize = newSize(oldCapacity, capacity - oldCapacity, oldCapacity >> 1);
            return elements = Arrays.copyOf(elements, newSize);
        } else {
            return elements = new Object[Math.max(DEFAULT_SIZE, capacity)];
        }
    }

    private int newSize(int oldSize, int minGrowth, int prefGrowth) {
        int newSize = Math.max(minGrowth, prefGrowth) + oldSize;
        if (newSize - MAX_ARRAY_SIZE <= 0) {
            return newSize;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    private class Itr<T> implements Iterator<T> {
        int cursor;
        int lastIndex = -1;

        Itr() {}

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public T next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            Object[] elementData = DIYarrayList.this.elements;
            if (cursor >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            cursor = cursor + 1;
            return (T) elements[lastIndex = cursor];
        }
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}
