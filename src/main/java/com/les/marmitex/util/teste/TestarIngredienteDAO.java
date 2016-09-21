package com.les.marmitex.util.teste;

import com.les.marmitex.core.dao.impl.IngredienteDAO;
import com.les.marmitex.core.dominio.Categoria;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Ingrediente;
import java.sql.SQLException;
import java.util.Date;

/**
 * TODO descrição da classe main
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 12/09/2016
 */

public class TestarIngredienteDAO {

    public static void main(String[] args) throws SQLException {
        Ingrediente i = new Ingrediente();
        Categoria c = new Categoria();
        IngredienteDAO dao = new IngredienteDAO();

        c.setId(9);
        c.setNome("Mistura");
        
        i.setDtCriacao(new Date());
        i.setDtVencimento(new Date());
        i.setMedida("Kilos");
        i.setNome("Carne");
        i.setQuantidade(2);
        i.setCategoria(c);
        i.setValor(5.50);        
        
        i.setId(1);

//        dao.salvar(i);
        //dao.alterar(i);
        //dao.excluir(i);

//        Ingrediente in = null;
//
//        for(EntidadeDominio e : dao.consultar(i)){
//            in = (Ingrediente) e;
//            System.out.println("ID: "+in.getId()+"\n"
//                    + "Nome: "+in.getNome() +"\n"
//                    + "Quantidade: "+in.getQuantidade()+"\n"
//                    + "Medida: "+in.getMedida()+"\n"
//                    + "Dt Vencimento: "+in.getDtVencimento()+"\n"
//                    + "Dt cadastro: "+in.getDtCriacao()+"\n"
//                    + "------------------------");
//        }

    }

}
