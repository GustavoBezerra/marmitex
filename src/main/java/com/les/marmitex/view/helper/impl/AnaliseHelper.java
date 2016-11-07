package com.les.marmitex.view.helper.impl;

import com.google.gson.Gson;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Grafico;
import com.les.marmitex.core.dominio.Resultado;
import com.les.marmitex.view.helper.IViewHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 * TODO descrição da classe
 *
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 05/11/2016
 */
@Component("/marmitex/analise")
public class AnaliseHelper implements IViewHelper {

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        Grafico grafico = new Grafico();

        String operacao = request.getParameter("operacao");
        String tipo = request.getParameter("tipo");
        String teste = request.getParameter("itens");
        if (!teste.equals("")) {
            List<String> itens = new ArrayList();
            for (String parte : teste.split(",")) {
                itens.add(parte);
            }
            grafico.setItens(itens);
        }

        grafico.setTipo(tipo);
        String dtInicio = request.getParameter("inicio");
        String dtFim = request.getParameter("fim");
        grafico.setDtInicio(new Date(dtInicio));
        grafico.setDtFim(new Date(dtFim));

        return grafico;
    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String operacao = request.getParameter("operacao");
        String retorno = null;
        Gson gson = new Gson();
        retorno = gson.toJson(resultado.getEntidades());
        try {
            response.getWriter().write(retorno);
        } catch (IOException ex) {
            System.out.println("ERRO!");
        }
    }

}
