package com.example.taticoolsiege.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.taticoolsiege.activity.CadastroActivity;
import com.example.taticoolsiege.activity.MainActivity;
import com.example.taticoolsiege.R;
import com.example.taticoolsiege.helper.ConfiguracaoFirebase;
import com.example.taticoolsiege.model.Usuario;
import com.example.taticoolsiege.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    private FirebaseAuth autenticacao;
    private EditText campoEmail,campoSenha;
    private Button buttonLogar;
    private ProgressBar progressBar;
    private Usuario usuario;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_login, container, false);
        verificarUsuarioLogado();
        iniciarComponentes(root);


        return root;
    }


    private void iniciarComponentes(View root) {
        campoEmail=root.findViewById(R.id.editLoginEmail);
        campoSenha=root.findViewById(R.id.editLoginSenha);
        progressBar=root.findViewById(R.id.progressLogin);
        progressBar.setVisibility(View.GONE);
        buttonLogar=root.findViewById(R.id.buttonLogar);

        buttonLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textoEmail=campoEmail.getText().toString();
                String textoSenha=campoSenha.getText().toString();

                if(!textoEmail.isEmpty()){
                    if(!textoSenha.isEmpty()){
                        usuario=new Usuario();
                        usuario.setEmail(textoEmail);
                        usuario.setSenha(textoSenha);
                        validarLogin(usuario);

                    } else{
                        Toast.makeText(getActivity(),"Preencha a senha",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(),"Preencha o email",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button cadastrarButton=root.findViewById(R.id.buttonCadastro);
        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(getActivity(),CadastroActivity.class);
                startActivity(i);
            }
        });

    }

    private  void verificarUsuarioLogado(){
        autenticacao=ConfiguracaoFirebase.getFirebaseAutenticacao();
        if(autenticacao.getCurrentUser()!=null){
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameContainer, homeFragment);
            fragmentTransaction.commit();

        }

    }

    private void validarLogin(Usuario usuario) {
        progressBar.setVisibility(View.VISIBLE);
        autenticacao= ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    ((MainActivity)getActivity()).verificarUsuarioLogado();
                    HomeFragment homeFragment = new HomeFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameContainer, homeFragment);
                    fragmentTransaction.commit();


                }else{
                    Toast.makeText(getActivity(),"Erro ao fazer login",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                ((MainActivity)getActivity()).verificarUsuarioLogado();
            }
        });

    }


}
