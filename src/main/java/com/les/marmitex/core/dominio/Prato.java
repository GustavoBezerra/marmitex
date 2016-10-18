package com.les.marmitex.core.dominio;

import java.util.Date;

/**
 * Entidade para representar um prato pré-definido
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 01/10/2016
 */
public class Prato extends Marmitex{

    private Date dtDisponivel;

    /**
     * @return the dtDisponivel
     */
    public Date getDtDisponivel() {
        return dtDisponivel;
    }

    /**
     * @param dtDisponivel the dtDisponivel to set
     */
    public void setDtDisponivel(Date dtDisponivel) {
        this.dtDisponivel = dtDisponivel;
    }

}
