package gruppo06.rubrica_telefonica_gluon;


/**
 * @file Contatto.java
 * @brief Classe contatto
 * 
 * Sono attualmente presenti gli attrubuti nome, cognome, da 1 a 3 numeri di telefono, da 1 a 3 email e un'etichetta
 * 
 * @author AnthonyVita
 */

public class Contatto {

    private String Nome;

    private String Cognome;

    private String[] NumeriTelefono;

    private String[] Emails;

    private String Etichetta;

    
    
    /**
     * @brief Costruttore del programma
     * 
     * Il costruttore del programma, essenziale per istanziare il singolo contatto
     */
    
    public Contatto(String Nome, String Cognome, String[] NumeriTelefono, String[] Emails, String Etichetta) {
        this.Nome = Nome;
        this.Cognome = Cognome;
        this.NumeriTelefono = NumeriTelefono;
        this.Emails = Emails;
        this.Etichetta = Etichetta;
    }

    /**
     * @brief Sezione dei metodi get
     * 
     * La sezione del programma che seque questo commento è composta dai metodi get dei singoli attributi
     * 
     * @return Nome, Cognome, NumeriTelefono, Emails, Etichetta sono i valori ritornati dal programma a valle dell'esecuzione dei metodi
     */
    
    
    public String getNome() {
        return Nome;
    }

    public String getCognome() {
        return Cognome;
    }

    public String[] getNumeriTelefono() {
        return NumeriTelefono;
    }

    public String[] getEmails() {
        return Emails;
    }

    public String getEtichetta() {
        return Etichetta;
    }

    
    /**
     * @brief Sezione dei metodi set
     * 
     * La sezione del programma che seque questo commento è composta dai metodi set dei singoli attributi
     * 
     * @param[in] Nome, Cognome, NumeriTelefono, Emails, Etichetta sono tutti parametri necessari al corretto funzionamento dei metodi
     */
    
    
    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public void setCognome(String Cognome) {
        this.Cognome = Cognome;
    }

    public void setNumeriTelefono(String[] NumeriTelefono) {
        this.NumeriTelefono = NumeriTelefono;
    }

    public void setEmails(String[] Emails) {
        this.Emails = Emails;
    }

    public void setEtichetta(String Etichetta) {
        this.Etichetta = Etichetta;
    }
}

