package com.example.taticoolsiege.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taticoolsiege.R;
import com.example.taticoolsiege.RecyclerItemClickListener;
import com.example.taticoolsiege.activity.PerfilTaticaActivity;
import com.example.taticoolsiege.adapter.AdapterMyList;
import com.example.taticoolsiege.helper.ConfiguracaoFirebase;
import com.example.taticoolsiege.model.Taticas;
import com.example.taticoolsiege.ui.login.LoginFragment;
import com.example.taticoolsiege.ui.mylist.MyListFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private SearchView viewBusca;
    private RecyclerView buscaRecycler;
    private AdapterMyList adapter;
    private CollectionReference refDataBase;
    private List<Taticas> taticas;
    private Spinner campoTatica, campoMapa;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);


        iniciarComponentes(root);

        recuperarTaticas();
        return root;
    }

    private void iniciarComponentes(View root) {

        campoMapa =root.findViewById(R.id.spinnerMapa);
        campoTatica=root.findViewById(R.id.spinnerTatica);
        carregarSpinner();
        taticas=new ArrayList<Taticas>();
        viewBusca=root.findViewById(R.id.buscaView);

        viewBusca.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //Método quando uma searchView tem texto alterado
                String textoDeBusca=s.toUpperCase();
                pesquisarUsuario(textoDeBusca);
                return true;
            }
        });
        refDataBase= ConfiguracaoFirebase.getFirebase().collection("táticas");

        adapter=new AdapterMyList(taticas,this.getContext());
        buscaRecycler=root.findViewById(R.id.recyclerBusca);
        buscaRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
        buscaRecycler.setHasFixedSize(true);
        buscaRecycler.setAdapter(adapter);
        buscaRecycler.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), buscaRecycler, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                    Taticas taticaSelecionada=taticas.get(position);
                    Intent i =new Intent(getActivity(), PerfilTaticaActivity.class);
                    i.putExtra("taticaSelecionado",taticaSelecionada);
                    startActivity(i);
                }else{
                    LoginFragment loginFragment=new LoginFragment();
                    FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameContainer,loginFragment);
                    fragmentTransaction.commit();
                }

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }));
    }

    private void pesquisarUsuario(final String textoDeBusca) {
        String tipo=campoTatica.getSelectedItem().toString();
        String mapa= campoMapa.getSelectedItem().toString();
        Query query;
        taticas.clear();

        if(textoDeBusca.length()>0){
            if(tipo.equals("Todos")){
                query=refDataBase.orderBy("nomeBusca").whereEqualTo("mapa",mapa)
                        .startAt(textoDeBusca)
                        .endAt(textoDeBusca +"\uf8ff");
            }else if(mapa.equals("Todos")){
                query=refDataBase.orderBy("nomeBusca").whereEqualTo("tipoDeTatica",tipo)
                        .startAt(textoDeBusca)
                        .endAt(textoDeBusca +"\uf8ff");
            }
            else{
                query=refDataBase.orderBy("nomeBusca").whereEqualTo("tipoDeTatica",tipo).whereEqualTo("mapa",mapa)
                        .startAt(textoDeBusca)
                        .endAt(textoDeBusca +"\uf8ff");
            }
            if((tipo.equals("Todos")) && (mapa.equals("Todos")) ){
                query=refDataBase.orderBy("nomeBusca")
                        .startAt(textoDeBusca)
                        .endAt(textoDeBusca +"\uf8ff");
            }

            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        taticas.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            taticas.add(document.toObject(Taticas.class));

                        }
                    } else {

                    }
                    adapter.notifyDataSetChanged();
                }
            });

                }



    }

    private void recuperarTaticas() {
        refDataBase.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    taticas.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        taticas.add(document.toObject(Taticas.class));
                    }
                }
                Collections.reverse(taticas);
                adapter.notifyDataSetChanged();

            }
        });
    }

    private  void carregarSpinner(){
        String[] tipos=getResources().getStringArray(R.array.tipodetaticaBusca);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_spinner_item,
                tipos
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campoTatica.setAdapter(adapter);
        tipos=getResources().getStringArray(R.array.mapas);
        adapter=new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_spinner_item,
                tipos
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campoMapa.setAdapter(adapter);
    }

}