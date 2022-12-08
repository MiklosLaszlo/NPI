package com.example.drawerappugr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.google.android.material.navigation.NavigationView;
import android.widget.RelativeLayout;

@SuppressLint("ClickableViewAccessibility")
public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ViewFlipper viewFlipper;
    ConstraintLayout constraintLayout;

    // Sistema Horarios
    public static final Integer RecordAudioRequestCode = 1;

    ImplementaComedores implementaComedores;
    Navegacion navegacion;
    Horarios_bonitos horarios_bonitos;

    GestosSensor gestosSensor;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        constraintLayout = findViewById(R.id.base_layout);
        navigationView  = findViewById(R.id.navigationView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.menu_open,R.string.menu_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewFlipper = findViewById(R.id.flipper);
        viewFlipper.setDisplayedChild(0);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_inicio:
                        Log.i("MENU_DRAWER_TAG","Home item is clicked");
                        mostrarPantalla(0);
                        break;

                    case R.id.nav_horarios:
                        Log.i("MENU_DRAWER_TAG","Horarios item is clicked");
                        mostrarPantalla(1);
                        break;

                    case R.id.nav_comedores:
                        Log.i("MENU_DRAWER_TAG","Comedores item is clicked");
                        mostrarPantalla(2);
                        break;

                    case R.id.nav_navigation:
                        Log.i("MENU_DRAWER_TAG","Navegacion item is clicked");
                        mostrarPantalla(3);
                        break;

                    case R.id.nav_info:
                        Log.i("MENU_DRAWER_TAG","Info item is clicked");
                        mostrarPantalla(4);
                        break;

                }
                return true;
            }
        });

        creaGestosGenerales();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        }

        implementaComedores = new ImplementaComedores(this);
        navegacion = new Navegacion(this);
        horarios_bonitos = new Horarios_bonitos(this);
    }

    public void muestraAnterior(){
        mostrarPantalla((viewFlipper.getDisplayedChild() - 1) % viewFlipper.getChildCount());
    }
    public void muestraSiguiente(){
        mostrarPantalla( (viewFlipper.getDisplayedChild() + 1) % viewFlipper.getChildCount() );
    }

    private void mostrarPantalla(int i){
        drawerLayout.closeDrawer(GravityCompat.START);
        viewFlipper.setDisplayedChild(i);

        horarios_bonitos.descargar();
        implementaComedores.descargar();
        navegacion.descargar();

        switch (i) {
            case 1:
                horarios_bonitos.cargar(); break;
            case 2:
                implementaComedores.cargar(); break;
            case 3:
                navegacion.cargar(); break;
            default:
                break;
        }
    }

    private void creaGestosGenerales(){
        gestosSensor = new GestosSensor(this, true, false, false, false, true){
            @Override
            public void dobleGiroManoIzquierdaCallback() {muestraAnterior();}
            @Override
            public void dobleGiroManoDerechaCallback() {muestraSiguiente();}
            @Override
            public void acceleIzqCallback() {muestraAnterior();}
            @Override
            public void acceleDerCallback() {muestraSiguiente();}
        };
        gestosSensor.registerListener();

        drawerLayout.setOnTouchListener(new GestosPantalla(true,false,false){
            @Override
            public void doubleSwipeCallback(direction dir) {
                if(dir == direction.DERECHA || dir==direction.ABAJO){
                    muestraAnterior();
                }
                if(dir == direction.IZQUIERDA || dir==direction.ARRIBA){
                    muestraSiguiente();
                }
            }
        });
    }

    public void cargar(){
        gestosSensor.registerListener();
        switch (viewFlipper.getDisplayedChild()){
            case 1:
                horarios_bonitos.cargar(); break;
            case 2:
                implementaComedores.cargar(); break;
            case 3:
                navegacion.cargar(); break;
        }
    }
    public void descargar(){
        gestosSensor.unregisterListener();
        horarios_bonitos.descargar();
        implementaComedores.descargar();
        navegacion.descargar();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO}, RecordAudioRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0 ){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        navegacion.onResume();
        cargar();
    }

    @Override
    public void onPause(){
        super.onPause();
        navegacion.onPause();
        descargar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        navegacion.onDestroy();
    }

}