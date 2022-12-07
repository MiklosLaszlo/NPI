package com.example.drawerappugr;

import android.annotation.SuppressLint;
import android.hardware.SensorEvent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.exceptions.CameraNotAvailableException;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.SceneView;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class Navegador extends AppCompatActivity {
    //private SceneView sceneView;
    private SceneView backgroundSceneView;

    Node nodoIntermedio;
    Node nodoCursor;
    private GestosSensor gestoSensor;

    private final float ALPHA = 0.7f;
    private float rx_antiguo = 0;   // En grados
    private float ry_antiguo = 0;   // En grados

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.navegacion);
        backgroundSceneView = findViewById(R.id.scene_view);

        loadModels();

        gestoSensor = new GestosSensor(this, false, false, true, false) {
            @Override
            public void rotationCallback(float rotx, float roty, float rotz) {
                float heading = 0;
                Log.e("GRADOS", "X: " + Math.toDegrees(Math.asin(rotx)) +
                        " Y: "  + Math.toDegrees(2*Math.asin(roty)) +
                        " Z: " + Math.toDegrees(2*Math.asin(rotz)) );

                float rx = (float) Math.toDegrees(2*Math.asin(roty));
                float ry = (float) Math.toDegrees(2*Math.asin(rotz));

                rx_antiguo = ALPHA * rx_antiguo + (1-ALPHA) * rx;
                ry_antiguo = ALPHA * ry_antiguo + (1-ALPHA) * ry;

                Quaternion rotacion_x, rotacion_y, rotacion;
                rotacion_x = Quaternion.axisAngle(new Vector3(1f,0f,0f), (rx + 90) );
                rotacion_y = Quaternion.axisAngle(new Vector3(0f,1f,0f), -1*ry );
                rotacion = Quaternion.multiply(rotacion_x, rotacion_y);

                nodoCursor.setLocalRotation(rotacion);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            backgroundSceneView.resume();
        } catch (CameraNotAvailableException e) {
            Log.e("Flecha", "Should not happen");
            throw new AssertionError("Failed to resume SceneView", e);
        } catch (Exception e) {
            Log.e("Flecha", "No deberia pasar");
        }

         gestoSensor.registerListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        backgroundSceneView.pause();
        gestoSensor.unregisterListener();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        backgroundSceneView.destroy();
    }

    public void loadModels() {
        CompletableFuture<ModelRenderable> cursor = ModelRenderable
                .builder()
                .setSource(this, Uri.parse("models/Cursor.glb"))
                .setIsFilamentGltf(true)
                .setAsyncLoadEnabled(true)
                .build();

        Log.i("LoadModels", "Supuestamente cargados");

        nodoIntermedio = new Node();
        nodoIntermedio.setWorldPosition(new Vector3(0.0f, 0f, -2.0f));
        backgroundSceneView.getScene().addChild(nodoIntermedio);

        CompletableFuture.allOf(cursor)
                .handle((ok, ex) -> {
                    try {
                        nodoCursor = new Node();
                        nodoCursor.setRenderable(cursor.get());
                        nodoCursor.setLocalScale(new Vector3(0.3f, 0.3f, 0.3f));
                        nodoIntermedio.addChild(nodoCursor);

                    } catch (InterruptedException | ExecutionException ignore) {
                        Log.e("Captura errores", "error " + Uri.parse("models/cursor.glb"));
                    }
                    return null;
                });
    }
}
