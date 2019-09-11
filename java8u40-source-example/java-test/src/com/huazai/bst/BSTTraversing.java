package com.huazai.bst;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class BSTTraversing<T> {
    /**
     * 先序遍历交换左右子树元素
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
     * 中序遍历左右子树元素
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
     * 后序遍历交换左右子树元素
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

    /**
     * 层序递归遍历交换左右子树元素
     *
     * @param root
     * @return
     */

    public TreeNode<T> levelRecursiveInvertTree(TreeNode<T> root, Queue<TreeNode> queue) {
        if (queue == null) {
            throw new RuntimeException();
        }
        if (root != null) {
            if (root.getLeft() != null) {
                queue.offer(root.getLeft());
            }
            if (root.getRight() != null) {
                queue.offer(root.getRight());
            }

            TreeNode p = queue.poll();
            TreeNode<T> t = p.getLeft();
            p.setLeft(p.getRight());
            p.setRight(t);
            levelRecursiveInvertTree(p.getLeft(), queue);
            levelRecursiveInvertTree(p.getRight(), queue);
            return root;
        }
        return null;
    }

    /**
     * 借助队列层序非递归遍历交换左右子树元素
     *
     * @param root
     * @return
     */
    public TreeNode<T> levelNotRecursiveInvertTree(TreeNode<T> root) {
        if (root != null) {

            return root;
        }
        return null;
    }
}
