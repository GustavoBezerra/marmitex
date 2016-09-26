package com.les.marmitex.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    public static Connection getConnection()
            throws ClassNotFoundException,
            SQLException {
//		String driver = "org.mysql.Driver";
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/marmitex?autoReconnect=true&useSSL=false";
        String user = "root";
        String password = "fatec123";
        Class.forName(driver);
        Connection conn
                = DriverManager.getConnection(url, user, password);

        return conn;

    }

}
