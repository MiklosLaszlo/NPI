package com.example.drawerappugr;

import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class ScanQR {
    private ActivityResultLauncher<ScanOptions> barLauncher;
    private Button scanButton;
    private String textoEscaneado = new String();

    public ScanQR(AppCompatActivity activity){
        scanButton = activity.findViewById(R.id.scanButton);
        barLauncher = activity.registerForActivityResult(new ScanContract(), result->{
            if(result.getContents() != null){
                // GUARDA EL TEXTO LEIOD AQUI
                textoEscaneado=result.getContents();
            }
        });
        scanButton.setOnClickListener(v->{
            scanCode();
        });
    }

    public String getTexto(){
        return (String) textoEscaneado;
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
