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
        ArrayList<String> dir = new ArrayList<>();
        tmp.add(this.lugares.get(1));
        //Conexion entre entrada y bancos rojos
        coord.add(10.0F);
        dir.add("Cruza la puerta y ve a los bancos rojos");
        tmp.add(this.lugares.get(13));
        dir.add("Ve hasta la entrada del edificio principal, subiendo la escalinata");
        coord.add(320F);
        this.lugares.get(0).setAdyacentes(tmp);
        this.lugares.get(0).setCoordenadas(coord);
        this.lugares.get(0).setDirecciones(dir);

        //SECRETARÍA
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();
        dir=new ArrayList<>();
        tmp.add(this.lugares.get(0));
        coord.add(320F-180F);
        dir.add("Sal por la puerta, al lado de secretaría");
        //tmp.add(this.lugares.get(14));
        this.lugares.get(13).setAdyacentes(tmp);
        this.lugares.get(13).setCoordenadas(coord);
        this.lugares.get(13).setDirecciones(dir);

        //ESCALERAS BAJA COMEDOR
        /*
        tmp = new ArrayList<Node>();
        tmp.add(this.lugares.get(13));
        tmp.add(this.lugares.get(3));
        this.lugares.get(14).setAdyacentes(tmp);*/

        //Bancos Rojos / FUTBOLÍN
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();
        dir=new ArrayList<>();
        tmp.add(this.lugares.get(0));
        //90-180+360=270
        coord.add(270F);
        dir.add("Cruza la puerta roja");
        tmp.add(this.lugares.get(2));
        coord.add(210F);
        dir.add("Baje por las escaleras");
        tmp.add(this.lugares.get(4));
        dir.add("Suba por la rampa hasta llegar a los bancos");
        coord.add(30F);
        this.lugares.get(1).setAdyacentes(tmp);
        this.lugares.get(1).setCoordenadas(coord);
        this.lugares.get(1).setDirecciones(dir);
        //ESCALERAS EXTERIORES
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();
        dir=new ArrayList<>();

        tmp.add(this.lugares.get(1));
        //210-180
        coord.add(30F);
        dir.add("Suba las escaleras");
        tmp.add(this.lugares.get(3));
        dir.add("Cruce la puerta y encontrara el comedor");
        //135-180+360
        coord.add(315F);
        this.lugares.get(2).setAdyacentes(tmp);
        this.lugares.get(2).setCoordenadas(coord);
        this.lugares.get(2).setDirecciones(dir);
        //COMEDOR
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();
        dir=new ArrayList<>();
        tmp.add(this.lugares.get(2));
        coord.add(135F);
        dir.add("Cruce la puerta, hasta llegar a las escaleras");
        //tmp.add(this.lugares.get(14));
        this.lugares.get(3).setAdyacentes(tmp);
        this.lugares.get(3).setCoordenadas(coord);
        this.lugares.get(3).setDirecciones(dir);

        //FUENTE / BANCO CON TECHO
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();
        dir=new ArrayList<>();
        tmp.add(this.lugares.get(1));
        coord.add(220F);
        dir.add("Ve hacia los bancos rojos");
        tmp.add(this.lugares.get(5));
        coord.add(34F);
        dir.add("Siga al cursor hasta entrar la entrada del edificio");
        this.lugares.get(4).setAdyacentes(tmp);
        this.lugares.get(4).setCoordenadas(coord);
        this.lugares.get(4).setDirecciones(dir);
        //ENTRADA EDIFICIO B
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();
        dir=new ArrayList<>();
        tmp.add(this.lugares.get(4));
        dir.add("Ve hacia los bancos de madera");
        coord.add(224F);
        tmp.add(this.lugares.get(6));
        //290-180
        dir.add("Entre al edificio");
        coord.add(110F);
        this.lugares.get(5).setAdyacentes(tmp);
        this.lugares.get(5).setCoordenadas(coord);
        this.lugares.get(5).setDirecciones(dir);
        //AULA 0.X
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();
        dir=new ArrayList<>();
        tmp.add(this.lugares.get(5));
        coord.add(290F);
        dir.add("Salga del edificio por conserjería");
        tmp.add(this.lugares.get(7));
        coord.add(110F);
        dir.add("Ve hacia las escaleras en frente de los baños");
        this.lugares.get(6).setAdyacentes(tmp);
        this.lugares.get(6).setCoordenadas(coord);
        this.lugares.get(6).setDirecciones(dir);
        //ESCALERAS 0-1
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();
        dir=new ArrayList<>();
        tmp.add(this.lugares.get(6));
        coord.add(290F);
        dir.add("Baje las escaleras");
        tmp.add(this.lugares.get(8));
        dir.add("Suba las escaleras");
        coord.add(110F);
        this.lugares.get(7).setAdyacentes(tmp);
        this.lugares.get(7).setCoordenadas(coord);
        this.lugares.get(7).setDirecciones(dir);
        //AULA 1.X
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();
        dir=new ArrayList<>();
        tmp.add(this.lugares.get(7));
        coord.add(290F);
        dir.add("Ve a las escaleras de bajada en frente de los baños");
        tmp.add(this.lugares.get(9));
        coord.add(110F);
        dir.add("Ve a las escaleras de subida en frente de los baños");
        this.lugares.get(8).setAdyacentes(tmp);
        this.lugares.get(8).setCoordenadas(coord);
        this.lugares.get(8).setDirecciones(dir);
        //ESCALERAS 1-2
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();
        dir=new ArrayList<>();
        tmp.add(this.lugares.get(8));
        coord.add(290F);
        dir.add("Baje las escaleras");
        tmp.add(this.lugares.get(10));
        coord.add(110F);
        dir.add("Suba las escaleras");
        this.lugares.get(9).setAdyacentes(tmp);
        this.lugares.get(9).setCoordenadas(coord);
        this.lugares.get(9).setDirecciones(dir);

        //AULA 2.X
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();
        dir=new ArrayList<>();
        tmp.add(this.lugares.get(9));
        coord.add(290F);
        dir.add("Ve hacia las escaleras de bajada frente a los baños");
        tmp.add(this.lugares.get(11));
        dir.add("Ve a las escaleras de subida en frente de los baños");
        coord.add(110F);
        this.lugares.get(10).setAdyacentes(tmp);
        this.lugares.get(10).setCoordenadas(coord);
        this.lugares.get(10).setDirecciones(dir);
        //ESCALERAS 2-3
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();
        dir=new ArrayList<>();
        tmp.add(this.lugares.get(10));
        coord.add(290F);
        dir.add("Baje las escaleras");
        tmp.add(this.lugares.get(12));
        coord.add(110F);
        dir.add("Suba las escaleras");
        this.lugares.get(11).setAdyacentes(tmp);
        this.lugares.get(11).setCoordenadas(coord);
        this.lugares.get(11).setDirecciones(dir);

        //AULA 3.X
        tmp = new ArrayList<Node>();
        coord=new ArrayList<>();
        dir=new ArrayList<>();
        tmp.add(this.lugares.get(11));
        coord.add(112F);
        dir.add("Ve a las escaleras de bajada frente a los baños");
        this.lugares.get(12).setAdyacentes(tmp);
        this.lugares.get(12).setCoordenadas(coord);
        this.lugares.get(12).setDirecciones(dir);
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
            Log.i("Salida",tmp.get(i).getString());
            if(i+1<tmp.size())
                Log.i("Salida",String.valueOf( tmp.get(i).getCoordenadas().get( tmp.get(i).getAdyacentes().indexOf( tmp.get(i+1) ) ) ) );
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