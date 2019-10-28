package ru.koshkarovvitaliy;

import java.util.*;

public class DIYarrayList<T> implements List<T> {
    private static final Object[] DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA = {};
    private static final int MAX_ARRAY_LENGTH = Integer.MAX_VALUE - 8;
    private static final Object[] EMPTY_OBJECT = {};
    private static final int DEFAULT_SIZE = 10;
    Object[] elements;
    private int size;

    public DIYarrayList() {
        this.elements = EMPTY_OBJECT;
    }

    public DIYarrayList(int capacity) {
        if (capacity == 0) {
            this.elements = EMPTY_OBJECT;
            //this.size = elements.length;
        }
        else if (capacity > 0) {
            this.elements = new Object[capacity];
            //this.size = elements.length;
        }
        else {
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
    public T get(int index) {
        //Objects.checkIndex(index, size);
        return getOldElement(index);
    }

    @Override
    public T set(int index, T element) {
        //Objects.checkIndex(index, size);
        T oldValue = getOldElement(index);
        elements[index] = element;
        return oldValue;
    }

    private T getOldElement(int index) {
        return (T) elements[index];
    }

    @Override
    public void add(int index, T element) {
        final int s;
        Object[] elementData;
        if ( (s = size) == (elementData = this.elements).length ) {
            elementData = grow();
        }
        System.arraycopy(elementData, index, elementData, index + 1, s - index);
        elementData[index] = element;
        size = s + 1;
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

    private Object[] grow(int minCapacity) {
        int oldCapacity = elements.length;
        if (oldCapacity > 0 || elements != DEFAULT_CAPACITY_EMPTY_ELEMENT_DATA) {
            int newCapacity = newLength(oldCapacity,
                    minCapacity - oldCapacity,
                    oldCapacity >> 1);
            return elements = Arrays.copyOf(elements, newCapacity);
        } else {
            return elements = new Object[Math.max(DEFAULT_SIZE, minCapacity)];
        }
//        Object[] o = elements;
//        elements = new Object[o.length * 2 + 1];
//        System.arraycopy(o, 0, elements, 0, o.length);
//        return o;
    }

    private static int newLength(int oldLength, int minGrowth, int prefGrowth) {
        int newLength = Math.max(minGrowth, prefGrowth) + oldLength;
        if (newLength - MAX_ARRAY_LENGTH <= 0) {
            return newLength;
        }
        return hugeLength(oldLength, minGrowth);
    }

    private static int hugeLength(int oldLength, int minGrowth) {
        int minLength = oldLength + minGrowth;
        if (minLength < 0) { // overflow
            throw new OutOfMemoryError("Required array length too large");
        }
        if (minLength <= MAX_ARRAY_LENGTH) {
            return MAX_ARRAY_LENGTH;
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size)
            return (T[]) Arrays.copyOf(elements, size, a.getClass());
        System.arraycopy(elements, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<T> {
        int cursor;
        int lastReturn = -1;

        Itr() {}

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public T next() {
            int i = cursor;
            if (i >= size)
                throw new NoSuchElementException();
            Object[] o = DIYarrayList.this.elements;
            if (i >= o.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            lastReturn = i;
            return (T) elements[lastReturn];
        }
    }

    @Override
    public ListIterator<T> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ListItr(0);
    }

    private class ListItr extends Itr implements ListIterator<T> {

        ListItr(int index) {
            super();
            cursor = index;
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public T previous() {
            int i = cursor - 1;
            if (i < 0)
                throw new NoSuchElementException();
            Object[] o = DIYarrayList.this.elements;
            if (i >= o.length)
                throw new ConcurrentModificationException();
            cursor = i;
            return (T) o[i];
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void set(T t) {
            try {
                DIYarrayList.this.set(lastReturn, t);
            } catch (IndexOutOfBoundsException e) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void add(T t) {
            int i = cursor;
            DIYarrayList.this.add(i, t);
            cursor = i + 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public boolean contains(Object o) {
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
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}