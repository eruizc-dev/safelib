package dev.eruizc.collections.lists;

import java.util.Iterator;

import dev.eruizc.Stream;
import dev.eruizc.Streamable;
import dev.eruizc.safelib.Optional;

public class ArrayList<T> implements Iterable<T>, Streamable<T> {
    private int size;
    private Object[] array;

    public ArrayList() {
        this(16);
    }

    public ArrayList(int capacity) {
        assert capacity > 0 : "Capacity must be a positive integer";

        this.size = 0;
        this.array = new Object[capacity];
    }

    public ArrayList(@SuppressWarnings("unchecked") T... elements) {
        this.size = elements.length;
        this.array = new Object[elements.length];
        System.arraycopy(elements, 0, this.array, 0, elements.length);
    }

    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    public Optional<T> get(int index) {
        return index < size ? Optional.of((T) array[index]) : Optional.empty();
    }

    public void add(T obj) {
        ensureCapacity(this.size + 1);
        this.array[this.size] = obj;
        this.size++;
    }

    private void ensureCapacity(int desired) {
        if (array.length >= desired) {
            return;
        }

        var capacity = array.length;
        while (capacity < desired) {
            capacity *= 2;
        }

        var newArr = new Object[capacity];
        System.arraycopy(this.array, 0, newArr, 0, this.size);
        this.array = newArr;
    }

    @Override
    public Stream<T> stream() {
        return new Stream<>(this.iterator());
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int next = 0;

            @Override
            public boolean hasNext() {
                return next < size;
            }

            @Override
            @SuppressWarnings("unchecked")
            public T next() {
                return (T) array[next++];
            }
        };
    }
}
