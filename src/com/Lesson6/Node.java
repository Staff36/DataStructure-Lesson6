package com.Lesson6;

public class Node<T extends Comparable<? super T>>  {
    public Node(T value) {
        this.value = value;
    }

    private final T value;

    private Node<T> leftChild;

    public T getValue() {
        return value;
    }

    public Node<T> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node<T> leftChild) {
        this.leftChild = leftChild;
    }

    public Node<T> getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node<T> rightChild) {
        this.rightChild = rightChild;
    }
    public boolean isLeftChild(T value){
        return value.compareTo(this.value)<0;
    }
    private Node<T> rightChild;

    public boolean isLeaf() {
       return leftChild==null && rightChild==null;
    }

    public boolean hasOnlyOneChild() {
        return leftChild==null^rightChild==null;
    }
}
