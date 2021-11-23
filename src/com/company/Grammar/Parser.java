package com.company.Grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class Parser {
    private final List<Terminal> terminals = new ArrayList<>();
    private final List<NonTerminal> nonTerminals = new ArrayList<>();
    private List<List<Terminal>> FIRST = new ArrayList<>();

    public void computeFIRST() {
        int arrayIndex = 0;
        for (NonTerminal nonTerminal : nonTerminals) {
            nonTerminal.ARRAY_IDX = arrayIndex++;
            final ArrayList<Terminal> f0 = new ArrayList<>();
            FIRST.add(f0);
            nonTerminal.productions
                .stream()
                .map((prod) -> prod.result.get(0))
                .filter(Node::isTerminal)
                .forEach((terminal) -> f0.add((Terminal) terminal));
        }
        while (computeNextF()) {}
    }

    private boolean computeNextF() {
        boolean changed = false;
        List<List<Terminal>> nextFIRST = new ArrayList<>();
        for (NonTerminal nonTerminal : nonTerminals) {
            final ArrayList<Terminal> nextF = new ArrayList<>(FIRST.get(nonTerminal.ARRAY_IDX));
            for (int i = 0; i < nonTerminal.productions.size(); i++) {
                if (conjunctionOfOne(nextF, nonTerminal.productions.get(i), 0)) {
                    changed = true;
                }
            }
            nextFIRST.add(nextF);
        }
        FIRST = nextFIRST;
        return changed;
    }

    private boolean conjunctionOfOne(List<Terminal> nextF, Production production, int index) {
        boolean firstChanged = false;
        if (production.result.get(index).isTerminal()) {
            if (!nextF.contains((Terminal) production.result.get(index))) {
                nextF.add((Terminal) production.result.get(index));
                firstChanged = true;
            }
        }
        else {
            NonTerminal resultingNonTerminal = (NonTerminal) production.result.get(index);
            if (FIRST.get(resultingNonTerminal.ARRAY_IDX).contains(Terminal.epsilon)) {
                firstChanged = conjunctionOfOne(nextF, production, index + 1);
            }
            else {
                for (Terminal terminalOfPreviousF : FIRST.get(resultingNonTerminal.ARRAY_IDX)) {
                    if (!nextF.contains(terminalOfPreviousF)) {
                        nextF.add(terminalOfPreviousF);
                        firstChanged = true;
                    }
                }
            }
        }

        return firstChanged;
    }

    private NonTerminal getNonTerminal(String identifier) {
        Optional<NonTerminal> result = nonTerminals.stream()
                .filter(n -> n.identifier.equals(identifier))
                .findAny();
        return result.get();
    }

    private void assignTerminals(String stringOfTerminals) {
        String[] splitString = stringOfTerminals.split(",");
        for(String identifier : splitString) {
            terminals.add(new Terminal(identifier));
        }
    }

    private void assignNonTerminals(String stringOfNonTerminals) {
        String[] splitString = stringOfNonTerminals.split(",");
        for(String identifier : splitString) {
            nonTerminals.add(new NonTerminal(identifier));
        }
    }

    private boolean checkIfTerminal(String identifier) {
        return terminals.stream()
                .anyMatch(n -> n.identifier.equals(identifier));
    }

    private void assignProductions(String productionString) {
        String identifier = productionString.split("=>")[0];
        NonTerminal rootNode = getNonTerminal(identifier);

        String[] productionResultList = productionString.split("=>")[1].split("\\|");

        for(String productionResult : productionResultList) {
            Production createdProduction = new Production();
            for (String outIdentifier : productionResult.split(" ")) {
                if (checkIfTerminal(outIdentifier)) createdProduction.result.add(new Terminal(outIdentifier));
                else createdProduction.result.add(new NonTerminal(outIdentifier));
            }
            rootNode.productions.add(createdProduction);
        }
    }



}
