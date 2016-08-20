package com.les.marmitex.core.dao.impl;

import com.les.marmitex.core.dominio.Cliente;
import com.les.marmitex.core.dominio.EntidadeDominio;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

// TODO pensar no esquema de Exceptions (quando não é um cliente)
public class ClienteDAO extends AbstractJdbcDAO {

    public ClienteDAO() {
        super("tb_cliente", "id_cliente");
    }

    public void salvar(EntidadeDominio entidade) {
        openConnection();
        PreparedStatement pst = null;
        Cliente cliente = (Cliente) entidade;

        try {
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
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                pst.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * TODO Descrição do Método
     *
     * @param entidade
     * @see fai.dao.IDAO#alterar(fai.domain.EntidadeDominio)
     */
    @Override
    public void alterar(EntidadeDominio entidade) {
        // TODO implementar o alterar

    }

    /**
     * TODO Descrição do Método
     *
     * @param entidade
     * @return
     * @see fai.dao.IDAO#consulta(fai.domain.EntidadeDominio)
     */
    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) {
        // TODO implementar o consultar
        return null;
    }

}
