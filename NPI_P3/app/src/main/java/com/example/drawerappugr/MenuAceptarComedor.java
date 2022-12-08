package com.example.drawerappugr;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;

@SuppressLint("SetTextI18n")
public class MenuAceptarComedor {
    final private Button btn_aceptar;
    final private Button btn_cancelar;
    final private TextView tvDescripcion;

    final private ScrollView scrollView;
    final private LinearLayout layout;
    private CountDownTimer contador;

    GestosSensor gestosSensor;

    String dia;
    String vegano;

    Activity activity;
    ImplementaComedores implementaComedores;

    @SuppressLint("ClickableViewAccessibility")
    MenuAceptarComedor(@NonNull Activity ac, ImplementaComedores padre){
        activity = ac;
        implementaComedores = padre;

        layout = ac.findViewById(R.id.AceptarComedores);
        scrollView = ac.findViewById(R.id.Comedores);

        btn_aceptar = ac.findViewById(R.id.btnAceptarCompra);
        btn_cancelar = ac.findViewById(R.id.btnCancelarCompra);
        tvDescripcion = ac.findViewById(R.id.tvDescripcionCompra);

        tvDescripcion.setText("");
        layout.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);

        btn_aceptar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {aceptar(); return false;}
        });

        btn_cancelar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {rechazar(); return false;}
        });

        gestosSensor = new GestosSensor(ac.getApplicationContext(), true, false, false, false, false){
            @Override
            public void gestoAceptarCallback() {aceptar();}

            @Override
            public void gestoRechazarCallback() {rechazar();}
        };
    }

    public void aceptar(){
        btn_aceptar.setVisibility(View.GONE);
        btn_cancelar.setVisibility(View.GONE);
        tvDescripcion.setText("Reserva realizada");
        gestosSensor.unregisterListener();

        contador = new CountDownTimer(1500, 1) {
            @Override
            public void onTick(long l) {}

            @Override
            public void onFinish() {
                layout.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                implementaComedores.cargar();
            }
        };
        contador.start();
    }

    public void rechazar(){
        gestosSensor.unregisterListener();
        btn_aceptar.setVisibility(View.GONE);
        btn_cancelar.setVisibility(View.GONE);
        tvDescripcion.setText("Se ha cancelado la reserva");
        contador = new CountDownTimer(1500, 1) {
            @Override
            public void onTick(long l) {}

            @Override
            public void onFinish() {
                layout.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                implementaComedores.cargar();
            }
        };
        contador.start();
    }
    public void aparecer(String dia_, String vegano_){
        dia = dia_; vegano = vegano_;
        implementaComedores.descargar();
        btn_aceptar.setVisibility(View.VISIBLE);
        btn_cancelar.setVisibility(View.VISIBLE);
        tvDescripcion.setText("¿Seguro que quiere encargar el menú " + vegano + "\npara el día " + dia + "?");
        scrollView.setVisibility(View.GONE);
        layout.setVisibility(View.VISIBLE);
        gestosSensor.registerListener();
    }
}
