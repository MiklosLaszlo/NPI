package database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Asignatura {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nombre;
    public String grado;
    public int cuatri;
}
