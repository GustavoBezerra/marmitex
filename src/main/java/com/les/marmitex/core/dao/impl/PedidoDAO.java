package com.les.marmitex.core.dao.impl;

import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RED;
import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RESET;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Pedido;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Classe responsável pelas ações na tb_pedido
 *
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 27/08/2016
 */
@Component("com.les.marmitex.core.dominio.Pedido")
public class PedidoDAO extends AbstractJdbcDAO {

    public PedidoDAO() {
        super("tb_pedido", "id_pedido");
    }

    @Override
    public void salvar(EntidadeDominio entidade) throws SQLException {
        openConnection();
        PreparedStatement pst = null;

        try {
            Pedido pedido = (Pedido) entidade;
            MarmitexDAO marmitexDAO = new MarmitexDAO();
            EnderecoDAO enderecoDAO = new EnderecoDAO();
            EntregadorDAO entregadorDAO = new EntregadorDAO();
            PagamentoDAO pagamentoDAO = new PagamentoDAO();
            connection.setAutoCommit(false);
            
            if(pedido.getEndereco().getId() == 0){
                pedido.getEndereco().setAtivo(false);
                enderecoDAO.salvar(pedido.getEndereco());
            }
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO tb_pedido(id_endereco, id_entregador, id_campanha, valor_frete, ");
            sql.append("valor_total, status, troco, dt_criacao, id_cliente) VALUES (?,?,?,?,?,?,?,?,?);");

            pst = connection.prepareStatement(sql.toString(),
                    Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, pedido.getEndereco().getId());
            pst.setNull(2, Types.NULL);
            pst.setNull(3, Types.NULL);
            pst.setDouble(4, pedido.getValorFrete());
            pst.setDouble(5, pedido.getValorTotal());
            pst.setString(6, pedido.getStatus());            
            pst.setDouble(7, pedido.getTroco());
            Timestamp time = new Timestamp(new Date().getTime());
            pst.setTimestamp(8, time);            
            pst.setInt(9, pedido.getCliente().getId());

            pst.executeUpdate();
            connection.commit();
            ResultSet rs = pst.getGeneratedKeys();
            int idPedido = 0;
            if (rs.next()) {
                idPedido = rs.getInt(1);
            }
            pedido.setId(idPedido);

            // salvar todas as marmitex do pedido
            for (int i = 0; i < pedido.getMarmitex().size(); i++) {
                pedido.getMarmitex().get(i).setId(pedido.getId());
                marmitexDAO.salvar(pedido.getMarmitex().get(i));
            }
            
            // salvar os tipos de pagamento
            pagamentoDAO.salvar(pedido);
            
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(ANSI_RED + "[ERROR] ROLLBACK - " + e1.getMessage() + ANSI_RESET);
            }
            System.out.println(ANSI_RED + "[ERROR] - " + e.getMessage() + ANSI_RESET);
        } catch (ClassCastException ce) {
            System.out.println(ANSI_RED + "[ERROR] - Entidade " + entidade.getClass().getSimpleName() + " não é um Endereço!" + ANSI_RESET);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                connection.close();
            } catch (SQLException e) {
                System.out.println(ANSI_RED + "[ERROR] - " + e.getMessage() + ANSI_RESET);
            }
        }
    }

    @Override
    public void alterar(EntidadeDominio entidade) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
