package com.les.marmitex.core.strategy.impl;

import com.les.marmitex.core.dao.impl.ClienteDAO;
import com.les.marmitex.core.dominio.Cliente;
import com.les.marmitex.core.dominio.Credito;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Pedido;
import com.les.marmitex.core.dominio.Status;
import com.les.marmitex.core.strategy.IStrategy;

/**
 * TODO descrição da classe
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 26/09/2016
 */
public class EfetuarDevolucao implements IStrategy{

    @Override
    public String validar(EntidadeDominio entidade) {
        Pedido pedido = (Pedido)entidade;
        Cliente cliente;
        Credito credito = new Credito();
        ClienteDAO cDAO = new ClienteDAO();
        double saldo=0;
        if(pedido.getStatus().equals(Status.DEVOLVIDO.getDescricao())){            
            cliente = pedido.getCliente();
            saldo += cliente.getCredito().getValor() + pedido.getValorTotal();
            credito.setValor(saldo);
            cliente.setCredito(credito);
            cDAO.alterar(cliente);
        }
        return null;
    }    
}
