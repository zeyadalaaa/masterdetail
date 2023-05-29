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
    


    
    // Method to retrieve data from the database
    public ObservableList<Employee> retrieveDataFromDatabase(Connection connection1) {
        ObservableList<Employee> employeeData = FXCollections.observableArrayList();
        try{
//            String sql =    "SELECT * FROM masterdetail.employees;";
            String sql = "SELECT *, departments.name, departments.section FROM employees "
                + "LEFT JOIN departments ON employees.department_id = departments.id ";
      
            Statement statement =  connection1.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            
//            System.out.print(statement);
//            System.out.print(resultSet);
            
            int i =0;
            while (resultSet.next()) {
                int employeeId = resultSet.getInt("id");
                String employeeFirst_Name = resultSet.getString("first_name");
                String employeesLast_name = resultSet.getString("last_name");
                int employeesAge = resultSet.getInt("age");
                String employeesEmail = resultSet.getString("email");
                int departmentID = resultSet.getInt("department_id");
                String departmentName = resultSet.getString("departments.name");
                String departmentSection = resultSet.getString("departments.section");
                
//            System.out.print(departmentName +" "+departmentSection);
                Department department = new Department(departmentID,departmentName,departmentSection);
                Employee employee = new Employee(employeeId, employeeFirst_Name, employeesLast_name,
                                                employeesAge, employeesEmail,departmentID, department);

                employeeData.add(employee);
                System.out.println("Employee1: " + employeeData.get(i).getDepartment().getName());
                i++;
                System.out.println("Employee2: " + employee.getDepartment().getName());
                
            }
            statement.close();
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
    TableColumn<Employee, Integer> ageColumn ;
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
            Connection connection1 =connection.ConnectToDatabase();
            
            idColumn = new TableColumn<>("id");
            FirstNameColumn = new TableColumn<>("first_name");
            lastNameColumn = new TableColumn<>("last_name");
            ageColumn = new TableColumn<>("age");
            emailColumn = new TableColumn<>("email");
            department_ID = new TableColumn<>("department_id");
            department_name = new TableColumn<>("department.name");
            department_section = new TableColumn<>("department.section");

            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            FirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("first_name"));
            lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("last_name"));
            ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            department_ID.setCellValueFactory(new PropertyValueFactory<>("department_id"));
//            department_name.setCellValueFactory(new PropertyValueFactory<>("department.name"));
            
            department_name.setCellValueFactory(new Callback<CellDataFeatures<Employee, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<Employee, String> data) {
                    return new SimpleStringProperty(data.getValue().getDepartment().getName());
                }
            });
//            department_section.setCellValueFactory(new PropertyValueFactory<>("department.section"));

            department_section.setCellValueFactory(new Callback<CellDataFeatures<Employee, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<Employee, String> data) {
                    return new SimpleStringProperty(data.getValue().getDepartment().getSection());
                }
            });
            
            tableView.getColumns().addAll(idColumn, FirstNameColumn, lastNameColumn,
                                            ageColumn, emailColumn, department_ID,
                                            department_name,department_section);

            // Retrieve data from the database and populate the table
            ObservableList<Employee> employeeData = retrieveDataFromDatabase(connection1);
            tableView.setItems(employeeData);
            
//System.out.println("Employee1: " + employeeData.get(3).getDepartment().getName());
//System.out.println("Employee: " + employeeData.get(2).getDepartment().getName());
            connection1.close();
        } catch (Exception e) {
            e.printStackTrace();
        
        }
            
//            PreparedStatement statement =  connection1.createStatement();
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
