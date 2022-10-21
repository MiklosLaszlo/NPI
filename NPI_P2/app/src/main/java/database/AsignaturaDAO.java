package database;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;
import java.util.Map;

@Dao
public interface AsignaturaDAO {
    @Query("SELECT * FROM Asignatura")
    List<Asignatura> getAll();

    @Query("SELECT COUNT(*) FROM asignatura WHERE UPPER(asignatura.nombre) LIKE UPPER(:nombre) ")
    Integer getCountAsignatura(String nombre);

    @Query("SELECT COUNT(*) FROM asignatura WHERE UPPER(asignatura.grado) LIKE UPPER(:grado) ")
    Integer getCountGrado(String grado);

    @Query("SELECT COUNT(*) FROM asignatura WHERE UPPER(Asignatura.grado) LIKE UPPER(:grado) AND UPPER(asignatura.nombre) LIKE UPPER(:nombre)")
    Integer getCountGradoAsignatura(String grado, String nombre);


    //@Query("SELECT * FROM asignatura WHERE asignatura.grado=:grado")
    //List<Asignatura> getAllGrado(String grado);

    @Query("SELECT * FROM asignatura WHERE UPPER(asignatura.grado)=UPPER(:grado) AND UPPER(asignatura.nombre) LIKE UPPER(:nombre)")
    List<Asignatura> getAsignaturaId(String grado, String nombre);

    //@Query("SELECT * FROM asignatura, subgrupo WHERE asignatura.id=subgrupo.asignaturaId")
    //Map<Asignatura, List<Subgrupo>> getAllSubgrupos();

    //@Query("SELECT * FROM asignatura, subgrupo WHERE asignatura.id=subgrupo.asignaturaId AND subgrupo.teoria=:teoria")
    //Map<Asignatura, List<Subgrupo>> getSubgrupo(int teoria);
}
