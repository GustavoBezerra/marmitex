package com.les.marmitex.core.strategy.impl;

import com.les.marmitex.core.dao.impl.ClienteDAO;
import com.les.marmitex.core.dominio.Cliente;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.strategy.IStrategy;

/**
 * TODO descrição da classe
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 26/09/2016
 */
public class VerificarEmailRepetido implements IStrategy{

    @Override
    public String validar(EntidadeDominio entidade) {
        ClienteDAO cDAO = new ClienteDAO();
        Cliente c = (Cliente)entidade;
        if(!cDAO.verificarDuplicidade(c)){
            return "Já existe um registro com este e-mail!";
        }
        return null;
    }
}
