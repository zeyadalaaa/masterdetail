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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
    private void addSection() {
        
        String sectionName = sectionNameText.getText();
        
        try {
            ConnectDB connection = new ConnectDB();
            
            String STP = "CALL addSection(?)";
            Connection connection1 =connection.ConnectToDatabase();
            CallableStatement statement = null;
            connection.addSection(connection1,statement,STP, sectionName);
            
            connection1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * Initializes the controller class.
     */
    
    
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
