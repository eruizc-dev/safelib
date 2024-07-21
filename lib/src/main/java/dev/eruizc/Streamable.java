package dev.eruizc;

public interface Streamable<T> extends Iterable<T> {
    default Stream<T> stream() {
        return new Stream<>(this.iterator());
    }
}
