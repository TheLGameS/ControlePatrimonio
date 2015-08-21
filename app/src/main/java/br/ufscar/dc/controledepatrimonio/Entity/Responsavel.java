package br.ufscar.dc.controledepatrimonio.Entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Responsavel  implements Serializable {
    @Expose
    private int id;
    @Expose
    private String siape;
    @Expose
    private String nome;
    @Expose
    private String telefone;
    @Expose
    private String funcao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getSiape() {
        return siape;
    }

    public void setSiape(String siape) {
        this.siape = siape;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return nome;
    }
}
