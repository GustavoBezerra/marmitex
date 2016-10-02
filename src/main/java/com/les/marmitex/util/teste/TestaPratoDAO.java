package com.les.marmitex.util.teste;

import com.les.marmitex.core.dao.impl.PratoDAO;
import com.les.marmitex.core.dominio.Ingrediente;
import com.les.marmitex.core.dominio.Prato;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TODO descrição da classe main
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 02/10/2016
 */

public class TestaPratoDAO {

    public static void main(String[] args) throws SQLException {
        Prato p = new Prato();
        PratoDAO pDAO = new PratoDAO();
        List<Ingrediente> ingredientes = new ArrayList<>();
        Ingrediente i1 = new Ingrediente();
        Ingrediente i2 = new Ingrediente();
        double valor=0;
        
        i1.setId(14);
        i1.setValor(14);
        
        i2.setId(13);
        i2.setValor(6);
        
        ingredientes.add(i1);
        ingredientes.add(i2);
        
        for(Ingrediente i : ingredientes){
            System.out.println("Valor do ingrediente "+i.getId()+": "+i.getValor());
            valor += i.getValor();
        }
        System.out.println("Valor total: "+valor);
        p.setAtivo(true);
        p.setDtCriacao(new Date());
        p.setDtDisponivel(new Date());
        p.setIngredientes(ingredientes);
        p.setValor(valor);
        p.setId(4);
        
        pDAO.alterar(p);        
        
    }

}
