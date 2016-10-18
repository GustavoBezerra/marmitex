package com.les.marmitex.view.helper.impl;

import com.google.gson.Gson;
import com.les.marmitex.core.dominio.Categoria;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Resultado;
import com.les.marmitex.view.helper.IViewHelper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 * TODO descrição da classe
 *
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 12/09/2016
 */
@Component("/marmitex/categoria")
public class CategoriaHelper implements IViewHelper {

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        String operacao = request.getParameter("operacao");
        String nome;
        int id;
        Categoria c = null;

        if (("SALVAR").equals(operacao)) {
            c = new Categoria();
            nome = request.getParameter("nome");
            c.setNome(nome);
        } else if (("ALTERAR").equals(operacao)) {
            c = new Categoria();
            id = Integer.valueOf(request.getParameter("id"));
            c.setId(id);
            nome = request.getParameter("nome");
            c.setNome(nome);
        } else if (("EXCLUIR").equals(operacao)) {
            c = new Categoria();
            id = Integer.valueOf(request.getParameter("id"));
            c.setId(id);
        } else if (("CONSULTAR").equals(operacao)) {
            c = new Categoria();
        }

        return c;
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
