package com.efp.contratame.ar.Actividades.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.efp.contratame.ar.R;
import com.efp.contratame.ar.modelo.Servicio;

import java.util.List;

public class ServicioIconRecyclerAdapter extends RecyclerView.Adapter<ServicioIconRecyclerAdapter.ViewHolder>{

    private List<Servicio> listaServicios;
    private LayoutInflater mInflater;

    ServicioIconRecyclerAdapter(Context ctx, List<Servicio> dataSet){
        this.mInflater = LayoutInflater.from(ctx);
        this.listaServicios = dataSet;
    }

    @NonNull
    @Override
    public ServicioIconRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.icon_servicio,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServicioIconRecyclerAdapter.ViewHolder holder, int position) {
        //TODO setear imagen servicio
    }

    @Override
    public int getItemCount() {
        return listaServicios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
