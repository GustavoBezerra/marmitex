package com.les.marmitex.core.dominio;

/**
 * Classe para representar uma Pessoa
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 27/08/2016
 */
public class Pessoa extends EntidadeDominio {

    private String nome;

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
}
