package com.les.marmitex.core.dao.impl;

import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RED;
import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RESET;
import com.les.marmitex.core.dominio.Cliente;
import com.les.marmitex.core.dominio.Endereco;
import com.les.marmitex.core.dominio.EntidadeDominio;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Classe responsável pelas ações na tb_endereco
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 20/08/2016
 */
@Component("com.les.marmitex.core.dominio.Endereco")
public class EnderecoDAO extends AbstractJdbcDAO{

    public EnderecoDAO() {
        super("tb_endereco", "id_endereco");
    }
    
    /**
     * Método para inserir um registro na base
     *
     * @param entidade Entidade para registro no banco (id cliente, cidade, cep,
     *                         logradouro, numero, complemento e bairro)
     * @see com.les.marmitex.core.dominio.Endereco
     */
    public void salvar(EntidadeDominio entidade) {
        openConnection();
        PreparedStatement pst = null;

        try {
            Endereco endereco = (Endereco) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO tb_endereco(id_cliente, cidade, cep, ");
            sql.append("logradouro, numero, complemento, bairro, dt_criacao) VALUES (?,?,?,?,?,?,?,?)");

            pst = connection.prepareStatement(sql.toString());
            pst.setInt(1, endereco.getId_cliente());
            pst.setString(2, endereco.getCidade());
            pst.setString(3, endereco.getCep());
            pst.setString(4, endereco.getRua());
            pst.setString(5, endereco.getNumero());
            pst.setString(6, endereco.getComplemento());
            pst.setString(7, endereco.getBairro());
            Timestamp time = new Timestamp(new Date().getTime());
            pst.setTimestamp(8, time);
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

    /**
     * Método para alterar um registro específico na base
     *
     * @param entidade Entidade com ID do endereco que deve ser alterado
     * @see com.les.marmitex.core.dominio.Endereco
     */
    @Override
    public void alterar(EntidadeDominio entidade) {
        openConnection();
        PreparedStatement pst = null;        

        try {
            Endereco endereco = (Endereco) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE tb_endereco SET cidade=?, cep=?, logradouro=?, numero=?, complemento=?,");
            sql.append(" bairro=? WHERE id_endereco=?;");

            pst = connection.prepareStatement(sql.toString());            
            pst.setString(1, endereco.getCidade());
            pst.setString(2, endereco.getCep());
            pst.setString(3, endereco.getRua());
            pst.setString(4, endereco.getNumero());
            pst.setString(5, endereco.getComplemento());
            pst.setString(6, endereco.getBairro());
            pst.setInt(7, endereco.getId());
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

    /**
     * Método para consultar um registro específico/todos registros na base
     *
     * @param entidade Entidade com ID para uma pesquisa específica ou ID do cliente para
     * pesquisar todos os endereços do mesmo
     * @return Lista de EntidadeDominio contendo um ou vários registros
     * @see com.les.marmitex.core.dominio.Endereco
     * @see com.les.marmitex.core.dominio.Cliente
     */
    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        openConnection();
        PreparedStatement pst = null;        
        Endereco e;
        List<EntidadeDominio> enderecos = new ArrayList<>();
        boolean enderecoEspecifico = false;

        try {
            Endereco endereco = (Endereco) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT * FROM tb_endereco");
            if (endereco.getId() != 0) {
                sql.append(" WHERE id_endereco=?;");
                enderecoEspecifico = true;
            } else {
                sql.append(" WHERE id_cliente=?;");
            }

            pst = connection.prepareStatement(sql.toString());
            if (enderecoEspecifico) {
                pst.setInt(1, endereco.getId());
            }
            else{
                pst.setInt(1, endereco.getId_cliente());
            }

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                e = new Endereco();
                e.setId(rs.getInt("id_endereco"));
                e.setId_cliente(rs.getInt("id_cliente"));
                e.setCidade(rs.getString("cidade"));
                e.setCep(rs.getString("cep"));
                e.setRua(rs.getString("logradouro"));
                e.setNumero(rs.getString("numero"));
                e.setComplemento(rs.getString("complemento"));
                e.setBairro(rs.getString("bairro"));                
                e.setDtCriacao(rs.getDate("dt_criacao"));
                
                enderecos.add(e);
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(ANSI_RED + "[ERROR] ROLLBACK - " + e1.getMessage() + ANSI_RESET);
            }
            System.out.println(ANSI_RED + "[ERROR] - " + ex.getMessage() + ANSI_RESET);
        } catch (ClassCastException ce) {
            System.out.println(ANSI_RED + "[ERROR] - Entidade " + entidade.getClass().getSimpleName() + " não é um Endereço!" + ANSI_RESET);
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
        return enderecos;
    }
    
}
