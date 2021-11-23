package com.company.Grammar;

public class Terminal extends Node{
    public final static Node epsilon = new Terminal("3");

    public Terminal(String identifier) {
        super(identifier);
    }

    @Override
    public boolean isTerminal() {
        return true;
    }
}
