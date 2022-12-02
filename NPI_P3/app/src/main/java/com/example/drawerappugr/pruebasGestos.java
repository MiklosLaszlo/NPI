package com.example.drawerappugr;

import android.annotation.SuppressLint;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class pruebasGestos extends AppCompatActivity {

    private LinearLayout layout;

    private TextView text1;
    private TextView text2;
    private TextView text3;


    GestosPantallaJM gestoPantalla = new GestosPantallaJM();
    GestosSensorJM gestoSensor;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_gestos);
        layout = findViewById(R.id.lin_test);
        text1 = findViewById(R.id.textView7);
        text2 = findViewById(R.id.textView8);
        text3 = findViewById(R.id.textView9);


        GestosPantallaJM.Callback cb = new GestosPantallaJM.Callback() {
            @Override
            public boolean cb(GestosPantallaJM.direction dir) {
                Log.e("cb",dir.toString());
                text1.setText(dir.toString());
                return true;
            }
        };

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestoPantalla.onTouchCallback(view, motionEvent, cb);
            }
        });

        GestosSensorJM.Callback cb_sensor = new GestosSensorJM.Callback() {
            @Override
            public boolean cb(boolean b) {
                text2.setText(b + "");
                return false;
            }
        };

        gestoSensor = new GestosSensorJM(this){
            @Override
            public void onSensorChanged(SensorEvent sensorEvent){
                onSensorChangedCallback(sensorEvent, cb_sensor);
            }
        };

    }

    /*@Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Log.e("main","Touch");
        GestosPantallaJM.double_swipe(motionEvent);

        return true;
    }*/
}