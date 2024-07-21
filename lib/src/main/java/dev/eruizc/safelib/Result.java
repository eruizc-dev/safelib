package dev.eruizc.safelib;

import java.util.function.Consumer;
import java.util.function.Function;

public sealed interface Result<T, E> permits Result.Ok, Result.Error {
    public boolean isOk();
    public boolean isError();
    public <T2> Result<T2, E> map(Function<T, T2> function);
    public Result<T, E> ifError(Consumer<E> consumer);

    public static <T> Result<T, Void> ok(T value) {
        return new Ok<>(value);
    }

    public static <E extends Throwable> Result<Void, E> error(E error) {
        return new Error<>(error);
    }

    public static final class Ok<T> implements Result<T, Void> {
        private final Object value;

        public Ok(T value) {
            this.value = value;
        }

        @SuppressWarnings("unchecked")
        public T get() {
            return (T) this.value;
        }

        @Override public boolean isOk() {
            return true;
        }

        @Override public boolean isError() {
            return false;
        }

        @SuppressWarnings("unchecked")
        @Override public <T2> Result<T2, Void> map(Function<T, T2> function) {
            var result = function.apply((T) this.value);
            return new Ok<>(result);
        }

        @Override public Result<T, Void> ifError(Consumer<Void> consumer) {
            return this;
        }
    }

    public static final class Error<E extends Throwable> implements Result<Void, E> {
        private final E error;

        public Error(E error) {
            this.error = error;
        }

        public E unwrap() {
            return this.error;
        }

        public void throwError() throws E {
            throw error;
        }

        @Override public boolean isOk() {
            return false;
        }

        @Override public boolean isError() {
            return true;
        }

        @SuppressWarnings("unchecked")
        @Override public <T2> Result<T2, E> map(Function<Void, T2> function) {
            return (Result<T2, E>) this;
        }

        @Override public Result<Void, E> ifError(Consumer<E> consumer) {
            consumer.accept(this.error);
            return this;
        }
    }
}
