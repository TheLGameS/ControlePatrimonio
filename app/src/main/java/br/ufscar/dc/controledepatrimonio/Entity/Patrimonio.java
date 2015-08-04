package br.ufscar.dc.controledepatrimonio.Entity;


import java.util.Date;

import br.ufscar.dc.controledepatrimonio.Entity.Local;
import br.ufscar.dc.controledepatrimonio.Entity.Responsavel;

public class Patrimonio {
    private int id;
    private String nome;
    private String descricao;
    private String identificacao;
    private String estado;
    private Date dataEntrada;
    private Local local;
    private Responsavel responsavel;
    private Boolean statusRegistro;

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

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }

    public Boolean getStatusRegistro() {
        return statusRegistro;
    }

    public void setStatusRegistro(Boolean statusRegistro) {
        this.statusRegistro = statusRegistro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
