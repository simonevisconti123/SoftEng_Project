/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gruppo06.rubrica_telefonica_gluon.Utilities.Controller;

import gruppo06.rubrica_telefonica_gluon.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

/**
 * @file ShowContactController.java
 * @brief Controller della visualizzazione contatti
 *
 * Il file in questione contiene il controller per la funzionalità di visualizzazione dei contatti / singolo contatto
 * 
 * @author SimoneVisconti
 */

public class ShowContactController {
    
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
