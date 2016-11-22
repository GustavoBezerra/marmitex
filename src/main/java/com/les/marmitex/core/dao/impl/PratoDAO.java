package com.les.marmitex.core.dao.impl;

import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RED;
import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RESET;
import com.les.marmitex.core.dominio.Categoria;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Ingrediente;
import com.les.marmitex.core.dominio.Prato;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Classe responsável pela persistência dos dados do prato do dias
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
            sql.append("INSERT INTO tb_prato(valor, dt_disponivel, nome) ");
            sql.append("VALUES (?,?, ?);");

            pst = connection.prepareStatement(sql.toString(),
                    Statement.RETURN_GENERATED_KEYS);
            pst.setDouble(1, prato.getValor());
            Timestamp dtDisponivel = new Timestamp(prato.getDtDisponivel().getTime());
            pst.setTimestamp(2, dtDisponivel);
            pst.setString(3, prato.getNome());

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
        openConnection();
        PreparedStatement pst = null;

        try {
            Prato prato = (Prato) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE tb_prato SET valor=?, dt_disponivel=? where id_prato=?;");

            pst = connection.prepareStatement(sql.toString());
            pst.setDouble(1, prato.getValor());
            Timestamp dtDisponivel = new Timestamp(prato.getDtDisponivel().getTime());
            pst.setTimestamp(2, dtDisponivel);
            pst.setInt(3, prato.getId());

            pst.executeUpdate();
            connection.commit();

            limparIngredientes(prato.getId());

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
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
        //TODO pensar melhor no consultar
    	// teste de commit
        openConnection();
        PreparedStatement pst = null;
        Ingrediente i;
        Categoria c;
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        List<EntidadeDominio> ingredientes = new ArrayList<>();
        Prato p;
        boolean ingredienteEspecifico = false;

        try {
            Prato prato = (Prato) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT * FROM tb_prato WHERE ativo=true");
            if (prato.getId() != 0) {
                sql.append(" and id_ingrediente=?");
                ingredienteEspecifico = true;
            }

            pst = connection.prepareStatement(sql.toString());
            if (ingredienteEspecifico) {
                pst.setInt(1, prato.getId());
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
                c.setId(rs.getInt("categoria"));
                i.setCategoria((Categoria)categoriaDAO.consultar(c).get(0));
                i.setValor(rs.getDouble("valor"));

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
    public void excluir(EntidadeDominio entidade){
        openConnection();
        PreparedStatement pst = null;

        try {
            Prato p = (Prato)entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();
            sql.append("DELETE FROM tb_prato_ingrediente WHERE id_prato=?;");

            pst = connection.prepareStatement(sql.toString());
            pst.setInt(1, p.getId());

            pst.executeUpdate();
            connection.commit();

            StringBuilder sql2 = new StringBuilder();
            sql2.append("DELETE FROM tb_prato WHERE id_prato=?;");

            pst=null;
            pst = connection.prepareStatement(sql2.toString());
            pst.setInt(1, p.getId());

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
            System.out.println(ANSI_RED + "[ERROR] - Entidade não é um Ingrediente!" + ANSI_RESET);
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

    private void salvarIngredientes(Ingrediente i, int id_prato) {
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

    private void limparIngredientes(int id_prato) {
        openConnection();
        PreparedStatement pst = null;

        try {
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();
            sql.append("DELETE FROM tb_prato_ingrediente WHERE id_prato=?;");

            pst = connection.prepareStatement(sql.toString());
            pst.setInt(1, id_prato);

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
            System.out.println(ANSI_RED + "[ERROR] - Entidade não é um Ingrediente!" + ANSI_RESET);
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
