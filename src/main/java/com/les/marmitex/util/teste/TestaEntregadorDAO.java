package com.les.marmitex.util.teste;

import com.les.marmitex.core.dao.impl.EntregadorDAO;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Entregador;
import java.sql.SQLException;

/**
 * TODO descrição da classe main
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 14/09/2016
 */

public class TestaEntregadorDAO {

    public static void main(String[] args) throws SQLException {
        Entregador e = new Entregador();
        EntregadorDAO dao = new EntregadorDAO();
        
        e.setNome("Pedro");
        //e.setId(2);
        
        dao.salvar(e);
        //dao.excluir(e);
        //dao.alterar(e);
//        for(EntidadeDominio ent : dao.consultar(e)){
//            Entregador en = (Entregador) ent;
//            System.out.println("ID: "+en.getId()+"\n"
//                    + "Nome: "+en.getNome()+"\n"
//                    + "----------------");
//        }

    }

}
