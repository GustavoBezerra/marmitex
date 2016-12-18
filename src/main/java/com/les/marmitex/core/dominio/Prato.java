package com.les.marmitex.core.dominio;

import java.util.Date;
import java.util.List;

/**
 * Entidade para representar um prato pré-definido
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 01/10/2016
 */
public class Prato extends EntidadeDominio{

    private List<Dias> dtDisponivel;
    private String nome;
    private List<Ingrediente> ingredientes;
    private double valor;

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

    /**
     * @return the ingredientes
     */
    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    /**
     * @param ingredientes the ingredientes to set
     */
    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    /**
     * @return the valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(double valor) {
        this.valor = valor;
    }}

