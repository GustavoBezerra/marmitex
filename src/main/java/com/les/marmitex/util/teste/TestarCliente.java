package com.les.marmitex.util.teste;

import com.les.marmitex.core.dao.impl.ClienteDAO;
import com.les.marmitex.core.dominio.Cliente;
import java.util.Date;

public class TestarCliente {

    public static void main(String[] args) {
        Cliente c = new Cliente();
        c.setDtCriacao(new Date());
        c.setLogin("gustavo@hotmail.com");
        c.setNome("Gustavo");
        c.setSenha("123");
        
        ClienteDAO dao = new ClienteDAO();
        dao.salvar(c);
    }

}
