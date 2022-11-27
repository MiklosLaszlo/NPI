package com.example.drawerappugr;

import java.util.ArrayList;

public class Comedores {
    public static ArrayList<String> c1;
    public static ArrayList<String> c2;

     public Comedores(){
        if(c1.isEmpty()){
            c1.add("Menú 1:");
            c1.add("");
            c1.add("");
            c1.add("");
            c1.add("");

            c2.add("Menú 2:");
            c2.add("");
            c2.add("");
            c2.add("");
            c2.add("");
        }
    }

    public String get(int i, int j){
        return c1.get(i);
    }
}
