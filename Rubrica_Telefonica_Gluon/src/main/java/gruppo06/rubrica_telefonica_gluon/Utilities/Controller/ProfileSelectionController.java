
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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

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
    private Button scritta;
    @FXML
    private Label prova;
    @FXML
    private Button crea;
    @FXML
    private Label doppiaprova;

    @FXML
    private void TestScritta(ActionEvent event){
      prova.setText("ciao a tutti");
        
    }

    @FXML
    private void ImpostaEta(ActionEvent event) {
        doppiaprova.setText("forse abbiamo risolto");
    }

  

    @FXML
    private void inpostaeta(ActionEvent event) {
    }
    
    
}
