package com.les.marmitex.core.dominio;

import java.util.List;

public class Usuario extends Pessoa {

    private String login;
    private String senha;

    public String getLogin() {
        if (login == null || login.equals("")) {
            return null;
        }
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        if (senha == null || senha.equals("")) {
            return null;
        }
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
