package br.ufscar.dc.controledepatrimonio.Entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Local  implements Serializable {
    @Expose
    private int id;
    @Expose
    private String nome;
    @Expose
    private String descricao;
    @Expose
    private Departamento departamento;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
