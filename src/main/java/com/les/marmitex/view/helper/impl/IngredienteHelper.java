package com.les.marmitex.view.helper.impl;

import com.google.gson.Gson;
import com.les.marmitex.core.dominio.Categoria;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Ingrediente;
import com.les.marmitex.core.dominio.Resultado;
import com.les.marmitex.view.helper.IViewHelper;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Component("/marmitex/ingrediente")
public class IngredienteHelper implements IViewHelper {

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        String operacao = request.getParameter("operacao");
        Ingrediente i = null;
        Categoria c = null;

        String nome;
        Double quantidade;
        String medida;
        String dtVencimento;
        String categoria;

        if (("SALVAR").equals(operacao)) {
            i = new Ingrediente();
            c = new Categoria();
            nome = request.getParameter("nome");
            quantidade = Double.valueOf(request.getParameter("quantidade"));
            medida = request.getParameter("medida");
            dtVencimento = request.getParameter("vencimento");
            categoria = request.getParameter("categoria");

            c.setNome(categoria);
            i.setCategoria(c);
            i.setNome(nome);
            i.setQuantidade(quantidade);
            i.setMedida(medida);
            i.setDtCriacao(new Date());
            i.setDtVencimento(new Date(dtVencimento));            
            
        } else if (("CONSULTAR").equals(operacao)) {
            i = new Ingrediente();

        } else if (("EXCLUIR").equals(operacao)) {
            i = new Ingrediente();
            String id = request.getParameter("id");
            i.setId(Integer.valueOf(id));

        } else if (("ALTERAR").equals(operacao)) {

        }

        return i;
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
