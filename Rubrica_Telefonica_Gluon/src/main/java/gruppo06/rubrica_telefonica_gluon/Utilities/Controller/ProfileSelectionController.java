
/**
 * @file ProfileSelectionController.java
 * @brief Controller della selezione del profilo
 *
 * Il file in questione contiene il controller per la funzionalità di selezione del profilo
 * 
 * @author SimoneVisconti
 */

package gruppo06.rubrica_telefonica_gluon.Utilities.Controller;

import gruppo06.rubrica_telefonica_gluon.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anthony
 */

public class ProfileSelectionController {

    @FXML
    private Pane profileListPane;
    @FXML
    private Pane buttonProfilePane;
    @FXML
    private MenuItem Profilo1;
    @FXML
    private Button importaProfilo;
    @FXML
    private Label prova;
    @FXML
    private Button creaProfilo;
    @FXML
    private Label doppiaprova;

    @FXML
    public void openPopupAction (ActionEvent event){
        Stage popUpStage = (Stage) buttonProfilePane.getScene().getWindow();
        
        Alert.AlertType type = Alert.AlertType.CONFIRMATION;
        
        Alert alert = new Alert (type, "");
        
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner (popUpStage);
        
        alert.getDialogPane().setContentText("Test completato");
        alert.getDialogPane().setHeaderText("FRANCO");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK)
        {
            System.out.println("Ok button pressed");
        }
        else if(result.get() == ButtonType.CANCEL)
        {
            System.out.println("Cancel button pressed");
        }
    }
    
}