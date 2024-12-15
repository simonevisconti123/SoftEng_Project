package gruppo06.rubrica_telefonica_gluon.Model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContattoTest {
   
    private Contatto contatto=new Contatto("","","","","","","","","");

    @BeforeEach
    public void setUp() {
        // Instantiate the Contatto object before each test
        
        System.setProperty("javafx.embed.singleThread", "true");
    }

    @AfterEach
    public void tearDown() {
        contatto = null; // Clean up after each test
    }

    @Test
    public void testGetNome() {
        String expResult = "";
        String result = contatto.getNome();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetNome() {
        String nome = "John";
        contatto.setNome(nome);
        assertEquals(nome, contatto.getNome());
        contatto.setNome("");
    }

    @Test
    public void testGetCognome() {
        String expResult = "";
        String result = contatto.getCognome();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetCognome() {
        String cognome = "Doe";
        contatto.setCognome(cognome);
        assertEquals(cognome, contatto.getCognome());
        contatto.setCognome("");
    }

    @Test
    public void testGetNumeroTelefono1() {
        String expResult = "";
        String result = contatto.getNumeroTelefono1();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetNumeroTelefono1() {
        String numeroTelefono1 = "1234567890";
        contatto.setNumeroTelefono1(numeroTelefono1);
        assertEquals(numeroTelefono1, contatto.getNumeroTelefono1());
        contatto.setNumeroTelefono1("");
    }

    @Test
    public void testGetEmail1() {
        String expResult = "";
        String result = contatto.getEmail1();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetEmail1() {
        String email1 = "john.doe@example.com";
        contatto.setEmail1(email1);
        assertEquals(email1, contatto.getEmail1());
        contatto.setEmail1("");
    }

    @Test
    public void testGetEtichetta() {
        String expResult = "";
        String result = contatto.getEtichetta();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetEtichetta() {
        String etichetta = "Friend";
        contatto.setEtichetta(etichetta);
        assertEquals(etichetta, contatto.getEtichetta());
        contatto.setEtichetta("");
    }

    @Test
    public void testToString() {
        String expResult = "Contatto{Nome=, Cognome=, numeroTelefono1=, numeroTelefono2=, numeroTelefono3=, email1=, email2=, email3=, Etichetta=}";
        String result = contatto.toString();
        assertEquals(expResult, result);
    }

    @Test
    public void testToStringFormatoFile() {
        String expResult = ",,,,,,,,";
        String result = contatto.toStringFormatoFile();
        assertEquals(expResult, result);
    }

    @Test
    public void testClone() {
        Contatto clone = contatto.clone();
        assertNotNull(clone);
        assertEquals(contatto.getNome(), clone.getNome());
    }
}
