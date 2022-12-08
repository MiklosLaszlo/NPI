package com.example.drawerappugr;

import java.util.ArrayList;

public class Horarios {
    public  ArrayList<ArrayList<StructHorarios>> h=new ArrayList<>();

    public Horarios(){
        ArrayList<StructHorarios> Lunes =new ArrayList<StructHorarios>();
        ArrayList<StructHorarios> Martes =new ArrayList<>();
        ArrayList<StructHorarios> Miercoles =new ArrayList<>();
        ArrayList<StructHorarios> Jueves =new ArrayList<>();
        ArrayList<StructHorarios> Viernes =new ArrayList<>();

        Lunes.add(new StructHorarios("9:30","10:30","1.1","SS"));
        Lunes.add(new StructHorarios("10:30","11:30","1.1","SS"));
        Lunes.add(new StructHorarios("11:30","12:30","2.2","TIC"));
        Lunes.add(new StructHorarios("12:30","13:30","2.2","TIC"));
        h.add(Lunes);

        Martes.add(new StructHorarios("9:30","10:30","1.1","TIC"));
        Martes.add(new StructHorarios("10:30","11:30","1.1","TIC"));
        Martes.add(new StructHorarios("11:30","12:30","2.2","PTC"));
        Martes.add(new StructHorarios("12:30","13:30","2.2","PTC"));
        h.add(Martes);

        Miercoles.add(new StructHorarios("9:30","10:30","3.7","SS"));
        Miercoles.add(new StructHorarios("10:30","11:30","3.7","SS"));
        Miercoles.add(new StructHorarios("11:30","12:30","1.5","PTC"));
        Miercoles.add(new StructHorarios("12:30","13:30","1.5","PTC"));
        h.add(Miercoles);

        Jueves.add(new StructHorarios("8:30","9:30","1.2","VC"));
        Jueves.add(new StructHorarios("9:30","10:30","1.2","VC"));
        Jueves.add(new StructHorarios("10:30","11:30","3.7","VC (practicas)"));
        Jueves.add(new StructHorarios("11:30","12:30","3.7","VC (practicas)"));
        Jueves.add(new StructHorarios("12:30","13:30","1.2","PL"));
        Jueves.add(new StructHorarios("13:30","14:30","1.2","PL"));
        h.add(Jueves);

        Viernes.add(new StructHorarios("8:30","9:30","3.3","NPI (practicas)"));
        Viernes.add(new StructHorarios("10:30","11:30","3.3","NPI (practicas)"));
        Viernes.add(new StructHorarios("10:30","11:30","1.2","NPI"));
        Viernes.add(new StructHorarios("11:30","12:30","1.2","NPI"));
        Viernes.add(new StructHorarios("12:30","13:30","3.9","PL (practicas)"));
        Viernes.add(new StructHorarios("13:30","14:30","3.9","PL (practicas)"));
        h.add(Viernes);

    }

    public ArrayList<StructHorarios> getDia(int i){ return h.get(i); }
    public StructHorarios getDiayHora(int i,int j){return h.get(i).get(j);}
}
