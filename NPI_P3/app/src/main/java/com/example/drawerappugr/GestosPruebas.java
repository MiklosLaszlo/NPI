package com.example.drawerappugr;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GestosPruebas extends AppCompatActivity {

    private LinearLayout layout;

    private TextView text1;
    private TextView text2;
    private TextView text3;


    GestosPantalla gestoPantalla = new GestosPantalla() {
        @Override
        public void touchUpCallback() {}
        @Override
        public void touchDownCallback() {}
        @Override
        public void doubleSwipeCallback(direction dir) {
            Log.e("cb",dir.toString());
            text1.setText(dir.toString());
        }
        @Override
        public void swipeCallback(direction dir) {}
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

        gestoSensor = new GestosSensor(this) {
            @Override
            void rotationCallback(float rx, float ry, float rz) {}
            @Override
            public void gestoAceptarCallback() {text2.setText("HECHO");}
            @Override
            public void giroManoIzquierdaCallback() {}
            @Override
            public void giroManoDerechaCallback() {}
            @Override
            public void gestoArribaCallback() {}
            @Override
            public void gestoAbajoCallback() {}
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