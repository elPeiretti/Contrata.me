package com.efp.contratame.ar.Actividades.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.efp.contratame.ar.R;
import com.efp.contratame.ar.modelo.Comentario;
import java.util.List;

public class ComentarioRecyclerAdapter extends RecyclerView.Adapter<ComentarioRecyclerAdapter.ComentarioViewHolder>{

    private List<Comentario> comentarioList;
    private final LayoutInflater mInflater;

    public ComentarioRecyclerAdapter(Context ctx, List<Comentario> dataSet){
        this.mInflater = LayoutInflater.from(ctx);
        this.comentarioList = dataSet;
    }

    @NonNull
    @Override
    public ComentarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ComentarioViewHolder(mInflater.inflate(R.layout.fila_comentario, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ComentarioViewHolder holder, int position) {
        holder.usuario.setText(comentarioList.get(position).getUsuario());
        holder.contenido.setText(comentarioList.get(position).getContenido());
        holder.fecha.setText(comentarioList.get(position).getFecha().toString());
    }

    @Override
    public int getItemCount() {
        return comentarioList.size();
    }

    public void updateData(List<Comentario> comentarios) {
        comentarioList.clear();
        comentarioList.addAll(comentarios);
        notifyDataSetChanged();
    }

    public static class ComentarioViewHolder extends RecyclerView.ViewHolder {

        TextView fecha;
        TextView usuario;
        TextView contenido;

        public ComentarioViewHolder(@NonNull View itemView) {
            super(itemView);

            usuario = itemView.findViewById(R.id.tv_usuario);
            contenido = itemView.findViewById(R.id.tv_desc_comentario);
            fecha = itemView.findViewById(R.id.tv_fecha_comm);
        }
    }

}
