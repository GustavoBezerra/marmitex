package com.les.marmitex.core.dominio;

/**
 * TODO descrição da classe
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 07/12/2016
 */
public class Cardapio extends EntidadeDominio{
    
    private Prato prato;
    private Ingrediente ingrediente;
    private Dias dia;

    /**
     * @return the prato
     */
    public Prato getPrato() {
        return prato;
    }

    /**
     * @param prato the prato to set
     */
    public void setPrato(Prato prato) {
        this.prato = prato;
    }

    /**
     * @return the ingrediente
     */
    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    /**
     * @param ingrediente the ingrediente to set
     */
    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    /**
     * @return the dia
     */
    public Dias getDia() {
        return dia;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(Dias dia) {
        this.dia = dia;
    }
}
