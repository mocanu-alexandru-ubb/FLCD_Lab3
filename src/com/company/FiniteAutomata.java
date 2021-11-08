package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FiniteAutomata {
    private List<State> possibleStates;
    private List<String> alphabet;
    private State rootState;

    private void assignStates(String readStates) {
        if (possibleStates != null) throw new IllegalArgumentException("already assigned value");
        var stateIdentifiers = readStates.split(",");
        possibleStates = new ArrayList<>();
        for(String identifier : stateIdentifiers) {
            possibleStates.add(new State(identifier));
        }
    }

    private void assignAlphabet(String readLetters) {
        if (alphabet != null) throw new IllegalArgumentException("already assigned value");
        var letters = readLetters.split(",");
        alphabet = new ArrayList<>();
        alphabet.addAll(Arrays.asList(letters));
    }

    private void assignRootNode(String rootNodeIdentifier) {
        possibleStates.stream()
                .filter(state -> state.identifier.equals(rootNodeIdentifier))
                .findAny()
                .ifPresentOrElse((state) -> rootState = state, () -> {
                    throw new IllegalArgumentException("state does not exist");
                });
    }

    private void assignFinalStates(String readFinalStates) {
        List<String> finalStates = Arrays.asList(readFinalStates.split(","));
        if (possibleStates == null) throw new IllegalArgumentException("access before assignment");
        possibleStates.stream()
                .filter((state) -> finalStates.contains(state.identifier))
                .forEach((state) -> state.isFinalState = true);
    }

    private State getNode(String identifier) {
        return possibleStates.stream()
                .filter(state -> state.identifier.equals(identifier))
                .findAny()
                .orElse(null);
    }

    public void generateFA(String inputFileName) throws FileNotFoundException {
        File inputFile = new File(inputFileName + ".in");
        var inputFS = new Scanner(inputFile);
        boolean isSplittingTransitions = false;
        while (inputFS.hasNextLine()) {
            var line = inputFS.nextLine();
            if (!isSplittingTransitions) {
                var splitLine = line.split("=");
                String attribute = splitLine[0];
                System.out.println(attribute);
                switch (attribute) {
                    case "q", "Q" -> assignStates(splitLine[1]);
                    case "q0", "Q0" -> assignRootNode(splitLine[1]);
                    case "E", "e" -> assignAlphabet(splitLine[1]);
                    case "f", "F" -> assignFinalStates(splitLine[1]);
                    case "s", "S" -> isSplittingTransitions = true;
                    default -> throw new IllegalArgumentException("illegal attribute");
                }
            }
            else {
                var splitLine = line.split(",");
                var transitionLetter = splitLine[1];
                var firstState = possibleStates.stream()
                        .filter(state -> state.identifier.equals(splitLine[0]))
                        .findAny()
                        .orElseThrow();
                var otherState = possibleStates.stream()
                        .filter(state -> state.identifier.equals(splitLine[2]))
                        .findAny()
                        .orElseThrow();
                firstState.neighbors.add(new StateTransition(otherState, transitionLetter));
            }
        }
    }

    @Override
    public String toString() {
        return "FiniteAutomata{" +
                "possibleStates=" + possibleStates +
                ", alphabet=" + alphabet +
                ", rootState=" + rootState.identifier +
                '}';
    }

    public String printStates() {
        StringBuilder builder = new StringBuilder("Q = [");
        possibleStates.forEach(state -> builder.append(state.identifier).append(','));
        builder.append("]");
        return builder.toString();
    }

    public String printFinalStates() {
        StringBuilder builder = new StringBuilder("F = [");
        possibleStates.forEach(state -> {
            if (state.isFinalState)
                builder.append(state.identifier).append(',');
        });
        builder.append("]");
        return builder.toString();
    }

    public String printTransitions() {
        StringBuilder builder = new StringBuilder();
        possibleStates.forEach(state -> state.neighbors.forEach(neighbor -> builder.append(state.identifier)
                .append(" -- ")
                .append(neighbor.transitionLetter)
                .append(" -> ")
                .append(neighbor.transferState.identifier)
                .append("\n")));
        return builder.toString();
    }

    private boolean tryGenerateSequence(List<String> sequenceToGenerate, int currentPos, State currentState) {
        if (currentPos == sequenceToGenerate.size()) return currentState.isFinalState;

        for(StateTransition transition : currentState.neighbors) {
            if (sequenceToGenerate.get(currentPos).equals(transition.transitionLetter)) {
                if (tryGenerateSequence(sequenceToGenerate, currentPos + 1, transition.transferState))
                    return true;
            }
        }
        return false;
    }

    public boolean test(String sequence) {
        var charSequence = List.of(sequence.split(""));
        return tryGenerateSequence(charSequence, 0, rootState);
    }
}
