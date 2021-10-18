package com.company;

public class Node {
    private Node leftNode;
    private Node rightNode;
    public final String id;

    Node(String id) {
        this.id = id;
    }

    public Node getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }

    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }
}
