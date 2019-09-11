package com.huazai.bst;

public class TreeNode<T> {
    private TreeNode left;
    private TreeNode right;
    private T value;

    public TreeNode() {
        super();
    }

    public TreeNode(T value) {
        this.value = value;
    }

    public TreeNode(TreeNode left, TreeNode right, T value) {
        this.left = left;
        this.right = right;
        this.value = value;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
