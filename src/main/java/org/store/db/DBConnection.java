package org.store.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private Connection connection;
    public DBConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/clothfystore","root","gihan123");
    }
    //    ?useSSL=false
    public Connection getConnection(){
        return connection;
    }
    public static DBConnection getInstance() throws ClassNotFoundException, SQLException {
        if(null==instance){
            instance=new DBConnection();
        }
        return instance;
    }
}
