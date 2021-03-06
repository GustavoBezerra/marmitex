package com.les.marmitex.core.dao.impl;

import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RED;
import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RESET;
import com.les.marmitex.core.dominio.Categoria;
import com.les.marmitex.core.dominio.Dias;
import com.les.marmitex.core.dominio.Endereco;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Ingrediente;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            sql.append("medida, dt_vencimento, dt_cadastro, categoria, valor, ativo)");
            sql.append(" VALUES (?,?,?,?,?,?,?,?);");

            pst = connection.prepareStatement(sql.toString(),
                    Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, ingrediente.getNome());
            pst.setDouble(2, ingrediente.getQuantidade());
            pst.setString(3, ingrediente.getMedida());
            Timestamp timeVenc = new Timestamp(ingrediente.getDtVencimento().getTime());
            Timestamp timeCad = new Timestamp(ingrediente.getDtCriacao().getTime());
            pst.setTimestamp(4, timeVenc);
            pst.setTimestamp(5, timeCad);
            pst.setInt(6, ingrediente.getCategoria().getId());
            pst.setDouble(7, ingrediente.getValor());
            pst.setBoolean(8, ingrediente.isAtivo());

            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            int idIngri = 0;
            if (rs.next()) {
                idIngri = rs.getInt(1);
            }
            ingrediente.setId(idIngri);

            connection.commit();

            if (ingrediente.getDias() != null) {
                DisponibilidadeDAO disponibilidadeDAO = new DisponibilidadeDAO();
                disponibilidadeDAO.salvar(ingrediente);
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
        openConnection();
        PreparedStatement pst = null;

        try {
            Ingrediente ingrediente = (Ingrediente) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE tb_ingredientes SET nome=?, quantidade=?, ");
            sql.append("medida=?, dt_vencimento=?, categoria=?, valor=? where id_ingrediente=?;");

            pst = connection.prepareStatement(sql.toString());
            pst.setString(1, ingrediente.getNome());
            pst.setDouble(2, ingrediente.getQuantidade());
            pst.setString(3, ingrediente.getMedida());
            Timestamp timeVenc = new Timestamp(ingrediente.getDtVencimento().getTime());
            pst.setTimestamp(4, timeVenc);
            pst.setInt(5, ingrediente.getCategoria().getId());
            pst.setDouble(6, ingrediente.getValor());
            pst.setInt(7, ingrediente.getId());

            pst.executeUpdate();
            connection.commit();

            DisponibilidadeDAO disponibilidadeDAO = new DisponibilidadeDAO();
            disponibilidadeDAO.excluir(ingrediente);
            disponibilidadeDAO.salvar(ingrediente);

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
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        List<EntidadeDominio> ingredientes = new ArrayList<>();
        boolean ingredienteEspecifico = false;

        try {
            Ingrediente ingrediente = (Ingrediente) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();
            if (ingrediente.getDias() != null && ingrediente.getDias().size() > 0) {
                sql.append("SELECT * FROM tb_ingredientes i\n"
                        + "inner join tb_disponibilidade d on d.id_ingrediente=i.id_ingrediente\n"
                        + "WHERE i.ativo=true and d.id_dia=?;");
                pst = connection.prepareStatement(sql.toString());
                pst.setInt(1, ingrediente.getDias().get(0).getCodigo());
            } else if (ingrediente.getId() != 0) {
                sql.append("SELECT * FROM tb_ingredientes WHERE");
                sql.append(" id_ingrediente=?");

                pst = connection.prepareStatement(sql.toString());

                pst.setInt(1, ingrediente.getId());
            } else {
                sql.append("SELECT * FROM tb_ingredientes WHERE ativo=true");
                if (ingrediente.getId() != 0) {
                    sql.append(" and id_ingrediente=?");
                    ingredienteEspecifico = true;
                }

                pst = connection.prepareStatement(sql.toString());
                if (ingredienteEspecifico) {
                    pst.setInt(1, ingrediente.getId());
                }
            }
            ResultSet rs = pst.executeQuery();
            DisponibilidadeDAO disponibilidadeDAO = new DisponibilidadeDAO();
            while (rs.next()) {
                i = new Ingrediente();
                c = new Categoria();
                i.setId(rs.getInt("id_ingrediente"));
                i.setNome(rs.getString("nome"));
                i.setQuantidade(rs.getDouble("quantidade"));
                i.setMedida(rs.getString("medida"));
                i.setDtVencimento(rs.getDate("dt_vencimento"));
                i.setDtCriacao(rs.getDate("dt_cadastro"));
                c.setId(rs.getInt("categoria"));
                i.setCategoria((Categoria) categoriaDAO.consultar(c).get(0));
                i.setValor(rs.getDouble("valor"));
                disponibilidadeDAO.consultar(i);

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

    @Override
    public void excluir(EntidadeDominio entidade) {
        openConnection();
        PreparedStatement pst = null;
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE tb_ingredientes SET ativo=false WHERE id_ingrediente=?;");

        try {
            connection.setAutoCommit(false);
            pst = connection.prepareStatement(sb.toString());
            pst.setInt(1, entidade.getId());

            pst.executeUpdate();
            connection.commit();
            Ingrediente ingrediente = (Ingrediente) entidade;
            if (ingrediente.getDias() != null) {
                DisponibilidadeDAO disponibilidadeDAO = new DisponibilidadeDAO();
                disponibilidadeDAO.excluir(entidade);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {

            try {
                pst.close();
                if (ctrlTransaction) {
                    connection.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void alterarQtde(EntidadeDominio entidade) throws SQLException {
        openConnection();
        PreparedStatement pst = null;

        try {
            Ingrediente ingrediente = (Ingrediente) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE tb_ingredientes SET quantidade=? ");
            sql.append("where id_ingrediente=?;");

            pst = connection.prepareStatement(sql.toString());
            pst.setDouble(1, ingrediente.getQuantidade());
            pst.setInt(2, ingrediente.getId());

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

}
