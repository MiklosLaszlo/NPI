package database;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;
import java.util.Map;

@Dao
public interface HoraDAO {

    @Query("SELECT * FROM hora WHERE hora.subgrupoId = :id_subgrupo ORDER BY hora.dia")
    List<Hora> getHorasSubgrupo(int id_subgrupo);

}
