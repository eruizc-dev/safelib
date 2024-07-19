package dev.eruizc.collections.lists;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ArrayListTest {
    @Test void create() {
        var list = new ArrayList<Integer>();
        assertEquals(0, list.size());

        for (int i = 1; i <= 256; i++) {
            list.add(i);
            assertEquals(i, list.size());
        }
    }
}
