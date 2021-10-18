package com.company;



public class Main {

    public static void main(String[] args) {
	// write your code here
        SymbolTable st = new SymbolTable();
        st.add("one");
        st.add("two");
        st.add("three");
        System.out.println(st.get("one").testVariable);
        System.out.println(st.get("two").testVariable);
        System.out.println(st.get("three").testVariable);
        System.out.println(st.get("four").testVariable);
    }
}
