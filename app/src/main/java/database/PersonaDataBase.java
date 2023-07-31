package database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Persona.class},version = 2)
public abstract class PersonaDataBase extends RoomDatabase {

    public abstract PersonaDao getPersonaDao();
}
