
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
import java.awt.Insets;
import java.io.*;
import java.net.URL;
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
    private void ImportazioneProfilo(ActionEvent event) throws FileNotFoundException, IOException {
        Stage stage = (Stage) buttonProfilePane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importazione profilo");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
         new ExtensionFilter("Text Files", "*.txt"));
        File file_scelto = fileChooser.showOpenDialog(stage);
        if(file_scelto != null){
            BufferedReader br = new BufferedReader(new FileReader(file_scelto));
            String st = br.readLine();
            String[] username_extracted = st.split("= ");
            
            if (username_extracted.length > 1) {
                // Da completare - Simone
                System.out.println("Il nome profilo è " + username_extracted[1]);
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Errore");
                alert.setHeaderText("Si è verificato un errore");
                alert.setContentText("Il file che hai scelto non ha il formato giusto");
                alert.showAndWait();
            }
        }
    }
    
    @FXML
    private void CreazioneProfilo (ActionEvent event) {
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
            System.out.println("Nessun username inserito.");
        } else {
            System.out.println("Username inserito: " + username);
        }
    } else {
        System.out.println("Operazione annullata.");
    }
    
    // Specifica il percorso della cartella dove vuoi salvare il file
            String folderPath = "C:/Users/Anthony/Documents/NetBeansProjects/SoftEng_Project/Rubrica_Telefonica_Gluon/src/main/resources/ProfiliSalvati"; // Personalizza questo percorso
            File folder = new File(folderPath);
            if (!folder.exists()) {
                System.out.println("La cartella non esiste");
            }
            
     // Crea un file con il nome dello username
            File file = new File(folder, username + ".txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // Scrivi la riga "username=<usernameInserito>"
                writer.write("username= " + username);
                System.out.println("File creato: " + file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Errore nella creazione del file.");
            }

    } //1)AGGIUNGERE EXCEPTION QUANDO CAMPO USERNAME LASCIATO VUOTO (attualmente crea file txt vuoto)
}     //2)AGGIUNGERE UN PATH CORRETTO ALLA FOLDER "ProfiliSalvati"                                                                
