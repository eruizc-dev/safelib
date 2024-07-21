package dev.eruizc.collections.lists;

import dev.eruizc.Stream;
import dev.eruizc.collections.List;
import dev.eruizc.safelib.Optional;
import java.util.Iterator;

public class LinkedList<T> implements List<T> {
    private final record Node<T>(T value, Node<T> previous, Node<T> next) { }

    private int size;
    private Node<T> first;
    private Node<T> last;

    public LinkedList() {
        size = 0;
        first = null;
        last = null;
    }

    @Override public int size() {
        return this.size;
    }

    @Override public Optional<T> get(int index) {
        if (size == 0) {
            return Optional.empty();
        }
        var current = first;
        while (--index > 0 && current != null) {
            current = current.next();
        }
        return current != null ? Optional.of(current.value()) : Optional.empty();
    }

    @Override public void add(T item) {
        size++;
        last = new Node<>(item, last, null);
        if (size == 1) {
            first = last;
        }
    }

    @Override public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = first;

            @Override public boolean hasNext() {
                return current.next() != null;
            }

            @Override public T next() {
                current = current.next();
                return current.value();
            }
        };
    }

    @Override public Stream<T> stream() {
        return new Stream<>(this.iterator());
    }

    @Override public boolean contains(T item) {
        return this.indexOf(item).isPresent();
    }

    @Override public Optional<Integer> indexOf(T item) {
        var current = this.first;
        for (int i = 0; i < this.size; i++) {
            if (item.equals(current.value())) {
                return Optional.of(i);
            }
            current = current.next();
        }
        return Optional.empty();
    }
}
