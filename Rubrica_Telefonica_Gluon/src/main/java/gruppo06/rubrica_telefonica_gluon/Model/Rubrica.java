package gruppo06.rubrica_telefonica_gluon.Model;

import gruppo06.rubrica_telefonica_gluon.*;
import java.util.TreeMap;


/**
 * @file Rubrica.java
 * @brief Classe rubrica
 * 
 * Il file in questione contiene le informazioni legate alla rubrica, entità collegata al profilo
 * 
 * @author AnthonyVita
 */

public class Rubrica {

    private TreeMap<String, Contatto> listaContatti= new TreeMap<>();

   

    /**
     * @brief Costruttore del programma
     * 
     * Il costruttore del programma, essenziale per istanziare la rubrica
     */
    
    public Rubrica(TreeMap<String,Contatto>  listaContatti) {
        this.listaContatti=listaContatti;
    
    }

    
    /**
     * @brief Sezione dei metodi get
     * 
     * La sezione del programma che seque questo commento è composta dai metodi get dei singoli attributi
     * 
     * @return ListaContatti è l'enttià ritornata dal programma all'esecuzione del metodo
     */
    
    public TreeMap<String,Contatto>  getListaContatti() {
        return this.listaContatti;
    }

    
    /**
     * @brief Metodi di rubrica
     * 
     * Di seguito sono presenti gli scheletri dei metodi di rubrica, descritti singolarmente
     * 
     * AggiuntaContatto: consente di aggiungere un contatto alla rubrica, fornendo il relativo contatto
     * SelezioneProfilo: consente di eliminare un contatto dalla rubrica, fornendo il relativo contatto
     * RicercaContatto: consente di ricercare un contatto, fornendo nome o cognome
     * ModificaContatto: consente di modificare un contatto, fornendo le informazioni necessarie
     * VisualizzaSingoloContatto: consente di visualizzare i dettagli del singolo contatto
     * VisualizzaContatti: consente di visualizzare tutti i contatti presenti in rubrica
     */
    
    public void AggiuntaContatto(Contatto contatto_da_aggiungere) {
    }

    public void EliminazioneContatto(Contatto contatto_da_eliminare) {
    }

    public Contatto RicercaContatto(String nome_cognome) {
       return this.listaContatti.get(nome_cognome);
        
    }

    public void ModificaContatto(String Nome, String Cognome, String[] NumeriTelefono, String[] Emails, String Etichetta) {
    }

    public void VisualizzaSingoloContatto(Contatto contatto_da_visualizzare) {
    }

    public void VisualizzaContatti(TreeMap<String,Contatto>  ListaContatti) {
    }

    @Override
    public String toString() {
        return "Rubrica{" + "listaContatti=" + listaContatti + '}';
    }
    
}