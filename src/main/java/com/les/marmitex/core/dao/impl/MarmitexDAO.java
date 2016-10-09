package com.les.marmitex.core.dao.impl;

import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RED;
import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RESET;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Ingrediente;
import com.les.marmitex.core.dominio.Marmitex;
import com.les.marmitex.core.strategy.impl.ValidarEstoque;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe responsável pelas ações na tb_marmitex
 *
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 27/08/2016
 */
public class MarmitexDAO extends AbstractJdbcDAO {

    public MarmitexDAO() {
        super("tb_marmitex", "id_marmitex");
    }

    @Override
    public void salvar(EntidadeDominio entidade) throws SQLException {
        openConnection();
        PreparedStatement pst = null;

        try {
            Marmitex marmitex = (Marmitex) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO tb_marmitex(id_pedido, valor) ");
            sql.append("VALUES (?,?);");

            pst = connection.prepareStatement(sql.toString(),
                    Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, marmitex.getId());
            pst.setDouble(2, marmitex.getValor());

            pst.executeUpdate();
            connection.commit();
            ResultSet rs = pst.getGeneratedKeys();
            int idMarmitex = 0;
            if (rs.next()) {
                idMarmitex = rs.getInt(1);
            }
            marmitex.setId(idMarmitex);

            for (int i = 0; i < marmitex.getIngredientes().size(); i++) {
                salvarIngredientes(marmitex.getIngredientes().get(i), marmitex.getId());
            }
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

    public void salvarIngredientes(Ingrediente i, int id_marmitex) {
        openConnection();
        PreparedStatement pst = null;

        try {
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO tb_marmitex_ingrediente(id_marmitex, id_ingrediente) ");
            sql.append("VALUES (?,?);");

            pst = connection.prepareStatement(sql.toString());
            pst.setInt(1, id_marmitex);
            pst.setInt(2, i.getId());

            pst.executeUpdate();
            connection.commit();
            darBaixaNoEstoque(i);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(ANSI_RED + "[ERROR] ROLLBACK - " + e1.getMessage() + ANSI_RESET);
            }
            System.out.println(ANSI_RED + "[ERROR] - " + e.getMessage() + ANSI_RESET);
        } catch (ClassCastException ce) {
            System.out.println(ANSI_RED + "[ERROR] - Entidade " + i.getClass().getSimpleName() + " não é um Ingrediente!" + ANSI_RESET);
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

    private void darBaixaNoEstoque(Ingrediente i) {
        double qtde;
        IngredienteDAO iDAO = new IngredienteDAO();
        try {
            Ingrediente ing = (Ingrediente) iDAO.consultar(i).get(0);
            if (!("Unidade(s)").equals(i.getMedida())) {
                try {
                    i.setQuantidade(ing.getQuantidade() - 0.150);
                    iDAO.alterar(i);
                } catch (SQLException ex) {
                    Logger.getLogger(ValidarEstoque.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    i.setQuantidade(ing.getQuantidade() - 1);
                    iDAO.alterar(i);
                } catch (SQLException ex) {
                    Logger.getLogger(ValidarEstoque.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {         
            Logger.getLogger(MarmitexDAO.class.getName()).log(Level.SEVERE, null, ex);
        }            
    }
}
