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
import gruppo06.rubrica_telefonica_gluon.Model.Contatto;
import gruppo06.rubrica_telefonica_gluon.Model.Rubrica;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anthony
 */
public class DashboardController implements Initializable {

    //ELEMENTI GRAFICI
    @FXML
    private AnchorPane paneSinistro;
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
    private TreeMap<String,Contatto> listaContatti=new TreeMap<>();
    private Rubrica rubrica;
    private Contatto contatto = new Contatto(null,null,null,null,null);
    
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
        //estrazione username
        usernameProfilo = this.estraiUsernameProfiloDaFile(pathProfiloCaricato);
        //estrazione rubrica
        rubrica=estraiRubricaDaFile(pathProfiloCaricato);
        
        //stampe
        System.out.println("USERNAME PROFILO: " + usernameProfilo);
        System.out.println("la rubrica è: "+rubrica);
        
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
    // Ottieni il riferimento alla finestra principale
    Stage parentStage = (Stage) paneSinistro.getScene().getWindow();

    // Crea un nuovo Alert
    Alert usernameAlert = new Alert(Alert.AlertType.NONE); // Nessun tipo predefinito
    usernameAlert.initModality(Modality.APPLICATION_MODAL);
    usernameAlert.initOwner(parentStage);
    usernameAlert.setTitle("Aggiunta contatto");
    usernameAlert.setHeaderText("Inserisci i dati del nuovo contatto");

    // Crea il contenuto personalizzato per il popup
    VBox content = new VBox(10);
    content.setAlignment(Pos.CENTER);
    content.setSpacing(10);

    Label promptLabel = new Label("Nome del profilo:");
    TextField usernameField = new TextField();
    usernameField.setPromptText("Inserisci lo username");

    Label feedbackLabel = new Label();
    feedbackLabel.setStyle("-fx-text-fill: red;"); // Testo rosso per errori

    content.getChildren().addAll(promptLabel, usernameField, feedbackLabel);

    // Aggiungi il contenuto al DialogPane
    usernameAlert.getDialogPane().setContent(content);

    // Aggiungi i pulsanti OK e Annulla
    ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
    ButtonType cancelButton = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);
    usernameAlert.getButtonTypes().addAll(okButton, cancelButton);

    // Gestisci il risultato
    Optional<ButtonType> result = usernameAlert.showAndWait();
    String username = null;
    
    if (result.isPresent() && result.get() == okButton) {
        username = usernameField.getText().trim();
        if (username.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore di validazione");
            alert.setHeaderText("Campo obbligatorio");
            alert.setContentText("Il campo di testo non può essere vuoto!");
            alert.showAndWait();
        } else {
            String userDir = System.getProperty("user.dir");
            File folder = new File(userDir, "/src/main/resources/ProfiliSalvati/");
            if (!folder.exists()) {
                folder.mkdirs();
            }
                 
            File file = new File(folder, username + ".txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("Username = " + username);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Errore nella creazione del file.");
            }
        }
    }
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
    
    public Rubrica estraiRubricaDaFile(String pathProfiloCaricato){
        try (BufferedReader br = new BufferedReader(new FileReader(pathProfiloCaricato))) {
        String line;
        int i = 0;
        while((line = br.readLine())!=null) {
             i++;
            if(i>2){
           
         String[] dati = line.split(",");
       
                    // Estrai i dati del contatto
                    String nome = dati[0].trim();
                    String cognome = dati[1].trim();
                    String telefono = dati[2].trim();
                    String email = dati[3].trim();
                    String etichetta = dati[4].trim();
 
                    // Crea un oggetto Contatto
                    
                   
                   contatto.setNome(nome);
                   contatto.setCognome(cognome);
                   contatto.setNumeriTelefono(Arrays.asList(telefono));
                   contatto.setEmails(Arrays.asList(email));
                   contatto.setEtichetta(etichetta);
                  
                      
                    // Crea un oggetto Contatto
                   listaContatti.put(contatto.getNome()+" "+contatto.getCognome(), contatto);
              // Contatto trovato, esci dal ciclo
            }
        }
        
    } catch (IOException e) {
        System.err.println("Errore durante la lettura del file: " + e.getMessage());
    } catch (Exception e) {
        System.err.println("Errore durante l'elaborazione della riga: " + e.getMessage());
    }
   rubrica=new Rubrica(listaContatti);
   return rubrica;
}
    
}
