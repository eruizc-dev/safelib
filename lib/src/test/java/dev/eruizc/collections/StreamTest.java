package dev.eruizc.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.eruizc.collections.lists.ArrayList;
import org.junit.jupiter.api.Test;

public class StreamTest {
    @Test void stream() {
        var list = new ArrayList<>(1, 2, 3, 4, 5);
        var sum = list.stream().reduce(0, (acc, x) -> acc + x);
        assertEquals(15, sum);
    }

    @Test void filteredStream() {
        var list = new ArrayList<>(1, 2, 3, 4);
        var sum = list.stream()
                .filter(x -> x % 2 == 0)
                .reduce(0, (acc, i) -> acc + i);
        assertEquals(6, sum);
    }

    @Test void multipleFilters() {
        var list = new ArrayList<>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        var sum = list.stream()
                .filter(x -> x % 2 == 0)
                .filter(x -> x % 3 == 0)
                .reduce(0, (acc, i) -> acc + i);
        assertEquals(18, sum);
    }

    @Test void mapping() {
        var list = new ArrayList<>(1, 2, 3);
        var str = list.stream()
                .map(x -> x.toString())
                .reduce("", (acc, x) -> acc + x );
        assertEquals("123", str);
    }

    @Test void mapsAndFilters() {
        var list = new ArrayList<>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        var str = list.stream()
                .filter(x -> x % 2 == 0)
                .map(x -> x.toString())
                .filter(x -> x.length() == 2)
                .reduce("", (acc, x) -> acc + x );
        assertEquals("1012", str);
    }

    @Test void parallel() {
        var list = new ArrayList<>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 16, 18, 20);
        var str = list.stream()
                .parallel()
                .filter(x -> x % 2 == 0)
                .map(x -> x.toString())
                .filter(x -> x.length() == 2)
                .toList();
        assertTrue(str.contains("10"));
        assertTrue(str.contains("12"));
        assertTrue(str.contains("14"));
        assertTrue(str.contains("16"));
        assertTrue(str.contains("18"));
        assertTrue(str.contains("20"));
    }
}
