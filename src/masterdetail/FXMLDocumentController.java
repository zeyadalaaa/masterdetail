/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package masterdetail;

import java.sql.PreparedStatement;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Zeyad.Alaa
 */
public class FXMLDocumentController implements Initializable {
    ConnectDB connect= new ConnectDB();
    
    @FXML
    private TextField firstNameText;
    @FXML
    private TextField lastNameText;
    @FXML
    private TextField ageText;
    @FXML
    private TextField emailText;
    
    @FXML
    private void handleAddButtonAction(ActionEvent event) {
        
        String firstName = firstNameText.getText();
        String lastName = lastNameText.getText();
        String age = ageText.getText(); //change later
        String email = emailText.getText();
        
        Connection connection = null;
        PreparedStatement stmt = null;
        
        try {
            connection = connect.ConnectToDatabase();
            
            String sql = "INSERT INTO Employees (first_name, last_name, age, email) " +
                         "VALUES (?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);

            // Set the values for the statement
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, age);
            stmt.setString(4, email);
            stmt.executeUpdate();
            System.out.println("Record added successfully...");
            
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
               if(stmt!=null)
                  stmt.close();
            } catch(SQLException se2) {
            } try {
               if(connection!=null)
                  connection.close();
            } catch(SQLException se){
               se.printStackTrace();
            }
         }
        
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
