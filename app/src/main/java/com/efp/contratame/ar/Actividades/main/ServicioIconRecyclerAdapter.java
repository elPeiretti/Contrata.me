package com.efp.contratame.ar.Actividades.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.efp.contratame.ar.R;
import com.efp.contratame.ar.modelo.Servicio;
import com.efp.contratame.ar.modelo.TipoServicio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServicioIconRecyclerAdapter extends RecyclerView.Adapter<ServicioIconRecyclerAdapter.ViewHolder>{

    private List<TipoServicio> listaTipos;
    private List<TipoServicio> listaOriginal;
    private LayoutInflater mInflater;
    private OnTipoServicioSelectedListener comListener;

    ServicioIconRecyclerAdapter(Context ctx, List<TipoServicio> dataSet, OnTipoServicioSelectedListener handler){
        this.mInflater = LayoutInflater.from(ctx);
        this.listaTipos = dataSet;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaTipos);
        this.comListener = handler;
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

        //setear listener al icono
        holder.icono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //este llama al metodo del fragment, y el del fragment llama al de la actividad, updateando el tiposervicio seleccionado.
                comListener.onTipoServicioSelected(listaTipos.get(holder.getAdapterPosition()));
                Navigation.findNavController(view).navigate(R.id.action_menuPpalFragment2_to_resultadosServiciosFragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaTipos.size();
    }

    public void filtrado(String s) {
        int longitud = s.length();
        if(longitud==0){
           listaTipos=listaOriginal;

            //TODO Capaz podríamos mostrar un mensaje que diga "no se encontraron resultados para la búsqueda"
        }else{
            List<TipoServicio> coleccion =  new ArrayList<TipoServicio>();
            coleccion= listaTipos.stream()
                    .filter(i -> i.getNombre().toLowerCase().contains(s.toLowerCase()))
                    .collect(Collectors.toList());

            listaTipos=coleccion;
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton icono;
        TextView nombre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icono = itemView.findViewById(R.id.service_icon);
            nombre = itemView.findViewById(R.id.service_name);
        }
    }

    public interface OnTipoServicioSelectedListener {
        void onTipoServicioSelected(TipoServicio tp);
    }
}
