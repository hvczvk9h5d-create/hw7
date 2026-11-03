package collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CustomArrayList<E> implements CustomList<E> {

    private static final int DEFAULT_CAPACITY = 10;

    private Object[] data;
    private int size;

    public CustomArrayList() {
        data = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public void add(E element) {
        if (element == null) {
            throw new NullPointerException("element is null");
        }
        if (size == data.length) {
            grow();
        }
        data[size] = element;
        size++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        checkIndex(index);
        return (E) data[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        checkIndex(index);
        E removed = (E) data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        size--;
        data[size] = null;
        return removed;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int current = 0;

            @Override
            public boolean hasNext() {
                return current < size;
            }

            @Override
            public E next() {
                if (current >= size) {
                    throw new NoSuchElementException();
                }
                E element = get(current);
                current++;
                return element;
            }
        };
    }

    private void grow() {
        int newCapacity = data.length + data.length / 2;
        Object[] newData = new Object[newCapacity];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }
    }
}
