package com.example.taticoolsiege.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.taticoolsiege.R;
import com.example.taticoolsiege.helper.ConfiguracaoFirebase;
import com.example.taticoolsiege.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome,campoEmail,campoSenha,campoConfirmarSenha;
    private Button cadastrarButton;
    private ProgressBar progressBar;
    private FirebaseAuth autenticacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cadastrar);

        iniciarComponentes();

        progressBar.setVisibility(View.GONE);
        cadastrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String textoNome=campoNome.getText().toString();
                String textoEmail=campoEmail.getText().toString();
                String textoSenha=campoSenha.getText().toString();
                String textoSenha2=campoConfirmarSenha.getText().toString();

                if(!textoNome.isEmpty()){
                    if(!textoEmail.isEmpty()){
                        if(!textoSenha.isEmpty()){
                            if(!textoSenha2.isEmpty() && textoSenha.equals(textoSenha2)){
                                Usuario usuario=new Usuario();
                                usuario.setNome(textoNome);
                                usuario.setEmail(textoEmail);
                                usuario.setSenha(textoSenha);
                                cadastrarUsuario(usuario);

                        }else{
                                Toast.makeText(CadastroActivity.this,"As senhas não se confirmam",Toast.LENGTH_SHORT).show();
                        }

                    } else{
                            Toast.makeText(CadastroActivity.this,"Preencha a senha",Toast.LENGTH_SHORT).show();
                    }
                    }else{
                        Toast.makeText(CadastroActivity.this,"Preencha o email",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(CadastroActivity.this,"Preencha o nome",Toast.LENGTH_SHORT).show();
                }

            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    private void cadastrarUsuario(Usuario usuario) {
        progressBar.setVisibility(View.VISIBLE);

        autenticacao= ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(CadastroActivity.this,"Cadastro feito com sucesso",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else{
                    progressBar.setVisibility(View.GONE);

                    String erroExcecao="";

                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        erroExcecao="Digite uma senha mais forte!";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erroExcecao="Por favor, digite um email válido";
                    }catch (FirebaseAuthUserCollisionException e){
                        erroExcecao="Esta conta já foi cadastrada";
                    }catch (Exception e){
                        erroExcecao="ao cadastrar usuário"+ e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this,"Erro: "+erroExcecao,Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void iniciarComponentes() {
        campoNome=findViewById(R.id.editCadstroName);
        campoEmail=findViewById(R.id.editCadastroEmail);
        campoSenha=findViewById(R.id.editCadastroSenha);
        campoConfirmarSenha=findViewById(R.id.editCadastroSenha2);
        cadastrarButton=findViewById(R.id.buttonCadastro);
        progressBar=findViewById(R.id.progressCadastro);
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
}
