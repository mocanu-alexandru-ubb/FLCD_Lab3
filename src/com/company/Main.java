package com.company;



public class Main {

    public static void main(String[] args) {
	// write your code here
        SymbolTable st = new SymbolTable();
        st.add("one");
        st.add("two");
        st.add("three");
        System.out.println(st.get("one"));
        System.out.println(st.get("two"));
        System.out.println(st.get("three"));
    }
}
