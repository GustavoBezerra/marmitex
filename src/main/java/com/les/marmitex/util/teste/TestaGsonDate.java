package com.les.marmitex.util.teste;

import com.google.gson.Gson;
import java.util.Date;

/**
 * TODO descrição da classe main
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 20/09/2016
 */

public class TestaGsonDate {

    public static void main(String[] args) {
        String retorno;
        Gson gson = new Gson();
        Date data = new Date();
        retorno = gson.toJson(data);
        System.out.println(retorno);
    }

}
