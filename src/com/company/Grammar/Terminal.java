package com.company.Grammar;

public class Terminal extends Node{
    public Terminal(String identifier) {
        super(identifier);
    }

    @Override
    public boolean isTerminal() {
        return true;
    }
}
