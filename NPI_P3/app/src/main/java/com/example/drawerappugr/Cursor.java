package com.example.drawerappugr;

import android.annotation.SuppressLint;
import android.app.Activity;
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


public class Cursor{
    //private SceneView sceneView;
    private SceneView backgroundSceneView;

    Node nodoIntermedio;
    Node nodoCursor;
    private GestosSensor gestoSensor;

    private final float ALPHA = 0.7f;
    private float rx_antiguo = 0;   // En grados
    private float ry_antiguo = 0;   // En grados



    @SuppressLint("ClickableViewAccessibility")
    public Cursor(Activity activity) {

        backgroundSceneView = activity.findViewById(R.id.scene_view);

        loadModels(activity);

        gestoSensor = new GestosSensor(activity, false, false, true, false) {
            @Override
            public void rotationCallback(float rotx, float roty, float rotz) {
                float heading = 0;

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

    public void onResume() {
        Log.e("Flecha", "Que esta pasando");
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
    public void onPause() {
        backgroundSceneView.pause();
        gestoSensor.unregisterListener();
    }

    public void onDestroy() {
        backgroundSceneView.destroy();
    }

    public void loadModels(Activity activity) {
        CompletableFuture<ModelRenderable> cursor = ModelRenderable
                .builder()
                .setSource(activity, Uri.parse("models/Cursor.glb"))
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
