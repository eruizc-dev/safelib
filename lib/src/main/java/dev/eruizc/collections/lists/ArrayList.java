package dev.eruizc.collections.lists;

import dev.eruizc.collections.List;
import dev.eruizc.safelib.Optional;
import java.util.Iterator;

public class ArrayList<T> implements List<T> {
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
    @Override public Optional<T> get(int index) {
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

    public boolean contains(T item) {
        return this.indexOf(item).isPresent();
    }

    @Override public Optional<Integer> indexOf(T item) {
        for (int i = 0; i < this.size; i++) {
            if (item.equals(this.array[i])) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }
}
