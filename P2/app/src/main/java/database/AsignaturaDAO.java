package database;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;
import java.util.Map;

@Dao
public interface AsignaturaDAO {
    @Query("SELECT * FROM Asignatura")
    List<Asignatura> getAll();

    @Query("SELECT * FROM asignatura WHERE asignatura.grado=:grado")
    List<Asignatura> getAllGrado(String grado);

    @Query("SELECT id FROM asignatura WHERE asignatura.grado=:grado AND UPPER(asignatura.nombre) LIKE UPPER(:nombre)")
    List<Integer> getAsignaturaId(String grado, String nombre);

    @Query("SELECT * FROM asignatura, subgrupo WHERE asignatura.id=subgrupo.asignaturaId")
    Map<Asignatura, List<Subgrupo>> getAllSubgrupos();

    @Query("SELECT * FROM asignatura, subgrupo WHERE asignatura.id=subgrupo.asignaturaId AND subgrupo.teoria=:teoria")
    Map<Asignatura, List<Subgrupo>> getSubgrupo(int teoria);

}
