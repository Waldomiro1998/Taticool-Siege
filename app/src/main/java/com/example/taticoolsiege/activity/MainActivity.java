package com.example.taticoolsiege.activity;

import android.annotation.SuppressLint;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;


import android.os.Bundle;


import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;

import androidx.fragment.app.FragmentTransaction;


import com.example.taticoolsiege.R;
import com.example.taticoolsiege.helper.ConfiguracaoFirebase;
import com.example.taticoolsiege.ui.create.CreateFragment;
import com.example.taticoolsiege.ui.create.ListFragment;
import com.example.taticoolsiege.ui.home.HomeFragment;

import com.example.taticoolsiege.ui.login.LoginFragment;
import com.example.taticoolsiege.ui.mylist.FavoritoFragment;
import com.example.taticoolsiege.ui.mylist.MyListFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.widget.ImageView;

import android.widget.TextView;


import static com.example.taticoolsiege.R.*;
import static com.example.taticoolsiege.R.color.colorPrimary;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener, NavigationView.OnNavigationItemSelectedListener  {



    private  DrawerLayout drawer;
    public NavigationView navigationView;
    private float _xDelta;
    private float _yDelta;
    private  String floor;
    private String key;
    private FirebaseAuth usuario=FirebaseAuth.getInstance();
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        Toolbar toolbar = findViewById(id.toolbar);
        toolbar.setTitle("Taticool Siege");
        toolbar.setTitleTextColor(color.colorDarkBlue);

        setSupportActionBar(toolbar);




        drawer = findViewById(id.drawer_layout);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar, string.navigation_drawer_open, string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        verificarUsuarioLogado();
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameContainer, homeFragment);
        fragmentTransaction.commit();

    }


    @Override
    public  void onBackPressed(){
        DrawerLayout drawer= findViewById(id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id= menuItem.getItemId();


        if(id==R.id.nav_home) {
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameContainer, homeFragment);
            fragmentTransaction.commit();

        }else if(id==R.id.nav_exit){

            if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                ListFragment loginFragment=new ListFragment();
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameContainer,loginFragment);
                fragmentTransaction.commit();
            }else{
                LoginFragment loginFragment=new LoginFragment();
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameContainer,loginFragment);
                fragmentTransaction.commit();
            }


        }else if(id==R.id.nav_voltar){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(menu.activity_main_drawer);
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameContainer, homeFragment);
            fragmentTransaction.commit();

        }else if(id==R.id.nav_login){
            if(menuItem.getTitle()=="Logout"){
                FirebaseAuth.getInstance().signOut();
                verificarUsuarioLogado();
                HomeFragment homeFragment=new HomeFragment();
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameContainer,homeFragment);
                fragmentTransaction.commit();
            }else {
                LoginFragment loginFragment = new LoginFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameContainer, loginFragment);
                fragmentTransaction.commit();
            }
        }else if(id== R.id.nav_mylist){

            if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                MyListFragment myListFragment=new MyListFragment();
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameContainer,myListFragment);
                fragmentTransaction.commit();
            }else{
                LoginFragment loginFragment=new LoginFragment();
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameContainer,loginFragment);
                fragmentTransaction.commit();
            }




        }else if(id== R.id.nav_favoritos){

            if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                FavoritoFragment favoritoFragment=new FavoritoFragment();
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameContainer,favoritoFragment);
                fragmentTransaction.commit();
            }else{
                LoginFragment loginFragment=new LoginFragment();
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frameContainer,loginFragment);
                fragmentTransaction.commit();
            }




        }else{
            //recarrega menu pra ajusta a imagem novamente
            NavigationView navigationView=findViewById(R.id.nav_view);
            navigationView.getMenu().clear();
            navigationView.inflateMenu(menu.activity_main_creation_drawer);


            final ImageView drawable = new ImageView(getBaseContext());
            Drawable creationIcon=menuItem.getIcon();
            drawable.setImageDrawable(creationIcon);
            ConstraintLayout constraintLayout = this.findViewById(R.id.constraintLayoutCreate);
            constraintLayout.addView(drawable);
            drawable.setOnTouchListener(this);


            //setViewToBitmapImage(constraintLayout); criar bit map do layout



            drawable.setX( constraintLayout.getWidth()/2);
            drawable.setY(constraintLayout.getHeight()/2);

            if(menuItem.getGroupId()==R.id.nav_group_atacantes || menuItem.getGroupId()==R.id.nav_group_defensores){
                drawable.setTag(R.id.type,"operadores");
                drawable.setTag(R.id.floor,floor);
                drawable.getLayoutParams().height = 65;
                drawable.getLayoutParams().width = 65;
            }else{
                drawable.getLayoutParams().height = 150;
                drawable.getLayoutParams().width = 200;
                drawable.setTag(R.id.floor,floor);
            }
            setViewToBitmapImage(constraintLayout);
        }
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    public void getFloor(String number){
        floor= number;

    }

    public void createFragment(View view){


        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            Bundle bundle = new Bundle();
            TextView textView=view.findViewById(id.textNome);
            key =textView.getText().toString();
            bundle.putString("MAP", key );


            CreateFragment newFragment = new CreateFragment();
            newFragment.setArguments(bundle);

            FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
            transaction.replace(id.frameContainer, newFragment);
            transaction.commit();
        }else{
            LoginFragment loginFragment=new LoginFragment();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameContainer,loginFragment);
            fragmentTransaction.commit();
        }

    }
    public String getMap(){
        return this.key;
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

    public static Bitmap setViewToBitmapImage(View view) {
    //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }


    public void verificarUsuarioLogado(){


        Menu menu = navigationView.getMenu();
        usuario= ConfiguracaoFirebase.getFirebaseAutenticacao();
        if(usuario.getCurrentUser()!=null){
            MenuItem itemLogin=menu.findItem(R.id.nav_login);
            itemLogin.setTitle("Logout");
        }else {
            MenuItem itemLogin=menu.findItem(R.id.nav_login);
            itemLogin.setTitle("Login");
        }

    }
}
