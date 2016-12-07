package com.les.marmitex.core.strategy.impl;

import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.strategy.IStrategy;

/**
 * TODO descrição da classe
 *
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 07/12/2016
 */
public class ConverterDiaDaSemana implements IStrategy {

    @Override
    public String validar(EntidadeDominio entidade) {
        String text = "Segunda-feira;Terça-feira;Sexta-feira";
        String partes[] = text.split(";");
        String retorno = "";
        for (int i = 0; i < partes.length; i++) {
            if (partes[i].equals("Segunda-feira")) {
                retorno += "1;";
            } else if (partes[i].equals("Terça-feira")) {
                retorno += "2;";
            } else if (partes[i].equals("Quarta-feira")) {
                retorno += "3;";
            } else if (partes[i].equals("Quinta-feira")) {
                retorno += "4;";
            } else if (partes[i].equals("Sexta-feira")) {
                retorno += "5;";
            } else if (partes[i].equals("Sabado")) {
                retorno += "6;";
            } else if (partes[i].equals("Domingo")) {
                retorno += "7;";
            }
        }
        if (retorno.length() > 0) {
            retorno = retorno.substring(0, retorno.length() - 1);
        }
        return retorno;
    }
}
