package com.huazai.bst;

import com.huazai.avl.AVLIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 迭递归方式的中序遍历输出所有节点
 *
 * 非递归方式的中序遍历参考{@link AVLIterator}
 *
 * @param <T>
 */
public class BSTIterator<T> implements Iterator<TreeNode<T>> {
    // 借助有序集合list输出，加入list元素的顺序就是中序递归遍历元素的顺序
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
