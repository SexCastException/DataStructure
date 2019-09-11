package com.huazai.avl;

import java.util.Iterator;
import java.util.Stack;

/**
 * 该迭代器了使用了栈实现非迭代的中序遍历，时间复杂度为log(n)，
 * 迭代方式的中序遍历时间复杂度为O(n)，迭代方式的中序遍历参考BSTIterator
 *
 * @param <K>
 * @param <V>
 */
public class AVLIterator<K, V> implements Iterator<AVLEntry<K, V>> {

    private Stack<AVLEntry<K, V>> stack;

    public AVLIterator(AVLEntry<K, V> root) {
        stack = new Stack<>();
        addLeftPath(root);
    }

    /**
     * 把p节点的所有左子树压入栈中
     *
     * @param p
     */
    private void addLeftPath(AVLEntry<K, V> p) {
        while (p != null) {
            stack.push(p);
            p = p.getLeft();
        }
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public AVLEntry<K, V> next() {
        AVLEntry<K, V> p = stack.pop();
        addLeftPath(p.getRight());
        return p;
    }

}
