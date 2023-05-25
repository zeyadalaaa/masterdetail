/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package masterdetail;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Zeyad.Alaa
 */
public class MasterDetail extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

            Scene scene = new Scene(    root);

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

////
//public class AddRecordExample {
//   // JDBC driver name and database URL
//   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
//   static final String DB_URL = "jdbc:mysql://localhost/mydatabase";
//
//   // Database credentials
//   static final String USER = "username";
//   static final String PASS = "password";
//   
//   public static void main(String[] args) {
//      Connection conn = null;
//      PreparedStatement stmt = null;
//      try{
//         Class.forName(JDBC_DRIVER);
//         System.out.println("Connecting to database...");
//         conn = DriverManager.getConnection(DB_URL,USER,PASS);
//
//         // Prepare a statement to insert a record
//         System.out.println("Creating statement...");
//         String sql = "INSERT INTO Employees (id, first_name, last_name, age) " +
//                      "VALUES (?, ?, ?, ?)";
//         stmt = conn.prepareStatement(sql);
//
//         // Set the values for the statement
//         stmt.setInt(1, 1);
//         stmt.setString(2, "John");
//         stmt.setString(3, "Doe");
//         stmt.setInt(4, 25);
//
//         // Execute the statement
//         stmt.executeUpdate();
//         System.out.println("Record added successfully...");
//
//         // Clean up
//         stmt.close();
//         conn.close();
//      } catch(SQLException se) {
//         //Handle errors for JDBC
//         se.printStackTrace();
//      } catch(Exception e) {
//         e.printStackTrace();
//      } finally {
//         try{
//            if(stmt!=null)
//               stmt.close();
//         } catch(SQLException se2) {
//         } try {
//            if(conn!=null)
//               conn.close();
//         } catch(SQLException se){
//            se.printStackTrace();
//         }
//      }
//   }
//}