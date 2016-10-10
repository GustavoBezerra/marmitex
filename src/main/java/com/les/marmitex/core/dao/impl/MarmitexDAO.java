package com.les.marmitex.core.dao.impl;

import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RED;
import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RESET;
import com.les.marmitex.core.dominio.Categoria;
import com.les.marmitex.core.dominio.Cliente;
import com.les.marmitex.core.dominio.Credito;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Ingrediente;
import com.les.marmitex.core.dominio.Marmitex;
import com.les.marmitex.core.dominio.Usuario;
import com.les.marmitex.core.strategy.impl.ValidarEstoque;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

/**
 * Classe responsável pelas ações na tb_marmitex
 *
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 27/08/2016
 */
@Component("com.les.marmitex.core.dominio.Marmitex")
public class MarmitexDAO extends AbstractJdbcDAO {

    public MarmitexDAO() {
        super("tb_marmitex", "id_marmitex");
    }

    int id_pedido;

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

    @Override
    public void excluir(EntidadeDominio entidade) {
        openConnection();
        PreparedStatement pst = null;

        try {
            Marmitex marmitex = (Marmitex) entidade;
            buscarMarmitex(marmitex);
            
            StringBuilder sql = new StringBuilder();
            sql.append("delete from tb_marmitex where id_marmitex=?;");

            pst = connection.prepareStatement(sql.toString());
            pst.setInt(1, marmitex.getId());            
            excluirMarmitexIngrediente(marmitex);
            pst.executeUpdate();
            creditarCliente(marmitex.getValor());
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(ANSI_RED + "[ERROR] ROLLBACK [EXCLUIR MARMITEXDAO] - " + e1.getMessage() + ANSI_RESET);
            }
            System.out.println(ANSI_RED + "[ERROR] [EXCLUIR MARMITEXDAO]- " + e.getMessage() + ANSI_RESET);
        } catch (ClassCastException ce) {
            System.out.println(ANSI_RED + "[ERROR] [EXCLUIR MARMITEXDAO]- Entidade " + entidade.getClass().getSimpleName() + " não é um Endereço!" + ANSI_RESET);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                connection.close();
            } catch (SQLException e) {
                System.out.println(ANSI_RED + "[ERROR] [EXCLUIR MARMITEXDAO]- " + e.getMessage() + ANSI_RESET);
            }
        }
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
                    iDAO.alterarQtde(i);
                } catch (SQLException ex) {
                    Logger.getLogger(ValidarEstoque.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    i.setQuantidade(ing.getQuantidade() - 1);
                    iDAO.alterarQtde(i);
                } catch (SQLException ex) {
                    Logger.getLogger(ValidarEstoque.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarmitexDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buscarMarmitex(Marmitex m) {
        openConnection();
        PreparedStatement pstB = null;

        try {
            connection.setAutoCommit(false);

            StringBuilder sqlB = new StringBuilder();
            sqlB.append("select * from tb_marmitex where id_marmitex=?;");

            pstB = connection.prepareStatement(sqlB.toString());
            pstB.setInt(1, m.getId());

            ResultSet rs = pstB.executeQuery();
            while (rs.next()) {
                id_pedido = rs.getInt("id_pedido");
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(ANSI_RED + "[ERROR] ROLLBACK [BUSCAR MARMITEX]- " + e1.getMessage() + ANSI_RESET);
            }
            System.out.println(ANSI_RED + "[ERROR] [BUSCAR MARMITEX]- " + ex.getMessage() + ANSI_RESET);
        } catch (ClassCastException ce) {
            System.out.println(ANSI_RED + "[ERROR] [BUSCAR MARMITEX]- Entidade " + m.getClass().getSimpleName() + " não é um Ingrediente!" + ANSI_RESET);
        } finally {
            try {
                if (pstB != null) {
                    pstB.close();
                }                
            } catch (SQLException ex) {
                System.out.println(ANSI_RED + "[ERROR] [BUSCAR MARMITEX]- " + ex.getMessage() + ANSI_RESET);
            }
        }
    }

    private void creditarCliente(double valor) {
        openConnection();
        PreparedStatement pst = null;
        Cliente c = new Cliente();
        ClienteDAO cDAO = new ClienteDAO();

        try {            
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();

            sql.append("select * from tb_pedido p\n"
                    + "inner join tb_cliente c on c.id_cliente=p.id_cliente\n"
                    + "where p.id_pedido=?;");

            pst.setInt(1, id_pedido);

            ResultSet rs = pst.executeQuery();
            Usuario u;
            Credito cr;
            while (rs.next()) {
                c = new Cliente();
                u = new Usuario();
                cr = new Credito();
                c.setCredito(cr);
                c.setUsuario(u);
                c.setId(rs.getInt("id_cliente"));
                c.setDtCriacao(rs.getDate("dt_criacao"));
                c.getUsuario().setLogin(rs.getString("login"));
                c.getUsuario().setSenha(rs.getString("senha"));
                c.setNome(rs.getString("nome"));
                c.getCredito().setValor(rs.getDouble("credito"));
                c.setTelefone(rs.getString("telefone"));
            }
            valor += c.getCredito().getValor();
            c.getCredito().setValor(valor);
            cDAO.alterar(c);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(ANSI_RED + "[ERROR] ROLLBACK [CREDITAR CLIENTE]- " + e1.getMessage() + ANSI_RESET);
            }
            System.out.println(ANSI_RED + "[ERROR] [CREDITAR CLIENTE]- " + e.getMessage() + ANSI_RESET);
        } catch (ClassCastException ce) {
            System.out.println(ANSI_RED + "[ERROR] [CREDITAR CLIENTE]- Entidade " + c.getClass().getSimpleName() + " não é um Cliente!" + ANSI_RESET);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }                
            } catch (SQLException e) {
                System.out.println(ANSI_RED + "[ERROR] [CREDITAR CLIENTE]- " + e.getMessage() + ANSI_RESET);
            }
        }
    }

    private void excluirMarmitexIngrediente(Marmitex marmitex) {
        openConnection();
        PreparedStatement pst = null;

        try {           
            
            StringBuilder sql = new StringBuilder();
            sql.append("delete from tb_marmitex_ingrediente where id_marmitex=?;");

            pst = connection.prepareStatement(sql.toString());
            pst.setInt(1, marmitex.getId());            

            pst.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(ANSI_RED + "[ERROR] ROLLBACK [EXCLUIR MARMITEXINGREDIENTE] - " + e1.getMessage() + ANSI_RESET);
            }
            System.out.println(ANSI_RED + "[ERROR] [EXCLUIR MARMITEXINGREDIENTE]- " + e.getMessage() + ANSI_RESET);
        } catch (ClassCastException ce) {
            System.out.println(ANSI_RED + "[ERROR] [EXCLUIR MARMITEXINGREDIENTE]- Entidade " + ce.getClass().getSimpleName() + " não é um Endereço!" + ANSI_RESET);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
                System.out.println(ANSI_RED + "[ERROR] [EXCLUIR MARMITEXINGREDIENTE]- " + e.getMessage() + ANSI_RESET);
            }
        }
    }
}
