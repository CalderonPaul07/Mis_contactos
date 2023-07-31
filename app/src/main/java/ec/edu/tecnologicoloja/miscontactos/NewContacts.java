package ec.edu.tecnologicoloja.miscontactos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import database.Persona;
import database.PersonaLab;

public class NewContacts extends AppCompatActivity {

    ImageButton btn_new_dire;
    EditText newNameJava, newNumberJava, newEmailJava, newDirecJava;
    PersonaLab personaLab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contacts);

        personaLab = new PersonaLab(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_new_contact);
        setSupportActionBar(toolbar);

        btn_new_dire = findViewById(R.id.btn_direction);
        newNameJava = findViewById(R.id.new_name);
        newNumberJava = findViewById(R.id.new_phone);
        newEmailJava = findViewById(R.id.new_email);
        newDirecJava = findViewById(R.id.new_direction);

        //Obtener la dirección del mapa
        Intent intent = getIntent();
        String direccionCalle = intent.getStringExtra("direction");
        if (direccionCalle != null) {
            newDirecJava.setText(direccionCalle);
        } else {
            newDirecJava.setText("");
        }

        //bonton para acceder al mapa
        btn_new_dire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(NewContacts.this,MapsActivity.class);
                startActivity(intent);

            }
        });



    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_contact, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save) {
            // Validar que todos los campos estén completos
            String nombre = newNameJava.getText().toString().trim();
            String telefono = newNumberJava.getText().toString().trim();

            if (nombre.isEmpty() ||telefono.isEmpty()) {
                // Mostrar un mensaje de error indicando que todos los campos son obligatorios
                Toast.makeText(NewContacts.this, "Por favor, complete todos los campos", Toast.LENGTH_LONG).show();
                return true; // No hacemos nada más porque los campos están incompletos
            }

            // Si todos los campos están completos, proceder a guardar el usuario
            Persona usuarios = new Persona();
            usuarios.setDireccion(newDirecJava.getText().toString().trim());
            usuarios.setNombre(nombre);
            usuarios.setEmail(newEmailJava.getText().toString().trim());
            usuarios.setNumero(telefono);

            // Guardar el usuario en la base de datos utilizando Room
            personaLab.addPersona(usuarios);

            Toast.makeText(NewContacts.this, "Guardado correctamente", Toast.LENGTH_LONG).show();

            // En la actividad que contiene los fragmentos
            Contact_fragment fragmentB = new Contact_fragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragmentB);
            fragmentTransaction.addToBackStack(null); // Opcional: agrega el fragmento actual a la pila de retroceso
            fragmentTransaction.commit();

            // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ContactFragment()).commit();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }






}


