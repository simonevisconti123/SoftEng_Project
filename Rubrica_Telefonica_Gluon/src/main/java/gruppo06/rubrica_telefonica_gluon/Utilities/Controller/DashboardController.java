/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gruppo06.rubrica_telefonica_gluon.Utilities.Controller;


/**
 * @file DashboardController.java
 * @brief Controller della dashboard
 *
 * Il file in questione contiene il controller per la dashboard successiva alla scelta del profilo
 * 
 * @author SimoneVisconti
 */

import gruppo06.rubrica_telefonica_gluon.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Anthony
 */
public class DashboardController implements Initializable {

    @FXML
    private Button addButton;
    @FXML
    private Button exportButton;
    @FXML
    private Button changeProfileButton;
    @FXML
    private TextField searchBar;
    @FXML
    private Button searchButton;
    @FXML
    private ListView<?> contactList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    private void switchPage(ActionEvent event) throws IOException {
        MainClass.setRoot("ProfileSelectionView");
    }

    @FXML
    private void aggiungiContatto(ActionEvent event) {
    }

    @FXML
    private void esportaContatto(ActionEvent event) {
    }

    @FXML
    private void cambiaProfilo(ActionEvent event) {
    }

    @FXML
    private void ricercaContatto(ActionEvent event) {
    }
    
}
