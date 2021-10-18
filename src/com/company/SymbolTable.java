package com.company;

import java.util.Objects;

public class SymbolTable {
    private Node root;

    public void add(String id) {
        if (root == null) root = new Node(id);
        else addDeeper(root, id);
    }

    private void addDeeper(Node root, String id) {
        int compareResult = root.id.compareTo(id);
        if (compareResult < 0) {
            if (root.getLeftNode() == null) root.setLeftNode(new Node(id));
            else addDeeper(root.getLeftNode(), id);
        }
        else if (compareResult > 0) {
            if (root.getRightNode() == null) root.setRightNode(new Node(id));
            else addDeeper(root.getRightNode(), id);
        }
        else {
            System.out.println("Syntactic error!");
        }
    }

    public Node get(String id) {
        if (root == null) {
            System.out.println("Syntactic error!");
        }
        return getDeeper(root, id);
    }

    private Node getDeeper(Node root, String id) {
        if (Objects.equals(root.id, id)) return root;
        if (root.id.compareTo(id) < 0) return getDeeper(root.getLeftNode(), id);
        else return getDeeper(root.getRightNode(), id);
    }

}
