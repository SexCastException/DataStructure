package com.huazai.avl;

import com.huazai.bst.BSTIterator;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Stack;

/**
 * LeetCode题目：通过栈实现非递归的中序遍历，并要求时间复杂度为log(n)
 * 思路：
 *      1、从根节点开始，从上到下把树的最左路径把加入栈中
 *      2、在弹出栈顶元素的时刻，如果该元素的右子树不为空，则把右子树的最左路径压入栈中，反复循环1、2步骤直至栈为空
 *
 * 递归方式的中序遍历时间复杂度为O(n)，不符合题目的要求。递归方式的中序遍历参考${@link BSTIterator}
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

    @Override
    public void remove() {
        throw new ConcurrentModificationException("Can not remove!");
    }

}
