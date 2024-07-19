package dev.eruizc.safelib;

import java.util.function.Consumer;

public sealed interface Optional<T> permits Optional.Present, Optional.Empty {
    public boolean isPresent();
    public boolean isEmpty();
    public Optional<T> ifPresent(Consumer<T> consumer);
    public T orElse(T value);

    public static <T> Optional<T> of(T obj) {
        return obj != null ? new Present<>(obj) : new Empty<>();
    }

    public static <T> Optional<T> empty() {
        return new Empty<T>();
    }

    public static final class Present<T> implements Optional<T> {
        private final T obj;

        private Present(T obj) {
            this.obj = obj;
        }

        public T get() {
            return obj;
        }

        @Override public boolean isPresent() {
            return true;
        }

        @Override public boolean isEmpty() {
            return false;
        }

        @Override public Optional<T> ifPresent(Consumer<T> consumer) {
            consumer.accept(this.obj);
            return this;
        }

        @Override public T orElse(T value) {
            return this.obj;
        }
    }

    public static final class Empty<T> implements Optional<T> {
        private Empty() { }

        @Override public boolean isPresent() {
            return false;
        }

        @Override public boolean isEmpty() {
            return true;
        }

        @Override public Optional<T> ifPresent(Consumer<T> consumer) {
            return this;
        }

        @Override public T orElse(T value) {
            return value;
        }
    }
}
