package com.huazai.bst;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BSTIterator<T> implements Iterator<TreeNode<T>> {
    private List<TreeNode<T>> list;
    private Iterator<TreeNode<T>> iterator;

    public BSTIterator(TreeNode root) {
        list = new ArrayList<>();
        inOrder(root, list);
        iterator = list.iterator();
    }

    /**
     * 迭代方式的中序遍历
     *
     * @param root
     * @param list
     */
    private void inOrder(TreeNode<T> root, List<TreeNode<T>> list) {
        while (root != null) {
            inOrder(root.getLeft(), list);
            list.add(root);
            inOrder(root.getRight(), list);
        }
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public TreeNode next() {
        return iterator.next();
    }
}
