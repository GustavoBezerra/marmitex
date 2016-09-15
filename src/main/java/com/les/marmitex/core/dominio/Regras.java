package com.les.marmitex.core.dominio;

/**
 * TODO descrição do ENUM
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 11/09/2016
 */
public enum Regras {

    NUMERO_DE_VENDAS("Número de pedidos",1),
    PRATO_ESPECIFICO("Prato específico",2),
    VALOR_COMPRA("Valor da compra acima de ",3);

    private int codigo;
    private String descricao;

    Regras(String descricao, int codigo){
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
