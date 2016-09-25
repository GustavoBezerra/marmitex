package com.les.marmitex.util.teste;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.les.marmitex.core.dominio.Ingrediente;
import java.util.List;

/**
 * TODO descrição da classe main
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 24/09/2016
 */

public class TesteGson {

    public static void main(String[] args) {
        
        //String json = "[{\"nome\":\"Carne\",\"quantidade\":5,\"medida\":\"Kilo(s)\",\"categoria\":{\"nome\":\"Mistura\",\"id\":1,\"ativo\":false},\"valor\":5.76,\"id\":9,\"ativo\":false},{\"nome\":\"Frango\",\"quantidade\":5.76,\"medida\":\"Kilo(s)\",\"categoria\":{\"nome\":\"Mistura\",\"id\":1,\"ativo\":false},\"valor\":3.45,\"id\":11,\"ativo\":false}]";
        String json = "[{\"nome\":\"Carne\",\"quantidade\":5,\"medida\":\"Kilo(s)\",\"dtVencimento\":\"25/09/2016\",\"categoria\":{\"nome\":\"Mistura\",\"id\":1,\"ativo\":false},\"valor\":5.76,\"id\":9,\"dtCriacao\":\"20/09/2016\",\"ativo\":false},{\"nome\":\"Frango\",\"quantidade\":5.76,\"medida\":\"Kilo(s)\",\"dtVencimento\":\"29/09/2016\",\"categoria\":{\"nome\":\"Mistura\",\"id\":1,\"ativo\":false},\"valor\":3.45,\"id\":11,\"dtCriacao\":\"21/09/2016\",\"ativo\":false}]";
        Gson gson = new GsonBuilder()
                .setDateFormat("dd/MM/yyyy").create();
        Ingrediente in;
        List<Ingrediente> ingredientes = gson.fromJson(json, new TypeToken<List<Ingrediente>>(){}.getType());
        for (int i = 0; i < ingredientes.size(); i++) {
            in = ingredientes.get(i);
            System.out.println("Nome: "+in.getNome()+"\n"
                    + "ID: "+in.getId()+"\n"
                    + "Data de vencimento: "+in.getDtVencimento()+"\n"
                    + "------------------------------");
        }
    }

}
