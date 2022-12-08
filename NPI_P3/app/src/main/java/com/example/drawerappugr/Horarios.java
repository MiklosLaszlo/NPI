package com.example.drawerappugr;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class Horarios {
    public  ArrayList<ArrayList<StructHorarios>> h=new ArrayList<>();

    public Horarios(){
        ArrayList<StructHorarios> Lunes =new ArrayList<StructHorarios>();
        ArrayList<StructHorarios> Martes =new ArrayList<>();
        ArrayList<StructHorarios> Miercoles =new ArrayList<>();
        ArrayList<StructHorarios> Jueves =new ArrayList<>();
        ArrayList<StructHorarios> Viernes =new ArrayList<>();

        Lunes.add(new StructHorarios("09:30","10:30","1.1","SS"));
        Lunes.add(new StructHorarios("10:30","11:30","1.1","SS"));
        Lunes.add(new StructHorarios("11:30","12:30","2.2","TIC"));
        Lunes.add(new StructHorarios("12:30","13:30","2.2","TIC"));
        h.add(Lunes);

        Martes.add(new StructHorarios("09:30","10:30","1.1","TIC"));
        Martes.add(new StructHorarios("10:30","11:30","1.1","TIC"));
        Martes.add(new StructHorarios("11:30","12:30","2.2","PTC"));
        Martes.add(new StructHorarios("12:30","13:30","2.2","PTC"));
        h.add(Martes);

        Miercoles.add(new StructHorarios("09:30","10:30","3.7","SS"));
        Miercoles.add(new StructHorarios("10:30","11:30","3.7","SS"));
        Miercoles.add(new StructHorarios("11:30","12:30","1.5","PTC"));
        Miercoles.add(new StructHorarios("12:30","13:30","1.5","PTC"));
        h.add(Miercoles);

        Jueves.add(new StructHorarios("08:30","09:30","1.2","VC"));
        Jueves.add(new StructHorarios("09:30","10:30","1.2","VC"));
        Jueves.add(new StructHorarios("10:30","11:30","3.7","VC (practicas)"));
        Jueves.add(new StructHorarios("11:30","12:30","3.7","VC (practicas)"));
        Jueves.add(new StructHorarios("12:30","13:30","1.2","PL"));
        Jueves.add(new StructHorarios("13:30","14:30","1.2","PL"));
        h.add(Jueves);

        Viernes.add(new StructHorarios("08:30","09:30","3.3","NPI (practicas)"));
        Viernes.add(new StructHorarios("09:30","10:30","3.3","NPI (practicas)"));
        Viernes.add(new StructHorarios("10:30","11:30","1.2","NPI"));
        Viernes.add(new StructHorarios("11:30","12:30","1.2","NPI"));
        Viernes.add(new StructHorarios("12:30","13:30","3.9","PL (practicas)"));
        Viernes.add(new StructHorarios("13:30","14:30","3.9","PL (practicas)"));
        h.add(Viernes);

    }

    public ArrayList<StructHorarios> getDia(int i){ return h.get(i); }
    public StructHorarios getDiayHora(int i,int j){return h.get(i).get(j);}

    //encuentra el Aula a partir del día y hora
    //devuelve null si no hay clase ya se por horario o por día(sabado y domingo)
    //int day = c.get(Calendar.DAY_OF_WEEK); es una caca y devuelve los siguientes valores
    //Lunes --> 2
    //Martes --> 3
    //Miercoles --> 4
    //Juevess --> 5
    //Viernes --> 6
    public String getAula() {
        final int CACA = 2;
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar c = Calendar.getInstance(timeZone);

        //Para poner day entre 0 y 6 en vez de 2 y 8
        //int day = c.get(Calendar.DAY_OF_WEEK) - CACA;
        int day = 4;
        if( day < 0 && 4 < day){
            // Log.e("Lugares ","Entra day fuera rango");
            return null;
        }

        boolean enClase=false;
        // DESCOMENTAR PARA HORA ACTUAL !!!!!
        // SimpleDateFormat sdfHour = new SimpleDateFormat("hh:mm");
        // String now = sdfHour.format(c.getTime());
        String now= "10:30";

        for(int i=0;i<h.get(day).size() && !enClase;++i){
            enClase = (now.compareTo(h.get(day).get(i).inicio) >= 0)
                    && (now.compareTo(h.get(day).get(i).fin) <= 0);
            //Log.e("Lugares día inicio",""+ h.get(day).get(i).inicio + h.get(day).get(i).fin);
            //Log.e("Lugares h",""+ now.compareTo(h.get(day).get(i).inicio));
            //Log.e("Lugares h",""+ now.compareTo(h.get(day).get(i).fin));
            if(enClase){
                return h.get(day).get(i).aula;
            }
        }

        return null;
    }

    public String getNextAula() {
        //final int CACA = 2;
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        Calendar c = Calendar.getInstance(timeZone);

        //Para poner day entre 0 y 6 en vez de 2 y 8
        //int day = c.get(Calendar.DAY_OF_WEEK) - CACA;
        int day = 4;

        if( day < 0 && 4 < day){
            // Log.e("Lugares ","Entra day fuera rango");
            return null;
        }

        boolean enClase=false;
        // DESCOMENTAR PARA HORA ACTUAL !!!!!
        //SimpleDateFormat sdfHour = new SimpleDateFormat("hh:mm");
        //String now = sdfHour.format(c.getTime());
        String now= "10:30";
        int j=-1;
        for(int i=0;i<h.get(day).size() && !enClase;++i){
            enClase = (now.compareTo(h.get(day).get(i).inicio) >= 0)
                    && (now.compareTo(h.get(day).get(i).fin) <= 0);
            //Log.e("Lugares día inicio",""+ h.get(day).get(i).inicio + h.get(day).get(i).fin);
            //Log.e("Lugares h",""+ now.compareTo(h.get(day).get(i).inicio));
            //Log.e("Lugares h",""+ now.compareTo(h.get(day).get(i).fin));
            if(enClase){
                if(i<h.get(day).size()-1)
                    return h.get(day).get(i+1).aula;
            }
        }

        return null;
    }
}
