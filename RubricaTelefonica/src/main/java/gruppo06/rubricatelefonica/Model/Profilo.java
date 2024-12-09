package gruppo06.rubricatelefonica.Model;

/**
 * @file Profilo.java
 * @brief Classe profilo
 * Il file in questione contiene le informazioni legate al profilo, entità che presenta un nome e una rubrica associata
 * 
 * @author SimoneVisconti
 */


public class Profilo {

    private String nome_Profilo;

    private Rubrica RubricaAssociata;

    
    /**
     * @brief Costruttore del programma
     * 
     * Il costruttore del programma, essenziale per istanziare il profilo
     */
    
    public Profilo(String nome_Profilo, Rubrica RubricaAssociata) {
    }

    
     /**
     * @brief Sezione dei metodi get
     * 
     * La sezione del programma che seque questo commento è composta dai metodi get dei singoli attributi
     * 
     * @return NomeProfilo e RubricaAssociata sono i valori ritornati dal programma a valle dell'esecuzione dei metodi
     */
    
    public String getNomeProfilo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Rubrica getRubricaAssociata() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    
    /**
     * @brief Metodi di profilo
     * 
     * Di seguito sono presenti gli scheletri dei metodi di profilo, descritti singolarmente
     * 
     * ImportazioneProfilo: consente di importare un profilo da un file esterno
     * SelezioneProfilo: consente di selezionare uno tra i profili disponibili
     * CreazioneProfilo: consente di creare un profilo da zero, fornendo il nome del profilo
     * EliminazioneProfilo: consente di eliminare un profilo fornendo il nome dello stesso
     */
    public void ImportazioneProfilo() {
    }

    public String SelezioneProfilo(String nome_Profilo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void CreazioneProfilo(String nome_Profilo) {
    }

    public String EliminazioneProfilo(String nome_Profilo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @brief sezione dei metodi set
     * 
     * La sezione del programma che seque questo commento è composta dai metodi set dei singoli attributi
     * 
     * @param[in] nome_Profilo è un parametro necessari al corretto funzionamento dei metodo
     */
    public String setNome_Profilo(String nome_Profilo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
