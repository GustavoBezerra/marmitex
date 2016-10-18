package com.les.marmitex.view.helper.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.les.marmitex.core.dominio.Endereco;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Marmitex;
import com.les.marmitex.core.dominio.Resultado;
import com.les.marmitex.core.dominio.Status;
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
 * @date 19/09/2016
 */
@Component("/marmitex/marmitex")
public class MarmitexHelper implements IViewHelper {

    Marmitex m = null;

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        String operacao = request.getParameter("operacao");

        Gson gson = new GsonBuilder()
                .setDateFormat("dd/MM/yyyy").create();
        if (("EXCLUIR").equals(operacao)) {
            m = gson.fromJson(request.getParameter("marmita"), Marmitex.class);
            String status = request.getParameter("status");
            if(status.equals(Status.DEVOLVIDO.getDescricao())){
                m.setStatus(Status.DEVOLVIDO);
            }
            else if(status.equals(Status.CANCELADO.getDescricao())){
                m.setStatus(Status.CANCELADO);
            }
        }
        return m;
    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String operacao = request.getParameter("operacao");
        String retorno = null;
        Gson gson = new Gson();

        if (("SALVAR").equals(operacao)) {

        } else if (("CONSULTAR").equals(operacao)) {

        } else if (("EXCLUIR").equals(operacao)) {
            retorno = gson.toJson(m);
        } else if (("ALTERAR").equals(operacao)) {

        }
    }

}
