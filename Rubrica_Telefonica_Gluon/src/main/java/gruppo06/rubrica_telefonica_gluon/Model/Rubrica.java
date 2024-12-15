package gruppo06.rubrica_telefonica_gluon.Model;

import gruppo06.rubrica_telefonica_gluon.*;
import java.util.TreeMap;


/**
 * @file Rubrica.java
 * @brief Classe rubrica
 * 
 * Il file in questione contiene le informazioni legate alla rubrica, entità collegata al profilo
 * 
 * @author Simone Visconti
 */

public class Rubrica {

    private TreeMap<String, Contatto> listaContatti= new TreeMap<>();

   

    /**
     * @brief Costruttore della rubrica
     * 
     * Il costruttore della rubrica, essenziale per istanziare la rubrica
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
public Contatto RicercaContatto(String nome_cognome) {
       return this.listaContatti.get(nome_cognome);
        
    }
public void aggiuntaContatto(Contatto c){
    listaContatti.put(c.getNome()+" "+c.getCognome(), c);
}

    @Override
    public String toString() {
        return "Rubrica{" + "listaContatti=" + listaContatti + '}';
    }
}    