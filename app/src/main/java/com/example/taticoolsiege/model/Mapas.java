package com.example.taticoolsiege.model;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;


public class Mapas implements Serializable {

    //Classe que representa o nó locais do firebase

    private String Nome="";

    private int foto;

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public Mapas() {

    }

    public Mapas(String nome, int foto) {
        Nome = nome;

        this.foto = foto;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }




}
