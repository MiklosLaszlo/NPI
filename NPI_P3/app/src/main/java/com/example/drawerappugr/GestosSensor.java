package com.example.drawerappugr;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.util.Log;

public class GestosSensor implements SensorEventListener {

    //region COMUN

    private final SensorManager mSensorManager;
    private final Sensor mGiroscope;
    private final Sensor mLinearAcelerometer;
    private final Sensor mRotation;
    private final Sensor mProximity;
    private final Sensor mAccelerometer;

    private final CountDownTimer count;
    private GestosAprendidos gestoReconocido = GestosAprendidos.DESCONOCIDO;
    private boolean listening = true;
    private boolean second_listening = false;

    private boolean directo = true;

    public GestosSensor(Context context, boolean giroscope, boolean linear, boolean rotation, boolean proximity, boolean accelerometer) {
        // this.context = context;
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (giroscope)
            mGiroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        else
            mGiroscope = null;

        if (linear)
            mLinearAcelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        else
            mLinearAcelerometer = null;

        if (rotation)
            mRotation = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        else
            mRotation = null;

        if (proximity)
            mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        else
            mProximity = null;

        if (accelerometer)
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        else
            mAccelerometer = null;

        count = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long l) {
                /*if ((gestoReconocido == GestosAprendidos.GIRODERECHA || gestoReconocido == GestosAprendidos.GIROIZQUIERDA) && (contando != 0)) {
                    second_listening = true;
                }
                contando = contando + 1;*/
            }

            @Override
            public void onFinish() {
                if(!directo)
                    ejecutarGesto();
                listening = true;
                second_listening = false;
                gestoReconocido = GestosAprendidos.DESCONOCIDO;
                Log.i("CONTADOR", "Finalizado");
            }
        };
    }

    private boolean hasGiroscopeSensor() {
        return mGiroscope != null;
    }

    private boolean hasLinearAcelerometerSensor() {
        return mLinearAcelerometer != null;
    }

    private boolean hasRotationSensor() {
        return mRotation != null;
    }

    private boolean hasProximitySensor() {
        return mProximity != null;
    }

    private boolean hasAccelerometerSensor() {
        return mAccelerometer != null;
    }

    public void registerListener() {
        if (hasGiroscopeSensor())
            mSensorManager.registerListener(this, mGiroscope, SensorManager.SENSOR_DELAY_NORMAL);
        if (hasLinearAcelerometerSensor())
            mSensorManager.registerListener(this, mLinearAcelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        if (hasRotationSensor())
            mSensorManager.registerListener(this, mRotation, SensorManager.SENSOR_DELAY_NORMAL);
        if (hasProximitySensor())
            mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
        if (hasAccelerometerSensor())
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregisterListener() {
        mSensorManager.unregisterListener(this);
    }

    private GestosAprendidos reconocerGestos(SensorEvent event) {
        GestosAprendidos gesto = GestosAprendidos.DESCONOCIDO;
        switch (event.sensor.getType()) {
            case Sensor.TYPE_GYROSCOPE:
                if (gestoAceptar(event))
                    gesto = GestosAprendidos.ACEPTAR;
                else if (gestoRechazar(event))
                    gesto = GestosAprendidos.CANCELAR;
                else if (gestoGiroManoIzquierda(event))
                    gesto = GestosAprendidos.GIROIZQUIERDA;
                else if (gestoGiroManoDerecha(event))
                    gesto = GestosAprendidos.GIRODERECHA;

                actualizaValoresGiros(event.values[1]);
                break;

            case Sensor.TYPE_LINEAR_ACCELERATION:
                if (gestoParaArriba(event))
                    gesto = GestosAprendidos.ARRIBA;
                else if (gestoParaAbajo(event))
                    gesto = GestosAprendidos.ABAJO;

                actualizaValoresLin(event.values[1]);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                if (gestoAcceleDer(event))
                    gesto = GestosAprendidos.ACCELEDER;
                else if (gestoAcceleIzq(event))
                    gesto = GestosAprendidos.ACCELEIZQ;
                break;
            case Sensor.TYPE_PROXIMITY:
                if(gestoProximidad(event))
                    gesto = GestosAprendidos.PROXIMIDAD;
                break;
        }

        return gesto;
    }

    private boolean esDoble(GestosAprendidos g1){
        return g1 == GestosAprendidos.GIROIZQUIERDA || g1==GestosAprendidos.GIRODERECHA;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (listening) {
            gestoReconocido = reconocerGestos(sensorEvent);
            if (gestoReconocido != GestosAprendidos.DESCONOCIDO) {
                listening = false;
                directo = !esDoble(gestoReconocido);
                if(directo) {
                    Log.e("directo", "es directo");
                    ejecutarGesto();
                }
                else
                    second_listening = true;

                count.start();

            }
        } else if (second_listening) {
            switch (reconocerGestos(sensorEvent)) {
                case GIROIZQUIERDA:
                    if (gestoReconocido == GestosAprendidos.GIROIZQUIERDA)
                        gestoReconocido = GestosAprendidos.DOSGIROIZQUIERDA;
                    break;
                case GIRODERECHA:
                    if (gestoReconocido == GestosAprendidos.GIRODERECHA)
                        gestoReconocido = GestosAprendidos.DOSGIRODERECHA;
                    break;
            }
        }

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            rotationCallback(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    private void ejecutarGesto() {
        switch (gestoReconocido) {
            case GIROIZQUIERDA:
                Log.i("Gesto", "Uno izquierda");
                giroManoIzquierdaCallback();
                break;
            case GIRODERECHA:
                Log.i("Gesto", "Uno derecha");
                giroManoDerechaCallback();
                break;
            case DOSGIROIZQUIERDA:
                Log.i("Gesto", "dos izquierda");
                dobleGiroManoIzquierdaCallback();
                break;
            case DOSGIRODERECHA:
                dobleGiroManoDerechaCallback();
                Log.i("Gesto", "dos derecha");
                break;
            case ARRIBA:
                gestoArribaCallback();
                Log.i("Gesto", "arriba");
                break;
            case ABAJO:
                gestoAbajoCallback();
                Log.i("Gesto", "abajo");
                break;
            case ACEPTAR:
                gestoAceptarCallback();
                Log.i("Gesto", "aceptar");
                break;
            case CANCELAR:
                gestoRechazarCallback();
                Log.i("Gesto", "cancelar");
                break;
            case ACCELEIZQ:
                Log.i("Gesto", "ACCELEIZQ");
                acceleIzqCallback();
                break;
            case ACCELEDER:
                Log.i("Gesto", "ACCELEDER");
                acceleDerCallback();
                break;
            case PROXIMIDAD:
                Log.i("Gesto", "PROXIMIDAD");
                proximidadCallback();
        }
    }

    //endregion

    //region Rotacion

    public void rotationCallback(float rx, float ry, float rz) {
    }

    //endregion

    //region Proximidad

    public void proximidadCallback() {}

    private boolean gestoProximidad(SensorEvent sensorEvent) {
        return (sensorEvent.values[0] < 3);
    }

    //endregion

    //region Giroscopio

    // region Gesto aceptar y rechazar

    private long t_girosc_a = -1;
    private double last_x_a = -1;
    private double last_y_a = -1;
    private double last_z_a = -1;

    private boolean anterior = false;

    private static final float U_ACEPTAR_X_INF = 250;
    private static final float U_ACEPTAR_X_SUP = 450;
    private static final float U_ACEPTAR_Y = 70;
    private static final float U_ACEPTAR_Z = 70;

    public void gestoAceptarCallback() {    }

    private boolean haceGestoAceptar(double dx, double dy, double dz, float time) {
        boolean aceptar_x, aceptar_y, aceptar_z;

        aceptar_y = Math.abs(dy) < U_ACEPTAR_Y;
        aceptar_z = Math.abs(dz) < U_ACEPTAR_Z;
        aceptar_x = /*dx > 0 &&*/ U_ACEPTAR_X_INF < dx && dx < U_ACEPTAR_X_SUP;

        //Log.e("aceptar", "dx " + dx + " dy " + Math.abs(dy ) + " dz " + Math.abs(dz));
        //Log.e("aceptar", "x " + aceptar_x + " dy " + aceptar_y + " dz " + aceptar_z + ((aceptar_x && aceptar_y && aceptar_z) ? " TRUE" : " FALSE"));
        //Log.i("aceptar", (aceptar_x && aceptar_y && aceptar_z) ? "TRUE" : "FALSE");

        return aceptar_x && aceptar_y && aceptar_z;
    }

    private boolean gestoAceptar(SensorEvent ev) {
        boolean hecho = false;
        if (last_x_a != -1) {
            boolean gesto = haceGestoAceptar(
                    Math.toDegrees(ev.values[0]) - last_x_a,
                    Math.toDegrees(ev.values[1]) - last_y_a,
                    Math.toDegrees(ev.values[2]) - last_z_a,
                    ev.timestamp - t_girosc_a
            );

            if (!gesto && anterior) anterior = false;
            hecho = gesto && !anterior;
        }

        last_x_a = Math.toDegrees(ev.values[0]);
        last_y_a = Math.toDegrees(ev.values[1]);
        last_z_a = Math.toDegrees(ev.values[2]);
        t_girosc_a = ev.timestamp;

        return hecho;
    }

    //Rechazar
    private long t_girosc_r = -1;
    private double last_x_r = -1;
    private double last_y_r = -1;
    private double last_z_r = -1;

    private boolean anterior_r = false;

    private static final float U_RECHAZAR_X_INF = 250;
    private static final float U_RECHAZAR_X_SUP = 450;
    private static final float U_RECHAZAR_Y = 70;
    private static final float U_RECHAZAR_Z = 70;

    public void gestoRechazarCallback() {    }

    private boolean haceGestoRechazar(double dx, double dy, double dz, float time) {
        boolean aceptar_x, aceptar_y, aceptar_z;

        aceptar_y = Math.abs(dy) < U_RECHAZAR_Y;
        aceptar_z = Math.abs(dz) < U_RECHAZAR_Z;
        aceptar_x = dx < 0 && U_RECHAZAR_X_INF < Math.abs(dx) && Math.abs(dx) < U_RECHAZAR_X_SUP;

        //Log.e("aceptar", "dx " + dx + " dy " + Math.abs(dy ) + " dz " + Math.abs(dz));
        //Log.e("aceptar", "x " + aceptar_x + " dy " + aceptar_y + " dz " + aceptar_z + ((aceptar_x && aceptar_y && aceptar_z) ? " TRUE" : " FALSE"));
        //Log.i("aceptar", (aceptar_x && aceptar_y && aceptar_z) ? "TRUE" : "FALSE");

        return aceptar_x && aceptar_y && aceptar_z;
    }

    private boolean gestoRechazar(SensorEvent ev) {
        boolean hecho = false;
        if (last_x_r != -1) {
            boolean gesto = haceGestoRechazar(
                    Math.toDegrees(ev.values[0]) - last_x_r,
                    Math.toDegrees(ev.values[1]) - last_y_r,
                    Math.toDegrees(ev.values[2]) - last_z_r,
                    ev.timestamp - t_girosc_a
            );

            if (!gesto && anterior_r) anterior_r = false;
            hecho = gesto && !anterior_r;
        }

        last_x_r = Math.toDegrees(ev.values[0]);
        last_y_r = Math.toDegrees(ev.values[1]);
        last_z_r = Math.toDegrees(ev.values[2]);
        t_girosc_a = ev.timestamp;

        return hecho;
    }

    //endregion

    //region giros

    private void actualizaValoresGiros(float y){
        prevy = (int) y;
    }

    private int currenty = 0;
    private int prevy = 0;

    //private float currentVel;
    //private float prevVel;

    public void giroManoIzquierdaCallback() {
    }

    public void dobleGiroManoIzquierdaCallback() {
    }

    private boolean gestoGiroManoIzquierda(SensorEvent event) {
        currenty = (int) event.values[1];
        //Log.i("GiroIzq", currenty + " | " +Math.abs(Math.abs(currenty) - Math.abs(prevy)) + " -> "
        //       + (currenty < 0) + " "+(Math.abs(Math.abs(currenty) - Math.abs(prevy)) >= 3) + " "+(Math.abs(Math.abs(currenty) - Math.abs(prevy)) < 15) + " ");
        // He cambiado > 5 por >= 4
        if (currenty < 0 && Math.abs(Math.abs(currenty) - Math.abs(prevy)) >= 3 && Math.abs(Math.abs(currenty) - Math.abs(prevy)) < 15) {
            Log.e("GiroIzq", "aceptado");
            return true;
        }
        return false;
    }

    public void giroManoDerechaCallback() {
    }

    public void dobleGiroManoDerechaCallback() {
    }

    private boolean gestoGiroManoDerecha(SensorEvent event) {
        currenty = (int) event.values[1];
        if (currenty > 0 && Math.abs(Math.abs(currenty) - Math.abs(prevy)) >= 3 && Math.abs(Math.abs(currenty) - Math.abs(prevy)) < 15) {
            //giroManoDerechaCallback();
            return true;
        }
        return false;
    }

    //endregion

    //endregion

    //region Linear

    private int alcurrenty = 0;
    private int alprevy = 0;

    private void actualizaValoresLin(float y){
        alprevy = (int) y;
    }

    public void gestoArribaCallback() {
    }

    private boolean gestoParaArriba(SensorEvent event) {
        alcurrenty = (int) event.values[1];
        //Log.i("Arriba", alcurrenty + " | " +Math.abs(Math.abs(alcurrenty) - Math.abs(alprevy)) + " -> "
        //        + (alcurrenty > 0) + " "+(Math.abs(Math.abs(alcurrenty) - Math.abs(alprevy)) >= 4) + " "+(Math.abs(Math.abs(alcurrenty) - Math.abs(alprevy)) < 10) + " ");
        // Donde pone >= 4 antes era > 5
        if (alcurrenty > 0 && Math.abs(Math.abs(alcurrenty) - Math.abs(alprevy)) >= 4 && Math.abs(Math.abs(alcurrenty) - Math.abs(alprevy)) < 10) {
            return true;
        }
        return false;
    }

    public void gestoAbajoCallback() {
    }

    private boolean gestoParaAbajo(SensorEvent event) {
        alcurrenty = (int) event.values[1];
        if (alcurrenty < 0 && Math.abs(Math.abs(alcurrenty) - Math.abs(alprevy)) >= 4 && Math.abs(Math.abs(alcurrenty) - Math.abs(alprevy)) < 10) {
            //gestoAbajoCallback();
            return true;
        }
        return false;
    }
    //endregion

    //region Acelerometro

    public void acceleIzqCallback(){}
    private boolean gestoAcceleIzq(SensorEvent sensorEvent){
        float x=sensorEvent.values[0];
        float y=sensorEvent.values[1];
        float z=sensorEvent.values[2];
        //Moviendo el movil hacia la derecha
        return x< -10;
    }

    public void acceleDerCallback(){}
    private boolean gestoAcceleDer(SensorEvent sensorEvent){
        float x=sensorEvent.values[0];
        float y=sensorEvent.values[1];
        float z=sensorEvent.values[2];
        //Moviendo el movil hacia la derecha
        return x > 10;
    }

    //endregion
}




