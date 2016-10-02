package com.les.marmitex.core.dao.impl;

import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RED;
import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RESET;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Ingrediente;
import com.les.marmitex.core.dominio.Prato;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * TODO descrição da classe
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 02/10/2016
 */
@Component("com.les.marmitex.core.dominio.Prato")
public class PratoDAO extends AbstractJdbcDAO{
    
    public PratoDAO(){
        super("tb_prato", "id_prato");
    }

    @Override
    public void salvar(EntidadeDominio entidade) throws SQLException {
        openConnection();
        PreparedStatement pst = null;

        try {
            Prato prato = (Prato) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO tb_prato(valor, dt_disponivel) ");
            sql.append("VALUES (?,?);");

            pst = connection.prepareStatement(sql.toString(),
                    Statement.RETURN_GENERATED_KEYS);            
            pst.setDouble(1, prato.getValor());
            Timestamp dtDisponivel = new Timestamp(prato.getDtDisponivel().getTime());
            pst.setTimestamp(2, dtDisponivel);

            pst.executeUpdate();
            connection.commit();
            ResultSet rs = pst.getGeneratedKeys();
            int idPrato = 0;
            if (rs.next()) {
                idPrato = rs.getInt(1);
            }
            prato.setId(idPrato);

            for (int i = 0; i < prato.getIngredientes().size(); i++) {
                salvarIngredientes(prato.getIngredientes().get(i), prato.getId());
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
    
    public void salvarIngredientes(Ingrediente i, int id_prato) {
        openConnection();
        PreparedStatement pst = null;

        try {
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO tb_prato_ingrediente(id_prato, id_ingrediente) ");
            sql.append("VALUES (?,?);");

            pst = connection.prepareStatement(sql.toString());
            pst.setInt(1, id_prato);
            pst.setInt(2, i.getId());

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
    
}
