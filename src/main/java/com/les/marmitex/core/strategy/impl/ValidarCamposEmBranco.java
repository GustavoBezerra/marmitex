package com.les.marmitex.core.strategy.impl;

import java.lang.reflect.Method;

import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.strategy.IStrategy;

public class ValidarCamposEmBranco implements IStrategy {

    private boolean emBranco = false;

    @Override
    public String validar(EntidadeDominio entidade) {
        StringBuilder sb = new StringBuilder();
        for (Method method : entidade.getClass().getDeclaredMethods()) {
            String campo = method.getName();
            if (campo.contains("get")) {
                System.out.println(campo);
                campo.replace("get", "");
                try {
                    if (method.invoke(entidade, null) == null) {
                        System.out.println("VAZIO");
                        sb.append(campo+" ");
                        emBranco = true;
                    } else {
                        System.out.println("Tem conteudo!");
                    }
                } catch (Exception e) {
                    System.out.println("Deu caca!");
                    System.out.println(e.getMessage());
                }
            }

        }
        if(emBranco){
            return "Favor preencher os seguintes campos: "+sb;
        }
        else{
            return null;
        }
    }
}
