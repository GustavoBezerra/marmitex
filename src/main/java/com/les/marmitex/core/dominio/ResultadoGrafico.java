package com.les.marmitex.core.dominio;

import java.util.List;

/**
 * TODO descrição da classe
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 05/11/2016
 */
public class ResultadoGrafico extends EntidadeDominio{
    
    private List<ItemGrafico> itens;

    /**
     * @return the itens
     */
    public List<ItemGrafico> getItens() {
        return itens;
    }

    /**
     * @param itens the itens to set
     */
    public void setItens(List<ItemGrafico> itens) {
        this.itens = itens;
    }
    
    
    
}
