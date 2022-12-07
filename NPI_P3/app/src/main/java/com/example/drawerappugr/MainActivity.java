package com.example.drawerappugr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import android.Manifest;
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

public class MainActivity extends AppCompatActivity {
    // Multi menús
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ViewFlipper viewFlipper;

    // Mis variables //
    RelativeLayout relativeLayout;
    TextView textView;

    private Spinner spinnerOrigen;
    private Spinner spinnerDestino;

    // Sistema Horarios
    public static final Integer RecordAudioRequestCode = 1;

    // Comedor
    ImplementaComedores implementaComedores;
    Navegacion navegacion;

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

        // Multi menús

        drawerLayout = findViewById(R.id.drawer_layout);
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
                        drawerLayout.closeDrawer(GravityCompat.START);
                        viewFlipper.setDisplayedChild(0);
                        break;

                    case R.id.nav_horarios:
                        Log.i("MENU_DRAWER_TAG","Horarios item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        viewFlipper.setDisplayedChild(1);
                        break;

                    case R.id.nav_comedores:
                        Log.i("MENU_DRAWER_TAG","Comedores item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        viewFlipper.setDisplayedChild(2);
                        break;
                    case R.id.nav_navigation:
                        Log.i("MENU_DRAWER_TAG","Navegacion item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        viewFlipper.setDisplayedChild(4);
                        break;

                    case R.id.nav_info:
                        Log.i("MENU_DRAWER_TAG","Info item is clicked");
                        drawerLayout.closeDrawer(GravityCompat.START);
                        viewFlipper.setDisplayedChild(3);
                        break;
                }

                return true;
            }
        });

        // Empieza la app
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        }

        implementaComedores = new ImplementaComedores(this);

        navegacion = new Navegacion(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        navegacion.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
        navegacion.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        navegacion.onDestroy();
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
}