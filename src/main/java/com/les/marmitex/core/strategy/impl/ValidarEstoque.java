package com.les.marmitex.core.strategy.impl;

import com.les.marmitex.core.dao.impl.IngredienteDAO;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Ingrediente;
import com.les.marmitex.core.dominio.Marmitex;
import com.les.marmitex.core.strategy.IStrategy;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TODO descrição da classe
 *
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 26/09/2016
 */
public class ValidarEstoque implements IStrategy {

    @Override
    public String validar(EntidadeDominio entidade) {
        Marmitex m = new Marmitex();
        IngredienteDAO iDAO = new IngredienteDAO();
        double qtde;
        for (Ingrediente i : m.getIngredientes()) {
            if (!("Unidade(s)").equals(i.getMedida())) {
                if (i.getQuantidade() > 0.150) {
                    try {
                        qtde = i.getQuantidade();
                        i.setQuantidade(qtde - 0.150);
                        iDAO.alterar(i);
                    } catch (SQLException ex) {
                        Logger.getLogger(ValidarEstoque.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else{
                    return "Desculpe, infelizmente não temos mais "+i.getNome();
                }
            }
            else{
                if (i.getQuantidade() > 1) {
                    try {
                        qtde = i.getQuantidade();
                        i.setQuantidade(qtde - 1);
                        iDAO.alterar(i);
                    } catch (SQLException ex) {
                        Logger.getLogger(ValidarEstoque.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                else{
                    return "Desculpe, infelizmente não temos mais "+i.getNome();
                }
            }
        }
        return null;
    }

}
