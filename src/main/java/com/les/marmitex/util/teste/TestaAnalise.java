package com.les.marmitex.util.teste;

import com.les.marmitex.core.dao.impl.GraficoDAO;
import com.les.marmitex.core.dominio.EntidadeDominio;
import com.les.marmitex.core.dominio.Grafico;
import com.les.marmitex.core.dominio.ItemGrafico;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * TODO descrição da classe main
 * @author Gustavo de Souza Bezerra <gustavo.bezerra@hotmail.com>
 * @date 05/11/2016
 */

public class TestaAnalise {

    public static void main(String[] args) throws SQLException {
        
        Grafico g = new Grafico();
        g.setDtInicio(new Date(1702931316));
        g.setDtFim(new Date(752531316));
        
        GraficoDAO dao = new GraficoDAO();
        List<EntidadeDominio> itens = dao.consultar(g);
        System.out.println("Quantidade de itens: "+itens.size());
        for (EntidadeDominio iten : itens) {
            ItemGrafico item = (ItemGrafico) iten;
            System.out.println("Nome: "+item.getNome());
            System.out.println("Valor: "+item.getValor());
            System.out.println("Data: "+item.getData().toString());
        }
        
    }

}
