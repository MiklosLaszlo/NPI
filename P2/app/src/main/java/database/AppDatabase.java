package database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.Room;

// Lo hago estilo singleton para evitar crear varios desde distintas hebras

@Database(entities = {Asignatura.class, Subgrupo.class, Hora.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "horario_database.db";
    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }
    protected AppDatabase() {}; // Para que no llamemos al constructor
    private static AppDatabase create(final Context context) {
        return Room.databaseBuilder( context, AppDatabase.class, DB_NAME)
                .createFromAsset("database/" + DB_NAME)
                .allowMainThreadQueries()
                .build();
    }

    public abstract AsignaturaDAO asignaturaDAO();
    public abstract SubgrupoDAO subgrupoDAO();
}

