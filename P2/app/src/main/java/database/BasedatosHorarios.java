package database;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasedatosHorarios {
    private Asignatura asignatura = new Asignatura();
    private List<Subgrupo> lista_subgrupos = new ArrayList<Subgrupo>();
    private Subgrupo subrupo = new Subgrupo();
    private List<Hora> lista_horas = new ArrayList<Hora>();
    private AppDatabase db;

    private AsignaturaDAO asignaturaDAO;
    private SubgrupoDAO subgrupoDAO;
    private HoraDAO horaDAO;

    private static HashMap<Integer, String> dias = new HashMap<Integer, String>();
    static {
        dias.put(1,"Lunes");
        dias.put(2, "Martes");
        dias.put(3,"Miércoles");
        dias.put(4,"Jueves");
        dias.put(5,"Viernes");
    };

    private static HashMap<String, String> numeros = new HashMap<String, String>();
    static {
        numeros.put("uno","1");
        numeros.put("dos","2");
        numeros.put("tres","3");
        numeros.put("cuatro","4");
        numeros.put("cinco","5");
        numeros.put("seis","6");
        numeros.put("siete","7");
        numeros.put("ocho","8");
        numeros.put("nueve","9");
        numeros.put("diez","10");
        numeros.put("one","11");
        numeros.put("doce","12");
        numeros.put("trece","13");
        numeros.put("catorce","14");
        numeros.put("quince","15");
    };

    public BasedatosHorarios(Context context){
        db = AppDatabase.getInstance(context);
        asignaturaDAO = db.asignaturaDAO();
        subgrupoDAO = db.subgrupoDAO();
        horaDAO = db.horaDAO();

        asignatura.id = -1;
        subrupo.id = -1;
    }

    public boolean existeAsignaturaGrado(String asignatura, String grado){
        boolean salida = false;
        if (!asignatura.isEmpty() && grado.isEmpty()){
            salida = asignaturaDAO.getCountAsignatura(asignatura) > 0;
        }
        else if (asignatura.isEmpty() && !grado.isEmpty()){
            salida = asignaturaDAO.getCountGrado(grado) > 0;
        }else{
            salida = asignaturaDAO.getCountGradoAsignatura(grado, asignatura) > 0;
        }
        return salida;
    }

    public boolean setAsignatura(String asignatura_p, String grado){
        Log.i("a", "asignatura: " + asignatura_p + " grado " + grado);
        List<Asignatura> asignaturas = asignaturaDAO.getAsignaturaId(grado, asignatura_p);
        if(asignaturas.size() != 1) {
            asignatura.id = -1; subrupo.id = -1;
            lista_subgrupos.clear();
            lista_horas.clear();
            return false;
        }
        asignatura = asignaturas.get(0) ;
        lista_subgrupos = subgrupoDAO.getSubgruposAsignatura(asignatura.id);
        return true;
    }

    public Subgrupo existeSubgrupo(String nombre){
        boolean salida = false;
        Subgrupo sb = new Subgrupo();
        sb.id = -1;
        for(int i = 0; i < lista_subgrupos.size() && !salida; i++){
            salida = lista_subgrupos.get(i).nombre.toUpperCase().equals(nombre.toUpperCase());
            if(numeros.get(nombre) != null){
                salida = lista_subgrupos.get(i).nombre.toUpperCase().equals( numeros.get(nombre));
            }

            if(salida){
                sb = lista_subgrupos.get(i);
            }
        }
        return sb;
    }

    public boolean setSubgrupo(String nombre) {
        //Log.i("a", "subgrupo: " + nombre);

        subrupo = existeSubgrupo(nombre);
        boolean existe = subrupo.id != -1 && asignatura.id != -1 && lista_subgrupos.size() > 0;
        if ( existe ){
            lista_horas = horaDAO.getHorasSubgrupo(subrupo.id);
        }
        else{
            subrupo.id = -1; lista_horas.clear();
        }
        return existe;
    }

    public String getHorario() {
        if(lista_horas.isEmpty()) return "Rellene el resto de campos con valores válidos";
        String salida = "La asignatura " + asignatura.nombre + " del grado " + asignatura.grado + " (subgrupo " + subrupo.nombre + ") son los:\n" ;
        for (Hora h : lista_horas){
            salida+= "     " + BasedatosHorarios.dias.get(h.dia) + ": de " + h.horaInicio + " a " + h.horaFin + " en el aula: " + h.clase + "\n";
        }
        return salida;
    }
}
