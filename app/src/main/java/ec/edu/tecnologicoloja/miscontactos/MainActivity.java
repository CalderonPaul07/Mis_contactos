package ec.edu.tecnologicoloja.miscontactos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import database.PersonaLab;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_contact);
        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.navigation_view);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_closed);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Contact_fragment()).commit();
            //navigationView.setCheckedItem(R.id.nav_contactos);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_contactos) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Contact_fragment()).commit();

        } else if (itemId == R.id.nav_eliminar) {

            PersonaLab lab= new PersonaLab(this);
            lab.deleteAllPersona();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Contact_fragment()).commit();
            Toast.makeText(MainActivity.this, "Eliminaste tus Contactos", Toast.LENGTH_LONG).show();


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}