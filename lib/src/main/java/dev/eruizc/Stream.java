package dev.eruizc;

import dev.eruizc.collections.lists.ArrayList;
import java.util.Iterator;
import java.util.function.*;

public class Stream<T> {
    private final Iterator<T> iterator;
    private final ArrayList<Transformation<T, T>> transformations;

    public Stream(Iterator<T> iterator) {
        this.iterator = iterator;
        this.transformations = new ArrayList<>();
    }

    public int count() {
        return this.reduce(0, (acc, i) -> acc + 1);
    }

    public Stream<T> filter(Predicate<T> predicate) {
        transformations.add(new Transformation.Filter<T>(predicate));
        return this;
    }

    public ArrayList<T> toList() {
        return this.reduce(new ArrayList<T>(), (acc, i) -> { acc.add(i); });
    }

    public <E> E reduce(E accumulator, BiConsumer<E, T> reducer) {
        while(iterator.hasNext()) {
            reducer.accept(accumulator, iterator.next());
        }
        return accumulator;
    }

    public <E> E reduce(E accumulator, BiFunction<E, T, E>  reducer) {
        while(iterator.hasNext()) {
            var item = iterator.next();
            for (var transformation: transformations) {
                item = transformation.apply(item);
                if (item == null) {
                    break;
                }
            }

            if (item != null) {
                accumulator = reducer.apply(accumulator, item);
            }
        }
        return accumulator;
    }

    public static sealed interface Transformation<T, R> permits Transformation.Filter {
        public R apply(T item);

        public static final class Filter<T> implements Transformation<T, T> {
            public final Predicate<T> predicate;

            public Filter(Predicate<T> predicate) {
                this.predicate = predicate;
            }

            @Override public T apply(T item) {
                return predicate.test(item) ? item : null;
            }
        }
    }
}
