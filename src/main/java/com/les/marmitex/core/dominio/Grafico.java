package com.les.marmitex.core.dominio;

import java.util.Date;
import java.util.List;

/**
 * TODO descrição da classe
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 05/11/2016
 */
public class Grafico extends EntidadeDominio{
    
    private List<String> itens;
    private String tipo;
    private Date dtInicio;
    private Date dtFim;

    /**
     * @return the itens
     */
    public List<String> getItens() {
        return itens;
    }

    /**
     * @param itens the itens to set
     */
    public void setItens(List<String> itens) {
        this.itens = itens;
    }

    /**
     * @return the dtInicio
     */
    public Date getDtInicio() {
        return dtInicio;
    }

    /**
     * @param dtInicio the dtInicio to set
     */
    public void setDtInicio(Date dtInicio) {
        this.dtInicio = dtInicio;
    }

    /**
     * @return the dtFim
     */
    public Date getDtFim() {
        return dtFim;
    }

    /**
     * @param dtFim the dtFim to set
     */
    public void setDtFim(Date dtFim) {
        this.dtFim = dtFim;
    }
    
    public String getTipo(){
        return tipo;
    }
    
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
}
