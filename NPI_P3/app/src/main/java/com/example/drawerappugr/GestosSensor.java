package com.example.drawerappugr;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public abstract class GestosSensor implements SensorEventListener{

    private final SensorManager mSensorManager;
    private final Sensor mGiroscope;
    private final Sensor mLinearAcelerometer;
    private final Sensor mRotation;

    private Context context;

    public GestosSensor(Context context) {
        prevy=0;
        this.context = context;
        mSensorManager = (SensorManager) context.getSystemService (Context.SENSOR_SERVICE);
        mGiroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mLinearAcelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        mRotation = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }


    public boolean hasGiroscopeSensor(){return mGiroscope != null;}

    public boolean hasLinearAcelerometerSensor(){return mLinearAcelerometer != null;}

    public boolean hasRotationSensor(){
        return mRotation != null;
    }


    public void registerListener(){
        if(hasGiroscopeSensor())
            mSensorManager.registerListener(this,mGiroscope,SensorManager.SENSOR_DELAY_NORMAL);
        if(hasLinearAcelerometerSensor())
            mSensorManager.registerListener(this,mLinearAcelerometer,SensorManager.SENSOR_DELAY_NORMAL );
        if(hasRotationSensor())
            mSensorManager.registerListener(this,mRotation,SensorManager.SENSOR_DELAY_NORMAL );
    }

    public void unregisterListener(){mSensorManager.unregisterListener(this);}

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()){
            case Sensor.TYPE_GYROSCOPE:
                gestoAceptar(sensorEvent); break;
            case Sensor.TYPE_ROTATION_VECTOR:
                rotationCallback(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
        }
        gestoAceptar(sensorEvent);
    }

    abstract void rotationCallback(float rx, float ry, float rz);


    // Gesto aceptar giroscopio

    public abstract void gestoAceptarCallback();

    static final float UMBRAL_GIROSCOPIO = 5;

    long t_girosc = -1;
    double last_x = -1;
    double last_y = -1;
    double last_z = -1;

    boolean anterior = false;

    static final float U_ACEPTAR_X_INF = 350;
    static final float U_ACEPTAR_X_SUP = 400;
    static final float U_ACEPTAR_Y = 70;
    static final float U_ACEPTAR_Z = 70;


    private boolean haceGesto(double dx, double dy, double dz, float time) {
        boolean aceptar_x, aceptar_y, aceptar_z;

        aceptar_y = Math.abs(dy ) < U_ACEPTAR_Y;
        aceptar_z = Math.abs(dz * time) < U_ACEPTAR_Z;
        aceptar_x = /*dx > 0 &&*/ U_ACEPTAR_X_INF < dx  && dx < U_ACEPTAR_X_SUP;

        return aceptar_x && aceptar_y && aceptar_z;
    }


    private void gestoAceptar(SensorEvent ev) {
        if (last_x != -1) {
            boolean gesto = haceGesto(
                    Math.toDegrees(ev.values[0]) - last_x,
                    Math.toDegrees(ev.values[1]) - last_y,
                    Math.toDegrees(ev.values[2]) - last_z,
                    ev.timestamp - t_girosc
            );

            if (!gesto && anterior) anterior = false;
            if (gesto && !anterior) gestoAceptarCallback();
        }

        last_x = Math.toDegrees(ev.values[0]);
        last_y = Math.toDegrees(ev.values[1]);
        last_z = Math.toDegrees(ev.values[2]);
        t_girosc = ev.timestamp;
    }


    private int currenty;
    private int prevy;

    private int alcurrenty;
    private int alprevy;

    private float currentVel;
    private float prevVel;

    public abstract void giroManoIzquierdaCallback();
    private boolean gestoGiroManoIzquierda(SensorEvent event){
        currenty=(int) event.values[1];
        if (currenty < 0 && Math.abs(Math.abs(currenty)-Math.abs(prevy)) > 8 && Math.abs(Math.abs(currenty)-Math.abs(prevy)) < 15){
            giroManoIzquierdaCallback();
            return true;
        }
        return false;
    }

    public abstract void giroManoDerechaCallback();
    private boolean gestoGiroManoDerecha(SensorEvent event){
        currenty=(int) event.values[1];
        if (currenty > 0 && Math.abs(Math.abs(currenty)-Math.abs(prevy)) > 8 && Math.abs(Math.abs(currenty)-Math.abs(prevy)) < 15){
            giroManoDerechaCallback();
            return true;
        }
        return false;
    }

    public abstract void gestoArribaCallback();
    private boolean gestoParaArriba(SensorEvent event){
        alcurrenty=(int) event.values[1];
        if (alcurrenty > 0 && Math.abs(Math.abs(alcurrenty)-Math.abs(alprevy)) > 5 && Math.abs(Math.abs(alcurrenty)-Math.abs(alprevy)) < 10){
            gestoArribaCallback();
            return true;
        }
        return false;
    }

    public abstract void gestoAbajoCallback();
    private boolean gestoParaAbajo(SensorEvent event){
        alcurrenty=(int) event.values[1];
        if (alcurrenty < 0 && Math.abs(Math.abs(alcurrenty)-Math.abs(alprevy)) > 5 && Math.abs(Math.abs(alcurrenty)-Math.abs(alprevy)) < 10){
            gestoAbajoCallback();
            return true;
        }
        return false;
    }

    public GestosAprendidos reconocerGestos(SensorEvent event) {
        switch (event.sensor.getType()) {
            case Sensor.TYPE_GYROSCOPE:
                if (gestoGiroManoIzquierda(event)) {
                    return GestosAprendidos.GIROIZQUIERDA;
                } else if (gestoGiroManoDerecha(event)) {
                    return GestosAprendidos.GIRODERECHA;
                }
                break;
            case Sensor.TYPE_LINEAR_ACCELERATION:
                if (gestoParaArriba(event)) {
                    return GestosAprendidos.ARRIBA;
                } else if (gestoParaAbajo(event)) {
                    return GestosAprendidos.ABAJO;
                }

                break;
        }
        return GestosAprendidos.DESCONOCIDO;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){};
}




