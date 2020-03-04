package com.example.taticoolsiege.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfiguracaoFirebase {

    private  static FirebaseFirestore referenceFirebase;
    private  static FirebaseAuth referenceAuth;
    private  static StorageReference referenceStorage;

    public static String getIdUsuario(){
        FirebaseAuth autenticacao=getFirebaseAutenticacao();
        return autenticacao.getCurrentUser().getUid();
    }

    public static FirebaseAuth getFirebaseAutenticacao(){
        if(referenceAuth==null){
            referenceAuth=FirebaseAuth.getInstance();
        }
        return referenceAuth;
    }
    public static  FirebaseFirestore getFirebase(){
        if(referenceFirebase==null){
            referenceFirebase= FirebaseFirestore.getInstance();

        }
        return referenceFirebase;
    }

    public static StorageReference getReferenceStorage(){
        if(referenceStorage==null){
            referenceStorage =FirebaseStorage.getInstance().getReference();
        }
        return referenceStorage;
    }

}
