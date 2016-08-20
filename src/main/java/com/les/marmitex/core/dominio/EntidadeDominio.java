package com.les.marmitex.core.dominio;

import java.util.Date;

public class EntidadeDominio implements IEntidade {
    
    private int id;
    private Date dtCriacao;

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
}
