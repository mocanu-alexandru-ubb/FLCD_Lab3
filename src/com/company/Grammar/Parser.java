package com.company.Grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Parser {
    private final List<Terminal> terminals = new ArrayList<>();
    private final List<NonTerminal> nonTerminals = new ArrayList<>();

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
