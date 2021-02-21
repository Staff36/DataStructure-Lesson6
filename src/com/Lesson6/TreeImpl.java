package com.Lesson6;

import java.util.Stack;

public class TreeImpl<E extends Comparable<? super E>> implements Tree<E>{

  private int size;

    public Node<E> getRoot() {
        return root;
    }

    private Node<E> root;
    private int counter;

    public int getMAX_DEEP() {
        return MAX_DEEP;
    }

    private final int MAX_DEEP;

    public TreeImpl(int MAX_DEEP) {
        this.MAX_DEEP = MAX_DEEP;
    }

    @Override
    public void add(E value) {
        Node<E> newNode= new Node<>(value);
        if (isEmpty()){
            root=newNode;
            size++;
            return;
        }
        NodeAndParent nodeAndParent= doFind(value);
        if (nodeAndParent.current!=null||counter>=MAX_DEEP){
            return;
        }
        Node<E> previous=nodeAndParent.parent;
        if (previous.isLeftChild(value)){
            previous.setLeftChild(newNode);
        }else {
            previous.setRightChild(newNode);
        }
    size++;
    }

    @Override
    public boolean contains(E value) {
    NodeAndParent nodeAndParent=doFind(value);
    return  nodeAndParent.current!=null;
    }


    private NodeAndParent doFind(E value){
        counter=0;
        Node<E> current=root;
        Node<E> previous=null;
        while (current!=null){
            counter++;
            if(current.getValue().equals(value)){
                return new NodeAndParent(current, previous);
            }
            previous=current;
            if (current.isLeftChild(value)){
                current=current.getLeftChild();
            } else {
                current=current.getRightChild();
            }
        }
        return new NodeAndParent(null,previous);
    }


    @Override
    public boolean remove(E value) {
        NodeAndParent nodeAndParent=doFind(value);
        Node<E> removingNode = nodeAndParent.current;
        Node<E> parentNode=nodeAndParent.parent;

        if(removingNode==null){
            return false;
        }

        //Нет потомков
        if (removingNode.isLeaf()){
            if(removingNode==root){
                root=null;
                size--;
            }else if (parentNode.isLeftChild(value)){
                parentNode.setLeftChild(null);
            }else {
                parentNode.setRightChild(null);
            }

        //Есть 1 потомок
        } else if(removingNode.hasOnlyOneChild()){
            Node<E> childNode= removingNode.getLeftChild()!=null
                    ? removingNode.getLeftChild()
                    : removingNode.getRightChild();
            if (removingNode==root){
                root=childNode;
            }else if(parentNode.isLeftChild(value)){
                parentNode.setLeftChild(childNode);
            }else {
                parentNode.setRightChild(childNode);
            }

        //Есть 2 потомка
        } else {
            Node<E> successor=getSuccessor(removingNode);
            if (removingNode==root){
                root=successor;
            } else if (parentNode.isLeftChild(value)){
                parentNode.setLeftChild(successor);
            }else {
                parentNode.setRightChild(successor);
            }
            successor.setLeftChild(removingNode.getLeftChild());
        }
    size--;
        return true;
    }

    private Node<E> getSuccessor(Node<E> removingNode) {
        Node<E> successor=removingNode;
        Node<E>  successorParent = null;
        Node<E> current= removingNode.getRightChild();

        while (current!=null){
            successorParent=successor;
            successor=current;
            current=current.getLeftChild();
        }
        if (successor!=removingNode.getRightChild()&&successorParent!=null){
            successorParent.setLeftChild(successor.getRightChild());
            successor.setRightChild(removingNode.getRightChild());
        }
        return successor;
    }
    @Override
    public boolean isEmpty() {
        return size==0;
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public void display() {
        Stack<Node<E>> globalStack = new Stack<>();
        globalStack.push(root);
        int nBlanks = 64;

        boolean isRowEmpty = false;
        System.out.println("................................................................");

        while (!isRowEmpty) {
            Stack<Node<E>> localStack = new Stack<>();

            isRowEmpty = true;
            for (int i = 0; i < nBlanks; i++) {
                System.out.print(" ");
            }

            while (!globalStack.isEmpty()) {
                Node<E> tempNode = globalStack.pop();
                if (tempNode != null) {
                    System.out.print(tempNode.getValue());
                    localStack.push(tempNode.getLeftChild());
                    localStack.push(tempNode.getRightChild());
                    if (tempNode.getLeftChild() != null || tempNode.getRightChild() != null) {
                        isRowEmpty = false;
                    }
                } else {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < nBlanks * 2 - 2; j++) {
                    System.out.print(" ");
                }
            }

            System.out.println();

            while (!localStack.isEmpty()) {
                globalStack.push(localStack.pop());
            }

            nBlanks /= 2;
        }
        System.out.println("................................................................");
    }

    @Override
    public void traverse(TraverseMode mode) {
        switch (mode){
            case PRE_ORDER -> preOrder(root);
            case POST_ORDER -> postOrder(root);
            case IN_ORDER -> inOrder(root);
            default -> throw new IllegalArgumentException("Unknown traverse mode: "+mode);
        }
    }

    private void inOrder(Node<E> current){
        if (current==null)
            return;
        inOrder(current.getLeftChild());
        System.out.println(current.getRightChild());
        inOrder(current.getRightChild());
    }

    private void preOrder(Node<E> current){
        if (current==null)
            return;
        System.out.println(current.getRightChild());
        preOrder(current.getLeftChild());
        preOrder(current.getRightChild());
    }

    private void postOrder(Node<E> current){
        if (current==null)
            return;
        postOrder(current.getLeftChild());
        postOrder(current.getRightChild());
        System.out.println(current.getRightChild());
    }

    public boolean checkTree(){
        return isBalanced(root);
    }
    public boolean isBalanced(Node<E> node) {
        return (node == null) || isBalanced(node.getLeftChild()) && isBalanced(node.getRightChild()) && Math.abs(height(node.getLeftChild()) - height(node.getRightChild())) < 2;
    }
    private int height(Node<E> node) {
        return node == null ? 0 : 1 + Math.max(height(node.getLeftChild()), height(node.getRightChild()));
    }

    private class NodeAndParent{
        Node<E> current;

        Node<E> parent;
        public NodeAndParent(Node<E> current, Node<E> parent) {
            this.current = current;
            this.parent = parent;
        }
    }

}
