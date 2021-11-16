package com.company.Grammar;

import java.util.ArrayList;
import java.util.List;

public class NonTerminal extends Node{
    public final List<Production> productions = new ArrayList<>();

    public NonTerminal(String identifier) {
        super(identifier);
    }

    @Override
    public boolean isTerminal() {
        return false;
    }
}
