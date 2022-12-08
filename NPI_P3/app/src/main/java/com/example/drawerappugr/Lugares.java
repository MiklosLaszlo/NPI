package com.example.drawerappugr;

import android.util.Log;

import java.util.ArrayList;

public class Lugares{
    public ArrayList<Node> lugares;

    public Lugares(){
        this.lugares= new ArrayList<Node>();
        this.lugares.add(new Node("Entrada Principal"));
        this.lugares.add(new Node("Bancos Rojos / Futbolín"));
        this.lugares.add(new Node("Escaleras Exterior Baja Comedor"));
        this.lugares.add(new Node("Comedor"));
        this.lugares.add(new Node("Fuente / Banco con Techo"));
        this.lugares.add(new Node("Entrada Edificio B"));
        this.lugares.add(new Node("Aulas 0.x"));
        this.lugares.add(new Node("Escaleras 0-1"));
        this.lugares.add(new Node("Aulas 1.x"));
        this.lugares.add(new Node("Escaleras 1-2"));
        this.lugares.add(new Node("Aulas 2.x"));
        this.lugares.add(new Node("Escaleras 2-3"));
        this.lugares.add(new Node("Aulas 3.x"));
        this.lugares.add(new Node("Secretaría"));
        //this.lugares.add(new Node("Escaleras Baja Comedor"));

        //ENTRADA PRINCIPAL
        ArrayList<Node> tmp = new ArrayList<Node>();
        ArrayList<Float> coord=new ArrayList<>();
        tmp.add(this.lugares.get(1));
        //Conexion entre entrada y bancos rojos
        coord.add(10.0F);
        tmp.add(this.lugares.get(13));
        coord.add(320F);
        this.lugares.get(0).setAdyacentes(tmp);
        this.lugares.get(0).setCoordenadas(coord);

        //SECRETARÍA
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();

        tmp.add(this.lugares.get(0));
        coord.add(320F-180F);
        //tmp.add(this.lugares.get(14));
        this.lugares.get(13).setAdyacentes(tmp);
        this.lugares.get(13).setCoordenadas(coord);

        //ESCALERAS BAJA COMEDOR
        /*
        tmp = new ArrayList<Node>();
        tmp.add(this.lugares.get(13));
        tmp.add(this.lugares.get(3));
        this.lugares.get(14).setAdyacentes(tmp);*/

        //FUTBOLÍN
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();

        tmp.add(this.lugares.get(0));
        //90-180+360=270
        coord.add(270F);
        tmp.add(this.lugares.get(2));
        coord.add(210F);
        tmp.add(this.lugares.get(4));
        coord.add(30F);
        this.lugares.get(1).setAdyacentes(tmp);
        this.lugares.get(1).setCoordenadas(coord);
        //ESCALERAS EXTERIORES
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();

        tmp.add(this.lugares.get(1));
        //210-180
        coord.add(30F);
        tmp.add(this.lugares.get(3));
        //135-180+360
        coord.add(315F);
        this.lugares.get(2).setAdyacentes(tmp);
        this.lugares.get(2).setCoordenadas(coord);
        //COMEDOR
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();

        tmp.add(this.lugares.get(2));
        coord.add(135F);
        //tmp.add(this.lugares.get(14));
        this.lugares.get(3).setAdyacentes(tmp);
        this.lugares.get(3).setCoordenadas(coord);

        //FUENTE / BANCO CON TECHO
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();
        tmp.add(this.lugares.get(1));
        coord.add(220F);
        tmp.add(this.lugares.get(5));
        coord.add(34F);
        this.lugares.get(4).setAdyacentes(tmp);
        this.lugares.get(4).setCoordenadas(coord);
        //ENTRADA EDIFICIO B
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();
        tmp.add(this.lugares.get(4));
        coord.add(224F);
        tmp.add(this.lugares.get(6));
        //290-180
        coord.add(110F);
        this.lugares.get(5).setAdyacentes(tmp);
        this.lugares.get(5).setCoordenadas(coord);
        //AULA 0.X
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();
        tmp.add(this.lugares.get(5));
        coord.add(290F);
        tmp.add(this.lugares.get(7));
        coord.add(110F);
        this.lugares.get(6).setAdyacentes(tmp);
        this.lugares.get(6).setCoordenadas(coord);
        //ESCALERAS 0-1
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();
        tmp.add(this.lugares.get(6));
        coord.add(290F);
        tmp.add(this.lugares.get(8));
        coord.add(110F);
        this.lugares.get(7).setAdyacentes(tmp);
        this.lugares.get(7).setCoordenadas(coord);
        //AULA 1.X
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();
        tmp.add(this.lugares.get(7));
        coord.add(290F);
        tmp.add(this.lugares.get(9));
        coord.add(110F);
        this.lugares.get(8).setAdyacentes(tmp);
        this.lugares.get(8).setCoordenadas(coord);

        //ESCALERAS 1-2
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();
        tmp.add(this.lugares.get(8));
        coord.add(290F);
        tmp.add(this.lugares.get(10));
        coord.add(110F);
        this.lugares.get(9).setAdyacentes(tmp);
        this.lugares.get(9).setCoordenadas(coord);

        //AULA 2.X
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();
        tmp.add(this.lugares.get(9));
        coord.add(290F);
        tmp.add(this.lugares.get(11));
        coord.add(110F);
        this.lugares.get(10).setAdyacentes(tmp);
        this.lugares.get(10).setCoordenadas(coord);

        //ESCALERAS 2-3
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();
        tmp.add(this.lugares.get(10));
        coord.add(290F);
        tmp.add(this.lugares.get(12));
        coord.add(110F);
        this.lugares.get(11).setAdyacentes(tmp);
        this.lugares.get(11).setCoordenadas(coord);


        //AULA 3.X
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();
        tmp.add(this.lugares.get(11));
        coord.add(112F);
        this.lugares.get(12).setAdyacentes(tmp);
        this.lugares.get(12).setCoordenadas(coord);

    }

    public Lugares(String s){
        this.lugares= new ArrayList<Node>();
        this.lugares.add(new Node(s));
    }

    public Node get(int i){
        Node tmp = new Node();
        if(0 <= i && i < this.lugares.size()) {
            tmp = this.lugares.get(i);
        }
        return tmp;
    }

    //buscar el nodo que contiene s
    public int search(String s){
        boolean encontrado=false;
        int pos=-1;

        for(int i=0;i<this.lugares.size() && !encontrado ;++i){
            if(this.lugares.get(i).getString()==s){
                pos=i;
                encontrado=true;
            }
        }

        return pos;
    }
    //mira si todos los adyacentes han sido visitados
    public boolean visitados(Node n,ArrayList<Boolean> v){
        int visitados = 0;
        for(int i=0;i<n.getAdyacentes().size();++i){
            if(v.get(this.search(n.getAdyacentes().get(i).getString()))){
                visitados++;
            }
        }
        return (visitados == n.getAdyacentes().size());
    }

    //busca el mejor camino entre dos nodos
    public ArrayList<Node> mejorCamino(Node lim1, Node lim2,ArrayList<Node> camino,ArrayList<Boolean> visitados){
        //Log.i("Lugares data1",lim1.data);
        //Log.i("Lugares data2",lim2.data);
        if(lim1.data != lim2.data) {
            String ruta="";
            int pos;

            for (int i = 0; i < lim1.getAdyacentes().size(); ++i) {
                pos = this.search(lim1.getAdyacentes().get(i).getString());
                //Log.i("Lugares valor ","I: "+ i);
                if (!visitados.get(pos)) {
                    visitados.set(pos,true);
                    camino.add(lim1.getAdyacentes().get(i));
                    //for(int x=0;x < camino.size();++x){
                    //    ruta = ruta + " --> " + camino.get(x).getString();
                    //}
                    camino = mejorCamino(lim1.getAdyacentes().get(i), lim2, camino, visitados);
                }

            }

        }

        return camino;
    }

    //imprime el mejor camino
    public ArrayList<Node> printCamino(String s_inicio, String s_final){
        //Crear array de visitados
        ArrayList<Boolean> visitados = new ArrayList<Boolean>();
        for(int i =0; i < this.lugares.size(); ++i){
            visitados.add(false);
        }

        //Agrega a visitados los nodo inicio y final
        visitados.set(this.search(s_inicio),true);
        visitados.set(this.search(s_final),true);

        String ruta="";

        ArrayList<Node> tmp = new ArrayList<Node>();
        tmp.add(this.lugares.get(this.search(s_inicio)));
        tmp = mejorCamino(this.lugares.get(search(s_inicio)),this.lugares.get(search(s_final)),tmp,visitados);
        tmp.add(this.lugares.get(this.search(s_final)));

        for(int i=tmp.size()-1; 0<i ;--i){
            if(!sonAdyacentes(tmp.get(i),tmp.get(i-1))){
                tmp.remove(i-1);
            }
        }

        for(int i=0;i < tmp.size();++i){
            Log.i("Salida",)
        }
        return tmp;
    }


    public boolean sonAdyacentes(Node n1, Node n2){
        for(int i=0; i < n1.getAdyacentes().size();i++){
            if(n1.getAdyacentes().get(i).getString()==n2.getString()){
                return true;
            }
        }
        return false;
    }
    /*
    //buscar un nodo con el contenido s a partir de n
    public Node searchNode(Node n,String s){
        if(n.getString()==s){
            return n;
        }
        if(n.getNext()==null){
            return null;
        }
        if(n.getNext().size() > 1){
            //busca a partir del util 1
            for(int i=1;i<n.getNext().size();++i) {
                return searchNode(n.getNext().get(i), s);
            }
        }
        //busca el util 0
        return searchNode(n.getNext().get(0),s);
    }*/

    //inserta un nodo dado una cadena
    public void insert(String s){
        this.lugares.add(new Node(s));
    }

    //inserta un nodo
    public void insert_nodo(Node insertar){
        this.lugares.add(insertar);
    }
/*
    //añade los adyacentes dado un nodo n
    public void añadir_adyacentes(Node n,ArrayList<Node> lista_adyacentes){
        int pos = this.search(n.getString());
        this.lugares.get(pos).setAdyacentes(lista_adyacentes);
    }*/

    public void imprimir(){
        for(int i = 0;i < lugares.size();++i) {
            this.lugares.get(i).imprimir();
        }
    }

/*
    //inserta un elemento a_insertar y lo enlaza con el elemento anterior
    public void insert(Node a_insertar,Node p){
        //Node a_insertar = new Node(s);

        if(this.head==null){
            this.head = a_insertar;
        }
        else{
            a_insertar.setPrev(p);
            p.setNext(a_insertar);
        }


    }*/


}