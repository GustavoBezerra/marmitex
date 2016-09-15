package com.les.marmitex.core.dao.impl;

import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RED;
import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RESET;
import com.les.marmitex.core.dominio.Campanha;
import com.les.marmitex.core.dominio.EntidadeDominio;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * TODO descrição da classe
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 12/09/2016
 */
@Component("com.les.marmitex.core.dominio.Campanha")
public class CampanhaDAO extends AbstractJdbcDAO{

    public CampanhaDAO() {
        super("tb_campanha", "id_campanha");
    }

    @Override
    public void salvar(EntidadeDominio entidade) throws SQLException {
        openConnection();
        PreparedStatement pst = null;

        try {
            Campanha campanha = (Campanha) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO tb_campanha(titulo, descricao, ");
            sql.append("regra, valor_regra, beneficio, valor_beneficio, ativo, dt_inicio, dt_fim)");
            sql.append(" VALUES (?,?,?,?,?,?,?,?,?);");

            pst = connection.prepareStatement(sql.toString());
            pst.setString(1, campanha.getNome());
            pst.setString(2, campanha.getDescricao());
            pst.setInt(3, campanha.getRegra());
            pst.setString(4, campanha.getValor_regra());
            pst.setInt(5, campanha.getBenefico());
            pst.setString(6, campanha.getValor_beneficio());
            pst.setBoolean(7, true);
            Timestamp timeInicio = new Timestamp(campanha.getInicio().getTime());
            Timestamp timeFim = new Timestamp(campanha.getFim().getTime());
            pst.setTimestamp(8, timeInicio);
            pst.setTimestamp(9, timeFim);

            pst.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(ANSI_RED + "[ERROR] ROLLBACK - " + e1.getMessage() + ANSI_RESET);
            }
            System.out.println(ANSI_RED + "[ERROR] - " + e.getMessage() + ANSI_RESET);
        } catch (ClassCastException ce) {
            System.out.println(ANSI_RED + "[ERROR] - Entidade " + entidade.getClass().getSimpleName() + " não é uma Campanha!" + ANSI_RESET);
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
        openConnection();
        PreparedStatement pst = null;
        Campanha c;
        List<EntidadeDominio> campanhas = new ArrayList<>();
        boolean campanhaEspecifica = false;

        try {
            Campanha campanha = (Campanha) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT * FROM tb_campanha");
            if (campanha.getId() != 0) {
                sql.append(" WHERE id_campanha=?;");
                campanhaEspecifica = true;
            }

            pst = connection.prepareStatement(sql.toString());
            if (campanhaEspecifica) {
                pst.setInt(1, campanha.getId());
            }

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                c = new Campanha();
                c.setId(rs.getInt("id_campanha"));
                c.setNome(rs.getString("titulo"));
                c.setDescricao(rs.getString("descricao"));
                c.setRegra(rs.getInt("regra"));
                c.setValor_regra(rs.getString("valor_regra"));
                c.setBenefico(rs.getInt("beneficio"));
                c.setValor_beneficio(rs.getString("valor_beneficio"));
                c.setAtivo(rs.getBoolean("ativo"));
                c.setInicio(rs.getDate("dt_inicio"));
                c.setFim(rs.getDate("dt_fim"));

                campanhas.add(c);
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(ANSI_RED + "[ERROR] ROLLBACK - " + e1.getMessage() + ANSI_RESET);
            }
            System.out.println(ANSI_RED + "[ERROR] - " + ex.getMessage() + ANSI_RESET);
        } catch (ClassCastException ce) {
            System.out.println(ANSI_RED + "[ERROR] - Entidade " + entidade.getClass().getSimpleName() + " não é um Endereço!" + ANSI_RESET);
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
        return campanhas;
    }

}
