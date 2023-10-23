package ru.asvistunov.rtkit.internship.collections;

import java.util.*;

/**
 * Класс `MyHashMap` реализует интерфейс `Map` и представляет собой собственную реализацию хеш-таблицы.
 */
public class MyHashMap<K, V> implements Map<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Node<K, V>[] table;
    private int size;

    /**
     * Конструктор без параметров, инициализирует хеш-таблицу с размером по умолчанию.
     */
    public MyHashMap() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Конструктор с указанием начальной емкости хеш-таблицы.
     *
     * @param capacity Начальная ёмкость хеш-таблицы.
     */
    public MyHashMap(int capacity) {
        this.table = new Node[capacity];
        this.size = 0;
    }

    private static class Node<K, V> implements Entry {
        private final K key;
        private V value;
        private Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(Object value) {
            this.value = (V) value;
            return (V) value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }

    private int hash(Object key) {
        return hash(table.length, key);
    }

    private int hash(int tableCapacity, Object key) {
        return key == null ? 0 : key.hashCode() & (tableCapacity - 1);
    }

    private void resizeTable() {
        int newCapacity = table.length * 2;
        Node<K, V>[] newTable = new Node[newCapacity];

        for (Node<K, V> node : table) {
            while (node != null) {
                int index = hash(newCapacity, node.key);
                Node<K, V> nextNode = node.next;
                node.next = newTable[index];
                newTable[index] = node;
                node = nextNode;
            }
        }
        table = newTable;
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
    public boolean containsKey(Object key) {
        int index = hash(key);
        Node<K, V> node = table[index];
        while (node != null) {
            if (Objects.equals(node.key, key)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (Node<K, V> node : table) {
            while (node != null) {
                if (Objects.equals(node.value, value)) {
                    return true;
                }
                node = node.next;
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        int index = hash(key);
        Node<K, V> node = table[index];
        while (node != null) {
            if (Objects.equals(node.key, key)) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        if (size > table.length * LOAD_FACTOR) {
            resizeTable();
        }

        int index = hash(key);
        Node<K, V> node = table[index];
        while (node != null) {
            if (Objects.equals(node.key, key)) {
                V oldValue = node.value;
                node.value = value;

                return oldValue;
            }
            node = node.next;
        }

        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = table[index];
        table[index] = newNode;
        size++;

        return null;
    }


    @Override
    public V remove(Object key) {
        int index = hash(key);
        Node<K, V> currentNode = table[index];
        Node<K, V> prevNode = null;

        while (currentNode != null) {
            if (Objects.equals(currentNode.key, key)) {
                if (prevNode == null) {
                    table[index] = currentNode.next;
                } else {
                    prevNode.next = currentNode.next;
                }
                size--;
                return currentNode.value;
            }
            prevNode = currentNode;
            currentNode = currentNode.next;
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        table = new Node[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        for (Node<K, V> node : table) {
            while (node != null) {
                keys.add(node.key);
                node = node.next;
            }
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        List<V> values = new MyArrayList<>();
        for (Node<K, V> node : table) {
            while (node != null) {
                values.add(node.value);
                node = node.next;
            }
        }
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> nodes = new HashSet<>();
        for (Node<K, V> node : table) {
            while (node != null) {
                nodes.add(node);
                node = node.next;
            }
        }
        return nodes;
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        V value = get(key);
        if (value != null || containsKey(key)) {
            return value;
        }
        return defaultValue;
    }
}
