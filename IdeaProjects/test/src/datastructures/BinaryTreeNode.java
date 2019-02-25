package datastructures;

public class BinaryTreeNode<T> {
    private BinaryTreeNode left;
    private BinaryTreeNode right;
    private T val;

    public BinaryTreeNode(T val, BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public BinaryTreeNode getLeft() {
        return left;
    }

    public BinaryTreeNode getRight() {
        return right;
    }

    public T getVal() {
        return val;
    }
}
