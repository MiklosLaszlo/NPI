package com.example.drawerappugr;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Locale;
import android.widget.RelativeLayout;

@RequiresApi(api = Build.VERSION_CODES.O)
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
    SwipeListener swipeListener;

    // Sistema Horarios
    public static final Integer RecordAudioRequestCode = 1;
    private final String TAG = "DB";
    private TextToSpeech textToSpeechEngine;
    private TextToSpeech textToSpeechEngine2;
    private TextToSpeech textToSpeechEngine3;
    private TextToSpeech textToSpeechEnginesalida;

    private EditText editText;
    private ImageView micButton;
    private Button ttsButton;
    private SpeechRecognizer speechRecognizer;

    private EditText editText2;
    private ImageView micButton2;
    private Button ttsButton2;
    private SpeechRecognizer speechRecognizer2;

    private EditText editText3;
    private ImageView micButton3;
    private Button ttsButton3;
    private SpeechRecognizer speechRecognizer3;

    private Button ttsButtonSalida;
    private TextView textoSalida;

    private LinearLayout resultLayout;

    private GestosNico gestosNico;
    private boolean listening;
    private boolean second_listening;
    private boolean left_done;
    private boolean right_done;
    private boolean double_left_done;
    private boolean double_right_done;
    private boolean up_done;
    private boolean down_done;
    private CountDownTimer contador;
    private int contando;

    private ImplementaComedores implementaComedores;

    private String nombre_grado = "";
    private String nombre_asignatura = "";
    private String nombre_subgrupo = "";

    // Comedores
    private WebView dialogFlow;
    WebSettings webSettings;
    final String iframe = "" +
            "<html>\n" +
            "\t<body>\n" +
            "\t\t<iframe style=\"position:fixed; top:0; left:0; bottom:0; right:0; width:100%; height:100%; border:none; margin:0; padding:0; overflow:hidden; z-index:999999;\" src=\"https://console.dialogflow.com/api-client/demo/embedded/4e9c22e4-7818-4c96-bd8a-8acdb6c7b3d0\"></iframe>\n" +
            "\t</body>\n" +
            "</html>";


    //Clases //
    private class SwipeListener implements View.OnTouchListener{
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
    }




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
        listening = true;
        second_listening = false;
        left_done = false;
        right_done = false;
        double_left_done = false;
        double_right_done = false;
        down_done = false;
        up_done = false;
        contando=0;
        implementaComedores = new ImplementaComedores(this);
        contador =  new CountDownTimer(1000,500) {
            @Override
            public void onTick(long l) {
                if( (left_done || right_done) && (contando != 0)){
                    second_listening=true;
                }
                contando=contando+1;
            }

            @Override
            public void onFinish() {
                if(left_done) {
                    implementaComedores.prevDayMenu();
                    Log.e("Gesto","Uno izquierda");
                }
                if(right_done) {
                    implementaComedores.nextDayMenu();
                    Log.e("Gesto","Uno dereca");
                }
                if(double_left_done) {
                    Log.e("Gesto","dos izquierda");

                }
                if(double_right_done) {
                    Log.e("Gesto","dos derecha");
                }
                if(up_done) {
                    implementaComedores.menuSelecionado(1);
                    Log.e("Gesto","arriba");
                }
                if(down_done) {
                    implementaComedores.menuSelecionado(2);
                    Log.e("Gesto","abajo");
                }
                contando=0;
                listening = true;
                second_listening = false;
                left_done = false;
                right_done = false;
                double_left_done = false;
                double_right_done = false;
                down_done = false;
                up_done = false;
            }
        };

        gestosNico = new GestosNico(this) {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(listening) {
                    switch (event.sensor.getType()) {
                        case Sensor.TYPE_GYROSCOPE:
                            if (gestoGiroManoIzquierda(event)) {
                                contador.start();
                                listening = false;
                                left_done=true;
                            }
                            else if (gestoGiroManoDerecha(event)) {
                                contador.start();
                                listening = false;
                                right_done=true;
                            }
                            break;
                        case Sensor.TYPE_LINEAR_ACCELERATION:
                            if (gestoParaArriba(event)){
                                contador.start();
                                up_done = true;
                                listening = false;
                            }
                            else if(gestoParaAbajo(event)){
                                contador.start();
                                down_done = true;
                                listening = false;
                            }

                            break;
                    }

                }

                else if(second_listening){
                    switch (event.sensor.getType()) {
                        case Sensor.TYPE_GYROSCOPE:
                            if (gestoGiroManoIzquierda(event) && left_done) {
                                double_left_done = true;
                                left_done=false;
                            }
                            else if (gestoGiroManoDerecha(event) && right_done) {
                                double_right_done = true;
                                right_done=false;
                            }
                            second_listening=false;
                            break;
                    }
                }
            };
        };

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

        // Unimos cada texto, boton... a su correspondente en el Layout
        // Horarios

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer2 = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer3 = SpeechRecognizer.createSpeechRecognizer(this);

        textToSpeechEngine = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.SUCCESS) {
                    Log.e("TTS", "Inicio de la síntesis fallido");
                }
            }
        });

        textToSpeechEngine2 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.SUCCESS) {
                    Log.e("TTS", "Inicio de la síntesis fallido");
                }
            }
        });

        textToSpeechEngine3 = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.SUCCESS) {
                    Log.e("TTS", "Inicio de la síntesis fallido");
                }
            }
        });

        textToSpeechEnginesalida = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.SUCCESS) {
                    Log.e("TTS", "Inicio de la síntesis fallido");
                }
            }
        });

        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        final Intent speechRecognizerIntent2 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        final Intent speechRecognizerIntent3 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent3.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent3.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        // Reconocedores del habla, cada uno esta relacionado con un editText distinto y un speechRecognizer distinto

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {
                editText.setText("");
                editText.setHint("Listening...");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                micButton.setImageResource(R.drawable.ic_mic_black_off);
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                editText.setText(data.get(0));
                String text = String.valueOf(editText.getText());

                textToSpeechEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts1");
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        speechRecognizer2.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {
                editText2.setText("");
                editText2.setHint("Listening...");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                micButton2.setImageResource(R.drawable.ic_mic_black_off);
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                editText2.setText(data.get(0));
                String text = String.valueOf(editText2.getText());

                textToSpeechEngine2.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts1");
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        speechRecognizer3.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {
                editText3.setText("");
                editText3.setHint("Listening...");
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                micButton3.setImageResource(R.drawable.ic_mic_black_off);
                ArrayList<String> data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                editText3.setText(data.get(0));
                String text = String.valueOf(editText3.getText());

                textToSpeechEngine3.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts1");
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });


    }


    @Override
    public void onResume(){
        super.onResume();
        if(gestosNico.hasGiroscopeSensor())
            gestosNico.registerListener();
    }

    @Override
    public void onPause(){
        super.onPause();
        gestosNico.unregisterListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speechRecognizer.destroy();
        speechRecognizer2.destroy();
        speechRecognizer3.destroy();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO}, RecordAudioRequestCode);
        }
    }

    private void activateVisibility(){
        if(String.valueOf(editText.getText()).isEmpty() || String.valueOf(editText2.getText()).isEmpty() || String.valueOf(editText3.getText()).isEmpty())
            resultLayout.setVisibility(View.INVISIBLE);
        else
            resultLayout.setVisibility(View.VISIBLE);
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0 ){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
        }
    }
}