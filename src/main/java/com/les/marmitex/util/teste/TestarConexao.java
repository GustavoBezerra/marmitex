package com.les.marmitex.util.teste;

import com.les.marmitex.util.Conexao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gustavo
 */
public class TestarConexao {

    /**
     * Classe para testar a conexão com o banco
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Connection connection = Conexao.getConnection();
            if (!connection.isClosed()) {
                System.out.println("Funcionou!");
            } else {
                System.out.println("Erro!");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TestarCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TestarCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
