package com.efp.contratame.ar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.efp.contratame.ar.databinding.ItemGaleriaImagenBinding;

import java.util.List;

public class GaleriaRecyclerAdapter extends RecyclerView.Adapter<GaleriaRecyclerAdapter.ViewHolder>{

    private List<String> imagenes;
    private Context ctx;

    public GaleriaRecyclerAdapter(List<String> imagenes, Context ctx) {
        this.imagenes = imagenes;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_galeria_imagen,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("IMAGENES", imagenes.get(position));
        Glide.with(holder.imagen.getContext()).load(imagenes.get(position)).into(holder.imagen);
    }

    @Override
    public int getItemCount() {
        return imagenes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView card;
        ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            imagen = itemView.findViewById(R.id.imagen);
        }
    }
}
