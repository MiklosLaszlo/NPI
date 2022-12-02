package com.example.drawerappugr;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.os.CountDownTimer;
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_gestos);
        layout = findViewById(R.id.lin_test);
        text1 = findViewById(R.id.textView7);
        text2 = findViewById(R.id.textView8);
        text3 = findViewById(R.id.textView9);

        GestosJM.Callback cb = new GestosJM.Callback() {
            @Override
            public boolean cb(GestosJM.direction dir) {
                Log.e("cb",dir.toString());
                text1.setText(dir.toString());

                return true;
            }
        };

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return GestosJM.onTouchCallback(view, motionEvent, cb);
            }

        });
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Log.e("main","Touch");
        GestosJM.double_swipe(motionEvent);

        return true;
    }*/
}