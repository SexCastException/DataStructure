package com.huazai.bst;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class BSTTraversing<T> {
    /**
     * 递归先序遍历交换左右子树元素
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
     * 递归中序遍历左右子树元素
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
     * 递归后序遍历交换左右子树元素
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
     * 递归层序递归遍历交换左右子树元素
     *
     * @param root
     * @return
     */

    private static Queue<TreeNode> queue = new LinkedList<>();
    public TreeNode<T> levelRecursiveInvertTree(TreeNode<T> root) {
        if (queue == null) {
            throw new RuntimeException();
        }

        if (root != null) {
            queue.offer(root);
            // 取出队头元素
            TreeNode<T> p = queue.poll();

            // 交换左右子树
            TreeNode<T> t = p.getLeft();
            p.setLeft(p.getRight());
            p.setRight(t);

            if (p.getLeft() != null) {
                levelRecursiveInvertTree(p.getLeft());
            }
            if (p.getRight() != null) {
                levelRecursiveInvertTree(p.getRight());
            }
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
        if (root == null) {
            return null;
        }
        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            // 取出队头元素
            TreeNode<T> p = queue.poll();

            // 交换左右子树
            TreeNode<T> t = p.getLeft();
            p.setLeft(p.getRight());
            p.setRight(t);

            // 左子树加入队列
            if (p.getLeft() != null) {
                queue.offer(p.getLeft());
            }
            // 右子树加入队列
            if (p.getRight() != null) {
                queue.offer(p.getRight());
            }
        }
        return root;
    }
}
