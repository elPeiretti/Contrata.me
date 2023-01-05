package com.efp.contratame.ar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.efp.contratame.ar.databinding.FilaServicioBinding;
import com.efp.contratame.ar.modelo.Servicio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ServiciosRecyclerAdapter extends RecyclerView.Adapter<ServiciosRecyclerAdapter.ServiciosViewHolder> {

    private Context ctx;
    private List<Servicio> serviciosDataSet;
    private List<Servicio> listaOriginal;

    public ServiciosRecyclerAdapter(List<Servicio> dataSet, Context context) {
        this.serviciosDataSet = dataSet;
        this.ctx = context;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(serviciosDataSet);
    }

    @NonNull
    @Override
    public ServiciosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_servicio, parent, false);
        return new ServiciosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiciosViewHolder holder, int position) {
        holder.nombrePersona.setText(serviciosDataSet.get(position).getPrestador().getNombre());
        holder.descripcion.setText(serviciosDataSet.get(position).getDescripcion());
        holder.puntuacion.setRating(serviciosDataSet.get(position).getPuntuacion());

        String EDteamImage = serviciosDataSet.get(position).getPrestador().getImagen_perfil();
        Glide.with(holder.imagen.getContext()).load(EDteamImage).into(holder.imagen);
    }

    @Override
    public int getItemCount() {
        return serviciosDataSet.size();
    }

    public void filtrado(String txtBuscar){
        int longitud = txtBuscar.length();
        if(longitud==0){
            serviciosDataSet.clear();
            serviciosDataSet.addAll(listaOriginal);

            //TODO Capaz podríamos mostrar un mensaje que diga "no se encontraron resultados para la búsqueda"
        }else{
            List<Servicio> coleccion =  new ArrayList<Servicio>();
            coleccion= serviciosDataSet.stream()
                    .filter(i -> i.getPrestador().getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                    .collect(Collectors.toList());

            serviciosDataSet=coleccion;
        }
        notifyDataSetChanged();
    }

    public void ordenar(String ordenarPor) {
        switch (ordenarPor){
            case "Mejor puntuación primero":
                listaOriginal =serviciosDataSet.stream().sorted(Comparator.comparing(servicio -> servicio.getPuntuacion()))
                        .collect(Collectors.toList());
                Collections.reverse(listaOriginal);
                break;
            case "Peor puntuación primero":
                listaOriginal =serviciosDataSet.stream().sorted(Comparator.comparing(servicio -> servicio.getPuntuacion()))
                        .collect(Collectors.toList());
                break;
            case "A-Z":
                listaOriginal =serviciosDataSet.stream().sorted(Comparator.comparing(servicio -> servicio.getPrestador().getNombre()))
                        .collect(Collectors.toList());
                break;
            case "Z-A":
                listaOriginal =serviciosDataSet.stream().sorted(Comparator.comparing(servicio -> servicio.getPrestador().getNombre()))
                        .collect(Collectors.toList());
                Collections.reverse(listaOriginal);
                break;
        }
        serviciosDataSet=listaOriginal;
        notifyDataSetChanged();
    }



    public class ServiciosViewHolder extends RecyclerView.ViewHolder{

        CardView card;
        ImageView imagen;
        TextView nombrePersona;
        TextView descripcion;
        RatingBar puntuacion;

        public ServiciosViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            imagen= itemView.findViewById(R.id.imagen_perfil);
            nombrePersona= itemView.findViewById(R.id.nombre_persona);
            descripcion = itemView.findViewById(R.id.descrpcion);
            puntuacion = itemView.findViewById(R.id.ratingBar);
        }
    }


}
