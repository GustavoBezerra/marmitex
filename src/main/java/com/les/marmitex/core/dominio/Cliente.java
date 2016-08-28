package com.les.marmitex.core.dominio;

import java.util.List;

/**
 * Classe para representar o Cliente
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 27/08/2016
 */
public class Cliente extends Usuario{
    
    private List<Endereco> enderecos;    

    /**
     * @return the enderecos
     */
    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    /**
     * @param enderecos the enderecos to set
     */
    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
    
}
