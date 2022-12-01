package com.example.myapplication;

// Para Usar los sensores del movil

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorEvent;  // Un evento de un sensor
import android.hardware.SensorEventListener; // Escucha los eventos en cualquier sensor
import android.hardware.SensorManager; // Detecta (maneja) los sensores del movil.
import android.hardware.Sensor;        // Manejadores especificos de cada sensor.
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;


public abstract class GestoGiroscopio implements SensorEventListener {
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


    public GestoGiroscopio(Context context) {
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
