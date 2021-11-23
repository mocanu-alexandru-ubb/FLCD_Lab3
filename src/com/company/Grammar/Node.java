package com.company.Grammar;

public abstract class Node {
    public final String identifier;
    public int ARRAY_IDX;

    public Node(String identifier) {
        this.identifier = identifier;
    }

    public abstract boolean isTerminal();

    public boolean isNonTerminal() {
        return !isTerminal();
    }
}
