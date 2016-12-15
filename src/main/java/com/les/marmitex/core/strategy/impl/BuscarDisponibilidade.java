package com.les.marmitex.core.strategy.impl;

import com.les.marmitex.core.dao.impl.DisponibilidadeDAO;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Ingrediente;
import com.les.marmitex.core.strategy.IStrategy;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TODO descrição da classe
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 15/12/2016
 */
public class BuscarDisponibilidade implements IStrategy{

    @Override
    public String validar(EntidadeDominio entidade) {
        try{
            DisponibilidadeDAO dao = new DisponibilidadeDAO();
            dao.consultar(entidade);
        } catch (SQLException ex) {
            Logger.getLogger(BuscarDisponibilidade.class.getName()).log(Level.SEVERE, null, ex);
            return "Erro ao buscar disponibilidade";
        }
        return null;
    }
    
}
