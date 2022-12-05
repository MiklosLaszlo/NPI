package com.example.drawerappugr;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButtonToggleGroup;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ImplementaComedores {
    private TextView mostrardia;
    private ArrayList<TextView> menu1;
    private ArrayList<TextView> menu2;
    private final Comedores comedor = new Comedores();
    private DayOfWeek diaSemana;
    private Button siguienteButton;
    private Button anteriorButton;
    private Context context;


    public ImplementaComedores(Activity activity) {
        mostrardia = (TextView) activity.findViewById(R.id.dia);
        menu1 = new ArrayList<TextView>();
        menu2 = new ArrayList<TextView>();
        menu1.add((TextView) activity.findViewById(R.id.Menu1));
        menu1.add((TextView) activity.findViewById(R.id.Menu1comida1));
        menu1.add((TextView) activity.findViewById(R.id.Menu1comida2));
        menu1.add((TextView) activity.findViewById(R.id.Menu1comida3));
        menu1.add((TextView) activity.findViewById(R.id.Menu1comida4));
        menu1.add((TextView) activity.findViewById(R.id.Menu1comida5));

        menu2.add((TextView) activity.findViewById(R.id.Menu2));
        menu2.add((TextView) activity.findViewById(R.id.Menu2comida1));
        menu2.add((TextView) activity.findViewById(R.id.Menu2comida2));
        menu2.add((TextView) activity.findViewById(R.id.Menu2comida3));
        menu2.add((TextView) activity.findViewById(R.id.Menu2comida4));
        menu2.add((TextView) activity.findViewById(R.id.Menu2comida5));

        siguienteButton= (Button) activity.findViewById(R.id.botonMenuSiguiente);
        anteriorButton= (Button) activity.findViewById(R.id.botonMenuAnterior);

        emptyContent();
        diaSemana = LocalDateTime.now().getDayOfWeek();
        if(diaSemana == DayOfWeek.SUNDAY || diaSemana == DayOfWeek.SATURDAY)
            diaSemana = DayOfWeek.MONDAY;
        setTextDay(diaSemana);

        siguienteButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    nextDayMenu();
                }
                return false;
            }
        });

        anteriorButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    prevDayMenu();
                }
                return false;
            }
        });
    }

    public void nextDayMenu(){
        switch (diaSemana){
            case MONDAY:
                diaSemana=DayOfWeek.TUESDAY;
                break;
            case TUESDAY:
                diaSemana=DayOfWeek.WEDNESDAY;
                break;
            case WEDNESDAY:
                diaSemana=DayOfWeek.THURSDAY;
                break;
            case THURSDAY:
                diaSemana=DayOfWeek.FRIDAY;
                break;
            case FRIDAY:
            case SATURDAY:
            case SUNDAY:
                diaSemana=DayOfWeek.MONDAY;
                break;
        }
        setTextDay(diaSemana);
    }

    public void prevDayMenu(){
        switch (diaSemana){
            case MONDAY:
                diaSemana=DayOfWeek.FRIDAY;
                break;
            case TUESDAY:
                diaSemana=DayOfWeek.MONDAY;
                break;
            case WEDNESDAY:
                diaSemana=DayOfWeek.TUESDAY;
                break;
            case THURSDAY:
                diaSemana=DayOfWeek.WEDNESDAY;
                break;
            case FRIDAY:
            case SATURDAY:
            case SUNDAY:
                diaSemana=DayOfWeek.THURSDAY;
                break;
        }
        setTextDay(diaSemana);
    }

    private void emptyContent(){
        mostrardia.setText("");
        for (TextView texto : menu1){
            texto.setText("");
        }
        for (TextView texto : menu2){
            texto.setText("");
        }
    }

    private void setTextMenus(int i){
        StructComedores auxComida = comedor.get(i,1);
        menu1.get(0).setText(auxComida.menu);
        Log.e("Dia", String.valueOf(i));
        for(int j=0;j<auxComida.comidas.size();j++){
            Log.e("Cantidad", String.valueOf(j));
            Log.e("Comida", auxComida.comidas.get(j));
            menu1.get(j+1).setText(auxComida.comidas.get(j));
        }

        auxComida = comedor.get(i,2);
        menu2.get(0).setText(auxComida.menu);
        for(int j=0;j<auxComida.comidas.size();j++){
            menu2.get(j+1).setText(auxComida.comidas.get(j));
        }
    }

    private void setTextDay(DayOfWeek dia){
        emptyContent();
        switch (dia){
            case TUESDAY:
                mostrardia.setText("Martes");
                setTextMenus(1);
                break;
            case WEDNESDAY:
                mostrardia.setText("Miércoles");
                setTextMenus(2);
                break;
            case THURSDAY:
                mostrardia.setText("Jueves");
                setTextMenus(3);
                break;
            case FRIDAY:
                mostrardia.setText("Viernes");
                setTextMenus(4);
                break;
            case SUNDAY:
            case SATURDAY:
            case MONDAY:
                mostrardia.setText("Lunes");
                setTextMenus(0);
                break;
            default:
                mostrardia.setText("ERROR CON LA SELECCION DEL DIA");
                break;
        }
    }
}
