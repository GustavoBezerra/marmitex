package com.les.marmitex.core.dao.impl;

import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RED;
import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RESET;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Ingrediente;
import com.les.marmitex.core.dominio.Preparo;
import com.les.marmitex.core.strategy.impl.ValidarEstoque;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

/**
 * TODO descrição da classe
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 18/12/2016
 */
@Component("com.les.marmitex.core.dominio.Preparo")
public class PreparoDAO extends AbstractJdbcDAO{

    public PreparoDAO() {
        super("tb_ingrediente", "id_ingrediente");
    }

    @Override
    public void salvar(EntidadeDominio entidade) throws SQLException {
        openConnection();
        PreparedStatement pst = null;

        try {
            Preparo preparo = (Preparo) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO tb_ingredientes(nome, quantidade, ");
            sql.append("medida, dt_vencimento, dt_cadastro, categoria, valor, ativo)");
            sql.append(" VALUES (?,?,?,?,?,?,?,?);");

            pst = connection.prepareStatement(sql.toString(),
                    Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, preparo.getPrato().getNome());
            pst.setDouble(2, preparo.getQuantidade());
            pst.setString(3, "Unidade(s)");
            Timestamp timeVenc = new Timestamp(preparo.getVencimento().getTime());
            Timestamp timeCad = new Timestamp(new Date().getTime());
            pst.setTimestamp(4, timeVenc);
            pst.setTimestamp(5, timeCad);
            pst.setInt(6, preparo.getCategoria().getId());
            pst.setDouble(7, preparo.getPrato().getValor());
            pst.setBoolean(8, true);

            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            int idIngri = 0;
            if (rs.next()) {
                idIngri = rs.getInt(1);
            }
            preparo.setId(idIngri);

            connection.commit();

            if (preparo.getPrato().getDtDisponivel()!= null) {
                DisponibilidadeDAO disponibilidadeDAO = new DisponibilidadeDAO();
                Ingrediente i = new Ingrediente();
                i.setId(preparo.getId());
                i.setDias(preparo.getPrato().getDtDisponivel());
                disponibilidadeDAO.salvar(i);
            }
            
            for (int i = 0; i < preparo.getPrato().getIngredientes().size(); i++) {
                darBaixaNoEstoque(preparo.getPrato().getIngredientes().get(i), preparo.getQuantidade());
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(ANSI_RED + "[ERROR] ROLLBACK - " + e1.getMessage() + ANSI_RESET);
            }
            System.out.println(ANSI_RED + "[ERROR] - " + e.getMessage() + ANSI_RESET);
        } catch (ClassCastException ce) {
            System.out.println(ANSI_RED + "[ERROR] - Entidade " + entidade.getClass().getSimpleName() + " não é um Ingrediente!" + ANSI_RESET);
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
    
    private void darBaixaNoEstoque(Ingrediente i, double quantidade) {
        IngredienteDAO iDAO = new IngredienteDAO();
        try {
            Ingrediente ing = (Ingrediente) iDAO.consultar(i).get(0);
            if (!("Unidade(s)").equals(i.getMedida())) {
                try {
                    i.setQuantidade(ing.getQuantidade() - (0.150*quantidade));
                    iDAO.alterarQtde(i);
                } catch (SQLException ex) {
                    Logger.getLogger(ValidarEstoque.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    i.setQuantidade(ing.getQuantidade() - quantidade);
                    iDAO.alterarQtde(i);
                } catch (SQLException ex) {
                    Logger.getLogger(ValidarEstoque.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarmitexDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
