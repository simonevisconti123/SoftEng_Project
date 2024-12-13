
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
import gruppo06.rubrica_telefonica_gluon.Model.Profilo;
import gruppo06.rubrica_telefonica_gluon.Model.Rubrica;
import java.awt.Insets;
import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anthony
 */

public class ProfileSelectionController implements Initializable{

    //ELEMENTI GRAFICI
    @FXML
    private Pane profileListPane;
    @FXML
    private Pane buttonProfilePane;
    @FXML
    private Button importaProfilo;
    @FXML
    private Button creaProfilo;
    @FXML
    private ComboBox<String> ListaProf;
    @FXML
    private Button selezionaProfiloButton;

    //ATTRIBUTI
    private final String folderDataSharing = System.getProperty("user.dir") + "/src/main/resources/DataSharing/";
    private String pathProfiloSelezionato;
    
    //METODI DI CONTROLLO
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showProfile(); // Chiamata per popolare la ComboBox all'avvio
        System.out.println("\n" + "PROFILE_SELECTION_CONTROLLER");
    }
    
    @FXML
    private void importazioneProfilo(ActionEvent event) throws FileNotFoundException, IOException {
        Stage stage = (Stage) buttonProfilePane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importazione profilo");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
         new ExtensionFilter("Text Files", "*.txt"));
        File file_scelto = fileChooser.showOpenDialog(stage);
        if(file_scelto != null){
            String st;
            try(BufferedReader br = new BufferedReader(new FileReader(file_scelto))){
                st = br.readLine();
            }
            String[] username_extracted = st.split("= ");
            
            if (username_extracted.length > 1) {
                
                String userDir = System.getProperty("user.dir");
                File cartella_destinazione = new File(userDir, "/src/main/resources/ProfiliSalvati/");
                
                if (!cartella_destinazione.exists()) {
                    cartella_destinazione.mkdirs();
                }
                
                Files.move(file_scelto.toPath(), cartella_destinazione.toPath().resolve(file_scelto.getName()), StandardCopyOption.REPLACE_EXISTING);
                
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Si è verificato un errore");
                alert.setContentText("Il file che hai scelto non ha il formato giusto");
                alert.showAndWait();
            }
        }
         ListaProf.getItems().clear();
            showProfile(); 
    }
    
    @FXML
    private void creazioneProfilo (ActionEvent event) {
    // Ottieni il riferimento alla finestra principale
    Stage parentStage = (Stage) buttonProfilePane.getScene().getWindow();

    // Crea un nuovo Alert
    Alert usernameAlert = new Alert(Alert.AlertType.NONE); // Nessun tipo predefinito
    usernameAlert.initModality(Modality.APPLICATION_MODAL);
    usernameAlert.initOwner(parentStage);
    usernameAlert.setTitle("Crea Profilo");
    usernameAlert.setHeaderText("Inserisci il nome del profilo");

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
            ListaProf.getItems().clear();
            showProfile(); 
        }
    }
    }
    
    @FXML
    public void eliminaProfilo() throws IOException{
        String sceltaUtente = ListaProf.getSelectionModel().getSelectedItem();
        Path pathProfiloSelezionato = Paths.get(System.getProperty("user.dir") + "/src/main/resources/profiliSalvati/" + sceltaUtente + ".txt");

        //messaggio errore in caso di mancata selezione
        if (sceltaUtente == null || sceltaUtente.isEmpty()) {
            System.out.println("Nessun profilo selezionato");
            ErrorAlertPage("Errore","Errore nella selezione","Devi selezionare un profilo per eliminarne uno");
            return;
        }else{
            Files.delete(pathProfiloSelezionato);
            InformationAlertPage("Conferma","Eliminazione confermata","Eliminazione avvenuta con successo");
            
            ListaProf.getItems().clear();
            showProfile();
        }
        
        
    }
    
    @FXML
    public void showProfile(){
    String userDir = System.getProperty("user.dir");
            String path=userDir+"/src/main/resources/ProfiliSalvati/";

        // Specificare la directory da cui leggere i file
        File directory = new File(path); // Sostituisci con il percorso della tua directory

        // Controllare che la directory esista ed è una cartella
        if (directory.exists() && directory.isDirectory()) {
            // Leggere i file della directory
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                      // Aggiungere solo i file, non le sottocartelle
                      String fileNameWithoutExtension = file.getName();
                        int dotIndex = fileNameWithoutExtension.lastIndexOf(".");
                        if (dotIndex > 0) {
                            fileNameWithoutExtension = fileNameWithoutExtension.substring(0, dotIndex);
                        }
                          ListaProf.getItems().add(fileNameWithoutExtension);
                    }

        // Aggiungi il nome del file senza estensione alla Combobox    
                }
            }
        } else {
                System.out.println("La directory specificata non esiste o non è una cartella.");
               }
        ListaProf.setOnAction(e -> {String selectedFile = ListaProf.getValue();});
    }

    @FXML
    public void selezionaProfilo(ActionEvent event) throws IOException {
        
        //SALVATAGGIO SCELTE UTENTE SU FILE
        // Verifica che l'utente abbia selezionato qualcosa
        String sceltaUtente = ListaProf.getSelectionModel().getSelectedItem();
        pathProfiloSelezionato = System.getProperty("user.dir") + "/src/main/resources/profiliSalvati/" + sceltaUtente + ".txt";

        //messaggio errore in caso di mancata selezione
        if (sceltaUtente == null || sceltaUtente.isEmpty()) {
            System.out.println("Nessun profilo selezionato");
            ErrorAlertPage("Errore","Errore nella selezione","Devi selezionare un profilo per procedere");

            return;
        }
        
        //messaggi sulla console
        System.out.println("Profilo selezionato: " + sceltaUtente);
        System.out.println("path del profilo selezionato: " + pathProfiloSelezionato);
        
        //SALVATAGGIO SCELTA SU FILE
        System.out.println("scrittura path del profilo selezionato sul file _profileFileSelection.txt_");
        DataShare("profileSelectionFile", pathProfiloSelezionato, "Path del profilo selezionato: ");
        
        //PASSAGGIO ALLA PROSSIMA VIEW
        System.out.println("->" + "Passaggio controlli a dashboardController per inizializzazione");
        MainClass.setRoot("DashboardView");
        
        System.out.println("Inizializzazione del DashboardController completata");
    }
    
    //METODI UTILITIES
    public void DataShare(String fileName, String dataToSave, String msg){
        //determina il filePath del DataSharingFile su cui dobbiamo scrivere
        String filePath = folderDataSharing + fileName + ".txt";
        
        // Scrivi la selezione dell'utente nel file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath), true))) {
            writer.write(msg + dataToSave);
            writer.newLine(); // Aggiunge una nuova linea
            System.out.println("Dato aggiunto con successo in: " + filePath);
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio del dato: " + e.getMessage());
        }
    }
    
    public static void ErrorAlertPage(String Title, String Header, String Content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(Title);
        alert.setHeaderText(Header);
        alert.setContentText(Content);
        alert.showAndWait();
    }
    
    public static void InformationAlertPage(String Title, String Header, String Content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Title);
        alert.setHeaderText(Header);
        alert.setContentText(Content);
        alert.showAndWait();
    }

}                                                             

