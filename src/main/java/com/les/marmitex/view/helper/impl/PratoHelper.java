package com.les.marmitex.view.helper.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.les.marmitex.core.dominio.Dias;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Ingrediente;
import com.les.marmitex.core.dominio.Marmitex;
import com.les.marmitex.core.dominio.Prato;
import com.les.marmitex.core.dominio.Resultado;
import com.les.marmitex.view.helper.IViewHelper;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * TODO descrição da classe
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 22/11/2016
 */
@Component("/marmitex/prato")
public class PratoHelper implements IViewHelper{

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        String operacao = request.getParameter("operacao");
        Prato p = new Prato();;
        Ingrediente ingrediente;
        List<Ingrediente> ingredientes;
        Gson gson = new Gson();
        
        if(("SALVAR").equals(operacao)){
            p = new Prato();
            p.setNome(request.getParameter("nome"));
            String json = request.getParameter("ingredientes");
            double preco = Double.valueOf(request.getParameter("preco"));
            p.setValor(preco);
            JSONArray ja = new JSONArray(json);
            JSONObject obj;
            ingredientes = new ArrayList<>();
            for (int i = 0; i < ja.length(); i++) {
                obj = ja.getJSONObject(i);
                ingrediente = new Ingrediente();
                ingrediente.setNome(obj.getString("nome"));
                ingrediente.setId(obj.getInt("id"));
                ingrediente.setMedida(obj.getString("medida"));
                ingrediente.setQuantidade(obj.getDouble("quantidade"));
                ingrediente.setValor(obj.getDouble("valor"));
                ingredientes.add(ingrediente);
            }
            p.setIngredientes(ingredientes);
        } else if(("EXCLUIR").equals(operacao)){
            p = new Prato();
            p.setId(Integer.valueOf(request.getParameter("id")));
        } else if(("CONSULTAR").equals(operacao)){
            p = new Prato();
        } else if(("ALTERAR").equals(operacao)){
            p = gson.fromJson(request.getParameter("prato"), Prato.class);
        }
        return p;
    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String operacao = request.getParameter("operacao");
        String retorno = null;
        //Gson gson = new Gson();
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
