package com.les.marmitex.core.dominio;

import java.util.List;

/**
 * Classe para representar o Usuario
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 27/08/2016
 */
public class Usuario extends EntidadeDominio {

    private String login;
    private String senha;
    private List<Pedido> pedidos;

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

    /**
     * @return the pedidos
     */
    public List<Pedido> getPedidos() {
        return pedidos;
    }

    /**
     * @param pedidos the pedidos to set
     */
    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

}
