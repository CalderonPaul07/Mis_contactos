package database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PersonaDao {

    @Query("SELECT * FROM Persona")
    List<Persona> getPersona();

    @Query("SELECT * FROM Persona WHERE nombre like :uuide")
    Persona getPersona(String uuide);

    @Insert
    void addPersona(Persona p);

    @Delete
    void deletePersona(Persona p);

    @Update
    void updatePersona(Persona p);

    @Query("DELETE FROM Persona")
    void deleteAllPersona();
}
