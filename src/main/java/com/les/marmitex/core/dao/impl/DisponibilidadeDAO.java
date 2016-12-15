package com.les.marmitex.core.dao.impl;

import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RED;
import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RESET;
import com.les.marmitex.core.dominio.Campanha;
import com.les.marmitex.core.dominio.Dias;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Ingrediente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO descrição da classe
 *
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 15/12/2016
 */
public class DisponibilidadeDAO extends AbstractJdbcDAO {

    public DisponibilidadeDAO() {
        super("tb_disponibilidade", "id_ingrediente");
    }

    @Override
    public void salvar(EntidadeDominio entidade) throws SQLException {
        openConnection();
        PreparedStatement pst = null;
        connection.setAutoCommit(false);
        StringBuilder sql = new StringBuilder();

        try {
            Ingrediente ingrediente = (Ingrediente) entidade;
            for (int i = 0; i < ingrediente.getDias().size(); i++) {
                sql.delete(0, sql.length());
                sql.append("INSERT INTO tb_disponibilidade(id_ingrediente, id_dia)");
                sql.append(" VALUES (?,?);");
                pst = connection.prepareStatement(sql.toString());
                pst.setInt(1, ingrediente.getId());
                pst.setInt(2, ingrediente.getDias().get(i).getCodigo());
                pst.executeUpdate();
            }
        } catch (ClassCastException e) {
            Campanha campanha = (Campanha) entidade;
            // implementação da lógica da campanha            
        } catch (SQLException e) {
            System.out.println("ERRO - " + e.getMessage());
        }

        try {
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
        openConnection();
        PreparedStatement pst = null;
        connection.setAutoCommit(false);
        List<EntidadeDominio> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        try {
            Ingrediente ingrediente = (Ingrediente) entidade;
            sql.append("SELECT * FROM tb_disponibilidade WHERE id_ingrediente=?;");

            pst = connection.prepareStatement(sql.toString());
            pst.setInt(1, ingrediente.getId());
            ResultSet rs = pst.executeQuery();
            List<Dias> dias = new ArrayList<>();

            int aux;
            while (rs.next()) {
                aux = rs.getInt("id_dia");
                switch (aux) {
                    case 1:
                        dias.add(Dias.SEGUNDA);
                        break;
                    case 2:
                        dias.add(Dias.TERCA);
                        break;
                    case 3:
                        dias.add(Dias.QUARTA);
                        break;
                    case 4:
                        dias.add(Dias.QUINTA);
                        break;
                    case 5:
                        dias.add(Dias.SEXTA);
                        break;
                    case 6:
                        dias.add(Dias.SABADO);
                        break;
                    case 7:
                        dias.add(Dias.DOMINGO);
                        break;
                }
            }
            ingrediente.setDias(dias);
            lista.add(ingrediente);
        } catch (ClassCastException e) {
            Campanha campanha = (Campanha) entidade;
            // implementação da lógica da campanha            
        } catch (SQLException e) {
            System.out.println("ERRO - " + e.getMessage());
        }
        return lista;
    }
}
