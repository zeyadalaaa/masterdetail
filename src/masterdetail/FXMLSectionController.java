/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package masterdetail;

import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
public class FXMLSectionController  extends SharedClass implements Initializable {

    @FXML
    private Button back;
    
    @FXML
    private Button addSectionButton;

    @FXML
    private TextField sectionNameText;
    
    @FXML
    private Label sectionLabel;
    
    
    @FXML
    private void addSection(ActionEvent event) {
        
        String sectionName = sectionNameText.getText();
        
        try {
            ConnectDB connection = new ConnectDB();
            
            String STP = "CALL addSection(?)";
            Connection connection1 =connection.ConnectToDatabase();
            CallableStatement statement = null;
            connection.addSection(connection1,statement,STP, sectionName);
            
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLSection.fxml"));
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
    /**
     * Initializes the controller class.
     */
    
    
    @FXML
    TableView<Department> tableView;
    @FXML
    TableColumn<Department, Integer> idColumn ;
    @FXML
    TableColumn<Department, String> sectionName ;
    
//    public ObservableList<Department> retrieveDataFromDatabase(ConnectDB connection) {
//        ObservableList<Department> departmentData = FXCollections.observableArrayList();
//        try{
//            String STP= "CALL getDepartment()";
//            Connection connection1 =connection.ConnectToDatabase();
//
//            CallableStatement statement = null;
//            ResultSet resultSet = connection.getEmployees(connection1,statement,STP);
//            idColumn = new TableColumn<>("id");
//            sectionName = new TableColumn<>("name");
//            
//            
//            while (resultSet.next()) {
//                int sectionID = resultSet.getInt("id");
//                String sectionName = resultSet.getString("name");
//                
//                Section section = new Section(sectionID,sectionName);
//                Department department = new Department(departmentID,departmentName,employees_number,section);
//                
//                
//                departmentData.add(department);
//                
//            }
//            connection1.close();
//            return departmentData;
//        }catch(Exception e)
//        {
//            
//        }
//        return departmentData;
//    }
//    
//    
//    public void tableUpdate(Parent root) throws Exception{
//        try {
//            
//            tableView = (TableView<Department>)root.lookup("#tableView");
//            ConnectDB connection = new ConnectDB();
//            
//            idColumn = new TableColumn<>("id");
//            departmentNameColumn = new TableColumn<>("department_name");
//            employees_number = new TableColumn<>("employees_number");
//            SectionIDColumn = new TableColumn<>("section_id");
//            sectionName = new TableColumn<>("section_Name");
//
//            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//            departmentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//            employees_number.setCellValueFactory(new PropertyValueFactory<>("employees_number"));
//            SectionIDColumn.setCellValueFactory(new PropertyValueFactory<>("s.id"));
//            sectionName.setCellValueFactory(new PropertyValueFactory<>("s.Name"));
//            
//            SectionIDColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Department, String>, ObservableValue<String>>() {
//                @Override
//                public ObservableValue<String> call(TableColumn.CellDataFeatures<Department, String> data) {
//                    int sectionId = data.getValue().getSection().getId();
//                    String sectionIdString = String.valueOf(sectionId);
//                    return new SimpleStringProperty(sectionIdString);
//                }
//            });
//            
//            sectionName.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Department, String>, ObservableValue<String>>() {
//                @Override
//                public ObservableValue<String> call(TableColumn.CellDataFeatures<Department, String> data) {
//                    return new SimpleStringProperty(data.getValue().getSection().getSectionName());
//                }
//            });
//            
//            tableView.getColumns().addAll(idColumn, departmentNameColumn,
//                                    employees_number,SectionIDColumn, sectionName);
//
//            ObservableList<Department> departmentData = retrieveDataFromDatabase(connection);
//            tableView.setItems(departmentData);
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        
//        }
//            
//    }
//    
    
    
    
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
