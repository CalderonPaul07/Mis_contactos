package ec.edu.tecnologicoloja.miscontactos;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import ec.edu.tecnologicoloja.miscontactos.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , GoogleMap.OnMapLongClickListener{

    private GoogleMap mMap;
    Button PosActual, PosNueva;
    private ActivityMapsBinding binding;
    public static final String MyPREFERENCES = "MyPrefs";


    EditText tf_latitud, tf_longitud;

    public static final String latitude = "lat";
    public static final String longitude = "lon";

    double longitud, latitud;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableMyLocation();

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        tf_latitud = findViewById(R.id.ET_latitud);
        tf_longitud=findViewById(R.id.ET_longitud);
        PosActual=findViewById(R.id.posActual);
        PosNueva=findViewById(R.id.posNueva);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        PosActual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MiPosicion();

            }
        });


        PosNueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddressText();
            }
        });

    }

    public void MiPosicion() {
        LocationManager objLocation = null;
        LocationListener objLocListener;
        objLocation = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        objLocListener = new MyPosition();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, 1000);
        }

        objLocation.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, objLocListener);

        if (objLocation.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (MyPosition.latitude != 0) {
                latitud = MyPosition.latitude;
                longitud = MyPosition.longitude;
                tf_longitud.setText(longitud + "");
                tf_latitud.setText(latitud + "");
                Toast.makeText(MapsActivity.this, "Latitud" + latitud + "Longitud" + longitud, Toast.LENGTH_SHORT).show();
//                Se declara una variable de tipo LatLng (Latitud, Longitud) y en ella se instancia los valores obtenidos de mi posicion
                LatLng miPosicio = new LatLng(latitud, longitud);
//                Se agrega esa variable latlng en posicion y se agrega un nombre a esa marca
                Marker mi_ubicacion = mMap.addMarker(new MarkerOptions().position(miPosicio).title("Mi casa").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
//                Se realiza un zoom de 16.0 al momento de obtener posicion
                float zoomLevel = 18.0f;

//                De acuerdo a donde se este mueve el mapa hacia los valores determinados antes y se agrega el zoom
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(miPosicio, zoomLevel));

            }

        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        mMap.setOnMapLongClickListener(this);


    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public void onMapLongClick(@NonNull LatLng latLng) {
        Toast.makeText(MapsActivity.this, "Latitud" + latLng.latitude + "\n Longitud" + latLng.longitude, Toast.LENGTH_LONG).show();
        Marker mi_ubicacion = mMap.addMarker(new MarkerOptions().position(latLng).title("Ubición Guardada").icon(bitmapDescriptorFromVector(this, R.drawable.baseline_room_24)));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(latitude, (float) latLng.latitude);
        editor.putFloat(longitude, (float) latLng.longitude);
        editor.apply();
        double la = sharedPreferences.getFloat(latitude, 0);
        double lo = sharedPreferences.getFloat(longitude, 0);
        tf_longitud.setText(la + "");
        tf_latitud.setText(lo+ "");
    }

    private String getAddressFromCoordinates(LatLng latLng) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                return address.getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void AddressText() {
        String lat = tf_latitud.getText().toString();
        String lon = tf_longitud.getText().toString();

        double lati = Double.parseDouble(lat);
        double longi = Double.parseDouble(lon);

        LatLng latLng = new LatLng(lati, longi);
        String address = getAddressFromCoordinates(latLng);
        if (address != null) {
            Intent intent = new Intent(MapsActivity.this, NewContacts.class);
            intent.putExtra("direction", address);
            startActivity(intent);
            finish();
        }
    }
    public void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        }
    }
}