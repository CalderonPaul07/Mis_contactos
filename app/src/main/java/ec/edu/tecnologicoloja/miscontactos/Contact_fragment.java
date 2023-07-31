package ec.edu.tecnologicoloja.miscontactos;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import adapter.ListAdapterPersona;
import database.Persona;
import database.PersonaLab;


public class Contact_fragment extends Fragment {
    FloatingActionButton btn_new;

    ListView listViewContactos;

    ListAdapter listAdapterPersona;

    PersonaLab personaLab;

    ArrayList<Persona> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_fragment, container, false);

        btn_new = (FloatingActionButton) view.findViewById(R.id.floating_new_contact);
        listViewContactos = view.findViewById(R.id.contact_list);
        personaLab= new PersonaLab(requireContext());

        llenarDatosUsuarios();

        if (list !=null&&list.size()>0){

            listViewContactos.setVisibility(View.VISIBLE);
            listAdapterPersona= new ListAdapterPersona(requireContext(),list);
            listViewContactos.setAdapter(listAdapterPersona);
            listViewContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    if (i >= 0 && i < list.size()) {

                        Persona usuariosItem = list.get(i);

                        Intent intent = new Intent(requireContext(), DetailContacts.class);
                        intent.putExtra("id", usuariosItem.getId());
                        intent.putExtra("nombre", usuariosItem.getNombre());
                        intent.putExtra("direccion", usuariosItem.getDireccion());
                        intent.putExtra("telefono", usuariosItem.getNumero());
                        intent.putExtra("correo", usuariosItem.getEmail());


                        startActivity(intent);
                    }

                }
            });

        }else {
            listViewContactos.setVisibility(View.GONE);
        }


        //Boton Para Cambiar a la Ventana de nuevo contacto
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewContacts.class);
                startActivity(intent);

            }
        });
        return view;

    }


    public void llenarDatosUsuarios() {
        list.clear();
        try {
            list.addAll(personaLab.getPersonas());
        } catch (Exception e) {
            Log.i(null, "Error: " + e);

        }
    }
}