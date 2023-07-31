package ec.edu.tecnologicoloja.miscontactos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashContatcs extends AppCompatActivity {

    private final int duracion = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_contatcs);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent= new Intent(SplashContatcs.this,MainActivity.class);
                startActivity(intent);
//                Contact_fragment fragmentB = new Contact_fragment();
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragmentB);
//                fragmentTransaction.addToBackStack(null); // Opcional: agrega el fragmento actual a la pila de retroceso
//                fragmentTransaction.commit();

            }
        },duracion);
    }
}