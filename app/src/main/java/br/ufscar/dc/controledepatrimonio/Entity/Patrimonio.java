package br.ufscar.dc.controledepatrimonio.Entity;


import java.io.Serializable;
import java.util.Date;

import br.ufscar.dc.controledepatrimonio.Entity.Local;
import br.ufscar.dc.controledepatrimonio.Entity.Responsavel;

public class Patrimonio implements Serializable {
    private int id;
    private String descricao;
    private String identificacao;
    private String estado;
    private Date dataEntrada;
    private Local local;
    private Responsavel responsavel;
    //private String TagRFID;
    private boolean enviarBancoOnline;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEnviarBancoOnline() {
        return enviarBancoOnline;
    }

    public void setEnviarBancoOnline(boolean enviarBancoOnline) {
        this.enviarBancoOnline = enviarBancoOnline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Patrimonio that = (Patrimonio) o;

        return !(identificacao != null ? !identificacao.equals(that.identificacao) : that.identificacao != null);

    }

    @Override
    public int hashCode() {
        return identificacao != null ? identificacao.hashCode() : 0;
    }
}
