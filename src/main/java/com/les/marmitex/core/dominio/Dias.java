package com.les.marmitex.core.dominio;

/**
 * TODO descrição do ENUM
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 21/10/2016
 */
public enum Dias {
    
    SEGUNDA("Segunda-feira",1),
    TERCA("Terça-feira",2),
    QUARTA("Quarta-feira",3),
    QUINTA("Quinta-feira",4),
    SEXTA("Sexta-feira",5),
    SABADO("Sabado",6),
    DOMINGO("Domingo",7),
    TODOS("Todos os dias", 8);

    private int codigo;
    private String descricao;

    Dias(String descricao, int codigo){
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
