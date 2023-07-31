package adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import database.Persona;
import ec.edu.tecnologicoloja.miscontactos.R;

public class ListAdapterPersona extends ArrayAdapter<Persona> {

    private final Context context;
    private final List<Persona> list;
    List<Integer> listColors;
    public ListAdapterPersona(Context context, List<Persona> list) {
        super(context, R.layout.context_list);
        this.context = context;
        this.list = list;
    }
    public Persona getPosicion(int position) {
        return list.get(position);
    }

    public int getCount() {
        return list.size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        final ViewHolder viewHolder;

        if (convertView == null || convertView.getTag() == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.context_list, parent, false);
            viewHolder.vItemName = (TextView) view.findViewById(R.id.list_name);
            viewHolder.vItemNumber = (TextView) view.findViewById(R.id.list_number);
            viewHolder.vItemLetter = (TextView) view.findViewById(R.id.txtChar);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }

        viewHolder.vItemName.setText(list.get(position).getNombre());

        viewHolder.vItemNumber.setText(list.get(position).getNumero());
        String letra = list.get(position).getNombre();
        char primeraletra = letra.charAt(0);
        viewHolder.vItemLetter.setText(String.valueOf(primeraletra));

        listColors = new ArrayList<>();

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
        viewHolder.vItemLetter.setBackground(shaDrawable);

        return view;

    }

    static class ViewHolder {
        protected TextView vItemName;
        protected TextView vItemNumber;
        protected TextView vItemLetter;

    }
}
