/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package masterdetail;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Zeyad.Alaa
 */
public class FXMLDepartmentController implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField sectionField;

    
    @FXML
    private void addDepartment() {
        String name = nameField.getText();
        String section = sectionField.getText();
        
        String sql = "INSERT INTO departments (name, section, employee_numbers) VALUES (?, ?, ?);";

        ConnectDB connection = new ConnectDB();
        try (
            Connection connection1 =connection.ConnectToDatabase();
            PreparedStatement statement = connection1.prepareStatement(sql);){
            
            statement.setString(1, name);
            statement.setString(2, section);
            statement.setInt(3, 0);
            statement.executeUpdate();
            
            statement.close();
            connection1.close();
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
