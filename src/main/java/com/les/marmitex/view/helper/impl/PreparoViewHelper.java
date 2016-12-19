package com.les.marmitex.view.helper.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.les.marmitex.core.dominio.Categoria;
import com.les.marmitex.core.dominio.Dias;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Ingrediente;
import com.les.marmitex.core.dominio.Prato;
import com.les.marmitex.core.dominio.Preparo;
import com.les.marmitex.core.dominio.Resultado;
import com.les.marmitex.view.helper.IViewHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

/**
 * TODO descrição da classe
 *
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 18/12/2016
 */
@Component("/marmitex/preparo")
public class PreparoViewHelper implements IViewHelper {

    @Override
    public EntidadeDominio getEntidade(HttpServletRequest request) {
        String operacao = request.getParameter("operacao");
        Prato p = new Prato();
        Preparo preparo = new Preparo();
        Categoria c = new Categoria();
        List<Dias> dias = new ArrayList<>();
        List<Ingrediente> ingredientes = new ArrayList<>();
        c.setId(10);
        double quantidade;
        String dia;
        String json;
        JSONArray ja;
        Ingrediente in;
        Gson gson = new GsonBuilder()
                .setDateFormat("dd/MM/yyyy").create();

        if (("SALVAR").equals(operacao)) {
            json = request.getParameter("ingredientes");
            ja = new JSONArray(json);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jsonObj = ja.getJSONObject(i);
                in = new Ingrediente();
                in.setNome(jsonObj.getString("nome"));
                in.setId(jsonObj.getInt("id"));
                in.setMedida(jsonObj.getString("medida"));
                in.setQuantidade(jsonObj.getDouble("quantidade"));
                in.setValor(jsonObj.getDouble("valor"));
                
                ingredientes.add(in);
            }
            quantidade = Double.valueOf(request.getParameter("quantidade"));
            dia = request.getParameter("dia");
            preparo.setPrato(p);
            preparo.getPrato().setNome(request.getParameter("nome"));
            preparo.getPrato().setValor(Double.valueOf(request.getParameter("valor")));
            preparo.getPrato().setIngredientes(ingredientes);
            preparo.setCategoria(c);
            preparo.setQuantidade(quantidade);
            Calendar calendario = Calendar.getInstance();
            calendario.set(Calendar.DAY_OF_MONTH, calendario.get(Calendar.DAY_OF_MONTH) + 1);
            Date vencimento = new Date(calendario.getTimeInMillis());
            preparo.setVencimento(vencimento);

            if (dia.equals("Hoje")) {
                calendario = Calendar.getInstance();
                dias.add(getDiaDaSemana(calendario));
            } else {
                dias.add(getDiaDaSemana(calendario));
            }
            preparo.getPrato().setDtDisponivel(dias);
        }

        return preparo;
    }

    @Override
    public void setView(Resultado resultado, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Dias getDiaDaSemana(Calendar c) {
        int dia = c.get(Calendar.DAY_OF_WEEK);
        if (dia == 1) {
            dia += 6;
        } else {
            dia -= 1;
        }
        switch (dia) {
            case 1:
                return Dias.SEGUNDA;
            case 2:
                return Dias.TERCA;
            case 3:
                return Dias.QUARTA;
            case 4:
                return Dias.QUINTA;
            case 5:
                return Dias.SEXTA;
            case 6:
                return Dias.SABADO;
            case 7:
                return Dias.DOMINGO;
        }
        return null;
    }

}
