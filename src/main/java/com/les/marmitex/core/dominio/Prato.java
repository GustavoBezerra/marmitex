package com.les.marmitex.core.dominio;

import java.util.Date;
import java.util.List;

/**
 * Entidade para representar um prato pré-definido
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 01/10/2016
 */
public class Prato extends Marmitex{

    private List<Dias> dtDisponivel;
    private String nome;    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the dtDisponivel
     */
    public List<Dias> getDtDisponivel() {
        return dtDisponivel;
    }

    /**
     * @param dtDisponivel the dtDisponivel to set
     */
    public void setDtDisponivel(List<Dias> dtDisponivel) {
        this.dtDisponivel = dtDisponivel;
    }
}
