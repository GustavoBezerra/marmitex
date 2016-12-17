package com.les.marmitex.util.teste;

import com.les.marmitex.core.dao.impl.IngredienteDAO;
import com.les.marmitex.core.dominio.Dias;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Ingrediente;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO descrição da classe main
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 15/12/2016
 */

public class TestarBuscaIngredientesPorDia {

    public static void main(String[] args) throws SQLException {
        Ingrediente i = new Ingrediente();
        List<Dias> dias = new ArrayList<>();
        IngredienteDAO dao = new IngredienteDAO();
        
        dias.add(Dias.SEXTA);        
        i.setDias(dias);
        
        for(EntidadeDominio e : dao.consultar(i)){
            Ingrediente ingrediente = (Ingrediente) e;
            System.out.println("Nome: "+ingrediente.getNome());
            System.out.println("------------------------");
        }
    }

}
