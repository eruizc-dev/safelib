package dev.eruizc.collections;

import dev.eruizc.Streamable;
import dev.eruizc.safelib.Optional;

public interface ReadOnlyList<T> extends Streamable<T>{
    int size();
    Optional<T> get(int index);
    boolean contains(T item);
    Optional<Integer> indexOf(T item);
}
