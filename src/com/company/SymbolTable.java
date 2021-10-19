package com.company;

public class SymbolTable {
    private Node root;

    public Node add(String id) {
        if (root == null) {
            root = new Node(id);
            return root;
        }
        else return addDeeper(root, id);
    }

    private Node addDeeper(Node root, String id) {
        int compareResult = root.id.compareTo(id);
        if (compareResult < 0) {
            if (root.getLeftNode() == null) root.setLeftNode(new Node(id));
            return addDeeper(root.getLeftNode(), id);
        }
        else if (compareResult > 0) {
            if (root.getRightNode() == null) root.setRightNode(new Node(id));
            return addDeeper(root.getRightNode(), id);
        }
        else {
            return root;
        }
    }

}
