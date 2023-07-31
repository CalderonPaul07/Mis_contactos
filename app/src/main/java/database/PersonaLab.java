package database;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.room.Room;

import java.util.List;

public class PersonaLab {

    @SuppressLint("StaticFieldLeak")
    private static PersonaLab usuariosLab;

    private PersonaDao  personaDao;

    public PersonaLab(Context context) {
        Context appContext = context.getApplicationContext();
        PersonaDataBase usuarioDataBase = Room.databaseBuilder(appContext, PersonaDataBase.class, "contactos").allowMainThreadQueries().build();
        personaDao = usuarioDataBase.getPersonaDao();

    }

    public static PersonaLab get(Context context) {
        if (usuariosLab == null) {
            usuariosLab = new PersonaLab(context);
        }
        return usuariosLab;
    }

    public List<Persona> getPersonas() {
        return personaDao.getPersona();
    }

    public Persona getPersona(String id) {
        return personaDao.getPersona(id);
    }

    public void addPersona(Persona persona) {
        personaDao.addPersona(persona);
    }

    public void updatePersona(Persona persona) {
        personaDao.updatePersona(persona);
    }

    public void deletePersona(Persona persona) {
        personaDao.deletePersona(persona);
    }

    public void deleteAllPersona() {
        personaDao.deleteAllPersona();
    }
}
