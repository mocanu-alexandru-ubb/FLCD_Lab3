package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class LexicalScanner {
    private final List<String> reservedKeywords = List.of("console", "let", "const", "array", "if", "else", "stop", "repeat", "int", "boolean", "string");
    private final List<String> separators = List.of("[", "]", "{", "}", "(", ")", "\n", "\t", "\"", " ");
    private final List<String> operators = List.of("+", "-", "*", "/", "=", "==", "!=", "<" , ">", "<=", ">=", ">>>", "<<<");

    private boolean checkIdentifier(String token) {
        return token.matches("^[+-]?[a-zA-Z]*$");
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

    private List<String> customSplit(String line) {
        List<String> result = new ArrayList<>();
        var tokens = line.split(" ");
        boolean foundString = false;
        StringBuilder theFoundString = new StringBuilder();
        for (String token : tokens) {
            if (token.contains("\"")) {
                theFoundString.append(" ").append(token);
                foundString = !foundString;
                if (!foundString) {
                    theFoundString.deleteCharAt(0);
                    result.add(theFoundString.toString());
                    theFoundString = new StringBuilder();
                }
            }
            else {
                if (!foundString) result.add(token);
                else theFoundString.append(" ").append(token);
            }
        }
        return result;
    }

    public void scan(String inputFileName) throws IOException {
        File inputFile = new File(inputFileName + ".in");
        var inputFS = new Scanner(inputFile);
        FileWriter pifWriter = new FileWriter(inputFileName + ".pif.out");
        FileWriter stWriter = new FileWriter(inputFileName + ".st.out");
        SymbolTable st = new SymbolTable();

        while (inputFS.hasNextLine()) {
            var line = inputFS.nextLine();
            if (!line.isEmpty()) {
                var tokens = customSplit(line);
                System.out.println(tokens);
                for (String token : tokens) {
                    if (separators.contains(token) || operators.contains(token) || reservedKeywords.contains(token)) {
                        pifWriter.write("(" + token + ", 0)\n");
                        System.out.println("(" + token + ", 0)");
                    } else {
                        if (checkIdentifier(token) || checkConstant(token)) {
                            if ("-+".contains("" + token.charAt(0))) {
                                pifWriter.write("(" + token.charAt(0) + ", 0)\n");
                                System.out.println("(" + token.charAt(0) + ", 0)");
                                token = token.substring(1);
                            }
                            var node = st.add(token);
                            pifWriter.write("(" + token + ", " + node.referenceNumber + ")\n");
                            System.out.println("(" + token + ", " + node.referenceNumber + ")");
                        } else {
                            pifWriter.write("Lexical Error");
                            System.out.println("Lexical Error");
                            st.writeToFile(stWriter);
                            stWriter.close();
                            pifWriter.close();
                            return;
                        }
                    }
                }
            }

        }
        st.writeToFile(stWriter);
        stWriter.close();
        pifWriter.close();
    }

}
