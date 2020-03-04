package com.example.taticoolsiege.ui.send;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.taticoolsiege.activity.MainActivity;
import com.example.taticoolsiege.R;
import com.example.taticoolsiege.helper.ConfiguracaoFirebase;
import com.example.taticoolsiege.helper.Permissoes;
import com.example.taticoolsiege.model.Taticas;
import com.example.taticoolsiege.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;



import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;


public class SaveFragment extends Fragment implements View.OnClickListener{

    private AlertDialog loadDialog;
    private StorageReference storage;

    private CollectionReference referencia= FirebaseFirestore.getInstance().collection("táticas");
    private Button buttonSalvar;
    private EditText campoNome, campoDetalhamento;

    private ImageView imagem1,imagem2,imagem3,imagem4;
    private Spinner campoTatica,campoNumeroPlayers;
    private String[] permissoes=new String[]
            {
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };

    private List<String> listaFotosRecuperadas=new ArrayList<>();
    private List<String> listaUrlFotos=new ArrayList<>();
    private Taticas tatica;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_save, container, false);
        storage= ConfiguracaoFirebase.getReferenceStorage();
        Permissoes.validarPermissoes(permissoes,getActivity(),1);

        iniciarComponentes(root);
        carregarSpinner();
        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSalvar:
                validarDados();
                break;
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            //Recuperando Imagem
            Uri imagemSelecionada=data.getData();
            String caminhoImagem=imagemSelecionada.toString();

            //Configura imagem na ImageView
            if(requestCode==1){
                imagem1.setImageURI(imagemSelecionada);
            }else if(requestCode==2){
                imagem2.setImageURI(imagemSelecionada);
            }else if (requestCode==3){
                imagem3.setImageURI(imagemSelecionada);
            }else if (requestCode==4){
                imagem4.setImageURI(imagemSelecionada);
            }
                listaFotosRecuperadas.add(caminhoImagem);


        }
    }

    public void salvarDados(){
        //Salvar Imagem no storage no Firebase

        loadDialog=new SpotsDialog.Builder().setContext(getContext()).setMessage("Salvando os Dados").setCancelable(false).build();
        loadDialog.show();


        salvarFotoStorage(0);
        salvarFotoStorage(1);
        salvarFotoStorage(2);
        if(tatica.getMapa().equals("Mansão")||tatica.getMapa().equals("Banco")){
            salvarFotoStorage(3);
        }

    }
    private void salvarFotoStorage( int contador){
        final StorageReference imagemLocal=storage.child("imagens")
                .child("táticas").child(tatica.getIdTatica())
                .child(ConfiguracaoFirebase.getIdUsuario())
                .child("imagem"+contador);

        byte[] data=null;
        if(contador==0){

            Bitmap bitmap = ((BitmapDrawable) imagem1.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            data= baos.toByteArray();

            /*
            imagem1.setDrawingCacheEnabled(true);
            imagem1.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) imagem1.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 65, baos);
            data = baos.toByteArray();*/

        }else if(contador==1){
            data=null;

            Bitmap bitmap = ((BitmapDrawable) imagem2.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            data= baos.toByteArray();
            /*
            imagem2.setDrawingCacheEnabled(true);
            imagem2.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) imagem2.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 65, baos);
            data = baos.toByteArray();*/
        }else if(contador==2){
            data=null;
            Bitmap bitmap = ((BitmapDrawable) imagem3.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            data= baos.toByteArray();
            /*
            imagem3.setDrawingCacheEnabled(true);
            imagem3.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) imagem3.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 65, baos);
            data = baos.toByteArray();*/

        }else if(contador==3){

            data=null;
            Bitmap bitmap = ((BitmapDrawable) imagem4.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            data= baos.toByteArray();
            /*
            imagem4.setDrawingCacheEnabled(true);
            imagem4.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) imagem4.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 65, baos);
            data = baos.toByteArray();*/
        }


        UploadTask uploadTask=imagemLocal.putBytes(data);
        //UploadTask uploadTask=imagemLocal.putFile(Uri.parse(url));

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                //Uri firebaseUrl=taskSnapshot.getUploadSessionUri();
                imagemLocal.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        listaUrlFotos.add(task.getResult().toString());

                        int control;
                        if(  tatica.getMapa().equals("Banco")||tatica.getMapa().equals("Mansão")){
                            control=4;
                        }else{
                            control=3;
                        }


                        if(listaUrlFotos.size()==control){
                            tatica.setBluePrints(listaUrlFotos);
                            tatica.salvar();
                            tatica.salvarTaticaPublica();
                            NavigationView navigationView=getActivity().findViewById(R.id.nav_view);
                            navigationView.getMenu().clear();
                            navigationView.inflateMenu(R.menu.activity_main_drawer);
                            loadDialog.dismiss();
                            listaFotosRecuperadas.clear();

                            HomeFragment homeFragment=new HomeFragment();
                            FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.frameContainer,homeFragment);
                            fragmentTransaction.commit();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                exibirMensagemErro("Falha ao fazer upload");
                loadDialog.dismiss();
            }
        });

    }
    private Taticas configurarLocal(){
        String nome=campoNome.getText().toString();
        String detalhamento= campoDetalhamento.getText().toString();
        String tipo=campoTatica.getSelectedItem().toString();
        String numerDeJodadores=campoNumeroPlayers.getSelectedItem().toString();
        String mapa=((MainActivity)getActivity()).getMap();
        Taticas tatica=new Taticas();

        tatica.setNome(nome);
        tatica.setDetalhamento(detalhamento);
        tatica.setTipoDeTatica(tipo);
        tatica.setNumeroDeJogadores(numerDeJodadores);
        tatica.setMapa(mapa);



        return tatica;
    }

    public void validarDados(){
       tatica=configurarLocal();

        //controlador de quantas fotos é necessário ser selecionadas
            if(!tatica.getNome().isEmpty()){
                if(!tatica.getDetalhamento().isEmpty()){
                    if(!tatica.getTipoDeTatica().isEmpty()){
                                    if(!tatica.getNome().isEmpty()){
                                        tatica.setNomeBusca(tatica.getNome().toUpperCase());
                                        salvarDados();
                                    }else{
                                        exibirMensagemErro("Preencha o campo nome");
                                    }
                                }else{
                                    exibirMensagemErro("Preencha o campo de tipo de tática");
                                }
                            }else{
                                exibirMensagemErro("Preencha o campo de detalhamento");
                            }
                        }


    }

    private void exibirMensagemErro(String mensagem){
        Toast.makeText(getContext(),mensagem,Toast.LENGTH_SHORT).show();

    }



    private  void carregarSpinner(){
        String[] tipos=getResources().getStringArray(R.array.tipodetatica);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_spinner_item,
                tipos
                );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campoTatica.setAdapter(adapter);
        tipos=getResources().getStringArray(R.array.numeroDePlayers);
        adapter=new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_spinner_item,
                tipos
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        campoNumeroPlayers.setAdapter(adapter);
    }


    private void iniciarComponentes(View view){

        campoNome=view.findViewById(R.id.editTextNome);
        campoDetalhamento=view.findViewById(R.id.editTextDetalhamento);

        campoTatica =view.findViewById(R.id.spinnerTatica);
        campoNumeroPlayers=view.findViewById(R.id.spinnerMapa);
        buttonSalvar=view.findViewById(R.id.buttonSalvar);

        imagem1=view.findViewById(R.id.imageViewFoto1);
        imagem2=view.findViewById(R.id.imageViewFoto2);
        imagem3=view.findViewById(R.id.imageViewFoto3);
        imagem4=view.findViewById(R.id.imageViewFoto4);
        imagem4.setVisibility(View.INVISIBLE);
        buttonSalvar.setOnClickListener(this);

        Bundle b = this.getArguments();
        if (b != null) {
            Bitmap f1 = b.getParcelable("f1");
            Bitmap f2 = b.getParcelable("f2");
            Bitmap f3 = b.getParcelable("f3");

            if(b.getParcelable("f4")!=null){
                Bitmap f4 =b.getParcelable("f4");
                imagem4.setImageBitmap(f4);
                imagem4.setVisibility(View.VISIBLE);
            }

            imagem1.setImageBitmap(f1);
            imagem2.setImageBitmap(f2);
            imagem3.setImageBitmap(f3);


        }




        //imagem1.setOnClickListener(this);
        //imagem2.setOnClickListener(this);
        //imagem3.setOnClickListener(this);
       // imagem4.setOnClickListener(this);


        String IdUser=ConfiguracaoFirebase.getIdUsuario();
        referencia.document(IdUser);

        }




}






