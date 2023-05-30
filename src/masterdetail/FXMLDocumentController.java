/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package masterdetail;

import java.sql.PreparedStatement;
import java.net.URL;
import java.sql.CallableStatement;
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
public class FXMLDocumentController extends SharedClass implements Initializable {
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
    
    
//    private int getDepartmentId(String name){
//        int departmentId = 0;
//        
//        try {
//            ConnectDB connection = new ConnectDB();
//            
//            String STP= "CALL getDepartmentId(?)";
//            Connection connection1 =connection.ConnectToDatabase();
//            CallableStatement statement = null;
//            ResultSet resultSet = connection.getDepartmentId(connection1,statement,STP,
//                                        name);
//            
//            if (resultSet.next()) {
//                departmentId = resultSet.getInt("id");
//            }
//            connection1.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return departmentId;
//    }
    
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
        int departmentId = super.getDepartmentId(departmentName);
        
        try {
            ConnectDB connection = new ConnectDB();
            
            String STP= "CALL addEmployee(?,?,?,?,?)";
            Connection connection1 =connection.ConnectToDatabase();
            CallableStatement statement = null;
            connection.addEmployee(connection1,statement,STP,
                                    firstName,lastName,age,email,departmentId);
            
            incrementDepartmentEmployeeCount(departmentId, connection);
            connection1.close();
            
            Stage currentStage = (Stage) firstNameText.getScene().getWindow();
            currentStage.close();
            MasterDetail masterDetail = new MasterDetail();
            masterDetail.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
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
        int departmentId = getDepartmentId(departmentName);
        
        try {
            ConnectDB connection = new ConnectDB();
            
            String STP = "CALL updateEmployee(?,?,?,?,?,?)";
            Connection connection1 =connection.ConnectToDatabase();
            CallableStatement statement = null;
            connection.updateEmployee(connection1,statement,STP,
                                    firstName,lastName,age,email,departmentId,employeeid);
            
            incrementDepartmentEmployeeCount(departmentId, connection);
            decrementDepartmentEmployeeCount(prevDepartmentId, connection);
            
            connection1.close();
            
            Stage currentStage = (Stage) firstNameText.getScene().getWindow();
            currentStage.close();
            MasterDetail masterDetail = new MasterDetail();
            masterDetail.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
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
        int departmentId = getDepartmentId(departmentName);
        
        try {
            ConnectDB connection = new ConnectDB();
            
            String STP = "CALL deleteEmployee(?)";
            Connection connection1 =connection.ConnectToDatabase();
            CallableStatement statement = null;
            connection.deleteEmployee(connection1,statement,STP,
                                    employeeid);
            
            decrementDepartmentEmployeeCount(departmentId, connection);
            connection1.close();
            
            Stage currentStage = (Stage) firstNameText.getScene().getWindow();
            currentStage.close();
            MasterDetail masterDetail = new MasterDetail();
            masterDetail.start(new Stage());
        } catch (Exception e) {
            
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
    private void SwitchDepartmentSceneButton(MouseEvent event)throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDepartment.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(    root);
            stage.setScene(scene);
            stage.show();
    }
    
    @FXML
    private void SwitchSectionSceneButton(MouseEvent event)throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLSection.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(    root);
            stage.setScene(scene);
            stage.show();
    }
    
    @FXML
    private void populateDepartmentComboBox(MouseEvent event) {
        
        ObservableList<String> list = FXCollections.observableArrayList("");
        departmentComboBox.setItems(list);
        try {
            ConnectDB connection = new ConnectDB();
            
            String STP = "CALL getDepartment()";
            Connection connection1 =connection.ConnectToDatabase();
            CallableStatement statement = null;
            ResultSet resultSet = connection.getDepartment(connection1,statement,STP);
            
            while (resultSet.next()) {
                String departmentID = resultSet.getString("id");
                String departmentName = resultSet.getString("name");
                String departmentSection = resultSet.getString("section");
                departmentComboBox.getItems().add(departmentName +", " +  departmentSection);
            }
            statement.close();
            connection1.close();
        } catch (Exception e) {
            
        }
    }
    
    private void incrementDepartmentEmployeeCount(int departmentId,ConnectDB connection) {

        try {
//            statement.setInt(1, departmentId);
            String STP = "CALL incrementDepartmentEmployeeCount(?)";
            Connection connection1 = connection.ConnectToDatabase();
            CallableStatement statement = null;
            connection.incrementDepartmentEmployeeCount(connection1,statement,STP,departmentId);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void decrementDepartmentEmployeeCount(int departmentId,ConnectDB connection) {
                            

        try {
            String STP = "CALL decrementDepartmentEmployeeCount(?)";
            Connection connection1 = connection.ConnectToDatabase();
            CallableStatement statement = null;
            connection.decrementDepartmentEmployeeCount(connection1,statement,STP,departmentId);
            
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