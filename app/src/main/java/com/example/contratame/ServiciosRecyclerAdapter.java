package com.example.contratame;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
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
import com.example.contratame.databinding.FilaServicioBinding;
import com.example.contratame.modelo.Servicio;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class ServiciosRecyclerAdapter extends RecyclerView.Adapter<ServiciosRecyclerAdapter.ServiciosViewHolder> {

    private Context ctx;
    private List<Servicio> serviciosDataSet;

    public ServiciosRecyclerAdapter(List<Servicio> dataSet, Context context){
        this.serviciosDataSet=dataSet;
        this.ctx=context;
    }

    @NonNull
    @Override
    public ServiciosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_servicio, parent,false);
      return new ServiciosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiciosViewHolder holder, int position) {
        holder.nombrePersona.setText(serviciosDataSet.get(position).getPrestador().getNombre());
        holder.descripcion.setText(serviciosDataSet.get(position).getDescripcion());
        holder.puntuacion.setRating(serviciosDataSet.get(position).getPuntuacion());
        // ponerle imagen

        String EDteamImage = serviciosDataSet.get(position).getPrestador().getImagen_perfil();
        Glide.with(holder.imagen.getContext()).load(EDteamImage).into(holder.imagen);
       // Glide.with(holder.imagen.).load(url).placeholder(R.drawable.default_profile).dontAnimate().into(view);
      Log.i("URL", EDteamImage);
     /*   GlideApp.with(holder.imagen.getContext())
                .load(EDteamImage)
                .placeholder(R.mipmap.ic_launcher)
                .circleCrop()
                .error(R.mipmap.ic_launcher)
                .into(holder.imagen);
*/
    }

    @Override
    public int getItemCount() { return serviciosDataSet.size(); }


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
