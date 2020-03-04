package com.example.taticoolsiege.model;

import com.example.taticoolsiege.helper.ConfiguracaoFirebase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;


import java.io.Serializable;
import java.util.List;

public class Taticas implements Serializable {

    //Classe que representa o nó locais do firebase

    private String Nome="";
    private String NomeBusca;
    private String Detalhamento;
    private String TipoDeTatica="";
    private String numeroDeJogadores;
    private  String idTatica;
    private String Mapa="";


    private List<String> BluePrints;


    public Taticas() {
        CollectionReference localRef= ConfiguracaoFirebase.getFirebase().collection("minhas_táticas");
         setIdTatica(localRef.document().getId());
    }

    public void salvar(){
        String idUsuario=ConfiguracaoFirebase.getIdUsuario();
        CollectionReference localRef= ConfiguracaoFirebase.getFirebase().collection("minhas_táticas");
        localRef.document(idUsuario).collection("táticas").document(this.getIdTatica()).set(this);

    }

    public void salvarTaticaPublica(){
        CollectionReference localRef=ConfiguracaoFirebase.getFirebase().collection("táticas");
        localRef.document(getIdTatica()).set(this);
    }

    public void remover(){
        String idUsuario=ConfiguracaoFirebase.getIdUsuario();
        DocumentReference localRef= ConfiguracaoFirebase.getFirebase().collection("minhas_táticas").document(idUsuario).collection("táticas").document(getIdTatica());
        localRef.delete();
        removerTaticaPublica();
    }
    public void removerTaticaPublica(){
        String idUsuario=ConfiguracaoFirebase.getIdUsuario();
        DocumentReference localRef= ConfiguracaoFirebase.getFirebase().collection("táticas").document(getIdTatica());
        localRef.delete();
    }

    public String getDetalhamento() {
        return Detalhamento;
    }

    public void setDetalhamento(String detalhamento) {
        Detalhamento = detalhamento;
    }

    public String getTipoDeTatica() {
        return TipoDeTatica;
    }

    public void setTipoDeTatica(String tipoDeTatica) {
        TipoDeTatica = tipoDeTatica;
    }

    public List<String> getBluePrints() {
        return BluePrints;
    }

    public void setBluePrints(List<String> bluePrints) {
        BluePrints = bluePrints;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }



    public String getNomeBusca() {
        return NomeBusca;
    }

    public void setNomeBusca(String nomeBusca) {
        NomeBusca = nomeBusca;
    }
    public String getIdTatica() {
        return idTatica;
    }

    public void setIdTatica(String idTatica) {
        this.idTatica = idTatica;
    }

    public String getNumeroDeJogadores() {
        return numeroDeJogadores;
    }

    public void setNumeroDeJogadores(String numeroDeJogadores) {
        this.numeroDeJogadores = numeroDeJogadores;
    }

    public String getMapa() {
        return Mapa;
    }

    public void setMapa(String mapa) {
        Mapa = mapa;
    }
}
