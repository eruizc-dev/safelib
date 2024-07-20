package dev.eruizc;

import dev.eruizc.collections.lists.ArrayList;
import java.util.Iterator;
import java.util.function.*;

public class Stream<T> {
    protected final Iterator<T> iterator;
    protected Function<Object, Object> transformation;

    public Stream(Iterator<T> iterator) {
        this.iterator = iterator;
        this.transformation = x -> x;
    }

    protected Stream(Iterator<T> iterator, Function<Object, Object> transformation) {
        this.iterator = iterator;
        this.transformation = transformation;
    }

    public final int count() {
        return this.reduce(0, (acc, i) -> acc + 1);
    }

    @SuppressWarnings("unchecked")
    public final Stream<T> filter(Predicate<T> predicate) {
        this.transformation = this.transformation.andThen(x -> {
            if (!predicate.test((T) x)) {
                throw new ItemFiltered();
            }
            return x;
        });
        return this;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public final <K> Stream<K> map(Function<T, K> mapper) {
        return new Stream(iterator, transformation.andThen((Object x) -> (Object) mapper.apply((T) x)));
    }

    public final ArrayList<T> toList() {
        return this.reduce(new ArrayList<T>(), (acc, i) -> { acc.add(i); });
    }

    public final <E extends Object> E reduce(E accumulator, BiConsumer<E, T> reducer) {
        return reduce(accumulator, (acc, x) -> {
            reducer.accept(acc, x);
            return acc;
        });
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

    public final ParallelStream<T> parallel() {
        return new ParallelStream<T>(iterator, transformation);
    }

    protected static class ItemFiltered extends Error { }
}
