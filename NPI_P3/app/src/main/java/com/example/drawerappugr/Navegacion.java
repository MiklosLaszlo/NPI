package com.example.drawerappugr;

import android.app.Activity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class Navegacion {

    private Spinner spinnerOrigen;
    private Spinner spinnerDestino;
    private Cursor cursor;
    private Button scannerButton;
    private Button initNavButton;
    private Button nextNode;
    private Button prevNode;
    private String origen = new String();
    private String destino = new String();
    private View viewCursor;
    private View layoutBotones;
    private ActivityResultLauncher<ScanOptions> barLauncher;

    public Navegacion(AppCompatActivity activity){
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(activity, R.array.lugares, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerOrigen = (Spinner) activity.findViewById(R.id.menuNavegacionOrigen);
        spinnerDestino = (Spinner) activity.findViewById(R.id.menuNavegacionDestino);
        scannerButton = (Button) activity.findViewById(R.id.scanbuttonav);
        initNavButton = (Button) activity.findViewById(R.id.inicarNav);
        viewCursor = (View) activity.findViewById(R.id.layoiutCursor);
        layoutBotones = (View) activity.findViewById(R.id.pasosNav);
        prevNode = (Button) activity.findViewById(R.id.PrevNode);
        nextNode = (Button) activity.findViewById(R.id.NextNode);


        spinnerDestino.setAdapter(adapter);
        spinnerOrigen.setAdapter(adapter);


        spinnerOrigen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                origen = parent.getItemAtPosition(position).toString();
                Log.e("Origen nav",origen);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        spinnerDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                destino = parent.getItemAtPosition(position).toString();
                Log.e("Origen nav",destino);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        cursor= new Cursor(activity);
        barLauncher = activity.registerForActivityResult(new ScanContract(), result->{
            if(result.getContents() != null){
                // GUARDA EL TEXTO LEIOD AQUI
                origen=result.getContents();
            }
        });
        scannerButton.setOnClickListener(v->{
            scanCode();
        });

        initNavButton.setOnClickListener(v ->{
            initNav();
        });
        prevNode.setOnClickListener(v->{
            //
        });
        nextNode.setOnClickListener(v->{
            //
        });


    }

    public void onResume() {
        cursor.onResume();
    }
    public void onPause() {
        cursor.onPause();
    }

    public void onDestroy() {
        cursor.onDestroy();
    }

    private void scanCode() {
        ScanOptions options=new ScanOptions();
        options.setPrompt("Busca QR");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    private void initNav(){
        viewCursor.setVisibility(View.VISIBLE);
        // Llamar funciones para iniciar navegación
    }
}
