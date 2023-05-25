/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package masterdetail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Zeyad.Alaa
 */
public class ConnectDB {
    
    static final String url = "jdbc:mysql://localhost:3306/MasterDetail";
    static final String username = "root";
    static final String password = "123456";
    
    public Connection ConnectToDatabase() {

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
//                System.out.println(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
