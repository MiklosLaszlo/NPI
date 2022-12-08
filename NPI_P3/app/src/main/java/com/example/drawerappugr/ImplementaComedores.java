package com.example.drawerappugr;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

@SuppressLint("ClickableViewAccessibility")
public class ImplementaComedores {
    private MainActivity activity;
    private ScrollView scrollView;
    private TextView mostrardia;
    private ArrayList<TextView> menu1;
    private ArrayList<TextView> menu2;
    private final Comedores comedor = new Comedores();
    private DayOfWeek diaSemana;
    private Button siguienteButton;
    private Button anteriorButton;
    private TableLayout tablaMenu1;
    private TableLayout tablaMenu2;

    private int seleccionado = 0;
    private final MenuAceptarComedor menuAceptarComedor;

    private GestosSensor gestosSensor;

    @SuppressLint("ClickableViewAccessibility")
    public ImplementaComedores(@NonNull MainActivity ac) {
        activity = ac;
        //activity.activaGestos();

        scrollView = activity.findViewById(R.id.Comedores);
        mostrardia = (TextView) activity.findViewById(R.id.diaMenu);
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

        tablaMenu1 = (TableLayout) activity.findViewById(R.id.tablaMenu1);
        tablaMenu2 = (TableLayout) activity.findViewById(R.id.tablaMenu2);

        Button btn = activity.findViewById(R.id.btnAceptarCompra);

        menuAceptarComedor = new MenuAceptarComedor(activity, this);

        emptyContent();
        diaSemana = LocalDateTime.now().getDayOfWeek();
        if(diaSemana == DayOfWeek.SUNDAY || diaSemana == DayOfWeek.SATURDAY)
            diaSemana = DayOfWeek.MONDAY;
        setTextDay(diaSemana);

        siguienteButton.setOnTouchListener(new GestosPantalla(false,false, true){
            @Override
            public void touchUpCallback() {nextDayMenu();}
        });

        anteriorButton.setOnTouchListener(new GestosPantalla(false,false, true){
            @Override
            public void touchUpCallback() {prevDayMenu();}
        });

        View.OnTouchListener escuchaMenus1 = new GestosPantalla(false, false, true) {
            @Override
            public void touchUpCallback() {
                menuSelecionado(1);
            }
        };

        View.OnTouchListener escuchaMenus2 = new GestosPantalla(false, false, true) {
            @Override
            public void touchUpCallback() {
                menuSelecionado(2);
            }
        };

        for(TextView texto : menu1){
            texto.setOnTouchListener(escuchaMenus1);
        }

        for(TextView texto : menu2){
            texto.setOnTouchListener(escuchaMenus2);
        }

        tablaMenu1.setOnTouchListener(escuchaMenus1);
        tablaMenu2.setOnTouchListener(escuchaMenus2);

        creaGestosGenerales();
    }

    public void cargar(){gestosSensor.registerListener();}
    public void descargar(){gestosSensor.unregisterListener();}

    private void creaGestosGenerales(){
        scrollView.setOnTouchListener(new GestosPantalla(true, true, false){
            @Override
            public void swipeCallback(direction dir) {
                switch (dir){
                    case IZQUIERDA:
                        nextDayMenu();
                        break;
                    case DERECHA:
                        prevDayMenu();
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
            public void giroManoIzquierdaCallback() {prevDayMenu();}
            @Override
            public void giroManoDerechaCallback() {nextDayMenu();}
            @Override
            public void gestoArribaCallback() {
                menuSelecionado(1);
            }
            @Override
            public void gestoAbajoCallback() {
                menuSelecionado(2);
            }

            @Override
            public void gestoAceptarCallback() {
                if(seleccionado!=0){
                    menuAceptarComedor.aparecer(mostrardia.getText().toString(), ((seleccionado == 1) ? "no vegano" : "vegano"));
                    finSeleccion();
                }
            }
        };
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
        seleccionado = 0;
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
        seleccionado = 0;
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
        finSeleccion();
        StructComedores auxComida = comedor.get(i,1);
        menu1.get(0).setText(auxComida.menu);
        for(int j=0;j<auxComida.comidas.size();j++){
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

    public void menuSelecionado(int i){
        finSeleccion();
        switch (i){
            case 1:
                tablaMenu1.setBackgroundColor(Color.parseColor("#85BB65"));
                break;
            case 2:
                tablaMenu2.setBackgroundColor(Color.parseColor("#85BB65"));
                break;
        }
        if(seleccionado == i) {
            finSeleccion();
            menuAceptarComedor.aparecer(mostrardia.getText().toString(), i == 1 ? "no vegano" : "vegano");
            seleccionado = 0;
        }
        else
            seleccionado = i;
    }

    public void finSeleccion(){
        tablaMenu1.setBackgroundColor(0x00000000);//"@color/black"));
        tablaMenu2.setBackgroundColor(0x00000000);//"@color/black"));
    }
}
