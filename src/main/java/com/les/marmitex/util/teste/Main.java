package com.les.marmitex.util.teste;

import java.util.ArrayList;
import java.util.List;

import com.les.marmitex.core.dominio.Endereco;
import com.les.marmitex.core.dominio.Usuario;
import com.les.marmitex.core.strategy.impl.ValidarCamposEmBranco;

public class Main {

    public static void main(String[] args) {
        Usuario u = new Usuario();
        u.setLogin("gbezerra");
        //u.setSenha("123");

        Endereco e = new Endereco();
        List<Endereco> lista = new ArrayList<>();
        lista.add(e);
        //u.setEndereco(lista);

        ValidarCamposEmBranco v = new ValidarCamposEmBranco();

        System.out.println(v.validar(u));

    }

}
