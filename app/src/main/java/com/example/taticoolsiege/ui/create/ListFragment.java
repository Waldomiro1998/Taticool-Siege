package com.example.taticoolsiege.ui.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taticoolsiege.activity.MainActivity;
import com.example.taticoolsiege.R;
import com.example.taticoolsiege.RecyclerItemClickListener;
import com.example.taticoolsiege.adapter.AdapterMapas;
import com.example.taticoolsiege.model.Mapas;

import java.util.ArrayList;

public class ListFragment extends Fragment  {

    private RecyclerView recyclerViewMapas;
    private float _xDelta;
    private float _yDelta;
    private ArrayList<Mapas> mapas=new ArrayList<Mapas>();
    private AdapterMapas adapterMapas =new AdapterMapas(mapas,this.getContext());
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        iniciarcomponetes(root);

        return root;
    }

    public void iniciarcomponetes(final View root){
       // this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mapas.add(new Mapas("Banco",R.drawable.banco));
        mapas.add(new Mapas("Litoral",R.drawable.litoral));
        mapas.add(new Mapas("Mansão",R.drawable.mansao));
        mapas.add(new Mapas("Outback",R.drawable.outback));
        mapas.add(new Mapas("Parque",R.drawable.parque));
        //ImageView mapView = root.findViewById(R.id.photo_view);
       // mapView.setImageResource(R.drawable.litoral1);
        adapterMapas =new AdapterMapas(mapas,this.getContext());
        //definindo layouts e adapters para recyclerview favoritos
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext());
        recyclerViewMapas =root.findViewById(R.id.recyclerViewMaps);
        recyclerViewMapas.setLayoutManager(layoutManager);
        recyclerViewMapas.setHasFixedSize(true);

        recyclerViewMapas.setAdapter(adapterMapas);
        recyclerViewMapas.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerViewMapas, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                ((MainActivity)getActivity()).createFragment(view);

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }));


        //criar drag-drop pra image view
        //imageViewAsh.setOnTouchListener( this);
    }


}