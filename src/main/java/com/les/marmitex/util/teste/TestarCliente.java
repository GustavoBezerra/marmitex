package com.les.marmitex.util.teste;

import com.les.marmitex.core.dominio.Cliente;
import com.les.marmitex.core.fachada.impl.Fachada;
import java.util.Date;

/**
 * Classe para testar o DAO de Cliente
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 21/08/2016
 */
public class TestarCliente {

    public static void main(String[] args) {

        /** ---- CADASTRAR CLIENTE ---- **/
        Cliente c = new Cliente();
        c.setDtCriacao(new Date());
//        c.setLogin("maria@hotmail.com");
//        c.setNome("Maria");
//        c.setSenha("12345");
//        c.setId(4);

        Fachada f = new Fachada();
        f.excluir(c);


        /** ---- ALTERAR CLIENTE ---- **/
//        Cliente c = new Cliente();
//        c.setLogin("gustavo@hotmail.com");
//        c.setNome("Gustavo");
//        c.setSenha("12345");
//        c.setId(1);
//
//        ClienteDAO dao = new ClienteDAO();
//        dao.alterar(c);



        /** ---- EXCLUIR CLIENTE ---- **/
//        Cliente c = new Cliente();
//        c.setId(2);
//
//        ClienteDAO dao = new ClienteDAO();
//        dao.excluir(c);



        /** ---- CONSULTAR CLIENTE ---- **/
//        Cliente c = new Cliente();
//        c.setId(1);
//
//        ClienteDAO dao = new ClienteDAO();
//        List<EntidadeDominio> cliente = dao.consultar(c);
//        System.out.println("Total de clientes encontrados: "+cliente.size());
//        for(EntidadeDominio e : cliente){
//            System.out.println(e.getId());
//        }

    }
}
