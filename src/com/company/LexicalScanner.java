package com.company;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class LexicalScanner {
    private final List<String> reservedKeywords = List.of("console", "let", "const", "array", "if", "else", "stop", "repeat", "int", "boolean", "string", ">>>", "<<<");
    private final List<String> separators = List.of("[", "]", "{", "}", "(", ")", "\n", "\t", " ");
    private final List<String> operators = List.of("+", "-", "*", "/", "=", "==", "!=", "<" , ">", "<=", ">=");

    private boolean checkIdentifier(String token) {
        return token.matches("^[a-zA-Z]*$");
    }

    private boolean checkIntConstant(String token) {
        return token.matches("^([+-]?[1-9][0-9]*)|[+-]?0$");
    }

    private boolean checkBooleanConstant(String token) {
        return token.matches("^true|false$");
    }

    private boolean checkStringConstant(String token) {
        return token.matches("^\"[a-zA-Z0-9 ]*\"$");
    }

    private boolean checkConstant(String token) {
        return checkIntConstant(token) || checkBooleanConstant(token) || checkStringConstant(token);
    }

    public void scan(String inputFileName) throws IOException {
        File inputFile = new File(inputFileName + ".in");
        var inputFS = new Scanner(inputFile);
        FileWriter pifWriter = new FileWriter(inputFileName + ".pif.out");
        FileWriter stWriter = new FileWriter(inputFileName + ".st.out");
        SymbolTable st = new SymbolTable();

        while (inputFS.hasNextLine()) {
            var line = inputFS.nextLine();
            var tokens = line.split(" ");
            for (String token : tokens) {
                if (separators.contains(token) || operators.contains(token) || reservedKeywords.contains(token)) {
                    pifWriter.write("(" + token + ", 0)\n");
                    System.out.println("(" + token + ", 0)");
                }
                else {
                    if (checkIdentifier(token) || checkConstant(token)) {
                        var node = st.add(token);
                        pifWriter.write("(" + token + ", " + node.referenceNumber + ")\n");
                        System.out.println("(" + token + ", " + node.referenceNumber + ")");
                    }
                    else {
                        System.out.println("Lexical Error");
                        return;
                    }
                }
            }

            //line.split("/\\[|]|\\{|}|\\(|\\)|\n|\t|\s/");

        }
    }

}
