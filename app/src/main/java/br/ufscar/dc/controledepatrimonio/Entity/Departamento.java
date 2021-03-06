package br.ufscar.dc.controledepatrimonio.Entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Departamento implements Serializable{
    @Expose
    private int id;
    @Expose
    private String sigla;
    @Expose
    private String nome;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
