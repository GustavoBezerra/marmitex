package com.les.marmitex.core.strategy.impl;

import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Ingrediente;
import com.les.marmitex.core.dominio.Preparo;
import com.les.marmitex.core.strategy.IStrategy;

/**
 * TODO descrição da classe
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 18/12/2016
 */
public class ValidarPreparo implements IStrategy{

    @Override
    public String validar(EntidadeDominio entidade) {
        Preparo preparo = (Preparo) entidade;
        for (int i = 0; i < preparo.getPrato().getIngredientes().size(); i++) {
            Ingrediente in = preparo.getPrato().getIngredientes().get(i);
            if(in.getMedida().equals("Unidade(s)")){
                if(in.getQuantidade() < preparo.getQuantidade()){
                    return "Não existe quantidade suficiente de "+in.getNome()+" para o preparo do prato!";
                }
            } else{
                if(in.getQuantidade() < (preparo.getQuantidade()*0.150)){
                    return "Não existe quantidade suficiente de "+in.getNome()+" para o preparo do prato!";
                }
            }
        }
        return null;
    }
    
}
