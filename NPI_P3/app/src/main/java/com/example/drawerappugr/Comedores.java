package com.example.drawerappugr;

import java.util.ArrayList;

public class Comedores {
    public static ArrayList<StructComedores> c1;
    public static ArrayList<StructComedores> c2;

     public Comedores(){
        if(c1.isEmpty()){
            ArrayList<String> comida = new ArrayList<String>();
            comida.add("Primero: Raviolis a la carbonara");
            comida.add("Segundo: Escalope de cerdo");
            comida.add("Acompañamiento: Ensalada Carol");
            comida.add("Postre: Naranja");
            c1.add(new StructComedores("Menú 1",comida));
            comida.clear();
            comida.add("Primero: Olla gitana tradicional");
            comida.add("Segundo: Muslo de pollo en escabeche de naranja");
            comida.add("Acompañamiento: Patatas a lo pobre");
            comida.add("Postre: Plátano");
            c1.add(new StructComedores("Menú 1",comida));
            comida.clear();
            comida.add("Ensaladas: Ensalada de surimi de cangrejo y patatas");
            comida.add("Primero: Arroz mar y montaña");
            comida.add("Segundo: Encebollado de bacalao");
            comida.add("Postre: Piña");
            c1.add(new StructComedores("Menú 1",comida));
            comida.clear();
            comida.add("Primero: Estofado de lentejas");
            comida.add("Acompañamiento: Pincho de merluza en salsa vizcaína");
            comida.add("Segundo: Lomo braseado");
            comida.add("Acompañamiento: Patatas paja, cabello o hilo");
            comida.add("Postre: Mandarinas");
            c1.add(new StructComedores("Menú 1",comida));
            comida.clear();
            comida.add("Entrante: Empanada de jamón cocido con espinacas");
            comida.add("Primero: Sopa minestrone");
            comida.add("Segundo: Huevos rotos con bacalao y garrapiñadas de frutos secos");
            comida.add("Postre: Tomate asado");
            c1.add(new StructComedores("Menú 1",comida));
            comida.clear();

            comida.add("Primero: Macarrones Florentina (OV)");
            comida.add("Segundo: Tortilla de calabacín");
            comida.add("Acompañamiento: Ensalada mixta");
            comida.add("Postre: Naranja");
            c2.add(new StructComedores("Menú 2",comida));
            comida.clear();
            comida.add("Primero: Olla gitana");
            comida.add("Segundo: Tofu villeroy");
            comida.add("Acompañamiento: Patatas a lo pobre");
            comida.add("Postre: Plátano");
            c2.add(new StructComedores("Menú 2",comida));
            comida.clear();
            comida.add("Ensaladas: Ensalada");
            comida.add("Primero: Arroz con hortalizas al curry");
            comida.add("Segundo: Milhojas de verduras gratinadas en salsa romesco (OV)");
            comida.add("Postre: Piña");
            c2.add(new StructComedores("Menú 2",comida));
            comida.clear();
            comida.add("Primero: Estofado de lentejas");
            comida.add("Acompañamiento: Queso rebozado en crocante");
            comida.add("Acompañamiento: Chutney de frutas de otoño");
            comida.add("Segundo: Tajín de verduras");
            comida.add("Postre: Mandarinas");
            c2.add(new StructComedores("Menú 2",comida));
            comida.clear();
            comida.add("Entrante: Empanada de espinacas");
            comida.add("Primero: Sopa minestrone");
            comida.add("Segundo: HHuevos rotos con setas");
            comida.add("Postre: Tocino de Cielo");
            c2.add(new StructComedores("Menú 2",comida));
        }
    }

    public StructComedores get(int i, int j) {
        switch (j) {
            case 1:
                return c1.get(i);
            break;
            case 2:
                return c2.get(i);
            break;
        }
        return new StructComedores("VACIO", new ArrayList<String>());
    }
}
