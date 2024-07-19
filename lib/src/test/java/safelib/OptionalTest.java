package safelib;

import static org.junit.jupiter.api.Assertions.*;

import dev.eruizc.safelib.Optional;
import org.junit.jupiter.api.Test;

class OptionalTest {
    @Test void nullIsEmpty() {
        var x = Optional.of(null);
        assertAll(
            () -> assertTrue(x.isEmpty()),
            () -> assertTrue(x instanceof Optional.Empty),
            () -> assertFalse(x.isPresent()),
            () -> assertFalse(x instanceof Optional.Present)
        );
    }

    @Test void somethingIsPresent() {
        var x = Optional.of(12);
        assertAll(
            () -> assertTrue(x.isPresent()),
            () -> assertTrue(x instanceof Optional.Present),
            () -> assertFalse(x.isEmpty()),
            () -> assertFalse(x instanceof Optional.Empty)
        );
    }
}
