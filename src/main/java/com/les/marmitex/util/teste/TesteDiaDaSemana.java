package com.les.marmitex.util.teste;

import java.util.Calendar;
import java.util.Date;

/**
 * TODO descrição da classe main
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 18/12/2016
 */

public class TesteDiaDaSemana {

    public static void main(String[] args) {
        Calendar c = Calendar.getInstance();
        System.out.println("Dia da semana: "+c.get(Calendar.DAY_OF_WEEK));
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)+1);
        System.out.println("Dia da do mês: "+c.get(Calendar.DAY_OF_MONTH));
        Date date = new Date(c.getTimeInMillis());
        System.out.println("Dia do mes: "+date.getDate());
    }

}
