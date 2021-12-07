package com.company.Grammar;

import java.util.ArrayList;
import java.util.List;

public class Production {
    public final int INDEX;
    private static int indexCounter = 0;
    public final List<Node> result = new ArrayList<>();

    public Production() {
        this.INDEX = indexCounter++;
    }
}
