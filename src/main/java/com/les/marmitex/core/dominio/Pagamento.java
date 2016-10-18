package com.les.marmitex.core.dominio;

/**
 * TODO descrição do ENUM
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 23/09/2016
 */
public enum Pagamento {

    CARTAO("Cartão",1),
    DINHEIRO("Dinheiro",2),
    CREDITO("Crédito",3);

    private int codigo;
    private String descricao;

    Pagamento(String descricao, int codigo){
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
