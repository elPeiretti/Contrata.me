package com.efp.contratame.ar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.efp.contratame.ar.R;
import com.efp.contratame.ar.modelo.Requerimiento;

import java.util.ArrayList;
import java.util.List;

public class MisServiciosRecyclerAdapter extends RecyclerView.Adapter<MisServiciosRecyclerAdapter.MisServiciosViewHolder>{

    private List<Requerimiento> requerimientos;
    private List<Requerimiento> listaOriginal;

    public MisServiciosRecyclerAdapter(List<Requerimiento> dataSet, Context context) {
        this.requerimientos = dataSet;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(dataSet);
    }

    @NonNull
    @Override
    public MisServiciosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_mis_servicios, parent, false);
        return new MisServiciosRecyclerAdapter.MisServiciosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MisServiciosViewHolder holder, int position) {
        //TODO cambiar porque no se si iria el nombre del prestador, si no tiene a nadie asignado entonces no tiene
        holder.nombrePrestador.setText(requerimientos.get(position).getTitulo());
        holder.estado.setText("PENDIENTE");
        holder.nombreServicio.setText(requerimientos.get(position).getTitulo());
        //TODO falta imagen
        //holder.imagen.setImageBitmap(requerimientos.get(position).getImagen());
    }

    @Override
    public int getItemCount() {
        return requerimientos.size();
    }

    public void updateData(List<Requerimiento> req) {
        requerimientos.clear();
        requerimientos.addAll(req);
        notifyDataSetChanged();
    }

    public class MisServiciosViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layout;
        private ImageView imagen;
        private TextView nombrePrestador;
        private TextView estado;
        private TextView nombreServicio;

        public MisServiciosViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.contendor_mis_servicios);
            imagen= itemView.findViewById(R.id.imagen_perfil);
            nombrePrestador=itemView.findViewById(R.id.nombre_mis_prestador);
            nombreServicio= itemView.findViewById(R.id.nombre_mis_servicio);
            estado = itemView.findViewById(R.id.estado_mis_servicios);
        }
    }


}
