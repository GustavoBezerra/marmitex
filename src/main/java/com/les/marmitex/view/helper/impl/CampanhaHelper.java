package com.les.marmitex.view.helper.impl;

import com.google.gson.Gson;
import com.les.marmitex.core.dominio.Campanha;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Resultado;
import com.les.marmitex.view.helper.IViewHelper;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 * TODO descrição da classe
 *
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 11/09/2016
 */
@Component("/marmitex/campanha")
public class CampanhaHelper implements IViewHelper {

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        String operacao = request.getParameter("operacao");
        Campanha c = null;

        String nome;
        String descricao;
        String[] tempo;
        int regra;
        String valor_regra;
        int beneficio;
        String valor_beneficio;

        if (("SALVAR").equals(operacao)) {
            nome = request.getParameter("nome");
            descricao = request.getParameter("descricao");
            tempo = request.getParameter("tempo").split("até");
            regra = Integer.valueOf(request.getParameter("regra"));
            valor_regra = request.getParameter("valor_regra");
            beneficio = Integer.valueOf(request.getParameter("beneficio"));
            valor_beneficio = request.getParameter("valor_beneficio");

            c = new Campanha();
            c.setNome(nome);
            c.setDescricao(descricao);
            c.setRegra(regra);
            c.setValor_regra(valor_regra);
            c.setBenefico(beneficio);
            c.setValor_beneficio(valor_beneficio);

            String formatoDt = "dd/MM/yyyy";
            SimpleDateFormat df = new SimpleDateFormat(formatoDt);

            try {
                Date inicio = df.parse(tempo[0].trim());
                Date fim = df.parse(tempo[1].trim());
                c.setInicio(inicio);
                c.setFim(fim);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }

        } else if (("CONSULTAR").equals(operacao)) {
            c = new Campanha();
        } else if (("EXCLUIR").equals(operacao)) {

        } else if (("ALTERAR").equals(operacao)) {

        }

        return c;
    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String operacao = request.getParameter("operacao");
        String retorno = null;
        Gson gson = new Gson();

        if (("SALVAR").equals(operacao)) {

        }
        else if (("CONSULTAR").equals(operacao)) {
            retorno = gson.toJson(resultado.getEntidades());
            try {
                response.getWriter().write(retorno);
            } catch (IOException ex) {
                System.out.println("ERRO!");
            }
        }
        else if(("EXCLUIR").equals(operacao)){

        }
        else if(("ALTERAR").equals(operacao)){

        }
    }

}
