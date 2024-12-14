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
import java.lang.reflect.Array;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sun.applet.Main;

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
    private TableView<Contatto> Table;
    @FXML
    private TableColumn<Contatto, String> NameCln;
    @FXML
    private TableColumn<Contatto, String> SurnameCln;
    @FXML
    private TableColumn<Contatto, String> numberCln;
    @FXML
    private TableColumn<Contatto, String> emailClm;
    @FXML
    private TableColumn<Contatto, String> EtichettaCln;
    
    //ATTRIBUTI
    private final String folderDataSharing = System.getProperty("user.dir") + "/src/main/resources/DataSharing/";
    private final String folderProfiles = System.getProperty("user.dir") + "/src/main/resources/ProfiliSalvati/";
    private String usernameProfilo;
    private String pathProfiloCaricato;
    private TreeMap<String,Contatto> listaContatti=new TreeMap<>();
    private Rubrica rubrica;
    private Contatto contatto = new Contatto(null,null,null,null,null);
private ObservableList<Contatto> contattiObservable = FXCollections.observableArrayList();

    
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
        showRubrica(rubrica);
        contattiObservable.addAll(listaContatti.values());
    Table.setItems(contattiObservable);
    Table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            mostraDettagliContatto(newValue);
        }
    });

    // Configura la barra di ricerca
 
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
    Alert contattoAlert = new Alert(Alert.AlertType.NONE); // Nessun tipo predefinito
    contattoAlert.initModality(Modality.APPLICATION_MODAL);
    contattoAlert.initOwner(parentStage);
    contattoAlert.setTitle("Aggiunta contatto");
    contattoAlert.setHeaderText("Inserisci i dati del nuovo contatto");

    // Crea il contenuto personalizzato per il popup
        GridPane content = new GridPane();
        content.setAlignment(Pos.CENTER);
        content.setHgap(10);
        content.setVgap(10);
        content.setPrefWidth(500); 

        // Etichette e campi di input
        Label nomeLabel = new Label("Nome:");
        TextField nomeField = new TextField();
        nomeField.setPromptText("Inserisci il nome");
        nomeField.setPrefWidth(300);

        Label cognomeLabel = new Label("Cognome:");
        TextField cognomeField = new TextField();
        cognomeField.setPromptText("Inserisci il cognome");
        cognomeField.setPrefWidth(300);

        Label telefono1Label = new Label("Telefono 1:");
        TextField telefono1Field = new TextField();
        telefono1Field.setPromptText("Inserisci il primo telefono");
        telefono1Field.setPrefWidth(300);

        Label telefono2Label = new Label("Telefono 2:");
        TextField telefono2Field = new TextField();
        telefono2Field.setPromptText("Inserisci il secondo telefono (opzionale)");
        telefono2Field.setPrefWidth(300);

        Label telefono3Label = new Label("Telefono 3:");
        TextField telefono3Field = new TextField();
        telefono3Field.setPromptText("Inserisci il terzo telefono (opzionale)");
        telefono3Field.setPrefWidth(300);

        Label email1Label = new Label("E-mail 1:");
        TextField email1Field = new TextField();
        email1Field.setPromptText("Inserisci la prima e-mail (opzionale)");
        email1Field.setPrefWidth(300);

        Label email2Label = new Label("E-mail 2:");
        TextField email2Field = new TextField();
        email2Field.setPromptText("Inserisci la seconda e-mail (opzionale)");
        email2Field.setPrefWidth(300);

        Label email3Label = new Label("E-mail 3:");
        TextField email3Field = new TextField();
        email3Field.setPromptText("Inserisci la terza e-mail (opzionale)");
        email3Field.setPrefWidth(300);

        Label etichettaLabel = new Label("Etichetta:");
        TextField etichettaField = new TextField();
        etichettaField.setPromptText("Inserisci un'etichetta (opzionale)");
        etichettaField.setPrefWidth(300);

        // Disposizione in griglia
        content.add(nomeLabel, 0, 0);
        content.add(nomeField, 1, 0);
        content.add(cognomeLabel, 0, 1);
        content.add(cognomeField, 1, 1);
        content.add(telefono1Label, 0, 2);
        content.add(telefono1Field, 1, 2);
        content.add(telefono2Label, 0, 3);
        content.add(telefono2Field, 1, 3);
        content.add(telefono3Label, 0, 4);
        content.add(telefono3Field, 1, 4);
        content.add(email1Label, 0, 5);
        content.add(email1Field, 1, 5);
        content.add(email2Label, 0, 6);
        content.add(email2Field, 1, 6);
        content.add(email3Label, 0, 7);
        content.add(email3Field, 1, 7);
        content.add(etichettaLabel, 0, 8);
        content.add(etichettaField, 1, 8);

        // Aggiungi il contenuto al DialogPane
        contattoAlert.getDialogPane().setContent(content);

        // Aggiungi i pulsanti OK e Annulla
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Annulla", ButtonBar.ButtonData.CANCEL_CLOSE);
        contattoAlert.getButtonTypes().addAll(okButton, cancelButton);

        // Gestisci il risultato
        Optional<ButtonType> result = contattoAlert.showAndWait();

        if (result.isPresent() && result.get() == okButton) {
            // Recupera i dati inseriti
            String nome = nomeField.getText().trim();
            String cognome = cognomeField.getText().trim();
            String telefono1 = telefono1Field.getText().trim();
            String telefono2 = telefono2Field.getText().trim();
            String telefono3 = telefono3Field.getText().trim();
            String email1 = email1Field.getText().trim();
            String email2 = email2Field.getText().trim();
            String email3 = email3Field.getText().trim();
            String etichetta = etichettaField.getText().trim();

            // Validazione dati
            if (nome.isEmpty() || cognome.isEmpty() || telefono1.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Errore di validazione");
                alert.setHeaderText("Campi obbligatori mancanti");
                alert.setContentText("Nome,Cognome e Telefono 1 sono obbligatori");
                alert.showAndWait();
            } else {
                //salvataggio dei dati o altre azioni necessarie
                //-creazione oggetto Contatto
                contatto = new Contatto (nome, cognome, Arrays.asList(telefono1, telefono2, telefono3), Arrays.asList(email1, email2, email3), etichetta);
                System.out.println(contatto.getNome());
                //-aggiunta contatto alla collection di contatti
                listaContatti.put(nome + " " + cognome, contatto);
                //aggiunta contatto su file di salvataggio del profilo
                addContactToFile(this.pathProfiloCaricato, contatto);
                //-messaggio su console
                System.out.println("Contatto aggiunto alla collection e al file: " + nome + " " + cognome);
            }
        }
    }

    @FXML
    private void esportaRubrica(ActionEvent event) throws IOException{
        System.out.println("Questo è il path" + pathProfiloCaricato);
        Stage stage = (Stage) exportButton.getScene().getWindow();
        FileChooser salva_file = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("File testuali (.txt)", ".txt");
        salva_file.setInitialDirectory(new File(System.getProperty("user.home")));
        salva_file.getExtensionFilters().add(filter);
        salva_file.setTitle("Esportazione rubrica");
        File file = new File(pathProfiloCaricato);
        salva_file.setInitialFileName(file.getName());
        File file_scelto = salva_file.showSaveDialog(null);
        if(file_scelto == null){
           ProfileSelectionController.WarningAlertPage("Annullamento", "Operazione annullata", "Hai annullato l'operazione");
           return;
        }else{
        Files.copy(file.toPath(), file_scelto.toPath(),StandardCopyOption.REPLACE_EXISTING);
        ProfileSelectionController.InformationAlertPage("Successo", "Rubrica esportata", "La rubrica è stata esportata correttamente");
        }
    }

    @FXML
    private void ricercaContatto(ActionEvent event) {
       if (searchBar == null) {
        contattiObservable.setAll(listaContatti.values());
    } else {
        String filtroLower = searchBar.getText();

        Contatto contattoEsatto=rubrica.RicercaContatto(searchBar.getText());
        if (contattoEsatto != null) {
            contattiObservable.setAll(contattoEsatto);
        } else {
            List<Contatto> contattiFiltrati = listaContatti.values().stream()
                .filter(contatto -> contatto.getNome().toLowerCase().contains(filtroLower) ||
                                    contatto.getCognome().toLowerCase().contains(filtroLower))
                .collect(Collectors.toList());
            contattiObservable.setAll(contattiFiltrati);
        }
    }
}
    
    
    public void showRubrica(Rubrica ru){
        NameCln.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
    SurnameCln.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCognome()));
    numberCln.setCellValueFactory(cellData -> new SimpleStringProperty(
            String.join("  ", cellData.getValue().getNumeriTelefono()))); // Unisci numeri di telefono
    emailClm.setCellValueFactory(cellData -> new SimpleStringProperty(
            String.join("   ", cellData.getValue().getEmails()))); // Unisci email
   EtichettaCln.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEtichetta()));
   ObservableList<Contatto> contattiList = FXCollections.observableArrayList(ru.getListaContatti().values());
    Table.setItems(contattiList);
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
                 /*   String nome = dati[0].trim();
                    String cognome = dati[1].trim();
                    String telefono = dati[2].trim();
                    String email = dati[3].trim();
                    String etichetta = dati[4].trim();
 
                    // Crea un oggetto Contatto
                   contatto = new Contatto(null,null,null,null,null);
                   contatto.setNome(nome);
                   contatto.setCognome(cognome);
                   contatto.setNumeriTelefono(Arrays.asList(telefono));
                   contatto.setEmails(Arrays.asList(email));
                   contatto.setEtichetta(etichetta);
                  
                      
                    // Crea un oggetto Contatto
                   listaContatti.put(contatto.getNome()+" "+contatto.getCognome(), contatto);
                    */
                 String nome = (dati.length > 0 && !dati[0].trim().isEmpty()) ? dati[0].trim() : null;
                String cognome = (dati.length > 1 && !dati[1].trim().isEmpty()) ? dati[1].trim() : null;
                String telefono = (dati.length > 2 && !dati[2].trim().isEmpty()) ? dati[2].trim() : null;
                String email = (dati.length > 3 && !dati[3].trim().isEmpty()) ? dati[3].trim() : null;
                String etichetta = (dati.length > 4 && !dati[4].trim().isEmpty()) ? dati[4].trim() : null;

                // Crea un oggetto Contatto anche se alcuni campi sono mancanti
                Contatto contatto = new Contatto(
                    nome != null ? nome : "Nome non disponibile",    // Imposta un nome di default
                    cognome != null ? cognome : "Cognome non disponibile",  // Imposta un cognome di default
                    telefono != null ? Arrays.asList(telefono) : new ArrayList<>(),  // Usa una lista vuota se non c'è telefono
                    email != null ? Arrays.asList(email) : new ArrayList<>(),  // Usa una lista vuota se non c'è email
                    etichetta != null ? etichetta : " "  // Imposta un'etichetta di default
                );
                
                // Aggiungi il contatto alla TreeMap
                listaContatti.put((nome != null ? nome : "Nome non disponibile") + " " + (cognome != null ? cognome : "Cognome non disponibile"), contatto);
            
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
    
    public void addContactToFile(String pathProfiloCaricato, Contatto contattoDaSalvare) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathProfiloCaricato, true))) {
            // Ottieni nome e cognome
            String nome = contattoDaSalvare.getNome();
            String cognome = contattoDaSalvare.getCognome();

            // Gestione numeri di telefono
            List<String> numeriTelefono = contattoDaSalvare.getNumeriTelefono();
            String telefono1 = (numeriTelefono.size() > 0 && numeriTelefono.get(0) != null && !numeriTelefono.get(0).isEmpty()) ? numeriTelefono.get(0) : "";
            String telefono2 = (numeriTelefono.size() > 1 && numeriTelefono.get(1) != null && !numeriTelefono.get(1).isEmpty()) ? numeriTelefono.get(1) : "";
            String telefono3 = (numeriTelefono.size() > 2 && numeriTelefono.get(2) != null && !numeriTelefono.get(2).isEmpty()) ? numeriTelefono.get(2) : "";

            // Gestione email
            List<String> emails = contattoDaSalvare.getEmails();
            String email1 = (emails.size() > 0 && emails.get(0) != null && !emails.get(0).isEmpty()) ? emails.get(0) : "";
            String email2 = (emails.size() > 1 && emails.get(1) != null && !emails.get(1).isEmpty()) ? emails.get(1) : "";
            String email3 = (emails.size() > 2 && emails.get(2) != null && !emails.get(2).isEmpty()) ? emails.get(2) : "";

            // Etichetta
            String etichetta = contattoDaSalvare.getEtichetta();

            // Costruisci la riga formattata
            StringBuilder contattoFormattato = new StringBuilder();

            // Aggiungi nome e cognome
            if (nome != null && !nome.isEmpty()) {
                contattoFormattato.append(nome);
            }
            if (cognome != null && !cognome.isEmpty()) {
                if (contattoFormattato.length() > 0) contattoFormattato.append(",");
                contattoFormattato.append(cognome);
            }

            // Aggiungi numeri di telefono
            String telefoni = telefono1;
            if (!telefono2.isEmpty()) {
                if (!telefoni.isEmpty()) telefoni += " ";
                telefoni += telefono2;
            }
            if (!telefono3.isEmpty()) {
                if (!telefoni.isEmpty()) telefoni += " ";
                telefoni += telefono3;
            }
            if (!telefoni.isEmpty()) {
                if (contattoFormattato.length() > 0) contattoFormattato.append(",");
                contattoFormattato.append(telefoni);
            }

            // Aggiungi email
            String emailsFormat = email1;
            if (!email2.isEmpty()) {
                if (!emailsFormat.isEmpty()) emailsFormat += " ";
                emailsFormat += email2;
            }
            if (!email3.isEmpty()) {
                  if (!emailsFormat.isEmpty()) emailsFormat += " ";
                emailsFormat += email3;
            }
            if (!emailsFormat.isEmpty()) {
                if (contattoFormattato.length() > 0) contattoFormattato.append(",");
                contattoFormattato.append(emailsFormat);
            }

            // Aggiungi etichetta
            if (etichetta != null && !etichetta.isEmpty()) {
                if (contattoFormattato.length() > 0) contattoFormattato.append(",");
                contattoFormattato.append(etichetta);
            }

            // Scrittura della riga sul file
            writer.newLine(); // Aggiungi una nuova riga prima di scrivere
            writer.write(contattoFormattato.toString());

        } catch (IOException e) {
            System.err.println("Errore durante la scrittura del file: " + e.getMessage());
        }
        
        this.showRubrica(rubrica);
    }
private void mostraDettagliContatto(Contatto contatto) {
     /*if (contatto == null) return;

    // Crea un nuovo Stage per visualizzare i dettagli del contatto
    Stage stage = new Stage();
    stage.setTitle("Dettagli Contatto");

    VBox layout = new VBox(10);
    layout.setPadding(new Insets(10));

    // Crea le etichette con i dati del contatto
    Label nomeLabel = new Label("Nome: " + contatto.getNome());
    Label cognomeLabel = new Label("Cognome: " + contatto.getCognome());
    Label telefonoLabel = new Label("Telefono: " + String.join(" ", contatto.getNumeriTelefono()));
    Label emailLabel = new Label("Email: " + String.join(" ", contatto.getEmails()));
    Label etichettaLabel = new Label("Etichetta: " + contatto.getEtichetta());

    // Crea il pulsante per la modifica
    Button modificaButton = new Button("Modifica Contatto");
    modificaButton.setOnAction(e -> {
        modificaContatto(contatto, stage);// Chiamata al metodo di modifica
        stage.close();;
    });

    layout.getChildren().addAll(nomeLabel, cognomeLabel, telefonoLabel, emailLabel, etichettaLabel, modificaButton);

    Scene scene = new Scene(layout, 800, 600);
    stage.setScene(scene);
    stage.show();*/
     
    if (contatto == null) return;

    // Crea un nuovo Stage per visualizzare i dettagli del contatto
    Stage stage = new Stage();
    stage.setTitle("Dettagli Contatto");

    VBox layout = new VBox(10);
    layout.setPadding(new Insets(10));

    // Etichetta del Nome
    Label nomeLabel = new Label("Nome: " + contatto.getNome());
    Label cognomeLabel = new Label("Cognome: " + contatto.getCognome());

    // Mostra i numeri di telefono uno sotto l'altro
    VBox telefonoBox = new VBox(5);
    telefonoBox.getChildren().add(new Label("Numeri di Telefono:"));
    for (String numero : contatto.getNumeriTelefono()) {
        telefonoBox.getChildren().add(new Label("- " + numero));
    }

    // Mostra le email una sotto l'altra
    VBox emailBox = new VBox(5);
    emailBox.getChildren().add(new Label("Email:"));
    for (String email : contatto.getEmails()) {
        emailBox.getChildren().add(new Label("- " + email));
    }

    // Etichetta
    Label etichettaLabel = new Label("Etichetta: " + contatto.getEtichetta());

    // Pulsante per la modifica
    Button modificaButton = new Button("Modifica Contatto");
    modificaButton.setOnAction(e -> {
        modificaContatto(contatto, stage); 
stage.close();// Chiamata al metodo di modifica
    });

    layout.getChildren().addAll(nomeLabel, cognomeLabel, telefonoBox, emailBox, etichettaLabel, modificaButton);

    Scene scene = new Scene(layout, 400, 400);
    stage.setScene(scene);
    stage.show();


}
private void modificaContatto(Contatto contatto, Stage stagePrecedente) {
   /* Stage stage = new Stage();
    stage.setTitle("Dettagli Contatto");
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(10));

    // Crea una finestra di modifica con i campi già popolati
    TextField nomeField = new TextField(contatto.getNome());
    TextField cognomeField = new TextField(contatto.getCognome());
    TextField telefonoField = new TextField(String.join(" ", contatto.getNumeriTelefono()));
    TextField telefonoField2 = new TextField(String.join(" ", contatto.getNumeriTelefono()));
    TextField telefonoField3 = new TextField(String.join(" ", contatto.getNumeriTelefono()));
    TextField emailField = new TextField(String.join(" ", contatto.getEmails()));
    TextField etichettaField = new TextField(contatto.getEtichetta());

    // Crea una finestra per l'input dei nuovi dati
    
      grid.add(new Label("Nome:"), 0, 0);
    grid.add(nomeField, 1, 0);
    grid.add(new Label("Cognome:"), 0, 1);
    grid.add(cognomeField, 1, 1);
    grid.add(new Label("Telefono:"), 0, 2);
    grid.add(telefonoField, 1, 2);
     grid.add(new Label("Telefono2:"), 0, 3);
    grid.add(telefonoField2, 1, 3);
     grid.add(new Label("Telefono3:"), 0, 4);
    grid.add(telefonoField3, 1, 4);
    grid.add(new Label("Email:"), 0, 5);
    grid.add(emailField, 1, 5);
    grid.add(new Label("Etichetta:"), 0, 6);
    grid.add(etichettaField, 1, 6);


      Button salvaButton = new Button("Salva");
    salvaButton.setOnAction(e -> salvaModifiche(contatto, nomeField, cognomeField, telefonoField, emailField, etichettaField, stage));

    // Aggiungi il listener per il tasto Enter per ogni campo di testo
    nomeField.setOnAction(e -> salvaModifiche(contatto, nomeField, cognomeField, telefonoField, emailField, etichettaField, stage));
    cognomeField.setOnAction(e -> salvaModifiche(contatto, nomeField, cognomeField, telefonoField, emailField, etichettaField, stage));
    telefonoField.setOnAction(e -> salvaModifiche(contatto, nomeField, cognomeField, telefonoField, emailField, etichettaField, stage));
        telefonoField2.setOnAction(e -> salvaModifiche(contatto, nomeField, cognomeField, telefonoField, emailField, etichettaField, stage));
            telefonoField3.setOnAction(e -> salvaModifiche(contatto, nomeField, cognomeField, telefonoField, emailField, etichettaField, stage));
    emailField.setOnAction(e -> salvaModifiche(contatto, nomeField, cognomeField, telefonoField, emailField, etichettaField, stage));
    etichettaField.setOnAction(e -> salvaModifiche(contatto, nomeField, cognomeField, telefonoField, emailField, etichettaField, stage));


        // Aggiorna la Tabella se necessario
       // Chiudi la finestra di modifica
    
VBox layout = new VBox(10);
    layout.setPadding(new Insets(10));

    layout.getChildren().addAll(grid, salvaButton);

    Scene scene = new Scene(layout, 800,600);
    stage.setScene(scene);
    stage.show();
}
private void salvaModifiche(Contatto contatto, TextField nomeField, TextField cognomeField, TextField telefonoField, TextField emailField, TextField etichettaField, Stage stage) {
    // Salva le modifiche nel contatto
    contatto.setNome(nomeField.getText());
    contatto.setCognome(cognomeField.getText());
    contatto.setNumeriTelefono(Arrays.asList(telefonoField.getText().split(" ")));
    contatto.setEmails(Arrays.asList(emailField.getText().split(" ")));
    contatto.setEtichetta(etichettaField.getText());

    // Aggiorna la Tabella se necessario
    Table.refresh();  // Questo aggiorna la tabella con i nuovi dati

    stage.close();  // Chiudi la finestra di modifica*/
      // Nuovo Stage per la modifica
    Stage stage = new Stage();
    stage.setTitle("Modifica Contatto");

    VBox layout = new VBox(10);
    layout.setPadding(new Insets(10));

    // Campi di input per modificare i dettagli
    TextField nomeField = new TextField(contatto.getNome());
    TextField cognomeField = new TextField(contatto.getCognome());

    VBox telefonoBox = new VBox(5);
    telefonoBox.getChildren().add(new Label("Numeri di Telefono:"));
    for (String numero : contatto.getNumeriTelefono()) {
        telefonoBox.getChildren().add(new TextField(numero));
    }

    VBox emailBox = new VBox(5);
    emailBox.getChildren().add(new Label("Email:"));
    for (String email : contatto.getEmails()) {
        emailBox.getChildren().add(new TextField(email));
    }

    TextField etichettaField = new TextField(contatto.getEtichetta());

    // Pulsante per salvare le modifiche
    Button salvaButton = new Button("Salva Modifiche");
    salvaButton.setOnAction(e -> {
        contatto.setNome(nomeField.getText());
        contatto.setCognome(cognomeField.getText());

        // Aggiorna numeri di telefono
        List<String> nuoviNumeri = telefonoBox.getChildren().stream()
                .filter(node -> node instanceof TextField)
                .map(node -> ((TextField) node).getText())
                .collect(Collectors.toList());
        contatto.setNumeriTelefono(nuoviNumeri);

        // Aggiorna email
        List<String> nuoveEmail = emailBox.getChildren().stream()
                .filter(node -> node instanceof TextField)
                .map(node -> ((TextField) node).getText())
                .collect(Collectors.toList());
        contatto.setEmails(nuoveEmail);

        contatto.setEtichetta(etichettaField.getText());

        stage.close();
       
    });

    layout.getChildren().addAll(
        new Label("Modifica Contatto:"),
        nomeField,
        cognomeField,
        telefonoBox,
        emailBox,
        etichettaField,
        salvaButton
    );

    Scene scene = new Scene(layout, 450, 450);
    stage.setScene(scene);
    stage.show();

}
}
