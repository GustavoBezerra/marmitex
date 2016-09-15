package com.les.marmitex.view.helper.impl;

import com.google.gson.Gson;
import com.les.marmitex.core.dominio.Categoria;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Entregador;
import com.les.marmitex.core.dominio.Resultado;
import com.les.marmitex.view.helper.IViewHelper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 * TODO descrição da classe
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 14/09/2016
 */
@Component("/marmitex/entregador")
public class EntregadorHelper implements IViewHelper{

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        String operacao = request.getParameter("operacao");
        String nome;
        int id;
        Entregador e = null;

        if(("SALVAR").equals(operacao)){
            e = new Entregador();
            nome = request.getParameter("nome");
            e.setNome(nome);
        } else if(("ALTERAR").equals(operacao)){
            e = new Entregador();
            id = Integer.valueOf(request.getParameter("id"));
            nome = request.getParameter("nome");

            e.setId(id);
            e.setNome(nome);
        } else if(("EXCLUIR").equals(operacao)){
            e = new Entregador();
            id = Integer.valueOf(request.getParameter("id"));
            e.setId(id);
        } else if(("CONSULTAR").equals(operacao)){
            e = new Entregador();
        }

        return e;
    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String operacao = request.getParameter("operacao");
        String retorno = null;
        Gson gson = new Gson();

        if (("SALVAR").equals(operacao)) {

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
