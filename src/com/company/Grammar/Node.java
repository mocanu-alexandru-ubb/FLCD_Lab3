package com.company.Grammar;

public abstract class Node {
    public final String identifier;

    public Node(String identifier) {
        this.identifier = identifier;
    }

    public abstract boolean isTerminal();
}
