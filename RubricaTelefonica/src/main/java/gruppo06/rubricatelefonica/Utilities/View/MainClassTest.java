/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gruppo06.rubricatelefonica.Utilities.View;
import gruppo06.rubricatelefonica.Utilities.Controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
        
/**
 *
 * @author Anthony
 */
public class MainClassTest extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ProfileSelectionView.fxml"));
        primaryStage.setTitle("HI");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }
    
    public static void main(String[] args){
        launch(args);
    }
}
