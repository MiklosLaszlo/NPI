package database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Subgrupo.class, parentColumns = "id", childColumns = "subgrupoId", onDelete = ForeignKey.CASCADE)})
public class Hora {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String horaInicio;
    public String horaFin;
    public int dia;
    public int clase;
    public int subgrupoId;
}

