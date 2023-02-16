package com.efp.contratame.ar.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.efp.contratame.ar.Actividades.main.TipoServicioGetter;
import com.efp.contratame.ar.R;
import com.efp.contratame.ar.auxiliares.MisServiciosSelectListener;
import com.efp.contratame.ar.modelo.Requerimiento;
import com.efp.contratame.ar.modelo.TipoServicio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MisServiciosRecyclerAdapter extends RecyclerView.Adapter<MisServiciosRecyclerAdapter.MisServiciosViewHolder> {

    private List<Requerimiento> requerimientos;
    private List<Requerimiento> listaOriginal;
    private ViewGroup par;
    private MisServiciosSelectListener listener;

    public MisServiciosRecyclerAdapter(List<Requerimiento> dataSet, Context context, MisServiciosSelectListener listener) {
        this.requerimientos = dataSet;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(dataSet);
        this.listener=listener;
    }

    @NonNull
    @Override
    public MisServiciosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        par = parent;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fila_mis_servicios, parent, false);
        return new MisServiciosRecyclerAdapter.MisServiciosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MisServiciosViewHolder holder, int position) {

        holder.descripcion.setText(requerimientos.get(position).getDescripcion());
        holder.estado.setText("PENDIENTE");
        holder.nombreServicio.setText(requerimientos.get(position).getTitulo());

        if(requerimientos.get(position).getImagen() == null){
            Glide.with(holder.imagen.getContext()).load(android.R.drawable.ic_menu_gallery).into(holder.imagen);
        }
        else {
            holder.imagen.setImageBitmap(requerimientos.get(position).getImagen());
        }

        Log.i("aca", holder.estado.getText().toString());
        Log.i("aca", holder.estado.getText().toString());
        if( holder.estado.getText().toString().equals("PENDIENTE")){
            holder.calificar.setEnabled(false);
        }
        else {
            holder.modificar.setEnabled(false);
            holder.eliminar.setEnabled(false
            );
        }


        holder.eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("aca", "eliminar");
                AlertDialog.Builder builder = new AlertDialog.Builder(par.getContext());
                builder.setMessage("¿Está seguro que desea eliminarlo?").setTitle("Mensaje de confirmación");
                builder.setIcon(R.drawable.icono_sin_fondo);
                builder.setPositiveButton("Si", (dialogInterface, i) -> {
                    //TODO agregar método eliminar requerimiento
                    Requerimiento reqElim = requerimientos.get(holder.getBindingAdapterPosition());
                    listener.navigateToEliminar(reqElim);
                });
                builder.setNegativeButton("No", (dialogInterface, i) -> Toast.makeText(par.getContext(),
                                "No se ha eliminado su requerimiento", Toast.LENGTH_LONG)
                        .show());
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        holder.modificar.setOnClickListener(view ->
                listener.navigateToModificar(requerimientos.get(holder.getBindingAdapterPosition()))
        );
        holder.calificar.setOnClickListener(view ->
                Toast.makeText(par.getContext(),"Presiona calificar", Toast.LENGTH_LONG).show()
        );
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

    public void removeItem(Requerimiento item) {
        updateData(
                requerimientos.stream()
                        .filter(r -> !r.getIdRequerimiento().equals(item.getIdRequerimiento()))
                        .collect(Collectors.toList()));
    }

    public class MisServiciosViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layout;
        private ImageView imagen;
        private TextView descripcion;
        private TextView estado;
        private TextView nombreServicio;
        private ImageView eliminar;
        private ImageView calificar;
        private ImageView modificar;

        public MisServiciosViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.contendor_mis_servicios);
            imagen = itemView.findViewById(R.id.imagen_fila_mi_servicio);
            descripcion = itemView.findViewById(R.id.descripcion_mis_servicios);
            nombreServicio = itemView.findViewById(R.id.nombre_mis_servicio);
            estado = itemView.findViewById(R.id.estado_mis_servicios);
            eliminar=itemView.findViewById(R.id.eliminar);
            modificar=itemView.findViewById(R.id.modificar);
            calificar=itemView.findViewById(R.id.calificar);
        }


    }


}
