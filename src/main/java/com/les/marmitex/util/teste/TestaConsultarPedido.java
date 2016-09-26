package com.les.marmitex.util.teste;

import com.les.marmitex.core.dao.impl.PedidoDAO;
import com.les.marmitex.core.dominio.Cliente;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Ingrediente;
import com.les.marmitex.core.dominio.Pedido;
import java.sql.SQLException;

/**
 * TODO descrição da classe main
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 26/09/2016
 */

public class TestaConsultarPedido {

    public static void main(String[] args) throws SQLException {
        PedidoDAO pedidoDAO = new PedidoDAO();
        Pedido p = new Pedido();
        Cliente c = new Cliente();
        c.setId(3);
        p.setCliente(c);
        
        for (EntidadeDominio e : pedidoDAO.consultar(p)) {
            Pedido pedido = (Pedido)e;
            System.out.println("ID do pedido: "+pedido.getId()+"\n"
                    + "Status: "+pedido.getStatus()+"\n"
                    + "Valor total: "+pedido.getValorTotal()+"\n"
                    + "Valor do frete: "+pedido.getValorFrete()+"\n"
                    + "Formas de pagamento:");
            for (int i = 0; i < pedido.getPagamento().size(); i++) {
                System.out.println("- "+pedido.getPagamento().get(i).getDescricao());
            }
            System.out.println("Marmitex:");
            for (Ingrediente i : pedido.getMarmitex().get(0).getIngredientes()) {
                System.out.println("- "+i.getNome());
            }
            System.out.println("-------------------------------");
        }
        
    }

}
