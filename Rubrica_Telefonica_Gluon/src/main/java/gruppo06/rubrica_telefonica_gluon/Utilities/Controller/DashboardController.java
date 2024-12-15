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
import java.nio.file.Paths;
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
import javafx.scene.layout.HBox;
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
        Table.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2) {  // Se il doppio click è avvenuto
            Contatto selectedContact = Table.getSelectionModel().getSelectedItem();
            if (selectedContact != null) {
                mostraDettagliContatto(selectedContact);  // Mostra i dettagli del contatto
            }
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
    private void aggiungiContatto(ActionEvent event) throws IOException {
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
            
            String telefono1 = telefono1Field.getText().replaceAll("\\s+", ""); //rimuove gli spazi 
           
            String telefono2 = telefono2Field.getText().trim().isEmpty() ? null : telefono2Field.getText().replaceAll("\\s+", "");
            String telefono3 = telefono3Field.getText().trim().isEmpty() ? null : telefono3Field.getText().replaceAll("\\s+", "");

            String email1 = email1Field.getText().trim().isEmpty() ? null : email1Field.getText().replaceAll("\\s+", "");
            String email2 = email2Field.getText().trim().isEmpty() ? null : email2Field.getText().replaceAll("\\s+", "");
            String email3 = email3Field.getText().trim().isEmpty() ? null : email3Field.getText().replaceAll("\\s+", "");

            String etichetta = etichettaField.getText().trim().isEmpty() ? null : etichettaField.getText().trim();


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
    
    @FXML
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
    
    @FXML
    private void mostraDettagliContatto(Contatto contatto) {
    if (contatto == null) return;

    // Crea un nuovo Stage per visualizzare i dettagli del contatto
    Stage stage = new Stage();
    stage.setTitle("Dettagli Contatto");
    stage.setWidth(410);  // impostazione larghezza
    stage.setHeight(550); // impostazione altezza
    
    VBox layout = new VBox(15);
    layout.setPadding(new Insets(15));

    // Etichetta del Nome
    HBox nomeLayout = creaCampo("Nome", contatto.getNome());
    nomeLayout.setAlignment(Pos.CENTER); // Allineamento centrale
    HBox cognomeLayout = creaCampo("Cognome", contatto.getCognome());
    cognomeLayout.setAlignment(Pos.CENTER); // Allineamento centrale

    // Mostra i numeri di telefono uno sotto l'altro
    VBox telefonoBox = new VBox(5);
    telefonoBox.setAlignment(Pos.CENTER); // Allineamento centrale
    
        // Ottieni i numeri di telefono
        List<String> numeriTelefonoList = contatto.getNumeriTelefono();
        String telefono1 = numeriTelefonoList.get(0);
        String telefono2 = numeriTelefonoList.get(1);
        String telefono3 = numeriTelefonoList.get(2);

        // Aggiungi i numeri di telefono al layout se esistono
        // Aggiungi i numeri di telefono al layout se esistono
        if (telefono1 == null)
            telefonoBox.getChildren().add(creaCampo("Telefono 1", "Non inserito"));
        else
            telefonoBox.getChildren().add(creaCampo("Telefono 1", telefono1));

        if (telefono2 == null)
            telefonoBox.getChildren().add(creaCampo("Telefono 2", "Non inserito"));
        else
            telefonoBox.getChildren().add(creaCampo("Telefono 2", telefono2));

        if (telefono3 == null)
            telefonoBox.getChildren().add(creaCampo("Telefono 3", "Non inserito"));
        else
            telefonoBox.getChildren().add(creaCampo("Telefono 3", telefono3));

        telefonoBox.setPadding(new Insets(10, 0, 0, 0)); // (top, right, bottom, left)


    // Mostra le email una sotto l'altra
    VBox emailBox = new VBox(5);
    emailBox.setAlignment(Pos.CENTER); // Allineamento centrale
    
        // Ottieni le email, se ci sono
        List<String> emailList = contatto.getEmails();
        String email1 = emailList.get(0);
        String email2 = emailList.get(1);
        String email3 = emailList.get(2);
        
        // Aggiungi le email al layout se esistono altrimenti specifica che non ci sono
        if (email1 == null)
            emailBox.getChildren().add(creaCampo("Email 1", "Non inserito"));
        else
            emailBox.getChildren().add(creaCampo("Email 1", email1));

        if (email2 == null)
            emailBox.getChildren().add(creaCampo("Email 2", "Non inserito"));
        else
            emailBox.getChildren().add(creaCampo("Email 2", email2));

        if (email3 == null)
            emailBox.getChildren().add(creaCampo("Email 3", "Non inserito"));
        else
            emailBox.getChildren().add(creaCampo("Email 3", email3));

        emailBox.setPadding(new Insets(10, 0, 0, 0)); // (top, right, bottom, left

    // Etichetta
    HBox etichettaLayout = creaCampo("Etichetta", contatto.getEtichetta());
    etichettaLayout.setAlignment(Pos.CENTER); // Allineamento centrale
    etichettaLayout.setPadding(new Insets(10, 10, 10, 10)); // (top, right, bottom, left)

    // Pulsante per la modifica
    Button modificaButton = new Button("Modifica Contatto");
    modificaButton.setOnAction(e -> {
        modificaContatto(contatto, stage); // Chiamata al metodo di modifica
        stage.close();
    });

    // Pulsante per l'eliminazione
    Button eliminaButton = new Button("Elimina Contatto");
    eliminaButton.setStyle("-fx-background-color: #ea0000; -fx-text-fill: white;");
    eliminaButton.setOnAction(e -> {
        eliminaContatto(contatto, stage);
    });

    // Creazione di un HBox per disporre i pulsanti uno accanto all'altro
    HBox buttonsLayout = new HBox(18, modificaButton, eliminaButton); // 15 è lo spazio tra i pulsanti
    buttonsLayout.setAlignment(Pos.CENTER); // Allinea i pulsanti al centro
    buttonsLayout.setPadding(new Insets(30, 30, 35, 30)); // (top, right, bottom, left)

    layout.getChildren().addAll(nomeLayout, cognomeLayout, telefonoBox, emailBox, etichettaLayout, buttonsLayout);

    Scene scene = new Scene(layout, 400, 450);
    stage.setScene(scene);
    stage.show();
}

    // Metodo helper per creare un campo con label in grassetto e textField non modificabile
    private HBox creaCampo(String labelText, String textValue) {
        Label label = new Label(labelText + ": ");
        label.setStyle("-fx-font-weight: bold;");

        TextField textField = new TextField(textValue);
        textField.setEditable(false);
        textField.setStyle("-fx-background-color: lightgray;");

        HBox layout = new HBox(10, label, textField);
        layout.setAlignment(Pos.CENTER);
        return layout;
    }

    
    
    //METODI UTILITIES
        //Metodi manipolazione file
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
                        String[] attributoContatto = line.split(",");

                        // Estrai i dati obbligatori
                        String nome = (attributoContatto[0].equals("null")) ? null : attributoContatto[0].trim();
                        String cognome = (attributoContatto[1].equals("null")) ? null : attributoContatto[1].trim();

                        // Gestisci il gruppo di numeri di telefono
                        List<String> phoneNumberList = new ArrayList<>();
                        String telefono1 = (attributoContatto[2].equals("null")) ? null : attributoContatto[2].trim();
                        String telefono2 = (attributoContatto[3].equals("null")) ? null : attributoContatto[3].trim();
                        String telefono3 = (attributoContatto[4].equals("null")) ? null : attributoContatto[4].trim();

                        phoneNumberList.add(telefono1);
                        phoneNumberList.add(telefono2);
                        phoneNumberList.add(telefono3);

                        // Gestisci il gruppo di email
                        List<String> emailList = new ArrayList<>();
                        String email1 = (attributoContatto[5].equals("null")) ? null : attributoContatto[5].trim();
                        String email2 = (attributoContatto[6].equals("null")) ? null : attributoContatto[6].trim();
                        String email3 = (attributoContatto[7].equals("null")) ? null : attributoContatto[7].trim();

                        emailList.add(email1);
                        emailList.add(email2);
                        emailList.add(email3);

                        // Gestisci l'etichetta (ultimo gruppo)
                        String etichetta = (attributoContatto[8].equals("null")) ? null : attributoContatto[8];
                        if(etichetta == null){
                            System.out.println("UOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                        }
                 
                        //creazione oggetto contatto
                        Contatto contatto = new Contatto (nome,cognome,phoneNumberList,emailList,etichetta);

                        // Aggiungi il contatto alla TreeMap
                        listaContatti.put(contatto.getNome() + " " + contatto.getCognome(), contatto);

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

        public void addContactToFile(String pathProfiloCaricato, Contatto contattoDaSalvare) throws IOException{
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathProfiloCaricato, true))) {
                    // Scrittura della riga sul file
                    writer.newLine();
                    writer.write(contattoDaSalvare.toStringFormatoFile());
                } catch (IOException e) {
                    System.err.println("Errore durante la scrittura del file: " + e.getMessage());
                }
                
                removeEmptyLinesFromFile(this.pathProfiloCaricato);
                this.showRubrica(rubrica);
            }

        public void eliminaContattoDaFile(String pathProfiloCaricato, Contatto contattoDaEliminare){      
            try {
                // Leggi tutte le righe del file in una lista
                File file = new File(pathProfiloCaricato);
                List<String> lines = new ArrayList<>(Files.readAllLines(file.toPath()));

                // Costruisci la stringa che rappresenta il contatto da rimuovere
                String contattoDaRimuovere = contattoDaEliminare.getNome() + "," + contattoDaEliminare.getCognome();

                // Rimuovi la riga del contatto dal file
                lines.removeIf(line -> line.startsWith(contattoDaRimuovere));

                // Scrivi di nuovo tutte le righe nel file, senza il contatto rimosso
                Files.write(file.toPath(), lines);
                
                //eliminiamo tutte le righe vuote a partire dalla riga 3 in poi
                removeEmptyLinesFromFile(this.pathProfiloCaricato);

                System.out.println("Contatto rimosso dal file.");
            } catch (IOException e) {
                System.err.println("Errore durante l'eliminazione del contatto dal file: " + e.getMessage());
            }
        }
        
        public static void removeEmptyLinesFromFile(String filePath) throws IOException {
        // Legge tutte le linee dal file
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        // Lista per le nuove linee senza vuoti a partire dalla terza riga
        List<String> updatedLines = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            // Mantiene tutte le linee prima della terza
            if (i < 2 || !lines.get(i).trim().isEmpty()) {
                updatedLines.add(lines.get(i));
            }
        }

        // Scrive le linee aggiornate nel file sovrascrivendolo
        Files.write(Paths.get(filePath), updatedLines);
    }
        
        
        
        //Metodi gestione dati
        private void modificaContatto(Contatto contatto, Stage stagePrecedente) {
            // Crea la finestra
            Stage stage = new Stage();
            stage.setTitle("Dettagli Contatto");

            // GridPane per disporre gli elementi
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(10)); // Padding interno
            grid.setAlignment(Pos.CENTER); // Allinea tutti gli elementi al centro

            stage.setWidth(410);  // impostazione larghezza
            stage.setHeight(550); // impostazione altezza

            // Crea i campi di testo con il contenuto predefinito
            TextField nomeField = new TextField(contatto.getNome());
            TextField cognomeField = new TextField(contatto.getCognome());

            // Campo per 3 numeri di telefono
            TextField telefonoField1 = new TextField(contatto.getNumeriTelefono().size() > 0 ? contatto.getNumeriTelefono().get(0) : "");
            TextField telefonoField2 = new TextField(contatto.getNumeriTelefono().size() > 1 ? contatto.getNumeriTelefono().get(1) : "");
            TextField telefonoField3 = new TextField(contatto.getNumeriTelefono().size() > 2 ? contatto.getNumeriTelefono().get(2) : "");

            // Campo per 3 email
            TextField emailField1 = new TextField(contatto.getEmails().size() > 0 ? contatto.getEmails().get(0) : "");
            TextField emailField2 = new TextField(contatto.getEmails().size() > 1 ? contatto.getEmails().get(1) : "");
            TextField emailField3 = new TextField(contatto.getEmails().size() > 2 ? contatto.getEmails().get(2) : "");

            // Etichetta
            TextField etichettaField = new TextField(contatto.getEtichetta());

            // Allinea le etichette e i campi di testo al centro
            HBox nomeBox = new HBox(new Label("Nome:"), nomeField);
            nomeBox.setAlignment(Pos.CENTER);
            HBox.setMargin(nomeBox, new Insets(10)); // Aggiungi margine

            HBox cognomeBox = new HBox(new Label("Cognome:"), cognomeField);
            cognomeBox.setAlignment(Pos.CENTER);
            HBox.setMargin(cognomeBox, new Insets(10));

            HBox telefonoBox1 = new HBox(new Label("Telefono 1:"), telefonoField1);
            telefonoBox1.setAlignment(Pos.CENTER);
            HBox.setMargin(telefonoBox1, new Insets(10));

            HBox telefonoBox2 = new HBox(new Label("Telefono 2:"), telefonoField2);
            telefonoBox2.setAlignment(Pos.CENTER);
            HBox.setMargin(telefonoBox2, new Insets(10));

            HBox telefonoBox3 = new HBox(new Label("Telefono 3:"), telefonoField3);
            telefonoBox3.setAlignment(Pos.CENTER);
            HBox.setMargin(telefonoBox3, new Insets(10));

            HBox emailBox1 = new HBox(new Label("Email 1:"), emailField1);
            emailBox1.setAlignment(Pos.CENTER);
            HBox.setMargin(emailBox1, new Insets(10));

            HBox emailBox2 = new HBox(new Label("Email 2:"), emailField2);
            emailBox2.setAlignment(Pos.CENTER);
            HBox.setMargin(emailBox2, new Insets(10));

            HBox emailBox3 = new HBox(new Label("Email 3:"), emailField3);
            emailBox3.setAlignment(Pos.CENTER);
            HBox.setMargin(emailBox3, new Insets(10));

            HBox etichettaBox = new HBox(new Label("Etichetta:"), etichettaField);
            etichettaBox.setAlignment(Pos.CENTER);
            HBox.setMargin(etichettaBox, new Insets(10));

            // Aggiungi le HBox nel GridPane
            grid.add(nomeBox, 0, 0);
            grid.add(cognomeBox, 0, 1);
            grid.add(telefonoBox1, 0, 2);
            grid.add(telefonoBox2, 0, 3);
            grid.add(telefonoBox3, 0, 4);
            grid.add(emailBox1, 0, 5);
            grid.add(emailBox2, 0, 6);
            grid.add(emailBox3, 0, 7);
            grid.add(etichettaBox, 0, 8);

            // Crea un pulsante per salvare con margine
            Button salvaButton = new Button("Salva");
            salvaButton.setPadding(new Insets(20,20,20,20));  // Padding interno al pulsante
            salvaButton.setOnAction(e -> salvaModifiche(contatto, nomeField, cognomeField, telefonoField1, telefonoField2, telefonoField3, emailField1, emailField2, emailField3, etichettaField, stage));

            // Posiziona il pulsante in basso a sinistra
            HBox salvaBox = new HBox(salvaButton);
            salvaBox.setAlignment(Pos.BOTTOM_LEFT);  // Allineamento a sinistra in basso
            HBox.setMargin(salvaBox, new Insets(20)); // Aggiungi margine al pulsante

            // Aggiungi il layout principale
            VBox layout = new VBox(10);
            layout.setPadding(new Insets(10));
            layout.getChildren().addAll(grid, salvaBox);

            Scene scene = new Scene(layout, 800, 600);
            stage.setScene(scene);
            stage.show();

        }

        private void salvaModifiche(Contatto contatto, TextField nomeField, TextField cognomeField, 
                                    TextField telefonoField1, TextField telefonoField2, TextField telefonoField3, 
                                    TextField emailField1, TextField emailField2, TextField emailField3, 
                                    TextField etichettaField, Stage stage) throws IOException {
            //MODIFICHE DEL MODEL 
                //creazione contatto temporeaneo che salva il contatto prima della modifica, esso verrà eliminato nel file e sostituito con quello modificato
                Contatto contattoTemp = contatto.clone();
                
                // Salva le modifiche nel contatto
                contatto.setNome(nomeField.getText());
                contatto.setCognome(cognomeField.getText());

                // Imposta i numeri di telefono come lista
                contatto.setNumeriTelefono(Arrays.asList(telefonoField1.getText(), telefonoField2.getText(), telefonoField3.getText()));

                // Imposta le email come lista
                contatto.setEmails(Arrays.asList(emailField1.getText(), emailField2.getText(), emailField3.getText()));

                // Imposta l'etichetta
                contatto.setEtichetta(etichettaField.getText());

                // Aggiorna la Tabella se necessario
                Table.refresh();  // Questo aggiorna la tabella con i nuovi dati

                stage.close();  // Chiudi la finestra di modifica
            
            //MODIFICHE SU FILE
               System.out.println(contattoTemp.toStringFormatoFile());
               eliminaContattoDaFile(this.pathProfiloCaricato, contattoTemp);
               addContactToFile(this.pathProfiloCaricato, contatto);
        }

        private void eliminaContatto(Contatto contatto, Stage stage) {
            if (contatto == null) return;
             
            //MODIFICA MODEL
                // Rimuovi il contatto dalla TreeMap usando la chiave del nome completo
                String key = contatto.getNome() + " " + contatto.getCognome();
                listaContatti.remove(key);
                ObservableList<Contatto> contattiList =FXCollections.observableArrayList(listaContatti.values());
                Table.setItems(contattiList);
                // Aggiorna la TableView
                Table.refresh(); // Supponiamo che tu abbia un metodo per aggiornare la tabella
                // Chiudi la finestra di dettagli
                stage.close();
            
            //MODIFICA DEL FILE
                eliminaContattoDaFile(this.pathProfiloCaricato, contatto);
        }
    
}