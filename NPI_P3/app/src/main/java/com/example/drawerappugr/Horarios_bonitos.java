package com.example.drawerappugr;

import android.annotation.SuppressLint;

import android.widget.Button;

import android.widget.HorizontalScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;


import java.util.ArrayList;
import java.util.Calendar;

@SuppressLint("ClickableViewAccessibility")
public class Horarios_bonitos  {

    private MainActivity activity;

    private GestosSensor gestosSensor;
    private HorizontalScrollView horizontalScrollView;

    private ArrayList<TextView> horarios_final;
    private  Horarios horario = new Horarios();
    private int dia_semana = diaSemana();
    private Button siguienteButton;
    private Button anteriorButton;

    public String devolver_dia(int diia){
        switch (diia){
            case 0:
                return "LUNES";
            case 1:
                return "MARTES";
            case 2:
                return "MIERCOLES";
            case 3:
                return "JUEVES";
            case 4:
                return "VIERNES";
        }
        return "LUNES";
    }

    int diaSemana() {

        int nD = -1;
        Calendar c = Calendar.getInstance();
        nD = Integer.parseInt(Integer.toString(c.get(Calendar.DAY_OF_WEEK)));
        switch (nD) {
            case 1:
            case 2:
                return 0;
            case 3:
            case 7:
                return 1;
            case 4:
                return 2;

            case 5:
                return 3;

            case 6:
                return 4;

        }

        return 1;
    }

    public void ImplementaHorarios(int dia_actual) {
        //Obtenemos el dia actual
        int i=0;
        int j=0;
        while(i<horario.getDia(dia_actual).size()){
            horarios_final.get(j).setText(horario.getDiayHora(dia_semana, i).asignatura);
            horarios_final.get(j+1).setText((horario.getDiayHora(dia_semana, i).inicio+ "-" +horario.getDiayHora(dia_semana, i).fin));
            horarios_final.get(j+2).setText(horario.getDiayHora(dia_semana, i).aula);
            i+=1;
            j+=3;
        }
        if(horario.getDia(dia_actual).size()==4){
            horarios_final.get(12).setText("");
            horarios_final.get(13).setText("");
            horarios_final.get(14).setText("");
            horarios_final.get(15).setText("");
            horarios_final.get(16).setText("");
            horarios_final.get(17).setText("");

        }
        horarios_final.get(18).setText(devolver_dia(dia_actual));
    }

    public void Horarios_dia(int dia_actual){
        int i=0;
        int j=0;

        while(i<horario.getDia(dia_actual).size()){
            horarios_final.get(j).setText(horario.getDiayHora(dia_semana, i).asignatura);
            horarios_final.get(j+1).setText(horario.getDiayHora(dia_semana, i).inicio + "-" +horario.getDiayHora(dia_semana, i).fin );
            horarios_final.get(j+2).setText(horario.getDiayHora(dia_semana, i).aula);
            i+=1;
            j+=3;
        }
        if(horario.getDia(dia_actual).size()==4){
            horarios_final.get(12).setText("");
            horarios_final.get(13).setText("");
            horarios_final.get(14).setText("");
            horarios_final.get(15).setText("");
            horarios_final.get(16).setText("");
            horarios_final.get(17).setText("");
        }
        horarios_final.get(18).setText(devolver_dia(dia_actual));
    }

    protected Horarios_bonitos(@NonNull MainActivity horario) {
        activity = horario;
        horizontalScrollView = horario.findViewById(R.id.hscrll1);

        creaGestosGenerales();

        horarios_final = new ArrayList<TextView>();

        horarios_final.add((TextView) horario.findViewById(R.id.Asignatura1));
        horarios_final.add((TextView) horario.findViewById(R.id.hora1));
        horarios_final.add((TextView) horario.findViewById(R.id.clase1));

        horarios_final.add((TextView) horario.findViewById(R.id.Asignatura2));
        horarios_final.add((TextView) horario.findViewById(R.id.hora2));
        horarios_final.add((TextView) horario.findViewById(R.id.clase2));

        horarios_final.add((TextView) horario.findViewById(R.id.Asignatura3));
        horarios_final.add((TextView) horario.findViewById(R.id.hora3));
        horarios_final.add((TextView) horario.findViewById(R.id.clase3));

        horarios_final.add((TextView) horario.findViewById(R.id.Asignatura4));
        horarios_final.add((TextView) horario.findViewById(R.id.hora4));
        horarios_final.add((TextView) horario.findViewById(R.id.clase4));


        horarios_final.add((TextView) horario.findViewById(R.id.Asignatura5));
        horarios_final.add((TextView) horario.findViewById(R.id.hora5));
        horarios_final.add((TextView) horario.findViewById(R.id.clase5));


        horarios_final.add((TextView) horario.findViewById(R.id.Asignatura6));
        horarios_final.add((TextView) horario.findViewById(R.id.hora6));
        horarios_final.add((TextView) horario.findViewById(R.id.clase6));


        horarios_final.add((TextView) horario.findViewById(R.id.dia));



        siguienteButton = (Button) horario.findViewById(R.id.button2);
        anteriorButton = (Button) horario.findViewById(R.id.button1);
        ImplementaHorarios(dia_semana);

        siguienteButton.setOnTouchListener(new GestosPantalla(false, false, true){
            @Override
            public void touchUpCallback() {
                nextDay();
            }
        });

        anteriorButton.setOnTouchListener(new GestosPantalla(false, false, true){
            @Override
            public void touchUpCallback() {
                previousDay();
            }
        });
    }

    private void nextDay(){
        dia_semana = (dia_semana + 1) % 5;
        Horarios_dia(dia_semana);
    }

    private void previousDay(){
        dia_semana -= 1;
        while (dia_semana < 0) {
            dia_semana += 5;
        }
        Horarios_dia(dia_semana);
    }

    private void creaGestosGenerales(){
        horizontalScrollView.setOnTouchListener(new GestosPantalla(true, true, false){
            @Override
            public void swipeCallback(direction dir) {
                switch (dir){
                    case IZQUIERDA:
                        nextDay();
                        break;
                    case DERECHA:
                        previousDay();
                        break;
                }
            }
            @Override
            public void doubleSwipeCallback(direction dir) {
                switch (dir) {
                    case ARRIBA:
                    case IZQUIERDA:
                        activity.muestraSiguiente(); break;
                    case ABAJO:
                    case DERECHA:
                        activity.muestraAnterior(); break;
                }
            }
        });

        gestosSensor = new GestosSensor(activity.getApplicationContext(), true, true, false, false, false){
            @Override
            public void giroManoIzquierdaCallback() {previousDay();}
            @Override
            public void giroManoDerechaCallback() {nextDay();}
        };
    }

    public void cargar(){ gestosSensor.registerListener(); }
    public void descargar(){ gestosSensor.unregisterListener(); }
}