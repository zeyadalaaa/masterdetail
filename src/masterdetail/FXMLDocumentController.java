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
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
    private TextField emailText;
    @FXML
    TableView<Employee> tableView;
    @FXML
    ComboBox<String> departmentComboBox;    
    @FXML
    private DatePicker date;
    @FXML
    private ComboBox<String> sectionComboBox;
    
    @FXML
    private void handleAddButtonAction(ActionEvent event) {
        String firstName = firstNameText.getText();
        String lastName = lastNameText.getText();
        LocalDate localDate = date.getValue();
        java.sql.Date DateSQL = java.sql.Date.valueOf(localDate);
        String email = emailText.getText();
        String departmentName = departmentComboBox.getValue(); 
        int departmentId = super.getDepartmentId(departmentName);
        String sectionName = sectionComboBox.getValue(); 
        int sectionId = super.getSectionId(sectionName);
        
        try {
            ConnectDB connection = new ConnectDB();
            
            String STP= "CALL addEmployee(?,?,?,?,?,?)";
            Connection connection1 =connection.ConnectToDatabase();
            CallableStatement statement = null;
            connection.addEmployee(connection1,statement,STP,
                                    firstName,lastName,DateSQL,email,departmentId,sectionId);
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
        LocalDate localDate = date.getValue();
        java.sql.Date DateSQL = java.sql.Date.valueOf(localDate);
        String email = emailText.getText();    
        String departmentName = departmentComboBox.getValue(); 
        int departmentId = super.getDepartmentId(departmentName);
        String sectionName = sectionComboBox.getValue(); 
        int sectionId = super.getSectionId(sectionName);
        
        try {
            ConnectDB connection = new ConnectDB();
            
            String STP = "CALL updateEmployee(?,?,?,?,?,?,?)";
            Connection connection1 =connection.ConnectToDatabase();
            CallableStatement statement = null;
            connection.updateEmployee(connection1,statement,STP,
                                    firstName,lastName,DateSQL,email,departmentId,employeeid,sectionId);
            
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
        try {
            ConnectDB connection = new ConnectDB();
            
            String STP = "CALL deleteEmployee(?)";
            Connection connection1 =connection.ConnectToDatabase();
            CallableStatement statement = null;
            connection.deleteEmployee(connection1,statement,STP,
                                    employeeid);
            
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
        try{
        
        int index = tableView.getSelectionModel().getSelectedIndex();
        Employee selectedEmployee = tableView.getSelectionModel().getSelectedItem();
        Department selectedDepartment = selectedEmployee.getDepartment();
        Section selectedSection = selectedEmployee.getDepartment().getSection();
        java.sql.Date dateToSet = selectedEmployee.getDOB();
        java.util.Date utilDate = new java.util.Date(dateToSet.getTime());
        Instant instant = utilDate.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        
        
        tableView.getSelectionModel().select(index);
        firstNameText.setText(selectedEmployee.getFirst_name());
        lastNameText.setText(selectedEmployee.getLast_name());
        date.setValue( localDate);
        emailText.setText(selectedEmployee.getEmail());
        departmentComboBox.setValue(selectedDepartment.getName());
        sectionComboBox.setValue(selectedSection.getSectionName());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @FXML
    private void SwitchDepartmentSceneButton(MouseEvent event)throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDepartment.fxml"));
            FXMLDepartmentController md = new FXMLDepartmentController();
            md.tableUpdate(root);
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
                String departmentName = resultSet.getString("department_name");
                departmentComboBox.getItems().add(departmentName);
            }
            statement.close();
            connection1.close();
        } catch (Exception e) {
            
        }
    }
    
    @FXML
    private void populateSectionComboBox(MouseEvent event) {
        
        ObservableList<String> list = FXCollections.observableArrayList("");
        sectionComboBox.setItems(list);
        try {
            ConnectDB connection = new ConnectDB();
            
            String STP = "CALL getSection()";
            Connection connection1 =connection.ConnectToDatabase();
            CallableStatement statement = null;
            ResultSet resultSet = connection.getSection(connection1,statement,STP);
            
            while (resultSet.next()) {
                String sectionID = resultSet.getString("id");
                String sectionName = resultSet.getString("name");
                sectionComboBox.getItems().add(sectionName);
            }
            statement.close();
            connection1.close();
        } catch (Exception e) {
            
        }
    }    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}



///store procedure