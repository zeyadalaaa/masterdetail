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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

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
    TableView<Employee> tableView;
    
    
    
    @FXML
    private void handleAddButtonAction(ActionEvent event) {
        
        String firstName = firstNameText.getText();
        String lastName = lastNameText.getText();
        String age = ageText.getText();
        String email = emailText.getText();
        
        Connection connection = null;
        PreparedStatement stmt = null;
        
        try {
            connection = connect.ConnectToDatabase();
            
            String sql = "INSERT INTO Employees (first_name, last_name, age, email) " +
                         "VALUES (?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, age);
            stmt.setString(4, email);
            stmt.executeUpdate();
            
            stmt.close();
            connection.close();
            
            Stage currentStage = (Stage) firstNameText.getScene().getWindow();
            currentStage.close();
            MasterDetail masterDetail = new MasterDetail();
            masterDetail.start(new Stage());
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
    
    
    @FXML
    private void handleUpdateButtonAction(ActionEvent event) {
        
        Employee selectedEmployee = tableView.getSelectionModel().getSelectedItem();

        int employeeid = selectedEmployee.getId();
        String firstName = firstNameText.getText();
        String lastName = lastNameText.getText();
        String age = ageText.getText(); 
        String email = emailText.getText();
        
        Connection connection = null;
        PreparedStatement stmt = null;
        
        try {
            connection = connect.ConnectToDatabase();
            
            String sql = "UPDATE employees SET first_name = ?, last_name = ?, age = ? ,email = ? WHERE id = ?";
            stmt = connection.prepareStatement(sql);
            
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, age);
            stmt.setString(4, email);
            stmt.setInt(5, employeeid);
            stmt.executeUpdate();
            
            stmt.close();
            connection.close();
            
            Stage currentStage = (Stage) firstNameText.getScene().getWindow();
            currentStage.close();
            MasterDetail masterDetail = new MasterDetail();
            masterDetail.start(new Stage());
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
   
    
    
    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        
        Employee selectedEmployee = tableView.getSelectionModel().getSelectedItem();

        int employeeid = selectedEmployee.getId();
        String firstName = firstNameText.getText();
        String lastName = lastNameText.getText();
        String age = ageText.getText(); 
        String email = emailText.getText();
        
        Connection connection = null;
        PreparedStatement stmt = null;
        
        try {
            connection = connect.ConnectToDatabase();
            String sql = "DELETE FROM employees WHERE id = ?";
            stmt = connection.prepareStatement(sql);
            
            stmt.setInt(1, employeeid);
            stmt.executeUpdate();
            
            stmt.close();
            connection.close();
            
            Stage currentStage = (Stage) firstNameText.getScene().getWindow();
            currentStage.close();
            MasterDetail masterDetail = new MasterDetail();
            masterDetail.start(new Stage());
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
    
    @FXML
    private void TableMouseClickAction(MouseEvent event) {
        int index = tableView.getSelectionModel().getSelectedIndex();
        Employee selectedEmployee = tableView.getSelectionModel().getSelectedItem();
        tableView.getSelectionModel().select(index);
        firstNameText.setText(selectedEmployee.getFirst_name());
        lastNameText.setText(selectedEmployee.getLast_name());
        ageText.setText(Integer.toString(selectedEmployee.getAge()));
        emailText.setText(selectedEmployee.getEmail());
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
