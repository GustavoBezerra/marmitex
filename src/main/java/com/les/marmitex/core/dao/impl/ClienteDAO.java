package com.les.marmitex.core.dao.impl;

import com.les.marmitex.core.dominio.Cliente;
import com.les.marmitex.core.dominio.Credito;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Classe responsável pelas ações na tb_cliente
 *
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 20/08/2016
 */
@Component("com.les.marmitex.core.dominio.Cliente")
public class ClienteDAO extends AbstractJdbcDAO {

    public ClienteDAO() {
        super("tb_cliente", "id_cliente");
    }

    /**
     * Método para inserir um registro na base
     *
     * @param entidade Entidade para registro no banco (nome, login e senha)
     * @see com.les.marmitex.core.dominio.Cliente
     */
    public void salvar(EntidadeDominio entidade) {
        openConnection();
        PreparedStatement pst = null;

        try {
            Cliente cliente = (Cliente) entidade;
            connection.setAutoCommit(false);
            
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO tb_cliente(nome, telefone, login, senha, ");
            sql.append("dt_criacao, credito) VALUES (?,?,?,?,?,?)");

            pst = connection.prepareStatement(sql.toString(),
                    Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, cliente.getNome());
            pst.setString(2, cliente.getTelefone());
            pst.setString(3, cliente.getUsuario().getLogin());
            pst.setString(4, cliente.getUsuario().getSenha());
            Timestamp time = new Timestamp(cliente.getDtCriacao().getTime());
            pst.setTimestamp(5, time);
            pst.setDouble(6, 0);

            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            int idCli = 0;
            if (rs.next()) {
                idCli = rs.getInt(1);
            }
            cliente.setId(idCli);

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(ANSI_RED + "[ERROR] ROLLBACK - " + e1.getMessage() + ANSI_RESET);
            }
            System.out.println(ANSI_RED + "[ERROR] - " + e.getMessage() + ANSI_RESET);
        } catch (ClassCastException ce) {
            System.out.println(ANSI_RED + "[ERROR] - Entidade " + entidade.getClass().getSimpleName() + " não é um Cliente!" + ANSI_RESET);
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

    /**
     * Método para alterar um registro específico na base
     *
     * @param entidade Entidade com ID que deve ser alterada
     * @see com.les.marmitex.core.dominio.Cliente
     */
    @Override
    public void alterar(EntidadeDominio entidade) {
        openConnection();
        PreparedStatement pst = null;
        boolean creditar = false;

        try {
            Cliente cliente = (Cliente) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();
            if (cliente.getId() != 0 && cliente.getCredito() != null && cliente.getEnderecos() == null) {
                sql.append("UPDATE tb_cliente SET credito=? WHERE id_cliente=?;");
                creditar = true;
            } else {
                sql.append("UPDATE tb_cliente SET nome=?, telefone=?, login=?, senha=? WHERE id_cliente=?;");
            }

            pst = connection.prepareStatement(sql.toString());
            if (creditar) {
                pst.setDouble(1, cliente.getCredito().getValor());
                pst.setInt(2, cliente.getId());
            } else {
                pst.setString(1, cliente.getNome());
                pst.setString(2, cliente.getTelefone());
                pst.setString(3, cliente.getUsuario().getLogin());
                pst.setString(4, cliente.getUsuario().getSenha());
                pst.setInt(5, cliente.getId());
            }
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
            System.out.println(ANSI_RED + "[ERROR] - Entidade " + entidade.getClass().getSimpleName() + " não é um Cliente!" + ANSI_RESET);
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

    /**
     * Método para consultar um registro específico/todos registros na base
     *
     * @param entidade Entidade com ID para uma pesquisa específica ou ID 0 para
     * pesquisar todos. Caso a entidade tenha ID 0 e login e senha preenchidos,
     * será feita a verificação de login
     * @return Lista de EntidadeDominio contendo um ou vários registros
     * @see com.les.marmitex.core.dominio.Cliente
     */
    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        openConnection();
        PreparedStatement pst = null;
        Cliente c;
        List<EntidadeDominio> clientes = new ArrayList<>();
        boolean clienteEspecifico = false;
        boolean clienteLogin = false;

        try {
            Cliente cliente = (Cliente) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT * FROM tb_cliente");
            if (cliente.getId() != 0) {
                sql.append(" WHERE id_cliente=?;");
                clienteEspecifico = true;
            } else if (cliente.getUsuario().getLogin() != null && cliente.getUsuario().getSenha() != null) {
                sql.append(" WHERE login like ? and senha like ?;");
                clienteLogin = true;
            } else {
                sql.append(";");
            }

            pst = connection.prepareStatement(sql.toString());
            if (clienteEspecifico) {
                pst.setInt(1, cliente.getId());
            } else if (clienteLogin) {
                pst.setString(1, cliente.getUsuario().getLogin());
                pst.setString(2, cliente.getUsuario().getSenha());
            }

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

                clientes.add(c);
            }

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(ANSI_RED + "[ERROR] ROLLBACK - " + e1.getMessage() + ANSI_RESET);
            }
            System.out.println(ANSI_RED + "[ERROR] - " + e.getMessage() + ANSI_RESET);
        } catch (ClassCastException ce) {
            System.out.println(ANSI_RED + "[ERROR] - Entidade " + entidade.getClass().getSimpleName() + " não é um Cliente!" + ANSI_RESET);
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
        return clientes;
    }
    
    public boolean verificarDuplicidade(Cliente c){
        openConnection();
        PreparedStatement pst = null;
        
        try {            
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT * FROM tb_cliente WHERE login like ?;");
            
            pst = connection.prepareStatement(sql.toString());            
            pst.setString(1, c.getUsuario().getLogin());
                
            ResultSet rs = pst.executeQuery();            
            while (rs.next()) {
                return false;
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(ANSI_RED + "[ERROR] ROLLBACK - " + e1.getMessage() + ANSI_RESET);
            }
            System.out.println(ANSI_RED + "[ERROR] - " + e.getMessage() + ANSI_RESET);
        } catch (ClassCastException ce) {
            System.out.println(ANSI_RED + "[ERROR] - Entidade " + c.getClass().getSimpleName() + " não é um Cliente!" + ANSI_RESET);
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
        return true;
    }

}
