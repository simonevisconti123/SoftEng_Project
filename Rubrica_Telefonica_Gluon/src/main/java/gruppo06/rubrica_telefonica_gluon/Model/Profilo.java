package gruppo06.rubrica_telefonica_gluon.Model;

import gruppo06.rubrica_telefonica_gluon.*;

/**
 * @file Profilo.java
 * @brief Classe profilo
 * 
 * Il file in questione contiene le informazioni legate al profilo, entità che presenta un nome e una rubrica associata
 * 
 * @author Simone Visconti
 */


public class Profilo {

    private String nome_Profilo;

    private Rubrica RubricaAssociata;

    
    /**
     * @brief Costruttore del Profilo
     * 
     * Il costruttore del Profilo, essenziale per istanziare il profilo
     */
    
    public Profilo(String nome_Profilo, Rubrica RubricaAssociata) {
        this.nome_Profilo=nome_Profilo;
        this.RubricaAssociata=RubricaAssociata;
    }

    
     /**
     * @brief Sezione dei metodi get
     * 
     * La sezione del programma che seque questo commento è composta dai metodi get dei singoli attributi
     * 
     * @return NomeProfilo e RubricaAssociata sono i valori ritornati dal programma a valle dell'esecuzione dei metodi
     */
    
    public String getNomeProfilo() {
        return this.nome_Profilo;
    }

    public Rubrica getRubricaAssociata() {
        return this.RubricaAssociata;
    }
    
    
    
    /**
     * @brief Sezione dei metodi set
     * 
     * La sezione del programma che seque questo commento è composta dai metodi set dei singoli attributi
     * 
     * @param[in] nome_Profilo e RubricaAssociata sono tutti parametri necessari al corretto funzionamento del Profilo
     */
    
    public void setNome_Profilo(String nome_Profilo) {
        this.nome_Profilo = nome_Profilo;
    }

    public void setRubricaAssociata(Rubrica RubricaAssociata) {
        this.RubricaAssociata = RubricaAssociata;
    }
}
