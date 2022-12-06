/*package com.example.drawerappugr;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class pseudoMainNico extends AppCompatActivity {


    private TextView existGiroscope;
    private TextView infoGiroscope;
    private TextView infoEsucha;

    private TextView existLinearAcelerometer;
    private TextView infoLinearAcelerometer;

    private CountDownTimer contador;

    private boolean listening;

    private boolean left_done;
    private boolean right_done;

    private boolean second_listening;

    private int contando;

    private GestosNico gestoGiroscopio;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        infoGiroscope = findViewById(R.id.textView1);
        existGiroscope = findViewById(R.id.textView);
        infoEsucha = findViewById(R.id.textView2);

        existLinearAcelerometer=findViewById(R.id.textView3);
        infoLinearAcelerometer = findViewById(R.id.textView4);
        contando=0;
        infoEsucha.setText("Escuchando");
        listening=true;
        left_done=false;
        right_done=false;
        second_listening=false;
        contador =  new CountDownTimer(1600,700) {
            @Override
            public void onTick(long l) {
                if(left_done || right_done && (contando != 0)){
                    second_listening=true;
                }
                contando=contando+1;
            }

            @Override
            public void onFinish() {
                contando=0;
                listening=true;
                left_done=false;
                right_done=false;
                second_listening=false;
                infoEsucha.setText("Escuchando");
            }
        };

        gestoGiroscopio = new GestoGiroscopio(this){
            @Override
            public void onSensorChanged(SensorEvent event){
                if(listening) {
                    switch (event.sensor.getType()) {
                        case Sensor.TYPE_GYROSCOPE:
                            if (gestoGiroManoIzquierda(event)) {
                                infoGiroscope.setText("GiroManoIzquierda");
                                contador.start();
                                listening = false;
                                infoEsucha.setText("No escuchando");
                                left_done=true;
                            }
                            else if (gestoGiroManoDerecha(event)) {
                                infoGiroscope.setText("GiroManoDerecha");
                                contador.start();
                                listening = false;
                                right_done=true;
                                infoEsucha.setText("No escuchando");
                            }
                            break;
                        case Sensor.TYPE_LINEAR_ACCELERATION:
                            if (gestoParaArriba(event)){
                                infoLinearAcelerometer.setText("VoyArriba");
                                contador.start();
                                listening = false;
                                infoEsucha.setText("No escuchando");
                            }
                            else if(gestoParaAbajo(event)){
                                infoLinearAcelerometer.setText("VoyAbajo");
                                contador.start();
                                listening = false;
                                infoEsucha.setText("No escuchando");
                            }

                            break;
                    }

                }

                else if(second_listening){
                    switch (event.sensor.getType()) {
                        case Sensor.TYPE_GYROSCOPE:
                            if (gestoGiroManoIzquierda(event) && left_done) {
                                infoGiroscope.setText("DobleGiroManoIzquierda");
                                infoEsucha.setText("No escuchando");
                                left_done=false;
                            }
                            else if (gestoGiroManoDerecha(event) && right_done) {
                                infoGiroscope.setText("DobleGiroManoDerecha");
                                infoEsucha.setText("No escuchando");
                                right_done=false;
                            }
                            second_listening=false;
                            break;
                    }
                }
            };
        };

        if(gestoGiroscopio.hasGiroscopeSensor()){
            existGiroscope.setText("Hay giroscopo");
        }

        if(gestoGiroscopio.hasLinearAcelerometerSensor()){
            existLinearAcelerometer.setText("Hay sensor aceleración lineal");
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        if(gestoGiroscopio.hasGiroscopeSensor())
            gestoGiroscopio.registerListener();
    }

    @Override
    public void onPause(){
        super.onPause();
        gestoGiroscopio.unregisterListener();
    }
}*/