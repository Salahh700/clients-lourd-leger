package controleur;

import java.sql.Date;

public class Contrat {
    private int idContrat;
    private int idReservation;
    private Date dateCreation;
    private String titre;
    private String contenu;

    public Contrat(int idContrat, int idReservation, Date dateCreation, String titre, String contenu) {
        this.idContrat = idContrat;
        this.idReservation = idReservation;
        this.dateCreation = dateCreation;
        this.titre = titre;
        this.contenu = contenu;
    }

    public Contrat(int idReservation, Date dateCreation, String titre, String contenu) {
        this.idContrat = 0;
        this.idReservation = idReservation;
        this.dateCreation = dateCreation;
        this.titre = titre;
        this.contenu = contenu;
    }

    public int getIdContrat() {
        return idContrat;
    }

    public void setIdContrat(int idContrat) {
        this.idContrat = idContrat;
    }

    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
}
