package com.les.marmitex.core.dominio;

import java.util.Date;

/**
 * TODO descrição da classe
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 18/12/2016
 */
public class Preparo extends EntidadeDominio{
    
    private Categoria categoria;
    private double quantidade;
    private Dias dia;
    private Date vencimento;
    private Prato prato;

    /**
     * @return the categoria
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * @param categoria the categoria to set
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * @return the quantidade
     */
    public double getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
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
     * @return the vencimento
     */
    public Date getVencimento() {
        return vencimento;
    }

    /**
     * @param vencimento the vencimento to set
     */
    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }
    
    
}
