package com.les.marmitex.view.helper.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.les.marmitex.core.dominio.Cliente;
import com.les.marmitex.core.dominio.Endereco;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Entregador;
import com.les.marmitex.core.dominio.Ingrediente;
import com.les.marmitex.core.dominio.Marmitex;
import com.les.marmitex.core.dominio.Pagamento;
import com.les.marmitex.core.dominio.Pedido;
import com.les.marmitex.core.dominio.Resultado;
import com.les.marmitex.core.dominio.Status;
import com.les.marmitex.util.teste.TestaConsultarPedido;
import com.les.marmitex.view.helper.IViewHelper;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static jdk.nashorn.internal.objects.NativeMath.round;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * TODO descrição da classe
 *
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 24/09/2016
 */
@Component("/marmitex/pedido")
public class PedidoHelper implements IViewHelper {

    Pedido pedido = null;

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        String operacao = request.getParameter("operacao");
        Cliente c;
        List<Marmitex> marmitexs;
        Marmitex marmitex;
        List<Pagamento> pagamento;
        Endereco endereco = null;
        Entregador entregador = null;
        String total;
        String dinheiro;
        String troco;
        String cartao;
        String credito;
        Gson gson = new GsonBuilder()
                .setDateFormat("dd/MM/yyyy").create();

        if (("SALVAR").equals(operacao)) {
            pedido = new Pedido();
            endereco = new Endereco();
            marmitex = new Marmitex();
            pagamento = new ArrayList<>();
            marmitexs = new ArrayList<>();
            List<Ingrediente> auxs = new ArrayList<>();
            c = new Cliente();
            double valor_marmitex = 0;

            String json = request.getParameter("ingredientes");

            endereco = gson.fromJson(request.getParameter("endereco"), Endereco.class);
            c.setId(Integer.valueOf(request.getParameter("id_cliente")));
            pedido.setCliente(c);
            Ingrediente in;
            JSONArray ja = new JSONArray(json);
            for (int i = 0; i < ja.length(); i++) {
                marmitex = new Marmitex();
                auxs = new ArrayList<>();
                valor_marmitex = 0;
                for (int j = 0; j < ja.getJSONArray(i).length(); j++) {
                    JSONObject jsonObj = ja.getJSONArray(i).getJSONObject(j);
                    in = new Ingrediente();
                    in.setNome(jsonObj.getString("nome"));
                    in.setId(jsonObj.getInt("id"));
                    in.setMedida(jsonObj.getString("medida"));
                    in.setQuantidade(jsonObj.getDouble("quantidade"));
                    in.setValor(jsonObj.getDouble("valor"));

                    valor_marmitex += in.getValor();
                    auxs.add(in);
                }
                Double truncatedDouble = new BigDecimal(valor_marmitex)
                        .setScale(3, BigDecimal.ROUND_HALF_UP)
                        .doubleValue();
                marmitex.setIngredientes(auxs);
                marmitex.setValor(truncatedDouble);
                marmitexs.add(marmitex);
            }
            pedido.setMarmitex(marmitexs);
            pedido.setValorFrete(2.00);
            pedido.setDtCriacao(new Date());
            pedido.setAtivo(true);
            pedido.setStatus(Status.ABERTO.getDescricao());

            total = request.getParameter("total");

            dinheiro = request.getParameter("dinheiro");
            troco = request.getParameter("troco");
            credito = request.getParameter("credito");
            cartao = request.getParameter("cartao");

            if (!("").equals(dinheiro) && dinheiro != null && !("false").equals(dinheiro)) {
                if (!("").equals(troco) && troco != null && !("false").equals(troco)) {
                    pedido.setTroco(Double.valueOf(troco));
                    pagamento.add(Pagamento.DINHEIRO);
                }
            }
            if (!("").equals(credito) && credito != null && !("false").equals(credito)) {
                pagamento.add(Pagamento.CREDITO);
            }
            if (!("").equals(cartao) && cartao != null && !("false").equals(cartao)) {
                pagamento.add(Pagamento.CARTAO);
            }

            pedido.setPagamento(pagamento);
            pedido.setEndereco(endereco);
            pedido.setValorTotal(Double.valueOf(total));

        } else if (("CONSULTAR").equals(operacao)) {
            pedido = new Pedido();
            c = new Cliente();
            String aux = request.getParameter("id_pedido");
            String aux_cliente = request.getParameter("id_cliente");
            if (aux_cliente != null & aux_cliente != "") {
                c.setId(Integer.valueOf(request.getParameter("id_cliente")));
                pedido.setCliente(c);
            }
            if (aux != null && aux != "") {
                pedido.setId(Integer.valueOf(request.getParameter("id_pedido")));
            }            
        } else if (("ALTERAR").equals(operacao)) {
            pedido = new Pedido();
            pedido.setId(Integer.valueOf(request.getParameter("id_pedido")));
            pedido.setStatus(request.getParameter("status"));
            if (pedido.getStatus().equals(Status.A_CAMINHO.getDescricao())) {
                entregador = new Entregador();
                entregador.setId(Integer.valueOf(request.getParameter("entregador")));
                pedido.setEntregador(entregador);
            } else if (pedido.getStatus().equals(Status.DEVOLVIDO.getDescricao())) {
                pedido.setValorTotal(Double.valueOf(request.getParameter("valor_total")));
                c = gson.fromJson(request.getParameter("cliente"), Cliente.class);
                pedido.setCliente(c);
            }
        }
        return pedido;
    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String operacao = request.getParameter("operacao");
        String retorno = null;
        //Gson gson = new Gson();
        Gson gson = new GsonBuilder()
                .setDateFormat("dd/MM/yyyy").create();

        if (("SALVAR").equals(operacao)) {
            try {
                response.getWriter().write(gson.toJson(pedido));
            } catch (IOException ex) {
                System.out.println("ERRO!");
            }
        } else if (("CONSULTAR").equals(operacao)) {
            retorno = gson.toJson(resultado.getEntidades());
            try {
                response.getWriter().write(retorno);
            } catch (IOException ex) {
                System.out.println("ERRO!");
            }
        } else if (("EXCLUIR").equals(operacao)) {

        } else if (("ALTERAR").equals(operacao)) {

        }
    }

}
