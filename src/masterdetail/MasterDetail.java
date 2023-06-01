/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package masterdetail;

import java.sql.Statement;
import java.sql.ResultSet;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
/**
 *
 * @author Zeyad.Alaa
 */
public class MasterDetail extends Application {
    
    public ObservableList<Employee> retrieveDataFromDatabase(ConnectDB connection) {
        ObservableList<Employee> employeeData = FXCollections.observableArrayList();
        try{
            String STP= "CALL getEmployees()";
            Connection connection1 =connection.ConnectToDatabase();
            CallableStatement statement = null;
            ResultSet resultSet = connection.getEmployees(connection1,statement,STP);
            
            while (resultSet.next()) {
                int employeeId = resultSet.getInt("id");
                String employeeFirst_Name = resultSet.getString("first_name");
                String employeesLast_name = resultSet.getString("last_name");
                Date employeesDOB = resultSet.getDate("date_of_birth");
                String employeesEmail = resultSet.getString("email");
                int departmentID = resultSet.getInt("department_id");
                int sectionID = resultSet.getInt("section_id");
                String sectionName = resultSet.getString("section_name");
                String departmentName = resultSet.getString("department_name");
                
                Section section = new Section(sectionID,sectionName);
                int employee_number = 0;
                Department department = new Department(departmentID,departmentName,employee_number,section);
                Employee employee = new Employee(employeeId, employeeFirst_Name, employeesLast_name,
                                                employeesDOB, employeesEmail,departmentID, department);
                
                employeeData.add(employee);
                
            }
            connection1.close();
            return employeeData;
        }catch(Exception e)
        {
            
        }
        return employeeData;
    }
    
    
    @FXML
    TableView<Employee> tableView;
    @FXML
    TableColumn<Employee, Integer> idColumn ;
    @FXML
    TableColumn<Employee, String> FirstNameColumn ;
    @FXML
    TableColumn<Employee, String> lastNameColumn ;
    @FXML
    TableColumn<Employee, Date> DOBColumn ;
    @FXML
    TableColumn<Employee, String> emailColumn ;
    @FXML
    TableColumn<Employee, Integer> department_ID ;
    @FXML
    TableColumn<Employee, String> department_name ;
    @FXML
    TableColumn<Employee, String> department_section ;
    
    public void tableUpdate(Parent root) throws Exception{
        try {
            
            tableView = (TableView<Employee>)root.lookup("#tableView");
            ConnectDB connection = new ConnectDB();
            
            idColumn = new TableColumn<>("id");
            FirstNameColumn = new TableColumn<>("first_name");
            lastNameColumn = new TableColumn<>("last_name");
            DOBColumn = new TableColumn<>("DOB");
            emailColumn = new TableColumn<>("email");
            department_ID = new TableColumn<>("department_id");
            department_name = new TableColumn<>("department.name");
            department_section = new TableColumn<>("department.section");

            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            FirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("first_name"));
            lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("last_name"));
            DOBColumn.setCellValueFactory(new PropertyValueFactory<>("DOB"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            department_ID.setCellValueFactory(new PropertyValueFactory<>("department_id"));
            
            department_name.setCellValueFactory(new Callback<CellDataFeatures<Employee, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<Employee, String> data) {
                    return new SimpleStringProperty(data.getValue().getDepartment().getName());
                }
            });

            department_section.setCellValueFactory(new Callback<CellDataFeatures<Employee, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<Employee, String> data) {
                    return new SimpleStringProperty(data.getValue().getDepartment().getSection().getSectionName());
                }
            });
            
            tableView.getColumns().addAll(idColumn, FirstNameColumn, lastNameColumn,
                                            DOBColumn, emailColumn, department_ID,
                                            department_name,department_section);

            ObservableList<Employee> employeeData = retrieveDataFromDatabase(connection);
            tableView.setItems(employeeData);
            
        } catch (Exception e) {
            e.printStackTrace();
        
        }
            
    }
    
    @Override
    public void start(Stage stage) throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            tableUpdate(root);
            Scene scene = new Scene(    root);

            stage.setScene(scene);
            stage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
