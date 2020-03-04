package com.example.taticoolsiege.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taticoolsiege.R;
import com.example.taticoolsiege.model.Mapas;
import com.example.taticoolsiege.model.Taticas;

import java.util.List;

public class AdapterMyList extends RecyclerView.Adapter<AdapterMyList.MyViewHolder> {
    private List<Taticas> listTaticas;
    private Context context;

    public AdapterMyList(List<Taticas> lista, Context context) {
        this.listTaticas=lista;
        this.context=context;
    }

    @NonNull
    @Override
    public AdapterMyList.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recycler_layout_my_list,parent,false);
        return new MyViewHolder(itemLista);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull AdapterMyList.MyViewHolder holder, int position) {

        Taticas tatica=listTaticas.get(position);
        holder.nome.setText(tatica.getNome());

        if(tatica.getMapa().equals("Litoral")){

            holder.foto.setImageResource(R.drawable.litoral);

        }else if(tatica.getMapa().equals("Mansão")){
            holder.foto.setImageResource(R.drawable.mansao);

        }else if(tatica.getMapa().equals("Outback")){
            holder.foto.setImageResource(R.drawable.outback);

        }else if(tatica.getMapa().equals("Parque")){
            holder.foto.setImageResource(R.drawable.parque);

        }else if(tatica.getMapa().equals("Banco")){
            holder.foto.setImageResource(R.drawable.banco);
        }
        holder.tatica.setText(tatica.getTipoDeTatica());
        holder.numPlayers.setText("Número de Players: "+tatica.getNumeroDeJogadores());

    }

    @Override
    public int getItemCount() {
        return listTaticas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        //Aqui ficam os atributos da listagem

        TextView nome,tatica,numPlayers;
        ImageView foto;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nome=itemView.findViewById(R.id.textNome);
            foto=itemView.findViewById(R.id.imageMapa);
            tatica=itemView.findViewById(R.id.textTipoTatica);
            numPlayers=itemView.findViewById(R.id.textNumPlayers);

        }
    }

}
