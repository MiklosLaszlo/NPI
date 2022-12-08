package com.example.drawerappugr;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class GestosPantalla implements View.OnTouchListener{

    final private boolean doble_swipe_active, swipe_active, touch_active;
    private final static int UMBRAL_MILIS = 500;
    private float last_t;

    public GestosPantalla(boolean doble_swipe, boolean swipe, boolean touch){
        //super();
        doble_swipe_active = doble_swipe;
        swipe_active = swipe;
        touch_active = touch;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent){
        boolean consumido = false;

        if(motionEvent.getEventTime() - last_t > UMBRAL_MILIS) {
            if (doble_swipe_active) consumido = doubleSwipe(motionEvent);
            if (!consumido && swipe_active) consumido = swipe(motionEvent);
            if (!consumido && touch_active) consumido = touch(motionEvent);
        }

        return consumido || motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN || motionEvent.getActionMasked() == MotionEvent.ACTION_MOVE;
    }

    // region TOUCH

    public void touchUpCallback(){}
    public void touchDownCallback(){}

    private boolean touch(MotionEvent motionEvent) {

        int eventType = motionEvent.getActionMasked();
        boolean aceptado = false;

        switch(eventType)
        {
            case MotionEvent.ACTION_DOWN:
                touchDownCallback(); Log.i("touch","Action Down");
                aceptado=true;
                break;

            case MotionEvent.ACTION_UP:
                touchUpCallback(); Log.i("touch","Action Up");
                aceptado = true;
                break;

            case MotionEvent.ACTION_MOVE:
                //Log.i("SensorEvents","Action Move");
                break;

            //multitouch

            case MotionEvent.ACTION_POINTER_DOWN:
                //Log.i("SensorEvents","Action Pointer Down " + motionEvent.getPointerCount());
                break;

            case MotionEvent.ACTION_POINTER_UP:
                //Log.i("SensorEvents","Action Pointer Up " + motionEvent.getPointerCount());
                break;
        }
        return aceptado;
    }

    //endregion

    // region DOBLE SWIPE
    public enum direction {ARRIBA, ABAJO, IZQUIERDA, DERECHA, QUIETO}

    public void doubleSwipeCallback(direction dir){}

    private static final float UMBRAL_VELOCIDAD_SWIPE = (float) 600;

    private float x1 = -1;
    private float y1 = -1;
    private float x2 = -1;
    private float y2 = -1;
    private long t_ds = -1;

    private direction asignaDireccion(float dx1, float dx2, float dy1, float dy2, float time) {
        direction salida = direction.QUIETO;
        //Log.i("SPEED", "#dx1: " + dx1/time + " #dy1: " + dy1/time + " #dx2: " + dx2/time + " #dy2: " + dy2/time + " #time: " + time);

        boolean rapido_x = Math.abs(dx1) + Math.abs(dx2) >= 2 * UMBRAL_VELOCIDAD_SWIPE;
        boolean rapido_y = Math.abs(dy1) + Math.abs(dy2) >= 2 * UMBRAL_VELOCIDAD_SWIPE;

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

    private boolean doubleSwipe(MotionEvent ev) {
        direction dir;
        boolean aceptado = false;

        if (ev.getPointerCount() == 2) {
            if (ev.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN && t_ds == -1) { // Se clava el segundo dedo
                //Log.i("Doble Swipe", "Se clava");
                x1 = ev.getX(0);
                y1 = ev.getY(0);
                x2 = ev.getX(1);
                y2 = ev.getY(1);
                t_ds = ev.getEventTime();

                aceptado = true;
            }

            if (ev.getActionMasked() == MotionEvent.ACTION_POINTER_UP && t_ds != -1) { // Se levanta el segundo dedo
                //Log.i("Doble Swipe", "Se levanta");
                dir = asignaDireccion(
                        ev.getX(0) - x1,
                        ev.getX(1) - x2,
                        ev.getY(0) - y1,
                        ev.getY(1) - y2,
                        ev.getEventTime() - t_ds);
                doubleSwipeCallback(dir);
                t_ds = -1;
                t = -1; // Para reiniciar el swipe normal
                last_t = ev.getEventTime();
                aceptado = true;
            }
        }
        return aceptado;
    }

    //endregion

    // region SIMPLE SWIPE

    public void swipeCallback(direction dir){}

    int threshold = 100;
    int velocity_threshold=100;

    float x = -1;
    float y = -1;
    float t = -1;

    private boolean swipe(MotionEvent ev) {
        boolean aceptado = false;
        if(ev.getPointerCount() == 1) {
            if(ev.getActionMasked() == MotionEvent.ACTION_DOWN && t == -1){
                aceptado = true;
                x = ev.getX(0);
                y = ev.getY(0);
                t = ev.getEventTime();
            }
            if(ev.getActionMasked() == MotionEvent.ACTION_UP && t != -1){
                aceptado = true;
                float xDiff = ev.getX(0) - x;
                float YDiff = ev.getY(0) - y;
                float dt = (ev.getEventTime() - t) / 100;
                float velocityX = xDiff / dt;
                float velocityY = YDiff / dt;
                // Log.i("Swipe", "vx " + velocityX + " vy " + velocityY + " t " +dt);

                direction dir = direction.QUIETO;

                if(Math.abs(xDiff)> Math.abs(YDiff)){
                    if(Math.abs(xDiff)> threshold && Math.abs(velocityX ) > velocity_threshold){
                        if(xDiff>0){
                            dir = direction.DERECHA; //"Swipe a la derecha");
                        }else{
                            dir = direction.IZQUIERDA;//textView.setText("Swipe a la izquierda");
                        }
                    }
                }else{
                    if(Math.abs(YDiff)> threshold && Math.abs(velocityY)>velocity_threshold) {
                        if (YDiff > 0) {
                            dir = direction.ABAJO; // textView.setText("Swipe hacia abajo");
                        }
                        else {
                            dir = direction.ARRIBA; //textView.setText("Swipe hacia arriba");
                        }
                    }
                }
                swipeCallback(dir);
                t=-1;
                last_t = ev.getEventTime();
            }
        }
        return aceptado;
    }

    //endregion

}