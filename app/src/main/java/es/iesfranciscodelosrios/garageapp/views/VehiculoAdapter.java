package es.iesfranciscodelosrios.garageapp.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import es.iesfranciscodelosrios.garageapp.R;
import es.iesfranciscodelosrios.garageapp.models.Vehiculo;

public class VehiculoAdapter extends RecyclerView.Adapter<VehiculoAdapter.VehiculoViewHolder> implements View.OnClickListener {

    private ArrayList<Vehiculo> items;
    private View.OnClickListener listener;

        // Clase interna:
        // Se implementa el ViewHolder que se encargará
        // de almacenar la vista del elemento y sus datos
        public static class VehiculoViewHolder extends RecyclerView.ViewHolder {

            private TextView listMarca;
            private TextView listModelo;
            private ImageView listImagen;

            public VehiculoViewHolder(View itemView) {
                super(itemView);
                listMarca = itemView.findViewById(R.id.listMarca);
                listModelo = itemView.findViewById(R.id.listModelo);
                listImagen = itemView.findViewById(R.id.listImagen);
            }

            public void VehiculoBind(Vehiculo item) {
                listMarca.setText(item.getMarca());
                listModelo.setText(item.getModelo());
                try{
                    byte[] decodedString = Base64.decode(item.getImagen(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(
                            decodedString,
                            0,
                            decodedString.length);
                    listImagen.setImageBitmap(decodedByte);
                } catch (NullPointerException e) {};

            }
        }

    // Contruye el objeto adaptador recibiendo la lista de datos
    public VehiculoAdapter(@NonNull ArrayList<Vehiculo> items) {
        this.items = items;
    }

    // Se encarga de crear los nuevos objetos ViewHolder necesarios
    // para los elementos de la colección.
    // Infla la vista del layout, crea y devuelve el objeto ViewHolder
    @Override
    public VehiculoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_data, parent, false);
        row.setOnClickListener(this);

        VehiculoViewHolder avh = new VehiculoViewHolder(row);
        return avh;
    }

    // Se encarga de actualizar los datos de un ViewHolder ya existente.
    @Override
    public void onBindViewHolder(VehiculoViewHolder viewHolder, int position) {
        Vehiculo item = items.get(position);
        viewHolder.VehiculoBind(item);
    }

    // Indica el número de elementos de la colección de datos.
    @Override
    public int getItemCount() {
        return items.size();
    }

    // Asigna un listener al elemento
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }

}
