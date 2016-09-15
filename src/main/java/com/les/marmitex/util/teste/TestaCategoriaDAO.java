package com.les.marmitex.util.teste;

import com.les.marmitex.core.dao.impl.CategoriaDAO;
import com.les.marmitex.core.dominio.Categoria;
import com.les.marmitex.core.dominio.EntidadeDominio;
import java.sql.SQLException;

/**
 * TODO descrição da classe main
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 12/09/2016
 */

public class TestaCategoriaDAO {

    public static void main(String[] args) throws SQLException {
        Categoria c = new Categoria();
        CategoriaDAO dao = new CategoriaDAO();

        c.setNome("Teste");
        c.setId(2);

        //dao.salvar(c);
        //dao.alterar(c);
        //dao.excluir(c);

        for(EntidadeDominio variavelTemporaria : dao.consultar(c)){
            Categoria categoria = (Categoria) variavelTemporaria;
            System.out.println("ID da categoria: "+categoria.getId()+"\n"
                    + "Nome: "+categoria.getNome()+"\n"
                    + "-----------------------");
        }

    }

}
