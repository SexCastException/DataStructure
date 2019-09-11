package com.huazai.bst;

import java.util.Objects;

public class BSTTraversing<T> {
    /**
     *
     *
     * @param root
     * @return
     */
    public TreeNode<T> firstInvertTree(TreeNode<T> root) {
        if (!Objects.isNull(root)) {
            TreeNode<T> t = root.getLeft();
            root.setLeft(root.getRight());
            root.setRight(t);
            firstInvertTree(root.getLeft());
            firstInvertTree(root.getRight());
            return root;
        }
        return null;
    }

    /**
     *
     *
     * @param root
     * @return
     */
    public TreeNode<T> midInvertTree(TreeNode<T> root) {
        if (!Objects.isNull(root)) {
            midInvertTree(root.getLeft());
            TreeNode<T> t = root.getLeft();
            root.setLeft(root.getRight());
            root.setRight(t);
            midInvertTree(root.getLeft());
            return root;
        }
        return null;
    }

    /**
     *
     * @param root
     * @return
     */
    public TreeNode<T> lastInvertTree(TreeNode<T> root) {
        if (!Objects.isNull(root)) {
            lastInvertTree(root.getLeft());
            lastInvertTree(root.getRight());
            TreeNode<T> t = root.getLeft();
            root.setLeft(root.getRight());
            root.setRight(t);
            return root;
        }
        return null;
    }
}
