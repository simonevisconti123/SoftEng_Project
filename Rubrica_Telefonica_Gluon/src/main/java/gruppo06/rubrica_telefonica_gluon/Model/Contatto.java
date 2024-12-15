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
 * @author Simone Visconti
 */

public class Contatto implements Cloneable{

    private String Nome;

    private String Cognome;

    private String numeroTelefono1;
    private String numeroTelefono2;
    private String numeroTelefono3;

    private String email1;
    private String email2;
    private String email3;



    private String Etichetta;

    
    
    /**
     * @brief Costruttore del contatto
     * 
     * Il costruttore del contatto, essenziale per istanziare il singolo contatto
     */
    
 public Contatto(String Nome, String Cognome, String numeroTelefono1, String numeroTelefono2, String numeroTelefono3, String email1, String email2, String email3, String Etichetta) {
        this.Nome = Nome;
        this.Cognome = Cognome;
        this.numeroTelefono1 = numeroTelefono1;
        this.numeroTelefono2 = numeroTelefono2;
        this.numeroTelefono3 = numeroTelefono3;
        this.email1 = email1;
        this.email2 = email2;
        this.email3 = email3;
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
    
    public String getNumeroTelefono1() {
        return numeroTelefono1;
    }

    public String getNumeroTelefono2() {
        return numeroTelefono2;
    }

    public String getNumeroTelefono3() {
        return numeroTelefono3;
    }

    public String getEmail1() {
        return email1;
    }

    public String getEmail2() {
        return email2;
    }

    public String getEmail3() {
        return email3;
    }

    public String getEtichetta() {
        return Etichetta;
    }

    
    /**
     * @brief Sezione dei metodi set
     * 
     * La sezione del programma che seque questo commento è composta dai metodi set dei singoli attributi
     * 
     * @param[in] Nome, Cognome, NumeriTelefono, Emails, Etichetta sono tutti parametri necessari al corretto funzionamento di Contatto
     */
    
    
    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public void setCognome(String Cognome) {
        this.Cognome = Cognome;
    }

    public void setNumeroTelefono1(String numeroTelefono1) {
        this.numeroTelefono1 = numeroTelefono1;
    }

    public void setNumeroTelefono2(String numeroTelefono2) {
        this.numeroTelefono2 = numeroTelefono2;
    }

    public void setNumeroTelefono3(String numeroTelefono3) {
        this.numeroTelefono3 = numeroTelefono3;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public void setEmail3(String email3) {
        this.email3 = email3;
    }

   

    public void setEtichetta(String Etichetta) {
        this.Etichetta = Etichetta;
    }

    @Override
    public String toString() {
        return "Contatto{" + "Nome=" + Nome + ", Cognome=" + Cognome + ", numeroTelefono1=" + numeroTelefono1 + ", numeroTelefono2=" + numeroTelefono2 + ", numeroTelefono3=" + numeroTelefono3 + ", email1=" + email1 + ", email2=" + email2 + ", email3=" + email3 + ", Etichetta=" + Etichetta + '}';
    }

   
    
    public String toStringFormatoFile(){
        String contattoFormatoFile = this.Nome + "," + this.Cognome + "," +
                this.numeroTelefono1 + "," +   this.numeroTelefono2+ "," +  this.numeroTelefono3 + ","  
                  + this.email1 + ","  + this.email2 + ","  + this.email3 + "," + 
                    this.Etichetta;
        
        return contattoFormatoFile;
    }
    
    @Override
    public Contatto clone() {
        try {
            return (Contatto) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}