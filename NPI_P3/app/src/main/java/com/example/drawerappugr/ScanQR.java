package com.example.drawerappugr;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class ScanQR {
    private Activity activity;
    private ActivityResultLauncher<ScanOptions> barLauncher;
    private TextView textoEscaneado;
    private Button scanButton;

    public ScanQR(AppCompatActivity activity){
        textoEscaneado= activity.findViewById(R.id.textView3);
        scanButton = activity.findViewById(R.id.scanButton);
        barLauncher = activity.registerForActivityResult(new ScanContract(), result->{
            if(result.getContents() != null){
                // GUARDA EL TEXTO LEIOD AQUI
                textoEscaneado.setText(result.getContents());
            }
        });
        scanButton.setOnClickListener(v->{
            scanCode();
        });
    }

    private void scanCode() {
        ScanOptions options=new ScanOptions();
        options.setPrompt("HOLA");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }
}
