package com.les.marmitex.core.dominio;

/**
 * TODO descrição do ENUM
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 24/09/2016
 */
public enum Status {
    
    ABERTO("Aberto",1),
    EM_PREPARO("Em preparo",2),
    PRONTO("Pronto",3),
    A_CAMINHO("A caminho",4),
    ENTREGUE("Entregue",5),
    CANCELADO("Cancelado",6),
    DEVOLVIDO("Devolvido",7);    

    private int codigo;
    private String descricao;

    Status(String descricao, int codigo){
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
