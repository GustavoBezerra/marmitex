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
            sql = getQuery(grafico);

            pst = connection.prepareStatement(sql.toString());
            Timestamp timeInicio = new Timestamp(grafico.getDtInicio().getTime());
            Timestamp timeFim = new Timestamp(grafico.getDtFim().getTime());
            pst.setTimestamp(1, timeInicio);
            pst.setTimestamp(2, timeFim);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ig = new ItemGrafico();
                ig.setNome(rs.getString("nome"));
                ig.setValor(rs.getString(("valor")));
                ig.setData(rs.getDate("data"));

                if (grafico.getTipo().equals("radar")) {
                    ig.setIngrediente(rs.getString("nome_ingrediente"));
                }

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

    private StringBuilder getQuery(Grafico grafico) {
        StringBuilder sql = new StringBuilder();

        if (grafico.getTipo().equals("linhas")) {
            sql.append("select i.nome, count(i.nome) as valor, p.dt_criacao as data from tb_ingredientes i\n"
                    + "inner join tb_marmitex_ingrediente mi on mi.id_ingrediente=i.id_ingrediente\n"
                    + "inner join tb_marmitex m on m.id_marmitex=mi.id_marmitex\n"
                    + "inner join tb_pedido p on m.id_pedido=p.id_pedido\n"
                    + "where ");
            if (grafico.getItens() == null || grafico.getItens().isEmpty()) {
                sql.append("p.dt_criacao BETWEEN ? and ?\n"
                        + "GROUP BY i.nome, p.dt_criacao\n"
                        + "ORDER BY p.dt_criacao;");
            } else {
                sql.append("(");
                for (int i = 0; i < grafico.getItens().size(); i++) {
                    sql.append("i.nome='" + grafico.getItens().get(i) + "' ");
                    if ((i + 1) == grafico.getItens().size()) {
                        sql.append(") and ");
                        sql.append("p.dt_criacao BETWEEN ? and ?\n"
                                + "GROUP BY i.nome, p.dt_criacao\n"
                                + "ORDER BY p.dt_criacao;");
                    } else {
                        sql.append("or ");
                    }
                }
            }
        } else if (grafico.getTipo().equals("barras")) {
            sql.append("select e.nome, count(e.nome) as valor, p.dt_criacao as data from tb_pedido p\n"
                    + "inner join tb_entregador e on p.id_entregador=e.id_entregador\n"
                    + "where ");

            if (grafico.getItens() == null || grafico.getItens().isEmpty()) {
                sql.append("p.dt_criacao BETWEEN ? and ?\n"
                        + "GROUP BY e.nome;");
            } else {
                sql.append("(");
                for (int i = 0; i < grafico.getItens().size(); i++) {
                    sql.append("e.nome='" + grafico.getItens().get(i) + "' ");
                    if ((i + 1) == grafico.getItens().size()) {
                        sql.append(") and ");
                        sql.append("p.dt_criacao BETWEEN ? and ?\n"
                                + "GROUP BY e.nome;");
                    } else {
                        sql.append("or ");
                    }
                }
            }
        } else if (grafico.getTipo().equals("radar")) {
            sql.append("select i.nome as nome_ingrediente, j.descricao as nome, count(j.id_justificativa) as valor, p.dt_criacao as data from tb_pedido p\n"
                    + "inner join tb_justificativa j on p.id_justificativa=j.id_justificativa\n"
                    + "  inner join tb_marmitex m on m.id_pedido=p.id_pedido\n"
                    + "  inner join tb_marmitex_ingrediente mi on mi.id_marmitex=m.id_marmitex\n"
                    + "  inner join tb_ingredientes i on i.id_ingrediente=mi.id_ingrediente\n"
                    + "  where ");

            if (grafico.getItens() == null || grafico.getItens().isEmpty()) {
                sql.append("p.dt_criacao BETWEEN ? and ?\n"
                        + "GROUP BY i.nome, j.descricao, p.dt_criacao\n"
                        + "ORDER BY p.dt_criacao, i.nome;");
            } else {
                sql.append("(");
                for (int i = 0; i < grafico.getItens().size(); i++) {
                    sql.append("j.descricao='" + grafico.getItens().get(i) + "' ");
                    if ((i + 1) == grafico.getItens().size()) {
                        sql.append(") and ");
                        sql.append("p.dt_criacao BETWEEN ? and ?\n"
                                + "GROUP BY i.nome, j.descricao, p.dt_criacao\n"
                                + "ORDER BY p.dt_criacao, i.nome;");
                    } else {
                        sql.append("or ");
                    }
                }
            }
        }
        return sql;
    }
}
