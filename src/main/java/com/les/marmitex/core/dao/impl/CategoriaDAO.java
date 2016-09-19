package com.les.marmitex.core.dao.impl;

import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RED;
import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RESET;
import com.les.marmitex.core.dominio.Categoria;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Ingrediente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * TODO descrição da classe
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 12/09/2016
 */
@Component("com.les.marmitex.core.dominio.Categoria")
public class CategoriaDAO extends AbstractJdbcDAO{

    public CategoriaDAO(){
        super("tb_categoria", "id_categoria");
    }

    @Override
    public void salvar(EntidadeDominio entidade) throws SQLException {
        openConnection();
        PreparedStatement pst = null;

        try {
            Categoria categoria = (Categoria) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO tb_categoria(nome, valor)");
            sql.append(" VALUES (?, ?);");

            pst = connection.prepareStatement(sql.toString());
            pst.setString(1, categoria.getNome());
            pst.setDouble(2, categoria.getValor());

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
            System.out.println(ANSI_RED + "[ERROR] - Entidade " + entidade.getClass().getSimpleName() + " não é uma Categoria!" + ANSI_RESET);
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
            Categoria categoria = (Categoria) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE tb_categoria SET nome=?, valor=?");
            sql.append(" WHERE id_categoria=?;");

            pst = connection.prepareStatement(sql.toString());
            pst.setString(1, categoria.getNome());
            pst.setDouble(2, categoria.getValor());
            pst.setInt(3, categoria.getId());

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
            System.out.println(ANSI_RED + "[ERROR] - Entidade " + entidade.getClass().getSimpleName() + " não é uma Categoria!" + ANSI_RESET);
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
        Categoria c;
        List<EntidadeDominio> categorias = new ArrayList<>();
        boolean categoriaEspecifica = false;

        try {
            Categoria categoria = (Categoria) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT * FROM tb_categoria");
            if (categoria.getId() != 0) {
                sql.append(" WHERE id_categoria=?;");
                categoriaEspecifica = true;
            }

            pst = connection.prepareStatement(sql.toString());
            if (categoriaEspecifica) {
                pst.setInt(1, categoria.getId());
            }

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                c = new Categoria();
                c.setNome(rs.getString("nome"));
                c.setId(rs.getInt("id_categoria"));
                c.setValor(rs.getDouble("valor"));
                categorias.add(c);
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(ANSI_RED + "[ERROR] ROLLBACK - " + e1.getMessage() + ANSI_RESET);
            }
            System.out.println(ANSI_RED + "[ERROR] - " + ex.getMessage() + ANSI_RESET);
        } catch (ClassCastException ce) {
            System.out.println(ANSI_RED + "[ERROR] - Entidade " + entidade.getClass().getSimpleName() + " não é uma Categoria!" + ANSI_RESET);
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
        return categorias;
    }

}
