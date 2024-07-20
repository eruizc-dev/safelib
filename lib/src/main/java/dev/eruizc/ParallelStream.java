package dev.eruizc;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import dev.eruizc.collections.lists.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.function.*;

public class ParallelStream<T> extends Stream<T> {
    public ParallelStream(Iterator<T> iterator, Function<Object, Object> transformations) {
        super(iterator, transformations);
    }

    @SuppressWarnings("unchecked")
    @Override public <E> E reduce(E accumulator, BiFunction<E, T, E>  reducer) {
        var futures = new ArrayList<CompletableFuture<Object>>();

        while(iterator.hasNext()) {
            var item = iterator.next();
            futures.add(supplyAsync(() -> transformation.apply(item)));
        }

        for (var future : futures) {
            try {
                accumulator = reducer.apply(accumulator, (T) future.join());
            } catch (ItemFiltered e) { }
        }

        return accumulator;
    }
}
