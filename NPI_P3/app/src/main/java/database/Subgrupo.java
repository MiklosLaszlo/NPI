package database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Asignatura.class, parentColumns = "id", childColumns = "asignaturaId", onDelete = ForeignKey.CASCADE)})
public class Subgrupo {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int asignaturaId;
    public String nombre;
    public boolean teoria;
}