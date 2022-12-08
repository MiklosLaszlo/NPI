package com.example.drawerappugr;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;

@SuppressLint("ClickableViewAccessibility")
public class Navegacion {

    private GestosSensor gestosSensor;
    // private GestosSensor gestosSensorNavega;
    private final MainActivity activity;
    private final LinearLayout linearLayout;
    private final Horarios h = new Horarios();

    private ArrayList<Node> camino = new ArrayList<Node>();;

    private Spinner spinnerOrigen;
    private Spinner spinnerDestino;
    private Cursor cursor;

    private Button scannerButton;
    private Button initNavButton;
    private final Button siguienteBtn;
    private Button nextNode;
    private Button prevNode;
    private Button cancelNavButton;

    private String origen = new String();
    private String destino = new String();
    private TextView textInstrucionesPaso;
    private TextView textOrigen;
    private TextView textDestino;
    private final TextView haLlegado;

    private View viewCursor;
    private View viewOpNav;

    private Lugares lugares;
    private int pos_actual;

    private ActivityResultLauncher<ScanOptions> barLauncher;

    public Navegacion(MainActivity activity){
        this.activity = activity;
        linearLayout = activity.findViewById(R.id.navegacion_layout);
        // Opciones del spinner (menu desplegable)
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(activity, R.array.lugares, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        // Conecto cada elemento con su layout
        spinnerOrigen = (Spinner) activity.findViewById(R.id.menuNavegacionOrigen);
        spinnerDestino = (Spinner) activity.findViewById(R.id.menuNavegacionDestino);
        scannerButton = (Button) activity.findViewById(R.id.scanbuttonav);
        initNavButton = (Button) activity.findViewById(R.id.inicarNav);
        siguienteBtn = activity.findViewById(R.id.siguienteClase);
        cancelNavButton = (Button) activity.findViewById(R.id.cancelarNavegacion);
        prevNode = (Button) activity.findViewById(R.id.PrevNode);
        nextNode = (Button) activity.findViewById(R.id.NextNode);

        textInstrucionesPaso = (TextView) activity.findViewById(R.id.tvDestino);
        textOrigen = (TextView) activity.findViewById(R.id.textOrigen);
        textDestino = (TextView) activity.findViewById(R.id.textDestino);
        haLlegado = activity.findViewById(R.id.tvLlegada);

        viewCursor = (View) activity.findViewById(R.id.viewCursor);
        viewOpNav = (View) activity.findViewById(R.id.viewOpcionesNav);

        // Destino y Origen por defecto
        siguienteClase();

        // Creo el menu desplegable
        spinnerDestino.setAdapter(adapter);
        spinnerOrigen.setAdapter(adapter);

        lugares = new Lugares();

        //Node algo = lugares.printCamino("Entrada Principal","Aulas 3.x").get(0);
        //Log.e("PRUEBA A AA A" , algo.getString());

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
                spinnerOrigen.setSelection(sitios.indexOf(origen));
                textOrigen.setText(origen);
            }
        });

        // Boton inicia escaner
        scannerButton.setOnTouchListener(new GestosPantalla(false,false, true){
            @Override
            public void touchDownCallback() {scanCode();}
        });

        // Boton inicia navegacion
        initNavButton.setOnTouchListener(new GestosPantalla(false,false, true){
            @Override
            public void touchDownCallback() {initNav();}
        });

        // Boton anterior paso Navegación
        prevNode.setOnTouchListener(new GestosPantalla(false,false, true){
            @Override
            public void touchDownCallback() {ponerPaso(pos_actual-1);}
        });

        // Boton siguiente paso Navegacion
        nextNode.setOnTouchListener(new GestosPantalla(false,false, true){
            @Override
            public void touchDownCallback() {ponerPaso(pos_actual+1);}
        });

        // Boton cancelar navegacion
        cancelNavButton.setOnTouchListener(new GestosPantalla(false,false, true){
            @Override
            public void touchDownCallback()  {cancelNav();}
        });

        siguienteBtn.setOnTouchListener(new GestosPantalla(false, false, true){
            @Override
            public void touchDownCallback() {siguienteClase();}
        });

        creaGestosGenerales();

    }

    private void cancelNav() {
        viewOpNav.setVisibility(View.VISIBLE);
        viewCursor.setVisibility(View.GONE);
        camino = new ArrayList<Node>();
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
        haLlegado.setVisibility(View.INVISIBLE);
        pos_actual = 0;

        // Log.e("INITNAV", pasarAString(origen) +"|"+pasarAString(destino));

        if(origen.equals(destino)) {
            camino = new ArrayList<Node>();
        }
        else {
            camino = lugares.printCamino(pasarAString(origen), pasarAString(destino)); //"Aulas 1.x", "Entrada Principal");
        }

        viewOpNav.setVisibility(View.GONE);
        viewCursor.setVisibility(View.VISIBLE);

        ponerPaso(0);
        // Llamar funciones para iniciar navegación
    }

    private void creaGestosGenerales(){
        linearLayout.setOnTouchListener(new GestosPantalla(true, true, false){
            @Override
            public void doubleSwipeCallback(direction dir) {
                switch (dir) {
                    case ARRIBA:
                    case IZQUIERDA:
                        cancelNav();
                        activity.muestraSiguiente(); break;
                    case ABAJO:
                    case DERECHA:
                        cancelNav();
                        activity.muestraAnterior(); break;
                }
            }

            @Override
            public void swipeCallback(direction dir) {
                switch (dir) {
                    case IZQUIERDA:
                        if(!camino.isEmpty()) ponerPaso(pos_actual + 1);
                    case DERECHA:
                        if(!camino.isEmpty()) ponerPaso(pos_actual - 1);
                }
            }
        });

        viewCursor.setOnTouchListener(new GestosPantalla(true, true, false){
            @Override
            public void swipeCallback(direction dir) {
                switch (dir) {
                    case IZQUIERDA:
                        ponerPaso(pos_actual+1);
                    case DERECHA:
                        ponerPaso(pos_actual-1);
                }
            }
        });

        gestosSensor = new GestosSensor(activity.getApplicationContext(), true, true, false, true, false){
            @Override
            public void giroManoIzquierdaCallback() {
                if(!camino.isEmpty()){ponerPaso(pos_actual-1);}
            }
            @Override
            public void giroManoDerechaCallback() {ponerPaso(pos_actual+1);}
            @Override
            public void proximidadCallback() {cancelNav();}
            @Override
            public void gestoAceptarCallback() {
                if(camino.isEmpty()){initNav();}
            }
            @Override
            public void gestoRechazarCallback() {
                if(!camino.isEmpty()){cancelNav();}
            }
        };
    }

    public void cargar(){ gestosSensor.registerListener(); }
    public void descargar(){ gestosSensor.unregisterListener(); }

    private static ArrayList<String> sitios = new ArrayList<String>();
    static {
        sitios.add("Entrada");
        sitios.add("Comedor");
        sitios.add("1.2");
        sitios.add("3.3");
        sitios.add("3.9");
    }

    private void siguienteClase(){
        String actual = h.getAula();
        String siguiente = h.getNextAula();

        origen = actual == null ? "Entrada" : actual;
        destino = siguiente == null ? "Entrada" : siguiente;

        spinnerOrigen.setSelection(sitios.indexOf(origen));
        spinnerDestino.setSelection(sitios.indexOf(destino));
        textOrigen.setText(origen);
        textDestino.setText(destino);
    }

    private String pasarAString(String cosa) { // La entrada es por ejemplo 0.1 o 2.8 o entrada.
        Log.e("Pasar", cosa + " " +cosa.length());
        if(cosa.equals("Entrada"))
            return "Entrada Principal";
        else if(cosa.length()==3 ){
            char planta = cosa.charAt(0);
            return ("Aulas " + planta + ".x").toString();
        }
        else if(cosa.length() == 8){
            char planta = cosa.charAt(5);
            return ("Aulas " + planta + ".x").toString();
        }
        else
            return cosa;
    }

    private float aulaAIzquierda(String nombre){
        float extra = 0;
        if(nombre.length() == "Aulas 0.x".length()){ // Es tipo aula
            extra = (Character.valueOf(nombre.charAt(7)) <= 8) ? 180 : 0;
        }
        return extra;
    }

    private float orientacionSiguiente(Node n1, Node n2){
        float grados = n1.getCoordenadas().get( n1.getAdyacentes().indexOf( n2 ) ) + aulaAIzquierda(n1.data) + aulaAIzquierda(n2.data);

        return grados;
    }

    private String direccionesSiguiente(Node n1, Node n2){
        return n1.getDirecciones().get( n1.getAdyacentes().indexOf( n2 ) );
    }

    private void ponerPaso(int pos){
        Log.i("Poner paso", camino.size()+"");
        if(pos < 0){
            pos_actual = 0;
        }
        else if(camino.size() == 1){
            mismoPasillo();
        }
        else if(pos >= camino.size() - 1){
            pos_actual = camino.size();
            llegadaAlDestino();
        }
        else{
            pos_actual = pos;
            Node actual = camino.get(pos);
            Node siguiente = camino.get(pos + 1);

            cursor.headTo(orientacionSiguiente(actual, siguiente));
            textInstrucionesPaso.setText(direccionesSiguiente(actual, siguiente));
        }
    }

    private void mismoPasillo(){
        cancelNav();
        haLlegado.setText("Está en la misma planta. Recorra el pasillo hasta el aula.");
        haLlegado.setVisibility(View.VISIBLE);
    }

    private void llegadaAlDestino(){
        cancelNav();
        haLlegado.setText("Ha llegado a su destino");
        haLlegado.setVisibility(View.VISIBLE);
    }
}
