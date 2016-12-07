package com.les.marmitex.core.dao.impl;

import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RED;
import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RESET;
import com.les.marmitex.core.dominio.Cardapio;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Ingrediente;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * TODO descrição da classe
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 07/12/2016
 */
@Component("com.les.marmitex.core.dominio.Cardapio")
public class CardapioDAO extends AbstractJdbcDAO{

    public CardapioDAO() {
        super("tb_agenda", "id_ingrediente");
    }

    @Override
    public void salvar(EntidadeDominio entidade) throws SQLException {
        PreparedStatement pst = null;
        openConnection();
        try {
            Cardapio agenda = (Cardapio) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();
            if(agenda.getIngrediente() != null){
                sql.append("INSERT INTO tb_cardapio(id_ingrediente, id_dia)");
                sql.append(" VALUES (?,?);");
            }
            else{
                sql.append("INSERT INTO tb_cardapio(id_prato, id_dia) ");
                sql.append(" VALUES (?,?);");
            }
            
            pst = connection.prepareStatement(sql.toString());
            if(agenda.getIngrediente() != null){
                pst.setInt(1, agenda.getIngrediente().getId());
            }
            else{
                pst.setInt(1, agenda.getPrato().getId());
            }
            pst.setInt(2, agenda.getDia().getCodigo());
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
    
}
