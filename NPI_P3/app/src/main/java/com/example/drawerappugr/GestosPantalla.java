package com.example.drawerappugr;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public abstract class GestosPantalla implements View.OnTouchListener{

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent){
        doubleSwipe(motionEvent);
        swipe(motionEvent);
        touch(motionEvent);

        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) return true;
        return true;
    }

    // Gesto pantalla TOUCH

    public abstract void touchUpCallback();
    public abstract void touchDownCallback();

    public void touch(MotionEvent motionEvent) {

        int eventType = motionEvent.getActionMasked();

        switch(eventType)
        {
            case MotionEvent.ACTION_DOWN:
                touchDownCallback();//Log.i("SensorEvents","Action Down");
                break;

            case MotionEvent.ACTION_UP:
                touchUpCallback(); //Log.i("SensorEvents","Action Up");
                break;

            case MotionEvent.ACTION_MOVE:
                Log.i("SensorEvents","Action Move");
                break;

            //multitouch

            case MotionEvent.ACTION_POINTER_DOWN:
                Log.i("SensorEvents","Action Pointer Down " + motionEvent.getPointerCount());
                break;

            case MotionEvent.ACTION_POINTER_UP:
                Log.i("SensorEvents","Action Pointer Up " + motionEvent.getPointerCount());
                break;
        }
    }

    // Gesto pantalla DOBLE SWIPE

    public abstract void doubleSwipeCallback(direction dir);

    public enum direction {ARRIBA, ABAJO, IZQUIERDA, DERECHA, QUIETO}

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

    private void doubleSwipe(MotionEvent ev) {
        direction dir = direction.QUIETO;

        if (ev.getPointerCount() == 2) {
            if (ev.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN && t_ds == -1) { // Se clava el segundo dedo
                //Log.i("Doble Swipe", "Se clava");
                x1 = ev.getX(0);
                y1 = ev.getY(0);
                x2 = ev.getX(1);
                y2 = ev.getY(1);
                t_ds = ev.getEventTime();
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
            }
        }
    }

    // Gesto swipe simple

    public abstract void swipeCallback(direction dir);

    int threshold = 100;
    int velocity_threshold=100;

    float x = -1;
    float y = -1;
    float t = -1;

    private void swipe(MotionEvent ev) {
        if(ev.getPointerCount() == 1) {
            if(ev.getActionMasked() == MotionEvent.ACTION_DOWN && t == -1){
                x = ev.getX();
                y = ev.getY();
                t = ev.getEventTime();
            }
            if(ev.getActionMasked() == MotionEvent.ACTION_UP && t != -1){
                float xDiff = ev.getX() - x;
                float YDiff = ev.getY() - y;
                float velocityX = xDiff / (ev.getEventTime() - t);
                float velocityY = YDiff / (ev.getEventTime() - t);
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
            }
        }

    }

}

//Clases //
    /*private class SwipeListener implements View.OnTouchListener{
        GestureDetector gestureDetector;

        SwipeListener(View view){
            int threshold = 100;
            int velocity_threshold=100;

            GestureDetector.SimpleOnGestureListener listener=
                    new GestureDetector.SimpleOnGestureListener(){
                        @Override
                        public boolean onDown(MotionEvent e){
                            return true;
                        }

                        @Override
                        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                            float xDiff= e2.getX()- e1.getX();
                            float YDiff = e2.getY() - e1.getY();
                            try {
                                if(Math.abs(xDiff)> Math.abs(YDiff)){
                                    if(Math.abs(xDiff)> threshold && Math.abs(velocityX ) > velocity_threshold){
                                        if(xDiff>0){
                                            textView.setText("Swipe a la derecha");
                                        }else{
                                            textView.setText("Swipe a la izquierda");
                                        }
                                        return true;
                                    }
                                }else{
                                    if(Math.abs(YDiff)> threshold && Math.abs(velocityY)>velocity_threshold) {
                                        if (YDiff > 0) {
                                            textView.setText("Swipe hacia abajo");
                                        }
                                        else {
                                            textView.setText("Swipe hacia arriba");
                                        }
                                        return true;
                                    }
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            return false;
                        }
                    };
            gestureDetector =new GestureDetector(listener);
            view.setOnTouchListener(this);
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return gestureDetector.onTouchEvent(motionEvent);
        }
    }*/




