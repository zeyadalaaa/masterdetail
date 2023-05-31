/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package masterdetail;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Date;

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public ResultSet getEmployees(Connection connection,CallableStatement statement ,String STP) throws SQLException {
            
        statement =  connection.prepareCall(STP);
        ResultSet resultSet = statement.executeQuery();
            
        return resultSet;
    }
    
    public void addEmployee(Connection connection,CallableStatement statement ,String STP,String firstName,String lastName,Date date,String email,Integer departmentId, int sectionId) throws SQLException {
        statement = connection.prepareCall(STP);    
        statement.setString(1, firstName);
        statement.setString(2, lastName);
        statement.setDate(3, date);
        statement.setString(4, email);
        statement.setInt(5, departmentId);
        statement.setInt(6, sectionId);
        statement.executeUpdate();
        statement.close();
    }

    public void updateEmployee(Connection connection, CallableStatement statement, String STP, String firstName, String lastName, Date date, String email, int departmentId, int employeeid, int sectionId) throws SQLException {
                    
        statement = connection.prepareCall(STP);
        statement.setString(1, firstName);
        statement.setString(2, lastName);
        statement.setDate(3, date);
        statement.setString(4, email);
        statement.setInt(5, departmentId);
        statement.setInt(6, employeeid);
        statement.setInt(7, sectionId);
        statement.executeUpdate();
        statement.close();
    }

    public void deleteEmployee(Connection connection, CallableStatement statement, String STP, int employeeid) throws SQLException {
        
        statement = connection.prepareCall(STP);
        statement.setInt(1, employeeid);
        statement.executeUpdate();
        statement.close();
    }
    
    public ResultSet getDepartment(Connection connection, CallableStatement statement, String STP) throws SQLException {
        
        statement = connection.prepareCall(STP);
        ResultSet resultSet = statement.executeQuery();
        
        return resultSet;
    }

    public ResultSet getDepartmentId(Connection connection, CallableStatement statement, String STP, String name) throws SQLException {
        statement = connection.prepareCall(STP);
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        
        return resultSet;
    }

    public void addSection(Connection connection, CallableStatement statement, String STP, String sectionName) throws SQLException {
               
        statement = connection.prepareCall(STP);    
        statement.setString(1, sectionName);
        statement.executeUpdate();
        statement.close();
    }

    ResultSet getSection(Connection connection, CallableStatement statement, String STP) throws SQLException {
        
        statement = connection.prepareCall(STP);
        ResultSet resultSet = statement.executeQuery();
       
        return resultSet;
    }

    ResultSet getSectionId(Connection connection, CallableStatement statement, String STP, String sectionName) throws SQLException {
        statement = connection.prepareCall(STP);
        statement.setString(1, sectionName);
        ResultSet resultSet = statement.executeQuery();
        
        return resultSet;
    }

    void addDepartment(Connection connection, CallableStatement statement, String STP, String name, int sectionId) throws SQLException {
        
        statement = connection.prepareCall(STP);    
        statement.setString(1, name);
        statement.setInt(2, sectionId);
        statement.executeUpdate();
        statement.close();
    }


}
