package com.example.drawerappugr;

import java.util.ArrayList;

public class Horarios {
    public static ArrayList<StructHorarios> h;

    public Horarios(){
        if(h.isEmpty()){
            h.add(new StructHorarios("","","",""));
            h.add(new StructHorarios("","","",""));
            h.add(new StructHorarios("","","",""));
            h.add(new StructHorarios("","","",""));
            h.add(new StructHorarios("","","",""));

        }
    }

    public StructHorarios get(int i){
        return h.get(i);
    }
}
