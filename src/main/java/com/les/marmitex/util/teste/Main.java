package com.les.marmitex.util.teste;

import com.les.marmites.util.Conexao;
import java.util.ArrayList;
import java.util.List;

import com.les.marmitex.core.dominio.Endereco;
import com.les.marmitex.core.dominio.Usuario;
import com.les.marmitex.core.strategy.impl.ValidarCamposEmBranco;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
//        Usuario u = new Usuario();
//        u.setLogin("gbezerra");
//        //u.setSenha("123");
//
//        Endereco e = new Endereco();
//        List<Endereco> lista = new ArrayList<>();
//        lista.add(e);
//        //u.setEndereco(lista);
//
//        ValidarCamposEmBranco v = new ValidarCamposEmBranco();
//
//        System.out.println(v.validar(u));

        testarConexao();

    }
    
    public static void testarConexao(){
        try {
            Connection connection = Conexao.getConnection();
            if(!connection.isClosed()){
                System.out.println("Funcionou!");
            }
            else{
                System.out.println("Erro!");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
