package com.les.marmitex.core.dao.impl;

import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RED;
import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RESET;
import com.les.marmitex.core.dominio.Categoria;
import com.les.marmitex.core.dominio.Endereco;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Ingrediente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Classe responsável pelas ações na tb_ingrediente
 *
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 27/08/2016
 */
@Component("com.les.marmitex.core.dominio.Ingrediente")
public class IngredienteDAO extends AbstractJdbcDAO {

    public IngredienteDAO() {
        super("tb_ingredientes", "id_ingrediente");
    }

    @Override
    public void salvar(EntidadeDominio entidade) throws SQLException {
        openConnection();
        PreparedStatement pst = null;

        try {
            Ingrediente ingrediente = (Ingrediente) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO tb_ingredientes(nome, quantidade, ");
            sql.append("medida, dt_vencimento, dt_cadastro, categoria)");
            sql.append(" VALUES (?,?,?,?,?,?);");

            pst = connection.prepareStatement(sql.toString());
            pst.setString(1, ingrediente.getNome());
            pst.setDouble(2, ingrediente.getQuantidade());
            pst.setString(3, ingrediente.getMedida());            
            Timestamp timeVenc = new Timestamp(ingrediente.getDtVencimento().getTime());
            Timestamp timeCad = new Timestamp(ingrediente.getDtCriacao().getTime());
            pst.setTimestamp(4, timeVenc);
            pst.setTimestamp(5, timeCad);
            pst.setString(6, ingrediente.getCategoria().getNome());
            
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
        openConnection();
        PreparedStatement pst = null;

        try {
            Ingrediente ingrediente = (Ingrediente) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE tb_ingredientes SET nome=?, quantidade=?, ");
            sql.append("medida=?, dt_vencimento=?, categoria=? where id_ingrediente=?;");            

            pst = connection.prepareStatement(sql.toString());
            pst.setString(1, ingrediente.getNome());
            pst.setDouble(2, ingrediente.getQuantidade());
            pst.setString(3, ingrediente.getMedida());            
            Timestamp timeVenc = new Timestamp(ingrediente.getDtVencimento().getTime());            
            pst.setTimestamp(4, timeVenc);
            pst.setString(5, ingrediente.getCategoria().getNome());
            pst.setInt(6, ingrediente.getId());
            
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
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
        openConnection();
        PreparedStatement pst = null;
        Ingrediente i;
        Categoria c;
        List<EntidadeDominio> ingredientes = new ArrayList<>();
        boolean ingredienteEspecifico = false;

        try {
            Ingrediente ingrediente = (Ingrediente) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT * FROM tb_ingredientes");
            if (ingrediente.getId() != 0) {
                sql.append(" WHERE id_ingrediente=?;");
                ingredienteEspecifico = true;
            } 

            pst = connection.prepareStatement(sql.toString());
            if (ingredienteEspecifico) {
                pst.setInt(1, ingrediente.getId());
            } 
            
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                i = new Ingrediente();
                c = new Categoria();
                i.setId(rs.getInt("id_ingrediente"));                
                i.setNome(rs.getString("nome"));
                i.setQuantidade(rs.getDouble("quantidade"));
                i.setMedida(rs.getString("medida"));
                i.setDtVencimento(rs.getDate("dt_vencimento"));
                i.setDtCriacao(rs.getDate("dt_cadastro")); 
                c.setNome(rs.getString("categoria"));
                i.setCategoria(c);

                ingredientes.add(i);
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(ANSI_RED + "[ERROR] ROLLBACK - " + e1.getMessage() + ANSI_RESET);
            }
            System.out.println(ANSI_RED + "[ERROR] - " + ex.getMessage() + ANSI_RESET);
        } catch (ClassCastException ce) {
            System.out.println(ANSI_RED + "[ERROR] - Entidade " + entidade.getClass().getSimpleName() + " não é um Ingrediente!" + ANSI_RESET);
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
        return ingredientes;
    }
    
    

}
