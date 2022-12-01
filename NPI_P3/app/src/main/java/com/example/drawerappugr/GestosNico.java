package com.example.drawerappugr;

// Para Usar los sensores del movil

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public abstract class GestosNico implements SensorEventListener {
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


    public GestosNico(Context context) {
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
