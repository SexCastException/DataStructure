package com.huazai.avl;

import java.util.Map;

public class AVLEntry<K, V> implements Map.Entry<K, V> {
    private K key;
    private V value;
    AVLEntry<K, V> left;
    AVLEntry<K, V> right;
    AVLEntry<K, V> parent;

    public AVLEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public AVLEntry(K key, V value, AVLEntry<K, V> left, AVLEntry<K, V> right, AVLEntry<K, V> parent) {
        this.key = key;
        this.value = value;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    public AVLEntry(K key, V value, AVLEntry<K, V> parent) {
        this.key = key;
        this.value = value;
        this.parent = parent;
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
    public V setValue(V value) {
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public AVLEntry<K, V> getLeft() {
        return left;
    }

    public void setLeft(AVLEntry<K, V> left) {
        this.left = left;
    }

    public AVLEntry<K, V> getRight() {
        return right;
    }

    public void setRight(AVLEntry<K, V> right) {
        this.right = right;
    }

    public AVLEntry<K, V> getParent() {
        return parent;
    }

    public void setParent(AVLEntry<K, V> parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "AVLEntry{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }


}
