package com.example.taticoolsiege.ui.create;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.taticoolsiege.activity.MainActivity;
import com.example.taticoolsiege.R;

import com.example.taticoolsiege.ui.send.SaveFragment;
import com.google.android.material.navigation.NavigationView;

public class CreateFragment extends Fragment implements View.OnTouchListener, View.OnClickListener {

    private int[] BluePrints;
    private float _xDelta;
    private float _yDelta;
    private ImageView mapView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_create, container, false);
        iniciarcomponetes(root);

        // Cria imagem do layout
        // ((MainActivity)getActivity()).setViewToBitmapImage(constraintLayout);

        return root;
    }

    public void iniciarcomponetes(View root){
       //this.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        NavigationView navigationView=getActivity().findViewById(R.id.nav_view);
        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.activity_main_creation_drawer);

       ((MainActivity)getActivity()).getFloor("1");

        String mapa = this.getArguments().getString("MAP");
        mapView = root.findViewById(R.id.photo_view);

        Button button1=root.findViewById(R.id.button1);
        Button button2=root.findViewById(R.id.button2);
        Button button3=root.findViewById(R.id.button3);
        Button button4=root.findViewById(R.id.button4);
        Button buttonSalvar=root.findViewById(R.id.buttonSalvar);
        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveFragment saveFragment=new SaveFragment();
                FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameContainer,saveFragment);
                fragmentTransaction.commit();
                NavigationView navigationView=getActivity().findViewById(R.id.nav_view);
                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.activity_main_drawer);

                ConstraintLayout constraintLayout = getActivity().findViewById(R.id.constraintLayoutCreate);

                mapView.setImageResource(BluePrints[0]);

                ((MainActivity)getActivity()).getFloor("1");
                int childCount = constraintLayout.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View v = constraintLayout.getChildAt(i);
                    if(v.getTag(R.id.floor)!="1" && v.getTag(R.id.floor)!=null){
                        v.setVisibility(View.INVISIBLE);
                    }else{
                        v.setVisibility(View.VISIBLE);
                    }
                }

                Bitmap f1= ((MainActivity)getActivity()).setViewToBitmapImage(constraintLayout);

                mapView.setImageResource(BluePrints[1]);
                ((MainActivity)getActivity()).getFloor("2");


                for (int i = 0; i < childCount; i++) {
                    View v = constraintLayout.getChildAt(i);
                    if(v.getTag(R.id.floor)!="2" && v.getTag(R.id.floor)!=null){
                        v.setVisibility(View.INVISIBLE);
                    }else{
                        v.setVisibility(View.VISIBLE);
                    }
                }
                Bitmap f2= ((MainActivity)getActivity()).setViewToBitmapImage(constraintLayout);




                mapView.setImageResource(BluePrints[2]);
                ((MainActivity)getActivity()).getFloor("3" );


                for (int i = 0; i < childCount; i++) {
                    View v = constraintLayout.getChildAt(i);
                    if(v.getTag(R.id.floor)!="3" && v.getTag(R.id.floor)!=null){
                        v.setVisibility(View.INVISIBLE);
                    }else{
                        v.setVisibility(View.VISIBLE);
                    }
                }
                Bitmap f3= ((MainActivity)getActivity()).setViewToBitmapImage(constraintLayout);

                Bundle b = new Bundle();
                if(BluePrints.length==4){
                    mapView.setImageResource(BluePrints[3]);
                    ((MainActivity)getActivity()).getFloor("4");


                    for (int i = 0; i < childCount; i++) {
                        View v = constraintLayout.getChildAt(i);
                        if(v.getTag(R.id.floor)!="4" && v.getTag(R.id.floor)!=null){
                            v.setVisibility(View.INVISIBLE);
                        }else{
                            v.setVisibility(View.VISIBLE);
                        }
                    }

                    Bitmap f4= ((MainActivity)getActivity()).setViewToBitmapImage(constraintLayout);
                    b.putParcelable("f4",f4);
                }

                //criar imagem do layout e definir no fragment


                b.putParcelable("f1",f1);
                b.putParcelable("f2",f2);
                b.putParcelable("f3",f3);




                saveFragment.setArguments(b);
            }
        });
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        if(mapa.equals("Litoral")){
           mapView.setImageResource(R.drawable.litoral1);
           BluePrints=new int[]{R.drawable.litoral1,R.drawable.litoral2,R.drawable.litoral3};
           button1.setText("1º Andar");
           button2.setText("2º Andar");
           button3.setText("Telhado");
            ((ViewManager)button4.getParent()).removeView(button4);

       }else if(mapa.equals("Mansão")){
           mapView.setImageResource(R.drawable.mansao1);
           BluePrints=new int[]{R.drawable.mansao1,R.drawable.mansao2,R.drawable.mansao3,R.drawable.mansao4};
            button1.setText("Porão");
            button2.setText("1º Andar");
            button3.setText("2º Andar");
            button4.setText("Telhado");

       }else if(mapa.equals("Outback")){
           mapView.setImageResource(R.drawable.outback1);
           BluePrints=new int[]{R.drawable.outback1,R.drawable.outback2,R.drawable.outback3};
            ((ViewManager)button4.getParent()).removeView(button4);
            button1.setText("1º Andar");
            button2.setText("2º Andar");
            button3.setText("Telhado");

       }else if(mapa.equals("Parque")){
           mapView.setImageResource(R.drawable.parque1);
           BluePrints=new int[]{R.drawable.parque1,R.drawable.parque2,R.drawable.parque3};
           ((ViewManager)button4.getParent()).removeView(button4);
            button1.setText("1º Andar");
            button2.setText("2º Andar");
            button3.setText("Telhado");

       }else if(mapa.equals("Banco")){
           mapView.setImageResource(R.drawable.banco1);
           BluePrints=new int[]{R.drawable.banco1,R.drawable.banco2,R.drawable.banco3,R.drawable.banco4};
            button1.setText("Porão");
            button2.setText("1º Andar");
            button3.setText("2º Andar");
            button4.setText("Telhado");

       }


        //criar drag-drop pra image view
        /*
        ImageView text=root.findViewById(R.id.imageView2);
        text.setImageResource(R.drawable.ash);
        text.setOnTouchListener( this);

         */

    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                _xDelta = view.getX() - event.getRawX();
                _yDelta = view.getY() - event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:

                view.animate()
                        .x(event.getRawX() + _xDelta)
                        .y(event.getRawY() + _yDelta)
                        .setDuration(0)
                        .start();
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        ConstraintLayout constraintLayout = getActivity().findViewById(R.id.constraintLayoutCreate);
        if(view.getId()==R.id.button1){
            mapView.setImageResource(BluePrints[0]);

            ((MainActivity)getActivity()).getFloor("1");
            int childCount = constraintLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View v = constraintLayout.getChildAt(i);
                if(v.getTag(R.id.floor)!="1" && v.getTag(R.id.floor)!=null){
                    v.setVisibility(View.INVISIBLE);
                }else{
                    v.setVisibility(View.VISIBLE);
                }
            }

        }else if(view.getId()==R.id.button2){
            mapView.setImageResource(BluePrints[1]);
            ((MainActivity)getActivity()).getFloor("2");

            int childCount = constraintLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View v = constraintLayout.getChildAt(i);
                if(v.getTag(R.id.floor)!="2" && v.getTag(R.id.floor)!=null){
                    v.setVisibility(View.INVISIBLE);
                }else{
                    v.setVisibility(View.VISIBLE);
                }
            }



        }else if(view.getId()==R.id.button3){
            mapView.setImageResource(BluePrints[2]);
            ((MainActivity)getActivity()).getFloor("3" );

            int childCount = constraintLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View v = constraintLayout.getChildAt(i);
                if(v.getTag(R.id.floor)!="3" && v.getTag(R.id.floor)!=null){
                    v.setVisibility(View.INVISIBLE);
                }else{
                    v.setVisibility(View.VISIBLE);
                }
            }

        }else if(view.getId()==R.id.button4){
            mapView.setImageResource(BluePrints[3]);
            ((MainActivity)getActivity()).getFloor("4");

            int childCount = constraintLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View v = constraintLayout.getChildAt(i);
                if(v.getTag(R.id.floor)!="4" && v.getTag(R.id.floor)!=null){
                    v.setVisibility(View.INVISIBLE);
                }else{
                    v.setVisibility(View.VISIBLE);
                }
            }




        }
    }



}