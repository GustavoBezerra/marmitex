package com.les.marmitex.util.teste;

import com.les.marmitex.core.dominio.Beneficios;
import com.les.marmitex.core.dominio.Campanha;
import com.les.marmitex.core.dominio.Regras;
import java.util.Date;

/**
 * TODO descrição da classe main
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 11/09/2016
 */

public class TestarEnum {

    public static void main(String[] args) {
        Campanha c = new Campanha();
        
        c.setNome("Nova campanha");
        c.setDescricao("Descricao da nova campanha");
        c.setRegra(Regras.VALOR_COMPRA.getCodigo());
        c.setBenefico(Beneficios.FRETE_GRATIS.getCodigo());
        c.setValor_regra("R$ 30");
        c.setInicio(new Date());
        c.setFim(new Date());
        
        System.out.println("Nome da campanha: "+c.getNome());
        System.out.println("Descrição: "+c.getDescricao());        
        
        if(c.getRegra() == Regras.VALOR_COMPRA.getCodigo()){
            System.out.println("Regra: "+ Regras.VALOR_COMPRA.getDescricao() + c.getValor_regra());
        }
        
        if(c.getBenefico() == Beneficios.FRETE_GRATIS.getCodigo()){
            System.out.println("Beneficio: "+Beneficios.FRETE_GRATIS.getDescricao());
        }
        
        System.out.println(c.toString());
    }

}
