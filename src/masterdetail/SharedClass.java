/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package masterdetail;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Zeyad.Alaa
 */

abstract class SharedClass {
   public  int getDepartmentId(String name){
        int departmentId = 0;

        try {
            ConnectDB connection = new ConnectDB();

            String STP= "CALL getDepartmentId(?)";
            Connection connection1 =connection.ConnectToDatabase();
            CallableStatement statement = null;
            ResultSet resultSet = connection.getDepartmentId(connection1,statement,STP,
                                        name);

            if (resultSet.next()) {
                departmentId = resultSet.getInt("id");
            }
            connection1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departmentId;
    }

}