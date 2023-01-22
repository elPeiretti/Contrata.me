package com.efp.contratame.ar.Actividades.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.efp.contratame.ar.R;
import com.efp.contratame.ar.modelo.TipoServicio;

import java.util.List;

public class ServicioIconRecyclerAdapter extends RecyclerView.Adapter<ServicioIconRecyclerAdapter.ViewHolder>{

    private List<TipoServicio> listaTipos;
    private LayoutInflater mInflater;

    ServicioIconRecyclerAdapter(Context ctx, List<TipoServicio> dataSet){
        this.mInflater = LayoutInflater.from(ctx);
        this.listaTipos = dataSet;
    }

    @NonNull
    @Override
    public ServicioIconRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.icon_servicio,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServicioIconRecyclerAdapter.ViewHolder holder, int position) {
        //setear nombre e imagen al tiposervicio
        holder.nombre.setText(listaTipos.get(position).getNombre());
        Glide
                .with(holder.icono.getContext())
                .load(listaTipos.get(position).getIcono())
                .into(holder.icono);
    }

    @Override
    public int getItemCount() {
        return listaTipos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icono;
        TextView nombre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icono = itemView.findViewById(R.id.service_icon);
            nombre = itemView.findViewById(R.id.service_name);
        }
    }
}
