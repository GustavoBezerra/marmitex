package com.les.marmitex.core.dominio;

import java.util.List;

/**
 *
 * @author gustavo
 */
public class Resultado {
    
    private String mensagem;
    private List<EntidadeDominio> entidades;

    /**
     * @return the mensagem
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * @param mensagem the mensagem to set
     */
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    /**
     * @return the entidades
     */
    public List<EntidadeDominio> getEntidades() {
        return entidades;
    }

    /**
     * @param entidades the entidades to set
     */
    public void setEntidades(List<EntidadeDominio> entidades) {
        this.entidades = entidades;
    }
}
