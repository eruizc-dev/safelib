package dev.eruizc;

import dev.eruizc.collections.lists.ArrayList;
import java.util.Iterator;
import java.util.function.*;

public class Stream<T> {
    private final Iterator<T> iterator;
    private Function<Object, Object> transformation;

    public Stream(Iterator<T> iterator) {
        this.iterator = iterator;
        this.transformation = x -> x;
    }

    private Stream(Iterator<T> iterator, Function<Object, Object> transformation) {
        this.iterator = iterator;
        this.transformation = transformation;
    }

    public int count() {
        return this.reduce(0, (acc, i) -> acc + 1);
    }

    @SuppressWarnings("unchecked")
    public Stream<T> filter(Predicate<T> predicate) {
        this.transformation = this.transformation.andThen(x -> {
            if (!predicate.test((T) x)) {
                throw new ItemFiltered();
            }
            return x;
        });
        return this;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <K> Stream<K> map(Function<T, K> mapper) {
        return new Stream(iterator, transformation.andThen((Object x) -> (Object) mapper.apply((T) x)));
    }

    public ArrayList<T> toList() {
        return this.reduce(new ArrayList<T>(), (acc, i) -> { acc.add(i); });
    }

    @SuppressWarnings("unchecked")
    public <E> E reduce(E accumulator, BiConsumer<E, T> reducer) {
        while(iterator.hasNext()) {
            try {
                var item = transformation.apply(iterator.next());
                reducer.accept(accumulator, (T) item);
            } catch (ItemFiltered e) { }
        }
        return accumulator;
    }

    @SuppressWarnings("unchecked")
    public <E> E reduce(E accumulator, BiFunction<E, T, E>  reducer) {
        while(iterator.hasNext()) {
            try {
                var item = transformation.apply(iterator.next());
                accumulator = reducer.apply(accumulator, (T) item);
            } catch (ItemFiltered e) { }
        }
        return accumulator;
    }

    private static class ItemFiltered extends Error { }
}
