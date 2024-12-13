package gruppo06.rubrica_telefonica_gluon.Model;

import gruppo06.rubrica_telefonica_gluon.*;
import java.util.ArrayList;
import java.util.List;


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

    private List<String> NumeriTelefono;

    private List<String> Emails;

    private String Etichetta;

    
    
    /**
     * @brief Costruttore del programma
     * 
     * Il costruttore del programma, essenziale per istanziare il singolo contatto
     */
    
 

    public Contatto(String nome, String cognome, List<String> numeriTelefono, List<String> Emails, String etichetta) {
         this.Nome = Nome;
        this.Cognome = Cognome;
            this.NumeriTelefono = numeriTelefono != null ? numeriTelefono : new ArrayList<>();
    this.Emails =Emails != null ? Emails : new ArrayList<>();
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

    public List<String> getNumeriTelefono() {
        return NumeriTelefono;
    }

    public List<String> getEmails() {
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

    public void setNumeriTelefono(List<String> NumeriTelefono) {
        this.NumeriTelefono = NumeriTelefono;
    }

    public void setEmails(List<String> Emails) {
        this.Emails = Emails;
    }

    public void setEtichetta(String Etichetta) {
        this.Etichetta = Etichetta;
    }

    @Override
    public String toString() {
        return "Contatto{" + "Nome=" + this.getNome() + ", Cognome=" + this.getCognome() + ", NumeriTelefono=" + this.getNumeriTelefono() + ", Emails=" + this.getEmails() + ", Etichetta=" + this.getEtichetta() + "}\n";
    }
    
}