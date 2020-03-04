package com.example.taticoolsiege.ui.mylist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

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
import com.example.taticoolsiege.ui.create.ListFragment;
import com.example.taticoolsiege.ui.login.LoginFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyListFragment extends Fragment {

    private RecyclerView recyclerMyTaticas;
    private List<Taticas> taticas=new ArrayList<>();
    private AdapterMyList adapterMyList;
    private  CollectionReference refDataBase;

    //ref banco

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_my_list, container, false);
        refDataBase= ConfiguracaoFirebase.getFirebase().collection("minhas_táticas").document(ConfiguracaoFirebase.getIdUsuario()).collection("táticas");
        recuperarTaticas();
        adapterMyList=new AdapterMyList(taticas,this.getContext());
        recyclerMyTaticas=root.findViewById(R.id.recyclerMylist);
        recyclerMyTaticas.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerMyTaticas.setHasFixedSize(true);

         recyclerMyTaticas.setAdapter(adapterMyList);
         recyclerMyTaticas.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerMyTaticas, new RecyclerItemClickListener.OnItemClickListener() {
             @Override
             public void onItemClick(View view, int position) {
                 Taticas taticaSelecionada=taticas.get(position);
                 Intent i =new Intent(getActivity(), PerfilTaticaActivity.class);
                 i.putExtra("taticaSelecionado",taticaSelecionada);
                 startActivity(i);

             }

             @Override
             public void onLongItemClick(View view, int position) {

                 final int i=position;
                 AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                 builder.setCancelable(true);
                 builder.setTitle("Excluir tática");
                 builder.setMessage("Você deseja excluir a tática "+taticas.get(i).getNome()+"?");
                 builder.setPositiveButton("Sim",
                         new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 Taticas taticaSelecionada=taticas.get(i);
                                 taticaSelecionada.remover();
                                 adapterMyList.notifyDataSetChanged();
                                 MyListFragment myListFragment=new MyListFragment();
                                 FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                                 fragmentTransaction.replace(R.id.frameContainer,myListFragment);
                                 fragmentTransaction.commit();
                             }
                         });
                 builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {

                     }
                 });

                 AlertDialog dialog = builder.create();
                 dialog.show();




             }

             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

             }
         }));

        iniciarComponentes();
        FloatingActionButton fab=root.findViewById(R.id.createActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                    ListFragment loginFragment=new ListFragment();
                    FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameContainer,loginFragment);
                    fragmentTransaction.commit();
                }else{
                    LoginFragment loginFragment=new LoginFragment();
                    FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameContainer,loginFragment);
                    fragmentTransaction.commit();
                }



            }
        });
        return root;
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
                adapterMyList.notifyDataSetChanged();

            }
        });
    }

    private void iniciarComponentes() {
        recyclerMyTaticas=getActivity().findViewById(R.id.recyclerMylist);
    }
}