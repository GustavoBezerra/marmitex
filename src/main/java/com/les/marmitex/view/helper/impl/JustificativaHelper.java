package com.les.marmitex.view.helper.impl;

import com.google.gson.Gson;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Justificativa;
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
 * @date 06/11/2016
 */
@Component("/marmitex/justificativa")
public class JustificativaHelper implements IViewHelper {

    Justificativa j;

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        String operacao = request.getParameter("operacao");
        j = new Justificativa();

        return j;
    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
