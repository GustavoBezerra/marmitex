package com.les.marmitex.core.dominio;

/**
 * TODO descrição do ENUM
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 11/09/2016
 */
public enum Beneficios {

    FRETE_GRATIS("Frete grátis",1),
    MARMITEX_GRATIS("Marmitex grátis",2),
    INGREDIENTE_GRATIS("Cortesia",3),
    DESCONTO_NO_PEDIDO("Desconto",4);

    private int codigo;
    private String descricao;

    Beneficios(String descricao, int codigo){
        this.descricao = descricao;
        this.codigo = codigo;
    }

    public int getCodigo(){
        return this.codigo;
    }

    public String getDescricao(){
        return this.descricao;
    }
}
