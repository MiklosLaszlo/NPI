package com.example.asignaturashorario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.util.DisplayMetrics;

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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

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

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = AppDatabase.getInstance(getApplicationContext());
        AsignaturaDAO asign = db.asignaturaDAO();
        List<Asignatura> asignaturas = asign.getAll();
        Log.i(TAG, asign.getAll().toString());
        Log.i(TAG, "La longitud es " + asignaturas.size());

        for(Asignatura a : asignaturas){
            Log.i(TAG, a.nombre);
            Log.i(TAG, a.grado);
            Log.i(TAG, String.valueOf(a.cuatri));
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            checkPermission();
        }

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
                text = "Has dicho el grado: " + text;
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
                text = "Has dicho la asignatura: " + text;
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
                text = "Has dicho el subgrupo: " + text;
                textToSpeechEngine3.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts1");
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

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

        ttsButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String text = String.valueOf(editText2.getText());
                if (!text.isEmpty())
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        textToSpeechEngine2.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts1");
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

        ttsButtonSalida.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                textoSalida.setText("Insertar funcion horario aqui");
                String text = String.valueOf(textoSalida.getText());
                if (!text.isEmpty())
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        textToSpeechEnginesalida.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts1");
                    }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RecordAudioRequestCode && grantResults.length > 0 ){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
        }
    }
}