package com.company;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

public class Main {
    private final static Map<String, Consumer<String>> cmds = new HashMap<>();
    FiniteAutomata fa = new FiniteAutomata();
    Scanner input = new Scanner(System.in);

    {
        cmds.put("states", this::printStates);
        cmds.put("final", this::printFinalStates);
        cmds.put("transitions", this::printTransitions);
        cmds.put("test", this::testAcceptance);
        cmds.put("help", this::help);
        cmds.put("quit", (ignored) -> {
            throw new RuntimeException("Finished program");
        });
    }

    private void help(String ignored) {
        System.out.println(cmds.keySet());
    }

    private void printStates (String ignored) {
        System.out.println(fa.printStates());
    }

    private void printTransitions (String ignored) {
        System.out.println(fa.printTransitions());
    }

    private void printFinalStates (String ignored) {
        System.out.println(fa.printFinalStates());

    }

    private void testAcceptance (String ignored) {
        String sequence = input.nextLine();
        System.out.println(fa.test(sequence));
    }

    public static void main(String[] args) throws IOException {
        new Main().solve();
    }

    private void solve() throws FileNotFoundException {
        fa.generateFA("FA");
        System.out.println(fa.toString());
        help(null);
        while(true) {
            var cmd = input.nextLine();
            if (cmds.containsKey(cmd))
                cmds.get(cmd).accept(null);
            else
                System.out.println("wrong!");
        }
    }
}
