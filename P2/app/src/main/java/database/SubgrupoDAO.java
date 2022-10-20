package database;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;
import java.util.Map;

@Dao
public interface SubgrupoDAO {
    @Query("SELECT * FROM subgrupo")
    List<Subgrupo> getAll();

    //@Query("SELECT id FROM subgrupo WHERE UPPER(subgrupo.nombre)")
    //List<Integer> getIdSubgrupo(String nombre);

    @Query("SELECT * FROM subgrupo WHERE subgrupo.asignaturaId = :id_asignatura")
    List<Subgrupo> getSubgruposAsignatura(int id_asignatura);

    //Query("SELECT * FROM subgrupo, hora WHERE subgrupo.id = hora.subgrupoId AND subgrupoId = :id")
    //Map<Subgrupo, List<Hora>> getHoras(int id);
}
