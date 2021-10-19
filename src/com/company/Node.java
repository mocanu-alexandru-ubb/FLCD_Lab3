package com.company;

public class Node {
    private Node leftNode;
    private Node rightNode;
    public final String id;

    private static int counter = 0;
    public final int referenceNumber;

    Node(String id) {
        this.id = id;
        counter++;
        referenceNumber = counter;
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
