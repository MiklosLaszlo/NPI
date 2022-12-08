package com.example.drawerappugr;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GestosPruebas extends AppCompatActivity {

    private LinearLayout layout;

    private TextView text1;
    private TextView text2;
    private TextView text3;

    GestosPantalla gestoPantalla = new GestosPantalla(true, true,false) {
        @Override
        public void doubleSwipeCallback(direction dir) {
            //Log.e("cb",dir.toString());
            text1.setText(dir.toString());
        }
        @Override
        public void swipeCallback(direction dir) {
            text2.setText(dir.toString());
        }
    };

    GestosSensor gestoSensor;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_gestos);
        layout = findViewById(R.id.lin_test);
        text1 = findViewById(R.id.textView7);
        text2 = findViewById(R.id.textView8);
        text3 = findViewById(R.id.textView9);

        layout.setOnTouchListener( gestoPantalla );

        gestoSensor = new GestosSensor(this, true, true, false, true, false) {
            @Override
            public void gestoAceptarCallback() {text3.setText("ACEPTAR");}
            @Override
            public void gestoRechazarCallback() {text3.setText("CANCELAR");}
            @Override
            public void giroManoIzquierdaCallback() {text3.setText("IZQUIERDA");}
            @Override
            public void dobleGiroManoIzquierdaCallback() {text3.setText("DOBLE IZQUIERDA");}
            @Override
            public void giroManoDerechaCallback() {text3.setText("DERECHA");}
            @Override
            public void dobleGiroManoDerechaCallback() {text3.setText("DOBLE DERECHA");}
            @Override
            public void gestoArribaCallback() {text3.setText("ARRIBA");}
            @Override
            public void gestoAbajoCallback() {text3.setText("ABAJO");}
            @Override
            public void acceleIzqCallback() {text3.setText("ACCELE IZQ");}
            @Override
            public void acceleDerCallback() {text3.setText("ACCELE DER");}
            @Override
            public void proximidadCallback() {text3.setText("PROXIMIDAD");}
        };
        gestoSensor.registerListener();
    }

    @Override
    public void onResume(){
        super.onResume();
        gestoSensor.registerListener();
    }

    @Override
    public void onPause(){
        super.onPause();
        gestoSensor.unregisterListener();
    }
}