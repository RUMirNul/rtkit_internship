package ru.asvistunov.rtkit.internship.collections;

import java.util.AbstractList;
import java.util.Arrays;

/**
 * Класс `MyArrayList` представляет собой собственную реализацию динамического массива.
 */
public class MyArrayList<E> extends AbstractList<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size;

    /**
     * Конструктор без параметров, инициализирует динамический массив с начальной емкостью по умолчанию.
     */
    public MyArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Конструктор с указанием начальной емкости динамического массива.
     *
     * @param capacity Начальная емкость динамического массива.
     */
    public MyArrayList(int capacity) {
        this.elements = new Object[capacity];
        this.size = 0;
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
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        return (E) elements[index];
    }

    @Override
    public boolean add(E element) {
        resize();
        elements[size] = element;
        size++;
        return true;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        resize();
        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }
        elements[index] = element;
        size++;
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        E previous = get(index);
        elements[index] = element;
        return previous;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        E removed = get(index);
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[size - 1] = null;
        size--;
        return removed;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            return (T[]) Arrays.copyOf(elements, size, a.getClass());
        }
        if (a.length > size) {
            System.arraycopy(elements, 0, a, 0, size);
        }
        System.arraycopy(elements, 0, a, 0, size);
        return a;
    }

    private void resize() {
        if (size == elements.length) {
            Object[] newElements = new Object[elements.length * 2];
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
    }
}
