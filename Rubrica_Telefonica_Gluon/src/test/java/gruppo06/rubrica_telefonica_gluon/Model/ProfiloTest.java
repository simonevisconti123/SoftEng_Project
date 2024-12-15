package gruppo06.rubrica_telefonica_gluon.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.TreeMap;

public class ProfiloTest {

    private Rubrica rubrica= new Rubrica(new TreeMap<String,Contatto>());
    private Profilo profilo= new Profilo("Test Profilo", rubrica);

    @BeforeEach
    public void setUp() {
    
    }

    @Test
    public void testGetNomeProfilo() {
        // Verifica che il nome del profilo venga restituito correttamente
        String result = profilo.getNomeProfilo();
        assertEquals("Test Profilo", result, "Il nome del profilo dovrebbe essere 'Test Profilo'.");
    }

    @Test
    public void testGetRubricaAssociata() {
        // Verifica che la rubrica associata al profilo venga restituita correttamente
        Rubrica result = profilo.getRubricaAssociata();
        assertNotNull(result, "La rubrica associata al profilo non dovrebbe essere null.");
    }
}
