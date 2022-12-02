package com.example.drawerappugr;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public abstract class GestosSensorJM implements SensorEventListener{
    // Gesto aceptar giroscopio

    public interface Callback {
        boolean cb(boolean b);
    }
     private SensorManager mSensorManager;
     private Sensor mGiroscope;
    private Sensor mLinearAcelerometer;
    private Context context;

    private int currenty;
    private int prevy;

    private int alcurrenty;
    private int alprevy;

    private float currentVel;
    private float prevVel;

    static final float UMBRAL_GIROSCOPIO = 5;

    long t_girosc = -1;
    double last_x = -1;
    double last_y = -1;
    double last_z = -1;

    boolean anterior = false;

    public void onSensorChangedCallback(SensorEvent ev, Callback cb) {
        gestoAceptar(ev, cb);
    }

    private boolean haceGesto(double dx, double dy, double dz, float time) {
        boolean aceptar_x, aceptar_y, aceptar_z;

        aceptar_y = Math.abs(dy * time) < UMBRAL_GIROSCOPIO;
        aceptar_z = Math.abs(dz * time) < UMBRAL_GIROSCOPIO;

        aceptar_x = dx > 0 && 8 < dx * time && dx * time < 15;

        Log.e("asas", aceptar_x + " " + aceptar_y + " " + aceptar_z);

        return aceptar_x && aceptar_y && aceptar_z;
    }


    private void gestoAceptar(SensorEvent ev, Callback cb) {
        if (last_x != -1) {
            boolean gesto = haceGesto(
                    Math.toDegrees(ev.values[0]) - last_x,
                    Math.toDegrees(ev.values[1]) - last_y,
                    Math.toDegrees(ev.values[2]) - last_z,
                    ev.timestamp - t_girosc
            );

            if (!gesto && anterior) anterior = false;
            if (gesto && !anterior) cb.cb(true);
        }

        last_x = Math.toDegrees(ev.values[0]);
        last_y = Math.toDegrees(ev.values[1]);
        last_z = Math.toDegrees(ev.values[2]);
        t_girosc = ev.timestamp;
    }

    public GestosSensorJM(Context context) {
        prevy=0;
        this.context = context;
        mSensorManager = (SensorManager) context.getSystemService (Context.SENSOR_SERVICE);
        mGiroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mLinearAcelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
    }

    public boolean hasGiroscopeSensor(){
        if(mGiroscope!=null)
            return true;
        return false;
    }

    public boolean hasLinearAcelerometerSensor(){
        if(mLinearAcelerometer!=null)
            return true;
        return false;
    }

    public void registerListener(){
        if(hasGiroscopeSensor())
            mSensorManager.registerListener(this,mGiroscope,SensorManager.SENSOR_DELAY_NORMAL);
        if(hasLinearAcelerometerSensor())
            mSensorManager.registerListener(this,mLinearAcelerometer,SensorManager.SENSOR_DELAY_NORMAL );
    }

    public void unregisterListener(){
        mSensorManager.unregisterListener(this);
    }

    public boolean gestoGiroManoIzquierda(SensorEvent event){
        currenty=(int) event.values[1];
        if (currenty < 0 && Math.abs(Math.abs(currenty)-Math.abs(prevy)) > 8 && Math.abs(Math.abs(currenty)-Math.abs(prevy)) < 15){
            return true;
        }
        return false;
    }

    public boolean gestoGiroManoDerecha(SensorEvent event){
        currenty=(int) event.values[1];
        if (currenty > 0 && Math.abs(Math.abs(currenty)-Math.abs(prevy)) > 8 && Math.abs(Math.abs(currenty)-Math.abs(prevy)) < 15){
            return true;
        }
        return false;
    }

    public boolean gestoParaArriba(SensorEvent event){
        alcurrenty=(int) event.values[1];
        if (alcurrenty > 0 && Math.abs(Math.abs(alcurrenty)-Math.abs(alprevy)) > 5 && Math.abs(Math.abs(alcurrenty)-Math.abs(alprevy)) < 10){
            return true;
        }
        return false;
    }

    public boolean gestoParaAbajo(SensorEvent event){
        alcurrenty=(int) event.values[1];
        if (alcurrenty < 0 && Math.abs(Math.abs(alcurrenty)-Math.abs(alprevy)) > 5 && Math.abs(Math.abs(alcurrenty)-Math.abs(alprevy)) < 10){
            return true;
        }
        return false;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    };

    @Override
    public abstract void onSensorChanged(SensorEvent event);
}


