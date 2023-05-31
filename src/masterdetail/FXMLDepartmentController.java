/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package masterdetail;

import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Zeyad.Alaa
 */
public class FXMLDepartmentController implements Initializable {

    @FXML
    private TextField departmentName;
    @FXML
    private ComboBox<String> sectionComboBox;

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
    
    
    @FXML
    private void addDepartment(ActionEvent event) {
        String name = departmentName.getText();
        int sectionId = 0;
        
        String sectionName = sectionComboBox.getValue(); 
        
        try {
            ConnectDB connection = new ConnectDB();
            
            String STP = "CALL getSectionId(?)";
            Connection connection1 =connection.ConnectToDatabase();
            CallableStatement statement = null;
            ResultSet resultSet = connection.getSectionId(connection1,statement,STP, sectionName);
            
            if (resultSet.next()) {
                sectionId = resultSet.getInt("id");
            }
            STP = "CALL addDepartment(?,?)";
            connection.addDepartment(connection1,statement,STP, name, sectionId);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDepartment.fxml"));
            Parent root = loader.load();
            FXMLDepartmentController controller = loader.getController();

            controller.tableUpdate(root);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            
            connection1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    
    @FXML
    TableView<Department> tableView;
    @FXML
    TableColumn<Department, Integer> idColumn ;
    @FXML
    TableColumn<Department, String> departmentNameColumn ;
    @FXML
    TableColumn<Department, String> SectionIDColumn ;
    @FXML
    TableColumn<Department, String> sectionName ;
    @FXML
            
    TableColumn<Department, Integer> employees_number ;
    
        public ObservableList<Department> retrieveDataFromDatabase(ConnectDB connection) {
        ObservableList<Department> departmentData = FXCollections.observableArrayList();
        try{
            String STP= "CALL getDepartment()";
            Connection connection1 =connection.ConnectToDatabase();

            CallableStatement statement = null;
            ResultSet resultSet = connection.getEmployees(connection1,statement,STP);
            idColumn = new TableColumn<>("id");
            departmentNameColumn = new TableColumn<>("department_name");
            SectionIDColumn = new TableColumn<>("section_id");
            employees_number = new TableColumn<>("employees_number");
            sectionName = new TableColumn<>("section_Name");
            
            
            while (resultSet.next()) {
                int departmentID = resultSet.getInt("id");
                String departmentName = resultSet.getString("department_name");
                int employees_number = resultSet.getInt("employees_number");
                int sectionID = resultSet.getInt("section_id");
                String sectionName = resultSet.getString("section_name");
                
                Section section = new Section(sectionID,sectionName);
                Department department = new Department(departmentID,departmentName,employees_number,section);
                
                
                departmentData.add(department);
                
            }
            connection1.close();
            return departmentData;
        }catch(Exception e)
        {
            
        }
        return departmentData;
    }
    
    
    public void tableUpdate(Parent root) throws Exception{
        try {
            
            tableView = (TableView<Department>)root.lookup("#tableView");
            ConnectDB connection = new ConnectDB();
            
            idColumn = new TableColumn<>("id");
            departmentNameColumn = new TableColumn<>("department_name");
            employees_number = new TableColumn<>("employees_number");
            SectionIDColumn = new TableColumn<>("section_id");
            sectionName = new TableColumn<>("section_Name");

            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            departmentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            employees_number.setCellValueFactory(new PropertyValueFactory<>("employees_number"));
            SectionIDColumn.setCellValueFactory(new PropertyValueFactory<>("s.id"));
            sectionName.setCellValueFactory(new PropertyValueFactory<>("s.Name"));
            
            SectionIDColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Department, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Department, String> data) {
                    int sectionId = data.getValue().getSection().getId();
                    String sectionIdString = String.valueOf(sectionId);
                    return new SimpleStringProperty(sectionIdString);
                }
            });
            
            sectionName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Department, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Department, String> data) {
                    return new SimpleStringProperty(data.getValue().getSection().getSectionName());
                }
            });
            
            tableView.getColumns().addAll(idColumn, departmentNameColumn,
                                    employees_number,SectionIDColumn, sectionName);

            ObservableList<Department> departmentData = retrieveDataFromDatabase(connection);
            tableView.setItems(departmentData);
            
        } catch (Exception e) {
            e.printStackTrace();
        
        }
            
    }
    
    
    
    @FXML
    private void Back(MouseEvent event) throws IOException, Exception {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            MasterDetail masterDetail = new MasterDetail();
            masterDetail.tableUpdate(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(    root);
            stage.setScene(scene);
            stage.show();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
}
