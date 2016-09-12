package com.les.marmitex.core.dominio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO descrição da classe
 *
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 11/09/2016
 */
public class Campanha extends EntidadeDominio {

    private String nome;
    private String descricao;
    private int benefico;
    private int regra;
    private String valor_beneficio;
    private String valor_regra;
    private Date inicio;
    private Date fim;
    private boolean ativo;

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
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the valor_beneficio
     */
    public String getValor_beneficio() {
        return valor_beneficio;
    }

    /**
     * @param valor_beneficio the valor_beneficio to set
     */
    public void setValor_beneficio(String valor_beneficio) {
        this.valor_beneficio = valor_beneficio;
    }

    /**
     * @return the valor_regra
     */
    public String getValor_regra() {
        return valor_regra;
    }

    /**
     * @param valor_regra the valor_regra to set
     */
    public void setValor_regra(String valor_regra) {
        this.valor_regra = valor_regra;
    }

    /**
     * @return the benefico
     */
    public int getBenefico() {
        return benefico;
    }

    /**
     * @param benefico the benefico to set
     */
    public void setBenefico(int benefico) {
        this.benefico = benefico;
    }

    /**
     * @return the regra
     */
    public int getRegra() {
        return regra;
    }

    /**
     * @param regra the regra to set
     */
    public void setRegra(int regra) {
        this.regra = regra;
    }
    
    /**
     * @return the inicio
     */
    public Date getInicio() {
        return inicio;
    }

    /**
     * @param inicio the inicio to set
     */
    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    /**
     * @return the fim
     */
    public Date getFim() {
        return fim;
    }

    /**
     * @param fim the fim to set
     */
    public void setFim(Date fim) {
        this.fim = fim;
    }

//    @Override
//    public String toString() {
//        String formatoDt = "dd/MM/yyyy";
//        
//        SimpleDateFormat df = new SimpleDateFormat(formatoDt);
//
//        String dtInicio = df.format(getInicio());
//        String dtFim = df.format(getFim());
//
//        return "Nome da campanha: " + nome
//                + "\nDescrição: " + descricao
//                + "\nDuração: " + dtInicio + " até " + dtFim
//                + "\nRegra: " + ;
//
//    }

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
