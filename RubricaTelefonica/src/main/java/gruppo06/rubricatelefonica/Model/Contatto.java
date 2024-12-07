package gruppo06.rubricatelefonica.Model;

public class Contatto {

    private String Nome;

    private String Cognome;

    private String[] NumeriTelefono;

    private String[] Emails;

    private String Etichetta;

    public Contatto(String Nome, String Cognome, String[] NumeriTelefono, String[] Emails, String Etichetta) {
        this.Nome = Nome;
        this.Cognome = Cognome;
        this.NumeriTelefono = NumeriTelefono;
        this.Emails = Emails;
        this.Etichetta = Etichetta;
    }

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
