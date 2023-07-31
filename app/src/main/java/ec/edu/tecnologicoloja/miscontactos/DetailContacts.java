package ec.edu.tecnologicoloja.miscontactos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import database.Persona;
import database.PersonaLab;

public class DetailContacts extends AppCompatActivity {

    EditText editxDirection, editxNumber, editxName, editxEmail;

    TextView chart_firts;

    PersonaLab personaLab;

    int idLista;

    List<Persona> personaList;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contacts);

        personaLab = new PersonaLab(this);

        chart_firts = findViewById(R.id.firs_chart_edit);
        editxName = findViewById(R.id.edit_name);
        editxNumber = findViewById(R.id.edit_phone);
        editxEmail = findViewById(R.id.edit_email);
        editxDirection = findViewById(R.id.edit_direction);

        Intent intent = getIntent();
        String nombre = intent.getStringExtra("nombre");
        String direct = intent.getStringExtra("direccion");
        String correo = intent.getStringExtra("correo");
        String numero = intent.getStringExtra("telefono");
        idLista = intent.getIntExtra("id", 0);

        ArrayList<Integer> listColors = new ArrayList<>();

        // Agregar los colores RGB a la lista
        listColors.add(Color.rgb(209, 242, 235));
        listColors.add(Color.rgb(208, 236, 231));
        listColors.add(Color.rgb(163, 228, 215));
        listColors.add(Color.rgb(118, 215, 196));
        listColors.add(Color.rgb(72, 201, 176));
        listColors.add(Color.rgb(162, 217, 206));
        listColors.add(Color.rgb(115, 198, 182));
        listColors.add(Color.rgb(69, 179, 157));


        Random random = new Random();
        int ramdomIndex = random.nextInt(listColors.size());
        int ramdomColor = listColors.get(ramdomIndex);

        GradientDrawable shaDrawable = new GradientDrawable();
        shaDrawable.setShape(GradientDrawable.OVAL);
        shaDrawable.setColor(ramdomColor);
        int borderRadius = 100;
        shaDrawable.setCornerRadius(borderRadius);

        char primeraletra = nombre.charAt(0);
        chart_firts.setText(String.valueOf(primeraletra));
        chart_firts.setBackground(shaDrawable);

        editxDirection.setText(direct);
        editxName.setText(nombre);
        editxEmail.setText(correo);
        editxNumber.setText(numero);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_edit_contact);
        setSupportActionBar(toolbar);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_contatcs, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_e) {
            Persona persona = new Persona(idLista);
            persona.setNombre(editxName.getText().toString());
            persona.setNumero(editxNumber.getText().toString());
            persona.setEmail(editxEmail.getText().toString());
            persona.setDireccion(editxDirection.getText().toString());
            personaLab.updatePersona(persona);
            Contact_fragment fragmentB = new Contact_fragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragmentB);
            fragmentTransaction.addToBackStack(null); // Opcional: agrega el fragmento actual a la pila de retroceso
            fragmentTransaction.commit();


        } else if (id == R.id.delete_e) {
//            System.out.println("ID: " + idLista);
            Persona persona = new Persona(idLista);
            personaLab.deletePersona(persona);

            Contact_fragment fragmentB = new Contact_fragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragmentB);
            fragmentTransaction.addToBackStack(null); // Opcional: agrega el fragmento actual a la pila de retroceso
            fragmentTransaction.commit();

        }

        return super.onOptionsItemSelected(item);
    }

}