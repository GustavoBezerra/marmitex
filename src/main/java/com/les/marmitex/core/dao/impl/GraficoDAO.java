package com.les.marmitex.core.dao.impl;

import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Grafico;
import com.les.marmitex.core.dominio.ItemGrafico;
import com.les.marmitex.core.dominio.ResultadoGrafico;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Classe resposável por extrair as informações da análise
 *
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 03/11/2016
 */
@Component("com.les.marmitex.core.dominio.Grafico")
public class GraficoDAO extends AbstractJdbcDAO {

    public GraficoDAO() {
        super("tb_pedido", "id_pedido");
    }

    @Override
    public void salvar(EntidadeDominio entidade) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void alterar(EntidadeDominio entidade) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
        openConnection();
        PreparedStatement pst = null;
        List<EntidadeDominio> itens = new ArrayList();
        ItemGrafico ig;

        try {
            Grafico grafico = (Grafico) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();

            sql.append("select i.nome, count(i.nome) as valor, p.dt_criacao as data from tb_ingredientes i\n"
                    + "inner join tb_marmitex_ingrediente mi on mi.id_ingrediente=i.id_ingrediente\n"
                    + "inner join tb_marmitex m on m.id_marmitex=mi.id_marmitex\n"
                    + "inner join tb_pedido p on m.id_pedido=p.id_pedido\n"
                    + "where p.dt_criacao BETWEEN ? and ?\n"
                    + "GROUP BY i.nome, p.dt_criacao\n"
                    + "ORDER BY p.dt_criacao;");

            pst = connection.prepareStatement(sql.toString());
            Timestamp timeInicio = new Timestamp(grafico.getDtInicio().getTime());
            Timestamp timeFim = new Timestamp(grafico.getDtFim().getTime());
            pst.setTimestamp(1, timeInicio);
            pst.setTimestamp(2, timeFim);
            System.out.println("QUERY: \n"+pst.toString());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ig = new ItemGrafico();
                ig.setNome(rs.getString("nome"));
                ig.setValor(rs.getString(("valor")));
                ig.setData(rs.getDate("data"));
                
                itens.add(ig);
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(ANSI_RED + "[ERROR] ROLLBACK - " + e1.getMessage() + ANSI_RESET);
            }
            System.out.println(ANSI_RED + "[ERROR] - " + ex.getMessage() + ANSI_RESET);
        } catch (ClassCastException ce) {
            System.out.println(ANSI_RED + "[ERROR] - Entidade " + entidade.getClass().getSimpleName() + " não é um Entregador!" + ANSI_RESET);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                connection.close();
            } catch (SQLException ex) {
                System.out.println(ANSI_RED + "[ERROR] - " + ex.getMessage() + ANSI_RESET);
            }
        }
        return itens;

    }

}
