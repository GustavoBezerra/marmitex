package com.les.marmitex.view.helper.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.les.marmitex.core.dominio.Categoria;
import com.les.marmitex.core.dominio.Dias;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Ingrediente;
import com.les.marmitex.core.dominio.Resultado;
import com.les.marmitex.view.helper.IViewHelper;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        String id_categoria;
        Double valor;
        String dia;

        if (("SALVAR").equals(operacao)) {
            i = new Ingrediente();
            c = new Categoria();
            nome = request.getParameter("nome");
            quantidade = Double.valueOf(request.getParameter("quantidade"));
            medida = request.getParameter("medida");
            dtVencimento = request.getParameter("vencimento");
            categoria = request.getParameter("categoria");
            id_categoria = request.getParameter("id_categoria");
            valor = Double.valueOf(request.getParameter("valor"));
            dia = request.getParameter("dias");

            c.setNome(categoria);
            c.setId(Integer.valueOf(id_categoria));
            i.setCategoria(c);
            i.setNome(nome);
            i.setQuantidade(quantidade);
            i.setMedida(medida);
            i.setDtCriacao(new Date());
            if (dtVencimento.contains("/")) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date = sdf.parse(dtVencimento);
                    i.setDtVencimento(date);
                } catch (ParseException ex) {
                    Logger.getLogger(IngredienteHelper.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                i.setDtVencimento(new Date(dtVencimento));
            }
            if (!dia.isEmpty()) {
                List<Dias> uteis = new ArrayList();
                String[] dias = dia.split(";");
                for (int j = 0; j < dias.length; j++) {
                    switch (dias[j]) {
                        case "Segunda-feira":
                            uteis.add(Dias.SEGUNDA);
                            break;
                        case "Terça-feira":
                            uteis.add(Dias.TERCA);
                            break;
                        case "Quarta-feira":
                            uteis.add(Dias.QUARTA);
                            break;
                        case "Quinta-feira":
                            uteis.add(Dias.QUINTA);
                            break;
                        case "Sexta-feira":
                            uteis.add(Dias.SEXTA);
                            break;
                        case "Sabado":
                            uteis.add(Dias.SABADO);
                            break;
                        case "Domingo":
                            uteis.add(Dias.DOMINGO);
                            break;
                    }
                }
                i.setDias(uteis);
            }
            i.setValor(valor);
            i.setAtivo(true);

        } else if (("CONSULTAR").equals(operacao)) {
            i = new Ingrediente();
            String diaDaSemana = request.getParameter("dia");
            if (diaDaSemana != null) {
                List<Dias> dias = new ArrayList<>();
                switch (diaDaSemana) {
                    case "1":
                        dias.add(Dias.SEGUNDA);
                        break;
                    case "2":
                        dias.add(Dias.TERCA);
                        break;
                    case "3":
                        dias.add(Dias.QUARTA);
                        break;
                    case "4":
                        dias.add(Dias.QUINTA);
                        break;
                    case "5":
                        dias.add(Dias.SEXTA);
                        break;
                    case "6":
                        dias.add(Dias.SABADO);
                        break;
                    case "7":
                        dias.add(Dias.DOMINGO);
                        break;
                }
                i.setDias(dias);
            }            
            
        } else if (("EXCLUIR").equals(operacao)) {
            i = new Ingrediente();
            String id = request.getParameter("id");
            i.setId(Integer.valueOf(id));

        } else if (("ALTERAR").equals(operacao)) {
            i = new Ingrediente();
            c = new Categoria();
            nome = request.getParameter("nome");
            quantidade = Double.valueOf(request.getParameter("quantidade"));
            medida = request.getParameter("medida");
            dtVencimento = request.getParameter("vencimento");
            categoria = request.getParameter("categoria");
            id_categoria = request.getParameter("id_categoria");
            valor = Double.valueOf(request.getParameter("valor"));
            dia = request.getParameter("dias");

            c.setNome(categoria);
            c.setId(Integer.valueOf(id_categoria));
            i.setCategoria(c);
            i.setNome(nome);
            i.setQuantidade(quantidade);
            i.setMedida(medida);

            i.setDtCriacao(new Date());
            if (dtVencimento.contains("/")) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date date = sdf.parse(dtVencimento);
                    i.setDtVencimento(date);
                } catch (ParseException ex) {
                    Logger.getLogger(IngredienteHelper.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                i.setDtVencimento(new Date(dtVencimento));
            }
            if (!dia.isEmpty()) {
                List<Dias> uteis = new ArrayList();
                String[] dias = dia.split(";");
                for (int j = 0; j < dias.length; j++) {
                    switch (dias[j]) {
                        case "Segunda-feira":
                            uteis.add(Dias.SEGUNDA);
                            break;
                        case "Terça-feira":
                            uteis.add(Dias.TERCA);
                            break;
                        case "Quarta-feira":
                            uteis.add(Dias.QUARTA);
                            break;
                        case "Quinta-feira":
                            uteis.add(Dias.QUINTA);
                            break;
                        case "Sexta-feira":
                            uteis.add(Dias.SEXTA);
                            break;
                        case "Sabado":
                            uteis.add(Dias.SABADO);
                            break;
                        case "Domingo":
                            uteis.add(Dias.DOMINGO);
                            break;
                    }
                }
                i.setDias(uteis);
            }

            i.setValor(valor);
            i.setAtivo(true);
            i.setId(Integer.valueOf(request.getParameter("id")));
        }

        return i;
    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String operacao = request.getParameter("operacao");
        String retorno = null;
        //Gson gson = new Gson();
        Gson gson = new GsonBuilder()
                .setDateFormat("dd/MM/yyyy").create();

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
