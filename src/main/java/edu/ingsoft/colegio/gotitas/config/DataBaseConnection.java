
package main.java.edu.ingsoft.colegio.gotitas.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    private static Connection connection;
    
    private DataBaseConnection(){}
    
    public static Connection getConnectionDataBase() throws SQLException{
        if(connection == null || connection.isClosed()){
            connection = DriverManager.getConnection(Credentials.DB_URL, Credentials.DB_USER, Credentials.DB_PASS);
        }
        return connection;
    }
}
