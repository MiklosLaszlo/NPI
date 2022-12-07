package com.example.drawerappugr;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.util.Log;

public class GestosSensor implements SensorEventListener{

    //region COMUN

    private final SensorManager mSensorManager;
    private final Sensor mGiroscope;
    private final Sensor mLinearAcelerometer;
    private final Sensor mRotation;
    private final Sensor mProximity;

    private final CountDownTimer count;
    private GestosAprendidos gestoReconozido = GestosAprendidos.DESCONOCIDO;
    private boolean listening = true;
    private boolean second_listening = false;
    private int contando = 0;

    // private final Context context;

    public GestosSensor(Context context, boolean giroscope, boolean linear, boolean rotation, boolean proximity) {
        prevy=0;
        // this.context = context;
        mSensorManager = (SensorManager) context.getSystemService (Context.SENSOR_SERVICE);
        if(giroscope) mGiroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE); else mGiroscope = null;
        if(linear) mLinearAcelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION); else mLinearAcelerometer = null;
        if(rotation) mRotation = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR); else mRotation = null;
        if(proximity) mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY); else mProximity = null;

        count = new CountDownTimer(1000, 500) {
            @Override
            public void onTick(long l) {
                if( (gestoReconozido == GestosAprendidos.GIRODERECHA || gestoReconozido == GestosAprendidos.GIROIZQUIERDA) && (contando != 0)){
                    second_listening=true;
                }
                contando=contando+1;
            }

            @Override
            public void onFinish() {
                ejecutarGesto();
                listening = true;
                second_listening = false;
                gestoReconozido = GestosAprendidos.DESCONOCIDO;
                contando = 0;
            }
        };
    }

    private boolean hasGiroscopeSensor(){return mGiroscope != null;}

    private boolean hasLinearAcelerometerSensor(){return mLinearAcelerometer != null;}

    private boolean hasRotationSensor(){return mRotation != null;}

    private boolean hasProximitySensor(){return mProximity != null;}

    public void registerListener(){
        if(hasGiroscopeSensor())
            mSensorManager.registerListener(this,mGiroscope,SensorManager.SENSOR_DELAY_NORMAL);
        if(hasLinearAcelerometerSensor())
            mSensorManager.registerListener(this,mLinearAcelerometer,SensorManager.SENSOR_DELAY_NORMAL );
        if(hasRotationSensor())
            mSensorManager.registerListener(this,mRotation,SensorManager.SENSOR_DELAY_NORMAL );
        if(hasProximitySensor())
            mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregisterListener(){mSensorManager.unregisterListener(this);}

    private GestosAprendidos reconocerGestos(SensorEvent event) {
        GestosAprendidos gesto = GestosAprendidos.DESCONOCIDO;
        switch (event.sensor.getType()) {
            case Sensor.TYPE_GYROSCOPE:
                if (gestoAceptar(event))
                    gesto = GestosAprendidos.ACEPTAR;
                else if (gestoGiroManoIzquierda(event))
                    gesto = GestosAprendidos.GIROIZQUIERDA;
                else if (gestoGiroManoDerecha(event))
                    gesto = GestosAprendidos.GIRODERECHA;

                // Log.i("giroscopo", gesto.toString());

                break;
            case Sensor.TYPE_LINEAR_ACCELERATION:
                if (gestoParaArriba(event))
                    gesto = GestosAprendidos.ARRIBA;
                else if (gestoParaAbajo(event))
                    gesto = GestosAprendidos.ABAJO;
                break;
        }
        return gesto;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if(listening) {
            gestoReconozido = reconocerGestos(sensorEvent);
            if(gestoReconozido != GestosAprendidos.DESCONOCIDO) {
                count.start();
                listening=false;
            }
        }
        else if(second_listening){
            switch (reconocerGestos(sensorEvent)) {
                case GIROIZQUIERDA:
                    if(gestoReconozido==GestosAprendidos.GIROIZQUIERDA)
                        gestoReconozido = GestosAprendidos.DOSGIROIZQUIERDA;
                    break;
                case GIRODERECHA:
                    if(gestoReconozido==GestosAprendidos.GIRODERECHA)
                        gestoReconozido = GestosAprendidos.DOSGIRODERECHA;
                    break;
            }
        }

        switch (sensorEvent.sensor.getType()){
            case Sensor.TYPE_ROTATION_VECTOR:
                rotationCallback(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
                break;
            case Sensor.TYPE_PROXIMITY:
                eventoProximidad(sensorEvent);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){};

    private void ejecutarGesto(){
        switch (gestoReconozido){
            case GIROIZQUIERDA:
                Log.e("Gesto","Uno izquierda");
                giroManoIzquierdaCallback();
                break;
            case GIRODERECHA:
                Log.e("Gesto","Uno derecha");
                giroManoDerechaCallback();
                break;
            case DOSGIROIZQUIERDA:
                Log.e("Gesto","dos izquierda");
                dobleGiroManoIzquierdaCallback();
                break;
            case DOSGIRODERECHA:
                dobleGiroManoDerechaCallback();
                Log.e("Gesto","dos derecha");
                break;
            case ARRIBA:
                gestoArribaCallback();
                Log.e("Gesto","arriba");
                break;
            case ABAJO:
                gestoAbajoCallback();
                Log.e("Gesto","abajo");
                break;
            case ACEPTAR:
                gestoAceptarCallback();
                Log.e("Gesto","aceptar");
                break;
            case CANCELAR:
                Log.e("Gesto","cancelar");
                break;
        }
    }

    //endregion

    //region Rotacion

    public void rotationCallback(float rx, float ry, float rz){}

    //endregion

    //region Proximidad

    public void eventoProximidadCallback(boolean cerca){}

    private void eventoProximidad(SensorEvent sensorEvent){
        if (sensorEvent.sensor.getType()==Sensor.TYPE_PROXIMITY){
            if (sensorEvent.values[0] > 0){
                Log.i("SensorEvents","FAR: " + sensorEvent.values[0]);
                eventoProximidadCallback(true);
            }
            else{
                Log.i("SensorEvents","NEAR: " + sensorEvent.values[0]);
                eventoProximidadCallback(false);
            }
        }
    }

    //endregion

    //region Giroscopio

    // region Gesto aceptar giroscopio

    private static final float UMBRAL_GIROSCOPIO = 5;

    private long t_girosc = -1;
    private double last_x = -1;
    private double last_y = -1;
    private double last_z = -1;

    private boolean anterior = false;

    private static final float U_ACEPTAR_X_INF = 150;
    private static final float U_ACEPTAR_X_SUP = 250;
    private static final float U_ACEPTAR_Y = 70;
    private static final float U_ACEPTAR_Z = 70;

    public void gestoAceptarCallback(){}

    private boolean haceGesto(double dx, double dy, double dz, float time) {
        boolean aceptar_x, aceptar_y, aceptar_z;

        aceptar_y = Math.abs(dy) < U_ACEPTAR_Y;
        aceptar_z = Math.abs(dz) < U_ACEPTAR_Z;
        aceptar_x = /*dx > 0 &&*/ U_ACEPTAR_X_INF < dx  && dx < U_ACEPTAR_X_SUP;

        Log.e("aceptar", "dx " + dx + " dy " + Math.abs(dy ) + " dz " + Math.abs(dz));
        Log.e("aceptar", "x " + aceptar_x + " dy " + aceptar_y + " dz " + aceptar_z + ((aceptar_x && aceptar_y && aceptar_z) ? " TRUE" : " FALSE"));
        //Log.i("aceptar", (aceptar_x && aceptar_y && aceptar_z) ? "TRUE" : "FALSE");

        return aceptar_x && aceptar_y && aceptar_z;
    }

    private boolean gestoAceptar(SensorEvent ev) {
        boolean hecho = false;
        if (last_x != -1) {
            boolean gesto = haceGesto(
                    Math.toDegrees(ev.values[0]) - last_x,
                    Math.toDegrees(ev.values[1]) - last_y,
                    Math.toDegrees(ev.values[2]) - last_z,
                    ev.timestamp - t_girosc
            );

            if (!gesto && anterior) anterior = false;
            hecho = gesto && !anterior;
        }

        last_x = Math.toDegrees(ev.values[0]);
        last_y = Math.toDegrees(ev.values[1]);
        last_z = Math.toDegrees(ev.values[2]);
        t_girosc = ev.timestamp;

        return hecho;
    }

    //endregion

    //region giros

    private int currenty;
    private int prevy;

    //private float currentVel;
    //private float prevVel;

    public void giroManoIzquierdaCallback(){}
    public void dobleGiroManoIzquierdaCallback(){}
    private boolean gestoGiroManoIzquierda(SensorEvent event){
        currenty=(int) event.values[1];
        if (currenty < 0 && Math.abs(Math.abs(currenty)-Math.abs(prevy)) > 8 && Math.abs(Math.abs(currenty)-Math.abs(prevy)) < 15){
            giroManoIzquierdaCallback();
            return true;
        }
        return false;
    }

    public void giroManoDerechaCallback(){}
    public void dobleGiroManoDerechaCallback(){}
    private boolean gestoGiroManoDerecha(SensorEvent event){
        currenty=(int) event.values[1];
        if (currenty > 0 && Math.abs(Math.abs(currenty)-Math.abs(prevy)) > 8 && Math.abs(Math.abs(currenty)-Math.abs(prevy)) < 15){
            giroManoDerechaCallback();
            return true;
        }
        return false;
    }

    //endregion

    //endregion

    //region Linear

    private int alcurrenty;
    private int alprevy;

    public void gestoArribaCallback(){}
    private boolean gestoParaArriba(SensorEvent event){
        alcurrenty=(int) event.values[1];
        if (alcurrenty > 0 && Math.abs(Math.abs(alcurrenty)-Math.abs(alprevy)) > 5 && Math.abs(Math.abs(alcurrenty)-Math.abs(alprevy)) < 10){
            gestoArribaCallback();
            return true;
        }
        return false;
    }

    public void gestoAbajoCallback(){}
    private boolean gestoParaAbajo(SensorEvent event){
        alcurrenty=(int) event.values[1];
        if (alcurrenty < 0 && Math.abs(Math.abs(alcurrenty)-Math.abs(alprevy)) > 5 && Math.abs(Math.abs(alcurrenty)-Math.abs(alprevy)) < 10){
            gestoAbajoCallback();
            return true;
        }
        return false;
    }
    //endregion
}




