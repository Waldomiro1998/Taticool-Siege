package com.example.taticoolsiege.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taticoolsiege.R;
import com.example.taticoolsiege.model.Mapas;

import java.util.List;

public class AdapterItemCreation extends RecyclerView.Adapter<AdapterItemCreation.MyViewHolder> {
    private List<Mapas> listMapaas;
    private Context context;

    public AdapterItemCreation(List<Mapas> lista, Context context) {
        this.listMapaas=lista;
        this.context=context;
    }

    @NonNull
    @Override
    public AdapterItemCreation.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recycler_layout_item_creations,parent,false);
        return new MyViewHolder(itemLista);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull AdapterItemCreation.MyViewHolder holder, int position) {

        Mapas mapas=listMapaas.get(position);
        holder.nome.setText(mapas.getNome());
        holder.foto.setImageResource(mapas.getFoto());

    }

    @Override
    public int getItemCount() {
        return listMapaas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        //Aqui ficam os atributos da listagem

        TextView nome;
        ImageView foto;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nome=itemView.findViewById(R.id.textNome);
            foto=itemView.findViewById(R.id.imageMapa);

        }
    }

}
