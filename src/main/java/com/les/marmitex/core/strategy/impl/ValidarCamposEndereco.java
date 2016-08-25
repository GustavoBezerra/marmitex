package com.les.marmitex.core.strategy.impl;

import com.les.marmitex.core.dominio.Endereco;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.strategy.IStrategy;

public class ValidarCamposEndereco implements IStrategy {

    StringBuilder msg = new StringBuilder();

    @Override
    public String validar(EntidadeDominio entidade) {
        Endereco end = (Endereco) entidade;

        if (end.getBairro() == null || end.getBairro().equals("")) {
            msg.append("- Favor insira um bairro. \n");
        }
        if (end.getCep() == null || end.getCep().equals("")) {
            msg.append("- Favor insira um CEP. \n");
        }
        if (end.getCidade() == null || end.getCidade().equals("")) {
            msg.append("- Favor insira uma cidade. \n");
        }
        if (end.getComplemento() == null || end.getComplemento().equals("")) {
            msg.append("- Favor insira um complemento. \n");
        }
        if (end.getId_cliente() == 0) {
            msg.append("- Favor insira um ID do Cliente. \n");
        }
        if (end.getNumero() == null || end.getNumero().equals("")) {
            msg.append("- Favor insira um n�mero. \n");
        }
        if (end.getRua() == null || end.getRua().equals("")) {
            msg.append("- Favor insira uma rua. \n");
        }

        return msg.toString();
    }

}
