package com.huazai.bst;


import org.junit.Test;

import java.util.LinkedList;

public class BSTTraversingTest {
    private static TreeNode<Integer> root = new TreeNode<>(4);

    private BSTTraversing<Integer> bstTraversing = new BSTTraversing<>();

    /**
     * 树结构：[4,2,7,1,3,6,9]
     *          4
     *       2     7
     *     1  3  6  9
     *
     * 交换左右子树后：[4,7,2,9,6,3,1]
     *          4
     *       7     2
     *     9  6  3  1
     */
    static {
        root.setLeft(new TreeNode<>(2));
        root.setRight(new TreeNode<>(7));

        root.getLeft().setLeft(new TreeNode(1));
        root.getLeft().setRight(new TreeNode(3));

        root.getRight().setLeft(new TreeNode(6));
        root.getRight().setRight(new TreeNode(9));
    }

    /**
     * 测试先序遍历交换左右子树元素
     */
    @org.junit.Test
    public void testFirstInvertTree() {
        bstTraversing.firstInvertTree(root);
        System.out.println(root);
    }

    /**
     * 测试中序遍历交换左右子树元素
     */
    @Test
    public void testMidInvertTree() {
        bstTraversing.midInvertTree(root);
        System.out.println(root);
    }

    /**
     * 测试后序遍历交换左右子树元素
     */
    @Test
    public void testLastInvertTree() {
        bstTraversing.lastInvertTree(root);
        System.out.println(root);
    }

    /**
     * 测试层序非递归遍历交换左右子树元素
     */
    @Test
    public void testLevelNotRecursiveInvertTree() {
        bstTraversing.levelNotRecursiveInvertTree(root);
        System.out.println(root);
    }

    /**
     * 测试层序递归遍历交换左右子树元素
     */
    @Test
    public void testLevelRecursiveInvertTree() {
        bstTraversing.levelRecursiveInvertTree(root);
        System.out.println(root);
    }
}
