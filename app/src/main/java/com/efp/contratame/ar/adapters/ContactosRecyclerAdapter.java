package com.efp.contratame.ar.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.efp.contratame.ar.R;
import com.efp.contratame.ar.modelo.Prestador;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactosRecyclerAdapter extends RecyclerView.Adapter<ContactosRecyclerAdapter.ContactoViewHolder>{

    private List<Prestador> listaContactosOriginal;
    private List<Prestador> contactosDataSet;
    private Context ctx;

    public ContactosRecyclerAdapter(List<Prestador> dataSet, Context context) {
        this.contactosDataSet = dataSet;
        listaContactosOriginal = new ArrayList<>();
        listaContactosOriginal.addAll(contactosDataSet);
        this.ctx = context;
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacto, parent, false);
        return new ContactosRecyclerAdapter.ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        String EDteamImage = contactosDataSet.get(position).getImagenPerfil();
        Glide.with(holder.imagen.getContext()).load(EDteamImage).into(holder.imagen);
        holder.nombre.setText(contactosDataSet.get(position).getNombre());

    }

    @Override
    public int getItemCount() {
        return contactosDataSet.size();
    }

    public void filtrado(String texto) {
        Log.d("TEXTO", texto);
        int longitud = texto.length();
        if(longitud==0){
            contactosDataSet.clear();
            contactosDataSet.addAll(listaContactosOriginal);


            //TODO Capaz podríamos mostrar un mensaje que diga "no se encontraron resultados para la búsqueda"
        }else{
            List<Prestador> coleccion =  new ArrayList<Prestador>();
            coleccion= contactosDataSet.stream()
                    .filter(i -> i.getNombre().toLowerCase().contains(texto.toLowerCase()))
                    .collect(Collectors.toList());

            contactosDataSet=coleccion;
        }
        notifyDataSetChanged();
    }

    class ContactoViewHolder extends RecyclerView.ViewHolder{

        private CardView card;
        private CircleImageView imagen;
        private TextView nombre;
        private TextView ultimoMensaje;
        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.cardView);
            imagen = itemView.findViewById(R.id.imagen_contacto);
            nombre = itemView.findViewById(R.id.tv_nombreContacto);
            ultimoMensaje = itemView.findViewById(R.id.tv_ultimoMensaje);


        }
    }

}
