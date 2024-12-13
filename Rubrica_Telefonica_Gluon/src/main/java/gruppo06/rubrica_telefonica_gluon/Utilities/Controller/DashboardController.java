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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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

    //ELEMENTI GRAFICI
    @FXML
    private Button addButton;
    @FXML
    private Button exportButton;
    @FXML
    private Button cambiaProfiloButton;
    @FXML
    private TextField searchBar;
    @FXML
    private Button searchButton;
    @FXML
    private ListView<?> contactList;
    
    //ATTRIBUTI
    private final String folderDataSharing = System.getProperty("user.dir") + "/src/main/resources/DataSharing/";
    private final String folderProfiles = System.getProperty("user.dir") + "/src/main/resources/ProfiliSalvati/";
    private String usernameProfilo;
    private String pathProfiloCaricato;
    
    //METODI DI CONTROLLO
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        System.out.println("\nDASHBOARD_CONTROLLER");
        //LETTURA DEL PATH DEL PROFILO DAL FILE DI COMUNICAZIONE DEI CONTROLLER
        // Specifica il percorso del file da leggere, che è il file presente nella cartella resources/DataSharing/profileSelectionFile
        String profileSelectionFilePath = this.folderDataSharing + "profileSelectionFile.txt";
        
        // Leggi pathProfiloCaricato dal file
        pathProfiloCaricato = leggiPathDaFile(profileSelectionFilePath);
        
        // Stampa il nome sulla console
        if (pathProfiloCaricato != null) {
            System.out.println("Profilo letto da file: " + pathProfiloCaricato);
        } else {
            System.out.println("Nessun path trovato nel file.");
            return;
        }
        
        //CREAZIONE OGGETTI DEL MODEL RELATIVI AL PROFILO
        usernameProfilo = this.estraiUsernameProfiloDaFile(pathProfiloCaricato);
        System.out.println("USERNAME PROFILO: " + usernameProfilo);
        
        //VISUALIZZAZIONE DELLA RUBRICA RELATIVA AL PROFILO
        
        //SVUOTAMENTO FILE "profileSelectionFile"
        svuotaFile(profileSelectionFilePath);
    }
    
    @FXML
    public void cambiaProfilo(ActionEvent event) throws IOException {
        MainClass.setRoot("ProfileSelectionView");
    }

    @FXML
    private void aggiungiContatto(ActionEvent event) {
    }

    @FXML
    private void esportaContatto(ActionEvent event) {
    }

    @FXML
    private void ricercaContatto(ActionEvent event) {
    }
    
    
    //METODI UTILITIES
    /**
     * Metodo per estrarre lo username del profilo dal file.txt.
     * @param filePath Il percorso del file da cui estrarre l'username
     * @return Il nome letto dal file, o null se non trovato
     */
    private String leggiPathDaFile(String filePath) {
        File file = new File(filePath);
        StringBuilder nome = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Path del profilo selezionato: ")) {
                    nome.append(line.substring("Path del profilo selezionato: ".length()).trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file: " + e.getMessage());
        }

        return nome.length() > 0 ? nome.toString() : null;
    }
    
    /**
     * Svuota completamente il file specificato.
     * @param filePath Il percorso completo del file da svuotare.
     */
    public void svuotaFile(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Il FileWriter senza argomenti di append sovrascrive il file, quindi il file diventa vuoto.
            writer.write(""); // Scrive una stringa vuota nel file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String estraiUsernameProfiloDaFile(String profileFilePath){
        String username = null;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(profileFilePath))) {
                    String primaRiga = reader.readLine(); // Legge solo la prima riga
                    
                    // Verifica se la riga contiene il formato richiesto
                    if (primaRiga != null && primaRiga.startsWith("Username = ")) {
                    username = primaRiga.substring("Username = ".length()).trim();
                    }
        } catch (IOException e) {
        System.err.println("Errore durante la lettura del file");
        e.printStackTrace();
        }
        
        return username;
        }    
    
    public void estraiContattoDaFile(String pathProfiloCaricato, int n){
        //deve leggere le righe del file del profilo e salvarne i contatti in oggetti per poi salvare tali oggetti nella rubrica del profilo
    }
}
