package com.les.marmitex.core.dominio;

import java.util.List;

/**
 * Classe para representar a marmitex
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 27/08/2016
 */
public class Marmitex extends EntidadeDominio{
    
    private List<Ingrediente> ingredientes;
    private double valor;

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
    }
    
}
