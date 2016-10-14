package com.les.marmitex.core.dao.impl;

import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RED;
import static com.les.marmitex.core.dao.impl.AbstractJdbcDAO.ANSI_RESET;
import com.les.marmitex.core.dominio.Cliente;
import com.les.marmitex.core.dominio.Endereco;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Ingrediente;
import com.les.marmitex.core.dominio.Marmitex;
import com.les.marmitex.core.dominio.Pagamento;
import com.les.marmitex.core.dominio.Pedido;
import com.les.marmitex.core.dominio.Status;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Classe responsável pelas ações na tb_pedido
 *
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 27/08/2016
 */
@Component("com.les.marmitex.core.dominio.Pedido")
public class PedidoDAO extends AbstractJdbcDAO {

    public PedidoDAO() {
        super("tb_pedido", "id_pedido");
    }

    @Override
    public void salvar(EntidadeDominio entidade) throws SQLException {
        openConnection();
        PreparedStatement pst = null;

        try {
            Pedido pedido = (Pedido) entidade;
            MarmitexDAO marmitexDAO = new MarmitexDAO();
            EnderecoDAO enderecoDAO = new EnderecoDAO();
            EntregadorDAO entregadorDAO = new EntregadorDAO();
            PagamentoDAO pagamentoDAO = new PagamentoDAO();
            connection.setAutoCommit(false);

            if (pedido.getEndereco().getId() == 0) {
                pedido.getEndereco().setAtivo(false);
                enderecoDAO.salvar(pedido.getEndereco());
            }
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO tb_pedido(id_endereco, id_entregador, id_campanha, valor_frete, ");
            sql.append("valor_total, status, troco, dt_criacao, id_cliente) VALUES (?,?,?,?,?,?,?,?,?);");

            pst = connection.prepareStatement(sql.toString(),
                    Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, pedido.getEndereco().getId());
            pst.setNull(2, Types.NULL);
            pst.setNull(3, Types.NULL);
            pst.setDouble(4, pedido.getValorFrete());
            pst.setDouble(5, pedido.getValorTotal());
            pst.setString(6, pedido.getStatus());
            pst.setDouble(7, pedido.getTroco());
            Timestamp time = new Timestamp(new Date().getTime());
            pst.setTimestamp(8, time);
            pst.setInt(9, pedido.getCliente().getId());

            pst.executeUpdate();
            connection.commit();
            ResultSet rs = pst.getGeneratedKeys();
            int idPedido = 0;
            if (rs.next()) {
                idPedido = rs.getInt(1);
            }
            pedido.setId(idPedido);

            // salvar todas as marmitex do pedido
            for (int i = 0; i < pedido.getMarmitex().size(); i++) {
                pedido.getMarmitex().get(i).setId(pedido.getId());
                marmitexDAO.salvar(pedido.getMarmitex().get(i));
            }

            // salvar os tipos de pagamento
            if (pedido.getPagamento().contains(Pagamento.CREDITO)) {
                alterarCreditos(pedido.getCliente().getId(), pedido.getValorTotal());
            }
            pagamentoDAO.salvar(pedido);

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
                    connection.close();
                }                
            } catch (SQLException e) {
                System.out.println(ANSI_RED + "[ERROR] - " + e.getMessage() + ANSI_RESET);
            }
        }
    }

    @Override
    public void alterar(EntidadeDominio entidade) throws SQLException {
        openConnection();
        PreparedStatement pst = null;
        IngredienteDAO iDAO = new IngredienteDAO();
        Ingrediente i;
        Pedido p;
        boolean entregador = false;

        try {
            Pedido pedido = (Pedido) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE tb_pedido SET status=?");
            if (pedido.getStatus().equals(Status.A_CAMINHO.getDescricao())) {
                sql.append(", id_entregador=?");
                entregador = true;
            }
            else if (pedido.getStatus().equals(Status.CANCELADO.getDescricao())) {  
                p = (Pedido)consultar(pedido).get(0);
                if(p.getStatus().equals(Status.ABERTO.getDescricao())){
                    for (Marmitex m : p.getMarmitex()) {
                        for (Ingrediente ingrediente : m.getIngredientes()) {
                            i = (Ingrediente)iDAO.consultar(ingrediente).get(0);
                            if(i.getMedida().equals("Unidade(s)")){
                                i.setQuantidade(i.getQuantidade() + 1);
                            }
                            else{
                                i.setQuantidade(i.getQuantidade() + 0.150);
                            }
                            iDAO.alterar(i);
                        }
                    }
                }
            }
            sql.append(" WHERE id_pedido=?;");

            if(connection.isClosed()){
                openConnection();
            }
            pst = connection.prepareStatement(sql.toString());
            pst.setString(1, pedido.getStatus());
            if (entregador) {
                pst.setInt(2, pedido.getEntregador().getId());
                pst.setInt(3, pedido.getId());
            } else {
                pst.setInt(2, pedido.getId());
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

    @Override
    public List<EntidadeDominio> consultar(EntidadeDominio entidade) throws SQLException {
        openConnection();
        PreparedStatement pst_consultar = null;
        ClienteDAO cDAO = new ClienteDAO();
        Ingrediente i;
        Endereco e;
        Marmitex m;
        Pedido p;
        Cliente c;
        List<Ingrediente> ingredientes;
        List<EntidadeDominio> pedidos = new ArrayList<>();
        List<Marmitex> marmitexs;
        boolean pedidoEspecifico = false;

        try {
            Pedido pedido = (Pedido) entidade;
            connection.setAutoCommit(false);

            StringBuilder sql = new StringBuilder();

            sql.append("select\n"
                    + "p.id_pedido, p.id_entregador, p.status, p.valor_total, p.valor_frete, p.troco, p.dt_criacao, p.id_cliente,\n"
                    + "                    m.id_marmitex, i.id_ingrediente, i.nome, i.valor as valor_ingrediente,\n"
                    + "                    e.id_endereco, e.cep, e.bairro, e.cidade, e.complemento, e.logradouro, e.numero\n"
                    + "from tb_marmitex m\n"
                    + "inner join tb_marmitex_ingrediente mi on m.id_marmitex=mi.id_marmitex\n"
                    + "inner join tb_pedido p on p.id_pedido=m.id_pedido\n"
                    + "inner join tb_cliente c on c.id_cliente=p.id_cliente\n"
                    + "inner join tb_ingredientes i on mi.id_ingrediente=i.id_ingrediente\n"
                    + "inner join tb_endereco e on e.id_endereco=p.id_endereco\n");

            if (pedido.getId() != 0) {
                sql.append("where p.id_pedido=?;");

            } else if (pedido.getCliente().getId() != 1) {
                sql.append("where c.id_cliente=?;");

            }
            //sql.append("GROUP BY p.id_pedido,i.id_ingrediente;");

            pst_consultar = connection.prepareStatement(sql.toString());

            if (pedido.getId() != 0) {
                pst_consultar.setInt(1, pedido.getId());
            } else if (pedido.getCliente().getId() != 1) {
                pst_consultar.setInt(1, pedido.getCliente().getId());
            }

            ResultSet rs = pst_consultar.executeQuery();
            p = new Pedido();
            i = new Ingrediente();
            m = new Marmitex();
            e = new Endereco();
            marmitexs = new ArrayList<>();
            ingredientes = new ArrayList<>();
            int anterior = 0;
            int id;
            while (rs.next()) {
                id = rs.getInt("id_pedido");
                if(id == 55){
                    System.out.println("Chegou!");
                }
                if (id == anterior) {
                    if (m.getId() == rs.getInt("id_marmitex")) {
                        //novo ingrediente do mesmo pedido
                        i = new Ingrediente();
                        i.setId(rs.getInt("id_ingrediente"));
                        i.setNome(rs.getString("nome"));
                        i.setValor(rs.getDouble("valor_ingrediente"));
                        ingredientes.add(i);
                    } else { // é outra marmitex do mesmo pedido                        
                        // salvar a marmitex anterior
                        marmitexs.add(m);
                        
                        // nova marmitex
                        m = new Marmitex();
                        i = new Ingrediente();
                        ingredientes = new ArrayList<>();
                        
                        m.setId(rs.getInt("id_marmitex"));
                        i.setId(rs.getInt("id_ingrediente"));
                        i.setNome(rs.getString("nome"));
                        i.setValor(rs.getDouble("valor_ingrediente"));
                        ingredientes.add(i);
                        m.setIngredientes(ingredientes);
                    }
                } else { // novo pedido
                    if (anterior != 0) { //vai perder o último registro do pedido anterior?
                        m.setIngredientes(ingredientes);
                        marmitexs.add(m);
                        p.setMarmitex(marmitexs);
                        p.setPagamento(getPagamentos(p));
                        p.setCliente((Cliente) cDAO.consultar(p.getCliente()).get(0));
                        pedidos.add(p);
                    }
                    anterior = id;
                    p = new Pedido();
                    m = new Marmitex();
                    i = new Ingrediente();
                    e = new Endereco();
                    c = new Cliente();
                    ingredientes = new ArrayList<>();
                    marmitexs = new ArrayList<>();
                    // settar valores basicos do pedido
                    p.setId(id);
                    c.setId(rs.getInt("id_cliente"));
                    p.setCliente(c);
                    p.setStatus(rs.getString("status"));
                    p.setValorTotal(rs.getDouble("valor_total"));
                    p.setValorFrete(rs.getDouble("valor_frete"));
                    p.setTroco(rs.getDouble("troco"));
                    p.setDtCriacao(rs.getDate("dt_criacao"));

                    //settar a marmitex
                    m.setId(rs.getInt("id_marmitex"));
                    i.setId(rs.getInt("id_ingrediente"));
                    i.setNome(rs.getString("nome"));
                    i.setValor(rs.getDouble("valor_ingrediente"));
                    ingredientes.add(i);
                    m.setIngredientes(ingredientes);

                    //settar o endereço
                    e.setId(rs.getInt("id_endereco"));
                    e.setCep(rs.getString("cep"));
                    e.setBairro(rs.getString("bairro"));
                    e.setCidade(rs.getString("cidade"));
                    e.setComplemento(rs.getString("complemento"));
                    e.setRua(rs.getString("logradouro"));
                    e.setNumero(rs.getString("numero"));
                    p.setEndereco(e);
                }
            }
            if (anterior != 0) {
                m.setIngredientes(ingredientes);
                marmitexs.add(m);
                p.setMarmitex(marmitexs);
                p.setPagamento(getPagamentos(p));
                p.setCliente((Cliente) cDAO.consultar(p.getCliente()).get(0));
                pedidos.add(p);
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(ANSI_RED + "[ERROR] ROLLBACK(pedido) - " + e1.getMessage() + ANSI_RESET);
            }
            System.out.println(ANSI_RED + "[ERROR] - " + ex.getMessage() + ANSI_RESET);
        } catch (ClassCastException ce) {
            System.out.println(ANSI_RED + "[ERROR] - Entidade " + entidade.getClass().getSimpleName() + " não é um Ingrediente!" + ANSI_RESET);
        } finally {
            try {
                if (pst_consultar != null) {
                    pst_consultar.close();
                    connection.close();
                }                                
            } catch (SQLException ex) {
                System.out.println(ANSI_RED + "[ERROR] - " + ex.getMessage() + ANSI_RESET);
            }
        }
        return pedidos;
    }

    public List<Pagamento> getPagamentos(Pedido pedido) {
        openConnection();
        PreparedStatement pst2 = null;
        List<Pagamento> pagamentos = new ArrayList<>();
        String pg;

        try {
            connection.setAutoCommit(false);

            StringBuilder sql2 = new StringBuilder();

            sql2.append("select pg.tipo_pagamento from tb_pedido p\n"
                    + "inner join tb_pagamento_pedido pp on pp.id_pedido=p.id_pedido\n"
                    + "inner join tb_pagamento pg on pp.id_pagamento=pg.id_pagamento\n"
                    + "where p.id_pedido=?;");

            pst2 = connection.prepareStatement(sql2.toString());

            pst2.setInt(1, pedido.getId());

            ResultSet rs2 = pst2.executeQuery();
            while (rs2.next()) {
                pg = rs2.getString("tipo_pagamento");
                if (pg.equals(Pagamento.CARTAO.getDescricao())) {
                    pagamentos.add(Pagamento.CARTAO);
                }
                if (pg.equals(Pagamento.CREDITO.getDescricao())) {
                    pagamentos.add(Pagamento.CREDITO);
                }
                if (pg.equals(Pagamento.DINHEIRO.getDescricao())) {
                    pagamentos.add(Pagamento.DINHEIRO);
                }
            }

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println(ANSI_RED + "[ERROR] ROLLBACK(pagamentos) - " + e1.getMessage() + ANSI_RESET);
            }
            System.out.println(ANSI_RED + "[ERROR] - " + e.getMessage() + ANSI_RESET);
        } catch (ClassCastException ce) {

        } finally {
            try {
                if (pst2 != null) {
                    pst2.close();
                }
            } catch (SQLException e) {
                System.out.println(ANSI_RED + "[ERROR] - " + e.getMessage() + ANSI_RESET);
            }
        }
        return pagamentos;
    }

    private void alterarCreditos(int id_cliente, double valor_pedido) {
        ClienteDAO cDAO = new ClienteDAO();
        Cliente cliente = new Cliente();
        cliente.setId(id_cliente);
        double aux;
        Double truncatedDouble;

        Cliente cli = (Cliente) cDAO.consultar(cliente).get(0);
        aux = cli.getCredito().getValor();
        if (aux >= valor_pedido) {
            truncatedDouble = new BigDecimal(aux - valor_pedido)
                    .setScale(3, BigDecimal.ROUND_HALF_UP)
                    .doubleValue();
            cli.getCredito().setValor(truncatedDouble);
        } else {
            truncatedDouble = new BigDecimal(valor_pedido - aux)
                    .setScale(3, BigDecimal.ROUND_HALF_UP)
                    .doubleValue();
            cli.getCredito().setValor(truncatedDouble);
        }
        cDAO.alterar(cli);
    }

}
