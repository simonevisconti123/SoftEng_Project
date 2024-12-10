/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gruppo06.rubrica_telefonica_gluon;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
        
/**
 *
 * @author Anthony
 */
public class MainClass extends Application{

    private static Scene scene;
      @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("ProfileSelectionView"), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource("ProfileSelectionView.fxml"));
        return fxmlLoader.load();
    }

    
    public static void main(String[] args){
      launch();
    }
}

