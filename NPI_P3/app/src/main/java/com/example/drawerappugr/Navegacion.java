package com.example.drawerappugr;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.w3c.dom.Text;

public class Navegacion {

    private Spinner spinnerOrigen;
    private Spinner spinnerDestino;
    private Cursor cursor;

    private Button scannerButton;
    private Button initNavButton;
    private Button nextNode;
    private Button prevNode;
    private Button cancelNavButton;

    private String origen = new String();
    private String destino = new String();
    private TextView textInstrucionesPaso;
    private TextView textOrigen;
    private TextView textDestino;

    private View viewCursor;
    private View viewOpNav;

    private ActivityResultLauncher<ScanOptions> barLauncher;

    public Navegacion(AppCompatActivity activity){
        // Opciones del spinner (menu desplegable)
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(activity, R.array.lugares, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        // Conecto cada elemento con su layout
        spinnerOrigen = (Spinner) activity.findViewById(R.id.menuNavegacionOrigen);
        spinnerDestino = (Spinner) activity.findViewById(R.id.menuNavegacionDestino);
        scannerButton = (Button) activity.findViewById(R.id.scanbuttonav);
        initNavButton = (Button) activity.findViewById(R.id.inicarNav);
        cancelNavButton = (Button) activity.findViewById(R.id.cancelarNavegacion);
        prevNode = (Button) activity.findViewById(R.id.PrevNode);
        nextNode = (Button) activity.findViewById(R.id.NextNode);

        textInstrucionesPaso = (TextView) activity.findViewById(R.id.tvDestino);
        textOrigen = (TextView) activity.findViewById(R.id.textOrigen);
        textDestino = (TextView) activity.findViewById(R.id.textDestino);

        viewCursor = (View) activity.findViewById(R.id.viewCursor);
        viewOpNav = (View) activity.findViewById(R.id.viewOpcionesNav);

        // Destino y Origen por defecto
        origen="Entrada";
        destino="Entrada";
        textOrigen.setText(origen);
        textDestino.setText(destino);

        // Creo el menu desplegable
        spinnerDestino.setAdapter(adapter);
        spinnerOrigen.setAdapter(adapter);

        // Si eligen algo cargo el origen y el destino
        spinnerOrigen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                origen = parent.getItemAtPosition(position).toString();
                textOrigen.setText(origen);
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
                textDestino.setText(destino);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        // Inicia la flecha
        cursor= new Cursor(activity);

        // Para el scanner, que hace al detectar un QR
        barLauncher = activity.registerForActivityResult(new ScanContract(), result->{
            if(result.getContents() != null){
                // GUARDA EL TEXTO LEIDO AQUI
                origen=result.getContents();
                textOrigen.setText(origen);
            }
        });

        // Boton inicia escaner
        scannerButton.setOnClickListener(v->{
            scanCode();
        });

        // Boton inicia navegacion
        initNavButton.setOnClickListener(v ->{
            initNav();
        });

        // Boton anterior paso Navegación
        prevNode.setOnClickListener(v->{
            //
        });

        // Boton siguiente paso Navegacion
        nextNode.setOnClickListener(v->{
            //
        });

        // Boton cancelar navegacion
        cancelNavButton.setOnClickListener(v -> {
            cancelNav();
        });


    }

    private void cancelNav() {
        viewOpNav.setVisibility(View.VISIBLE);
        viewCursor.setVisibility(View.GONE);

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
        viewOpNav.setVisibility(View.GONE);
        viewCursor.setVisibility(View.VISIBLE);
        // Llamar funciones para iniciar navegación
    }
}
