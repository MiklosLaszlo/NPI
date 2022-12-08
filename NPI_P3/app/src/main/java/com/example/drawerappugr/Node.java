package com.example.drawerappugr;

import android.util.Log;

import java.util.ArrayList;

public class Node {
    public String data;
    private ArrayList<Node> adyacentes;
    public ArrayList<Float> coordenadas;


    public Node(){
        this.data = null;
        this.adyacentes= new ArrayList<>();
        this.coordenadas = new ArrayList<>();
    }

    public Node(String s){
        this.data = s;
        this.adyacentes= new ArrayList<>();
        this.coordenadas = new ArrayList<>();

    }

    public void setAdyacentes(ArrayList<Node> lista_adyacentes){
        this.adyacentes = lista_adyacentes;
    }

    public void setCoordenadas(ArrayList<Float> coord){
        this.coordenadas=coord;
    }

    public ArrayList<Node> getAdyacentes(){
        return this.adyacentes;
    }

    public ArrayList<Float> getCoordenadas(){return  this.coordenadas;}

    public String getString() {
        return this.data;
    }

    public void imprimir(){
        Log.e("Lugares Node","" + getString());
        for(int i=0; i < this.adyacentes.size();++i){
            Log.i("Lugares Adj","" + this.adyacentes.get(i).getString());
        }
    }
}