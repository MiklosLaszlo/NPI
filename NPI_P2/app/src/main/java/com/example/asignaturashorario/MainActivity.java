package com.example.asignaturashorario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

// Para visibilidad en texto cambiado
import android.text.TextWatcher;
import android.text.Editable;

// Tener scroll view metido
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import database.*;

public class MainActivity extends AppCompatActivity {
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

    private BasedatosHorarios db;

    private Button irHorarios;
    private Button irComedores;
    private Button volverMenuHorarios;
    private Button volverMenuComedores;

    private ScrollView horarios;
    private ScrollView comedores;
    private LinearLayout menu;

    private String nombre_grado = "";
    private String nombre_asignatura = "";
    private String nombre_subgrupo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Pruebas con la base de datos
        db = new BasedatosHorarios(getApplicationContext());
        // Fin pruebas

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        }

        // Unimos cada texto, boton... a su correspondente en el Layout
        editText = findViewById(R.id.textmic);
        micButton = findViewById(R.id.mic);
        ttsButton = findViewById(R.id.button_mic);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        editText2 = findViewById(R.id.textmic2);
        micButton2 = findViewById(R.id.mic2);
        ttsButton2 = findViewById(R.id.button_mic2);
        speechRecognizer2 = SpeechRecognizer.createSpeechRecognizer(this);

        editText3 = findViewById(R.id.textmic3);
        micButton3 = findViewById(R.id.mic3);
        ttsButton3 = findViewById(R.id.button_mic3);
        speechRecognizer3 = SpeechRecognizer.createSpeechRecognizer(this);

        ttsButtonSalida = findViewById(R.id.button3);
        textoSalida = findViewById(R.id.textosalida);

        resultLayout = findViewById(R.id.resultLayout);

        // Gestion de menus
        irComedores = findViewById(R.id.Ir_comedores);
        irHorarios = findViewById(R.id.Ir_horarios);
        volverMenuComedores = findViewById(R.id.Volver_menu_comedores);
        volverMenuHorarios = findViewById(R.id.volver_menu_horarios);

        horarios = findViewById(R.id.Horarios);
        comedores = findViewById(R.id.Comedores);
        menu = findViewById(R.id.Menu);

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

                if(db.existeAsignaturaGrado(nombre_asignatura, text)) {
                    nombre_grado = text;
                    text = "Has dicho el grado: " + text;
                    if(!nombre_asignatura.isEmpty()) db.setAsignatura(nombre_asignatura, nombre_subgrupo);
                }
                else{
                    text = "No existe un grado " + text;
                    if ( !nombre_asignatura.isEmpty() ) text+= " con la asignatura" + nombre_asignatura;
                    textoSalida.setText(text);
                }

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

                if(db.existeAsignaturaGrado(text, nombre_grado)) {
                    nombre_asignatura = text;
                    text = "Has dicho la asignatura: " + text;
                    if(!nombre_grado.isEmpty()) db.setAsignatura(nombre_asignatura, nombre_subgrupo);
                }
                else{
                    text = "No existe la asignatura " + text;
                    if ( !nombre_grado.isEmpty() ) text+= " en el grado " + nombre_grado;
                    textoSalida.setText(text);
                }

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

                if(db.setAsignatura(editText2.getText().toString(), editText.getText().toString())){
                    if ( db.setSubgrupo(text) ){
                        nombre_subgrupo = text;
                        text = "Has dicho el subgrupo: " + text;
                    }
                    else {
                        text = "No existe el subgrupo " + text;
                        textoSalida.setText(text);
                    }
                }
                else{
                    text = "No existe la combinación de grado y asignatura proporcionada";
                }

                textToSpeechEngine3.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts1");
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        // Botones para escuchar al usuario, y repetir lo que ha dicho, cada uno por un texto

        micButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    speechRecognizer.stopListening();
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    micButton.setImageResource(R.drawable.ic_mic_black_24dp);
                    speechRecognizer2.cancel();
                    speechRecognizer3.cancel();
                    micButton2.setImageResource(R.drawable.ic_mic_black_off);
                    micButton3.setImageResource(R.drawable.ic_mic_black_off);
                    speechRecognizer.startListening(speechRecognizerIntent);
                }
                return false;
            }
        });

        ttsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String text = String.valueOf(editText.getText());
                if (!text.isEmpty())
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        textToSpeechEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts1");
                    }
            }
        });

        micButton2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    speechRecognizer2.stopListening();
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    micButton2.setImageResource(R.drawable.ic_mic_black_24dp);
                    speechRecognizer.cancel();
                    speechRecognizer3.cancel();
                    micButton.setImageResource(R.drawable.ic_mic_black_off);
                    micButton3.setImageResource(R.drawable.ic_mic_black_off);
                    speechRecognizer2.startListening(speechRecognizerIntent2);
                }
                return false;
            }
        });

        // Boton que sirve para llamar a la funcion de buscar horario, escribe en el texto de salida y lo dice.
        ttsButtonSalida.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db.setAsignatura(editText2.getText().toString(), editText.getText().toString());
                db.setSubgrupo(editText3.getText().toString());
                textoSalida.setText(db.getHorario());
                String text = String.valueOf(textoSalida.getText());
                String voz= db.getHorarioTTS();
                if (!text.isEmpty())
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        textToSpeechEnginesalida.speak(voz, TextToSpeech.QUEUE_FLUSH, null, "tts1");
                    }
            }
        });

        micButton3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    speechRecognizer3.stopListening();
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    micButton3.setImageResource(R.drawable.ic_mic_black_24dp);
                    speechRecognizer2.cancel();
                    speechRecognizer.cancel();
                    micButton2.setImageResource(R.drawable.ic_mic_black_off);
                    micButton.setImageResource(R.drawable.ic_mic_black_off);
                    speechRecognizer3.startListening(speechRecognizerIntent3);
                }
                return false;
            }
        });

        ttsButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String text = String.valueOf(editText3.getText());
                if (!text.isEmpty())
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        textToSpeechEngine3.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts1");
                    }
            }
        });


        // Boton que sirve para llamar a la funcion de buscar horario, escribe en el texto de salida y lo dice.
        ttsButtonSalida.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db.setAsignatura(editText2.getText().toString().trim(), editText.getText().toString().trim());
                db.setSubgrupo(editText3.getText().toString());
                textoSalida.setText(db.getHorario());
                String text = String.valueOf(textoSalida.getText());
                if (!text.isEmpty())
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        textToSpeechEnginesalida.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts1");
                    }
            }
        });

        // Cuando el texto de editText,editText2, editText3 cambie se aplica esta clase
        TextWatcher textWatcher = new  TextWatcher (){
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Funcion privada del main, si los tres editText no estan vacios, hace visible calcular horario
                // Si habia algún horario calculado este se borra.
                activateVisibility();
                textoSalida.setText("",TextView.BufferType.EDITABLE);
            }

        };

        editText.addTextChangedListener(textWatcher);
        editText2.addTextChangedListener(textWatcher);
        editText3.addTextChangedListener(textWatcher);

        irComedores.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
                menu.setVisibility(View.GONE);
                comedores.setVisibility(View.VISIBLE);
           }
        });

        irHorarios.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                menu.setVisibility(View.GONE);
                horarios.setVisibility(View.VISIBLE);
            }
        });

        volverMenuHorarios.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                horarios.setVisibility(View.GONE);
                menu.setVisibility(View.VISIBLE);
            }
        });

        volverMenuComedores.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                comedores.setVisibility(View.GONE);
                menu.setVisibility(View.VISIBLE);
            }
        });

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