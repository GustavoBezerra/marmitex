package com.les.marmitex.util.teste;

import com.les.marmitex.core.dao.impl.EnderecoDAO;
import com.les.marmitex.core.dao.impl.PedidoDAO;
import com.les.marmitex.core.dominio.Cliente;
import com.les.marmitex.core.dominio.Endereco;
import com.les.marmitex.core.dominio.Ingrediente;
import com.les.marmitex.core.dominio.Marmitex;
import com.les.marmitex.core.dominio.Pagamento;
import com.les.marmitex.core.dominio.Pedido;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TODO descrição da classe main
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 25/09/2016
 */

public class TestarSalvarPedido {

    public static void main(String[] args) throws SQLException {
        PedidoDAO pedidoDAO = new PedidoDAO();
        EnderecoDAO endDAO = new EnderecoDAO();
        Pedido p = new Pedido();
        Endereco e = new Endereco();
        Marmitex m = new Marmitex();
        Cliente c = new Cliente();
        Ingrediente i = new Ingrediente();
        Ingrediente i2 = new Ingrediente();
        double valor_marmitex=0;
        List<Pagamento> pagamentos = new ArrayList<>();
        List<Marmitex> marmitexs = new ArrayList<>();
        List<Ingrediente> ingredientes = new ArrayList<>();
        
        e.setId(3);
        c.setId(3);
        
        i.setId(9);
        i2.setId(11);
        
        ingredientes.add(i);
        ingredientes.add(i2);
        
        m.setAtivo(true);
        m.setDtCriacao(new Date());
        m.setIngredientes(ingredientes);
        for (int j = 0; j < ingredientes.size(); j++) {
            valor_marmitex += ingredientes.get(j).getValor();
        }
        m.setValor(valor_marmitex);
        marmitexs.add(m);
        
        pagamentos.add(Pagamento.CARTAO);
        pagamentos.add(Pagamento.DINHEIRO);
        
        p.setMarmitex(marmitexs);
        p.setPagamento(pagamentos);
        p.setAtivo(true);
        p.setDtCriacao(new Date());
        p.setEndereco(e);
        p.setCliente(c);
        p.setStatus("Aberto");
        p.setTroco(2.13);
        p.setValorFrete(2);
        p.setValorTotal(valor_marmitex+2);
        
        pedidoDAO.salvar(p);
    }

}
