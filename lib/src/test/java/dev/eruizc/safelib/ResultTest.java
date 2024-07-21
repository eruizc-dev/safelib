package dev.eruizc.safelib;

import static org.junit.jupiter.api.Assertions.*;

import dev.eruizc.safelib.Result.Ok;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

public class ResultTest {
    @Test void ok() {
        var result = Result.ok(33);
        assertAll(
            () -> assertTrue(result.isOk()),
            () -> assertTrue(result instanceof Ok),
            () -> assertFalse(result.isError()),
            () -> assertFalse(result instanceof Result.Error),
            () -> {
                switch (result) {
                    case Ok<Integer> x -> assertEquals(33, x.get());
                    default -> throw new AssertionFailedError("Expected Result.Ok");
                }
            }
        );
    }

    @Test void error() {
        var result = Result.error(new Throwable("oof"));
        assertAll(
            () -> assertTrue(result.isError()),
            () -> assertTrue(result instanceof Result.Error),
            () -> assertFalse(result.isOk()),
            () -> assertFalse(result instanceof Result.Ok),
            () -> {
                switch (result) {
                    case Result.Error<Throwable> e -> assertEquals("oof", e.unwrap().getMessage());
                    default -> throw new AssertionFailedError("Expected Result.Error");
                }
            }
        );
    }
}
