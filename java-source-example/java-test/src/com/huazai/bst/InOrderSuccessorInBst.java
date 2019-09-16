package com.huazai.bst;

import com.huazai.avl.AVLMap;

public class InOrderSuccessorInBst {

    /**
     * LeetCode题目：寻找后继节点，要求p没有父节点或者不使用父节点的情况来查找
     * 通过父节点查找后继节点可以参考{@link AVLMap} 的successor方法
     *
     * 遍历查找节点t的后继节点
     * 情况0：t是最大节点，没有后继节点
     * 情况1：t有右孩子，查找右孩子的firstEntry（即以右孩子为根节点的最小节点，和有parent域的情况一样）
     * 情况2：t没有右孩子，查找孩子是左孩子的父节点p（即比t大的第一个节点），【可以尝试使用栈把从根节点到查找p元素的路径压入栈中】
     *
     * @param root
     * @param p
     * @return
     */
    public TreeNode inOrderSuccessor(TreeNode<Integer> root, TreeNode<Integer> p) {
        if (p == null) {
            return null;
        }
        // 情况0
        if (getLastEntry(root) == p) {
            return null;
        }
        // 情况1
        if (p.getRight() != null) {
            return getFirstEntry(p.getRight());
        }
        // 情况2
        // 自顶向下使用临时变量temp记录左孩子是p的元素（即p的后继节点）
        TreeNode<Integer> temp = root;
        TreeNode<Integer> parent = root;
        while (parent != null) {
            if (parent == p) {
                break;
            } else if (p.getValue() < parent.getValue()) {
                temp = parent;
                parent = parent.getLeft();
            } else {
                parent = parent.getRight();
            }
        }
        return temp;
    }

    /**
     * 获取以p为根节点的二叉树的最小节点
     *
     * @param p
     * @return
     */
    public TreeNode getFirstEntry(TreeNode p) {
        if (p == null) {
            return null;
        }
        while (p != null) {
            if (p.getLeft() != null) {
                p = p.getLeft();
            }
        }
        return p;
    }

    /**
     * 获取以p为根节点的二叉树的最大节点
     *
     * @param p
     * @return
     */
    public TreeNode getLastEntry(TreeNode p) {
        if (p == null) {
            return null;
        }
        while (p != null) {
            if (p.getRight() != null) {
                p = p.getRight();
            }
        }
        return p;
    }
}
