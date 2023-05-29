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
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
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
    ComboBox<String> departmentComboBox;
    
    
    private int getDepartmentId(String name, String section){
        String sql = "SELECT id FROM departments WHERE name = ? AND section = ?;";
        int departmentId = 0; // Default value to indicate no department found

        
        
        Connection connection = null;
        PreparedStatement stmt = null;
        
        try {
            connection = connect.ConnectToDatabase();
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, section);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                departmentId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departmentId;
    }
    
    @FXML
    private void handleAddButtonAction(ActionEvent event) {
        
        String firstName = firstNameText.getText();
        String lastName = lastNameText.getText();
        String age = ageText.getText();
        String email = emailText.getText();
        String selectedDepartment = departmentComboBox.getValue(); 
        String[] departmentParts = selectedDepartment.split(", ");
        String departmentName = departmentParts[0];
        String departmentSection = departmentParts[1];
        int departmentId = getDepartmentId(departmentName, departmentSection);
        
        Connection connection = null;
        PreparedStatement stmt = null;
        
        try {
            connection = connect.ConnectToDatabase();
            
            String sql = "INSERT INTO Employees (first_name, last_name, age, email, department_id) " +
                         "VALUES (?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, age);
            stmt.setString(4, email);
            stmt.setInt(5, departmentId);
            stmt.executeUpdate();
            
            
            incrementDepartmentEmployeeCount(departmentId, connection);
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
        String selectedDepartment = departmentComboBox.getValue(); 
        String[] departmentParts = selectedDepartment.split(", ");
        String departmentName = departmentParts[0];
        String departmentSection = departmentParts[1];
        int prevDepartmentId = selectedEmployee.getDepartment_id();
        int departmentId = getDepartmentId(departmentName, departmentSection);
        String sql = "UPDATE employees SET first_name = ?, last_name = ?, age = ? ,email = ?, department_id = ?  WHERE id = ?";
        Connection connection = null;
        PreparedStatement stmt = null;
        
        try {
            connection = connect.ConnectToDatabase();
            
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, age);
            stmt.setString(4, email);
            stmt.setInt(5, departmentId);
            stmt.setInt(6, employeeid);
            stmt.executeUpdate();
            
            incrementDepartmentEmployeeCount(departmentId, connection);
            decrementDepartmentEmployeeCount(prevDepartmentId, connection);
            
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
        String selectedDepartment = departmentComboBox.getValue(); 
        String[] departmentParts = selectedDepartment.split(", ");
        String departmentName = departmentParts[0];
        String departmentSection = departmentParts[1];
        int departmentId = getDepartmentId(departmentName, departmentSection);
        
        Connection connection = null;
        PreparedStatement stmt = null;
        
        try {
            connection = connect.ConnectToDatabase();
            String sql = "DELETE FROM employees WHERE id = ?";
            stmt = connection.prepareStatement(sql);
            
            stmt.setInt(1, employeeid);
            stmt.executeUpdate();
            decrementDepartmentEmployeeCount(departmentId, connection);
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
        Department selectedDepartment = selectedEmployee.getDepartment();
        tableView.getSelectionModel().select(index);
        firstNameText.setText(selectedEmployee.getFirst_name());
        lastNameText.setText(selectedEmployee.getLast_name());
        ageText.setText(Integer.toString(selectedEmployee.getAge()));
        emailText.setText(selectedEmployee.getEmail());
        departmentComboBox.setValue(selectedDepartment.getName() + ", " + selectedDepartment.getSection());
    }
    
    @FXML
    private void SwitchSceneButton(MouseEvent event)throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDepartment.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(    root);
            stage.setScene(scene);
            stage.show();
    }
    
    @FXML
    private void populateDepartmentComboBox(MouseEvent event) {
        
        String sql = "SELECT * FROM departments;";
        
//        departmentComboBox = new ComboBox<>();
        ObservableList<String> list = FXCollections.observableArrayList("");
        departmentComboBox.setItems(list);
//        System.out.println(sql);
        ConnectDB connection = new ConnectDB();
        try (
            Connection connection1 =connection.ConnectToDatabase();
            PreparedStatement statement = connection1.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                String departmentID = resultSet.getString("id");
                String departmentName = resultSet.getString("name");
                String departmentSection = resultSet.getString("section");
                departmentComboBox.getItems().add(departmentName +", " +  departmentSection);
            }
            statement.close();
            connection1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    private void incrementDepartmentEmployeeCount(int departmentId,Connection connection) {
        String updateQuery = "UPDATE departments SET employee_numbers = employee_numbers + 1 WHERE id = ?;";

        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setInt(1, departmentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void decrementDepartmentEmployeeCount(int departmentId,Connection connection) {
        String updateQuery = "UPDATE departments SET employee_numbers = CASE\n" +
                            " WHEN employee_numbers - 1 < 0 THEN 0 ELSE employee_numbers - 1 \n" +
                            "END " + 
                            "WHERE id = ?;" ;
                            

        try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setInt(1, departmentId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}



///store procedure