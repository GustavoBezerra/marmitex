package com.les.marmitex.core.dao.impl;

import com.les.marmitex.core.dominio.Cliente;
import com.les.marmitex.core.dominio.EntidadeDominio;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável pelas ações na tb_cliente
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 20/08/2016
 */
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
            sql.append("INSERT INTO tb_cliente(nome, login, senha, ");
            sql.append("dt_criacao) VALUES (?,?,?,?)");

            pst = connection.prepareStatement(sql.toString());
            pst.setString(1, cliente.getNome());
            pst.setString(2, cliente.getLogin());
            pst.setString(3, cliente.getSenha());
            Timestamp time = new Timestamp(cliente.getDtCriacao().getTime());
            pst.setTimestamp(4, time);
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
     * Método para alterar um registro específico na base
     *
     * @param entidade Entidade com ID que deve ser alterada
     * @see com.les.marmitex.core.dominio.Cliente
     */
    @Override
    public void alterar(EntidadeDominio entidade) {
        openConnection();
        PreparedStatement pst = null;

        try {
            Cliente cliente = (Cliente) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE tb_cliente SET nome=?, login=?, senha=? WHERE id_cliente=?;");

            pst = connection.prepareStatement(sql.toString());
            pst.setString(1, cliente.getNome());
            pst.setString(2, cliente.getLogin());
            pst.setString(3, cliente.getSenha());
            pst.setInt(4, cliente.getId());
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
     * pesquisar todos.
     * Caso a entidade tenha ID 0 e login e senha preenchidos, será feita a verificação de login
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
            } 
            else if (!("").equals(cliente.getLogin()) && !("").equals(cliente.getSenha())) {
                sql.append(" WHERE login like ? and senha like ?;");
                clienteLogin = true;
            } 
            else {
                sql.append(";");
            }

            pst = connection.prepareStatement(sql.toString());
            if (clienteEspecifico) {
                pst.setInt(1, cliente.getId());
            }
            else if(clienteLogin){
                pst.setString(1, cliente.getLogin());
                pst.setString(2, cliente.getSenha());
            }

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                c = new Cliente();
                c.setId(rs.getInt("id_cliente"));
                c.setDtCriacao(rs.getDate("dt_criacao"));
                c.setLogin(rs.getString("login"));
                c.setSenha(rs.getString("senha"));
                c.setNome(rs.getString("nome"));

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

}
