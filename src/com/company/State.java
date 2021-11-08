package com.company;


import java.util.ArrayList;
import java.util.List;

class StateTransition {
    public final State transferState;
    public final String transitionLetter;

    StateTransition(State transferState, String transitionLetter) {
        this.transferState = transferState;
        this.transitionLetter = transitionLetter;
    }

    @Override
    public String toString() {
        return "StateTransition{" +
                "-> " + transferState.identifier +
                " ='" + transitionLetter + '\'' +
                '}';
    }
}

public class State {
    public final String identifier;
    public boolean isFinalState;
    public final List<StateTransition> neighbors = new ArrayList<>();

    public State(String identifier) {
        this.identifier = identifier;
        this.isFinalState = false;
    }

    public State(String identifier, boolean isFinalState) {
        this.identifier = identifier;
        this.isFinalState = isFinalState;
    }

    @Override
    public String toString() {
        return "State{" +
                "identifier='" + identifier + '\'' +
                ", isFinalState=" + isFinalState +
                ", neighbors=" + neighbors +
                '}';
    }
}
