package gruppo06.rubrica_telefonica_gluon.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.TreeMap;

public class RubricaTest {

    private Rubrica rubrica=new Rubrica(new TreeMap<>());;
    private Contatto contatto1= new Contatto("John", "Doe", "123456789", "john.doe@example.com", "", "", "", "", "");
    private Contatto contatto2= new Contatto("Jane", "Smith", "987654321", "jane.smith@example.com", "", "", "", "", "");

    @BeforeEach
    public void setUp() {
        // Inizializziamo la rubrica e i contatti
        rubrica = new Rubrica(new TreeMap<>());
        contatto1 = new Contatto("John", "Doe", "123456789", "john.doe@example.com", "", "", "", "", "");
        contatto2 = new Contatto("Jane", "Smith", "987654321", "jane.smith@example.com", "", "", "", "", "");
    }

    @Test
    public void testRicercaContatto() {
        rubrica.aggiuntaContatto(contatto1);
        rubrica.getListaContatti().put(contatto1.getNome()+" "+contatto1.getCognome(), contatto1);// Aggiungiamo il contatto1
        Contatto result = rubrica.RicercaContatto("John Doe");
        assertNotNull(result, "Dovremmo trovare il contatto John.");
        assertEquals(contatto1, result, "Il contatto trovato dovrebbe essere John Doe.");
    }

    @Test
    public void testRicercaContattoNonTrovato() {
        rubrica.aggiuntaContatto(contatto1);  // Aggiungiamo il contatto1
        Contatto result = rubrica.RicercaContatto("NonEsistente");
        assertNull(result, "Se cerchiamo un contatto che non esiste, il risultato dovrebbe essere null.");
    }
}
