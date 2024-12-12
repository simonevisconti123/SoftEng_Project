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
       
        //LETTURA DEL NOME DA FILE
        // Specifica il percorso del file da leggere, che è il file presente nella cartella resources/DataSharing/profileSelectionFile
        String profileSelectionFilePath = this.folderDataSharing + "profileSelectionFile";
        
        // Leggi il nome dal file
        usernameProfilo = leggiNomeDalFile(profileSelectionFilePath);
        
        // Stampa il nome sulla console
        if (usernameProfilo != null) {
            System.out.println("DashboardController-> Profilo letto da file: " + usernameProfilo);
        } else {
            System.out.println("Nessun nome trovato nel file.");
            return; //AGGIUNGERE MESSAGGIO ERRORE
        }
        
        //RICERCA DEL PROFILO
        //trova il path del file relativo al profilo cercato
        pathProfiloCaricato = profileFileSearch(this.usernameProfilo, this.folderProfiles);
        System.out.println("username_profilo=" + this.usernameProfilo + " --- path_Profilo=" + this.pathProfiloCaricato);
        
        //CREAZIONE OGGETTI DEL MODEL RELATIVI AL PROFILO
        
        //VISUALIZZAZIONE DELLA RUBRICA RELATIVA AL PROFILO
        
        //SVUOTAMENTO FILE "profileSelectionFile"
        svuotaFile(profileSelectionFilePath);
    }

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
     * Metodo per leggere il nome dal file.
     * @param filePath Il percorso del file da leggere
     * @return Il nome letto dal file, o null se non trovato
     */
    private String leggiNomeDalFile(String filePath) {
        File file = new File(filePath);
        StringBuilder nome = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Profilo selezionato: ")) {
                    nome.append(line.substring("Profilo selezionato: ".length()).trim());
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
    
             /**
     * Cerca un file nella cartella specificata che contenga lo username fornito nella prima riga.
     * @param usernameProfilo Lo username da cercare.
     * @param folder Il percorso della cartella dove effettuare la ricerca.
     * @return Il path del file che contiene lo username, oppure null se non trovato.
     */
    public String profileFileSearch(String usernameProfilo, String folder){

        File directory = new File(folder);

        // Controlla se la cartella esiste ed è una directory
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Il percorso specificato non è una cartella valida.");
            return null;
        }

        // Itera su tutti i file nella directory
        int n=0;
        for (File file : directory.listFiles()) {
            n=n+1;
            System.out.println("\nfile " + n + " ispezionato"); //messaggio console
            
            if (file.isFile()) { // Ignora eventuali sottocartelle
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String primaRiga = reader.readLine(); // Legge solo la prima riga
                    System.out.println(primaRiga);
                    
                    // Verifica se la riga contiene il formato richiesto
                    if (primaRiga != null && primaRiga.startsWith("Username = ")) {
                    String username = primaRiga.substring("Username = ".length()).trim();
                        
                        // Confronta con il nome utente ricercato
                        if (usernameProfilo.equals(username)) {
                            System.out.println("username trovato: " + username + "\n");
                            return this.folderProfiles + file.getName(); // Restituisce il nome del file trovato
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Errore durante la lettura del file: " + file.getName());
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    
    public void profileDataLoader(String pathProfiloCaricato){
        //deve leggere le righe del file del profilo e salvarne i contatti in oggetti per poi salvare tali oggetti nella rubrica del profilo
    }
}
