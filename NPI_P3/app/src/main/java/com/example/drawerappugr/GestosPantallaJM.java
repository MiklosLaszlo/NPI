package com.example.drawerappugr;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.View;

public class GestosPantallaJM {

    // Gesto pantalla DOBLE SWIPE

    public interface Callback {
        boolean cb(direction dir);
    }

    public enum direction {ARRIBA, ABAJO, IZQUIERDA, DERECHA, QUIETO}

    private static final float UMBRAL_VELOCIDAD_SWIPE = (float) 600;

    private float x1 = -1;
    private float y1 = -1;
    private float x2 = -1;
    private float y2 = -1;
    private long t = -1;

    public boolean onTouchCallback(View view, MotionEvent ev, Callback cb) {
        double_swipe(ev, cb);
        if (ev.getAction() == MotionEvent.ACTION_DOWN) return true;
        return true;
    }

    private direction asignaDireccion(float dx1, float dx2, float dy1, float dy2, float time) {
        //Log.i("asigna", "#dx1: " + dx1 + " #dy1: " + dy1 + " #dx2: " + dx2 + " #dy2: " + dy2 + " #time: " + time);
        direction salida = direction.QUIETO;
        //Log.i("SPEED", "#dx1: " + dx1/time + " #dy1: " + dy1/time + " #dx2: " + dx2/time + " #dy2: " + dy2/time + " #time: " + time);

        boolean rapido_x = Math.abs(dx1) + Math.abs(dx2) >= 2 * UMBRAL_VELOCIDAD_SWIPE;
        boolean rapido_y = Math.abs(dy1) + Math.abs(dy2) >= 2 * UMBRAL_VELOCIDAD_SWIPE;

        //Log.e("bool", rapido_x + " " + rapido_y);
        //Log.e("bool", Math.abs(dx1) + Math.abs(dx2) + " " + Math.abs(dy1) + Math.abs(dy2) + " " + 2*UMBRAL_VELOCIDAD_SWIPE);

        if (rapido_x && !rapido_y) {
            if (dx1 > 0 && dx2 > 0) salida = direction.DERECHA;
            if (dx1 < 0 && dx2 < 0) salida = direction.IZQUIERDA;
        }

        if (rapido_y && !rapido_x) {
            if (dy1 > 0 && dy2 > 0) salida = direction.ABAJO;
            if (dy1 < 0 && dy2 < 0) salida = direction.ARRIBA;
        }

        //Log.i("direccion", salida.toString());
        return salida;
    }

    private void double_swipe(MotionEvent ev, Callback cb) {
        direction dir = direction.QUIETO;

        if (ev.getPointerCount() == 2) {
            if (ev.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN && t == -1) { // Se clava el segundo dedo
                //Log.i("Doble Swipe", "Se clava");
                x1 = ev.getX(0);
                y1 = ev.getY(0);
                x2 = ev.getX(1);
                y2 = ev.getY(1);
                t = ev.getEventTime();
            }

            if (ev.getActionMasked() == MotionEvent.ACTION_POINTER_UP) { // Se levanta el segundo dedo
                //Log.i("Doble Swipe", "Se levanta");

                if (t != -1) {
                    dir = asignaDireccion(
                            ev.getX(0) - x1,
                            ev.getX(1) - x2,
                            ev.getY(0) - y1,
                            ev.getY(1) - y2,
                            ev.getEventTime() - t);
                    cb.cb(dir);
                }
                t = -1;
            }
        }
    }

}
