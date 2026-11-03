package collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CustomArrayListTest {

    private CustomArrayList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new CustomArrayList<>();
    }

    private void fill(int count) {
        for (int i = 0; i < count; i++) {
            list.add(i);
        }
    }

    @Test
    void newListIsEmpty() {
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    void addOneElement() {
        list.add(5);
        assertEquals(1, list.size());
        assertFalse(list.isEmpty());
        assertEquals(5, list.get(0));
    }

    @Test
    void addSeveralElementsKeepsOrder() {
        list.add(3);
        list.add(1);
        list.add(2);
        assertEquals(3, list.size());
        assertEquals(3, list.get(0));
        assertEquals(1, list.get(1));
        assertEquals(2, list.get(2));
    }

    @Test
    void addMoreThanTenElementsKeepsData() {
        fill(25);
        assertEquals(25, list.size());
        for (int i = 0; i < 25; i++) {
            assertEquals(i, list.get(i));
        }
    }

    @Test
    void getFirstMiddleAndLast() {
        fill(5);
        assertEquals(0, list.get(0));
        assertEquals(2, list.get(2));
        assertEquals(4, list.get(4));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 3})
    void getRejectsWrongIndex(int index) {
        fill(3);
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(index));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 3})
    void removeRejectsWrongIndex(int index) {
        fill(3);
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(index));
    }

    @Test
    void removeFirst() {
        fill(5);
        assertEquals(0, list.remove(0));
        assertEquals(4, list.size());
        assertEquals(1, list.get(0));
        assertEquals(4, list.get(3));
    }

    @Test
    void removeMiddle() {
        fill(5);
        assertEquals(2, list.remove(2));
        assertEquals(4, list.size());
        assertEquals(1, list.get(1));
        assertEquals(3, list.get(2));
        assertEquals(4, list.get(3));
    }

    @Test
    void removeLast() {
        fill(5);
        assertEquals(4, list.remove(4));
        assertEquals(4, list.size());
        assertEquals(3, list.get(3));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(4));
    }

    @Test
    void addNullThrows() {
        assertThrows(NullPointerException.class, () -> list.add(null));
    }

    @Test
    void iteratorGoesInOrder() {
        fill(4);
        int expected = 0;
        for (int value : list) {
            assertEquals(expected, value);
            expected++;
        }
        assertEquals(4, expected);
    }

    @Test
    void nextOfFinishedIteratorThrows() {
        fill(2);
        Iterator<Integer> iterator = list.iterator();
        iterator.next();
        iterator.next();
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }
}
