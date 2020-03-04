package com.example.taticoolsiege.activity;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;


import com.example.taticoolsiege.R;
import com.example.taticoolsiege.helper.ConfiguracaoFirebase;
import com.example.taticoolsiege.model.Taticas;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;


import java.lang.reflect.Type;
import java.util.ArrayList;


public class PerfilTaticaActivity extends AppCompatActivity {

    private ArrayList<Taticas> favoriteList;
    private Taticas taticaSelecionada;
    private CarouselView carouselView;
    private TextView textNome,textDetalhamento,textMapa, textNumeroJogadores,textTipo;
    private Button buttonFavorites;
    private CollectionReference ref= ConfiguracaoFirebase.getFirebase().collection("minhas_táticas").document(ConfiguracaoFirebase.getIdUsuario()).collection("favoritos");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_tatica);

        iniciarComponentes();


        //recuperar dado salvo localmente
        taticaSelecionada=(Taticas) getIntent().getSerializableExtra("taticaSelecionado");


        if(taticaSelecionada!=null) {
            textNome.setText(taticaSelecionada.getNome());
            textDetalhamento.setText(taticaSelecionada.getDetalhamento());
            textMapa.setText("Mapa: "+taticaSelecionada.getMapa());
            textNumeroJogadores.setText("Número de jogadores: "+taticaSelecionada.getNumeroDeJogadores());
            textTipo.setText("Tipo de tática: "+taticaSelecionada.getTipoDeTatica());

            //Recuperando fotos
            ImageListener imageListener = new ImageListener() {
                @Override
                public void setImageForPosition(int position, ImageView imageView) {
                    String urlString=taticaSelecionada.getBluePrints().get(position);
                    Picasso.get().load(urlString).into(imageView);
                }
            };
            carouselView.setPageCount(taticaSelecionada.getBluePrints().size());
            carouselView.setImageListener(imageListener);
            ajusteEstrela();
        }




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

    }

    private void ajusteEstrela() {
        ref.document(taticaSelecionada.getIdTatica()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    Drawable drw= ResourcesCompat.getDrawable(getResources(), R.drawable.ic_menu_favoritos, null);
                    buttonFavorites.setBackground(drw);
                } else{
                    Drawable drw= ResourcesCompat.getDrawable(getResources(), R.drawable.ic_menu_favoritos_branco, null);
                    buttonFavorites.setBackground(drw);
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void iniciarComponentes(){
        buttonFavorites=findViewById(R.id.buttonFavorites);
        carouselView=findViewById(R.id.carouseHomeView);
        textNome=findViewById(R.id.textViewTitle);
        textDetalhamento=findViewById(R.id.textViewDetalhamento);
        textMapa =findViewById(R.id.textViewMapa);
        textNumeroJogadores =findViewById(R.id.textViewNum);
        textTipo =findViewById(R.id.textViewTipoTatica);
        buttonFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               save();
            }
        });
    }
    public void save(){
        ref.document(taticaSelecionada.getIdTatica()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        ref.document(taticaSelecionada.getIdTatica()).delete();
                       ajusteEstrela();
                    } else {
                        ref.document(taticaSelecionada.getIdTatica()).set(taticaSelecionada);
                        ajusteEstrela();
                    }
                }
            }
        });
    }
}
