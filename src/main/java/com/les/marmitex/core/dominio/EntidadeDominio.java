package com.les.marmitex.core.dominio;

import java.util.Date;

/**
 * Classe entidade dominio
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 27/08/2016
 */
public class EntidadeDominio implements IEntidade {

    private int id;
    private Date dtCriacao;
    private boolean ativo;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the dtCriacao
     */
    public Date getDtCriacao() {
        return dtCriacao;
    }

    /**
     * @param dtCriacao the dtCriacao to set
     */
    public void setDtCriacao(Date dtCriacao) {
        this.dtCriacao = dtCriacao;
    }

    /**
     * @return the ativo
     */
    public boolean isAtivo() {
        return ativo;
    }

    /**
     * @param ativo the ativo to set
     */
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
