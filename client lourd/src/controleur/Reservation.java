package controleur;

import java.sql.Date;

public class Reservation {
	private int idreservation;
	private Date dateDebutReservation, dateFinReservation;
	private String nomClient, prenomClient, mailClient;
	private int telephoneClient, nbPersonnes;
	private int idGite;
	private int idUser;
	
	public Reservation(int idreservation, Date dateDebutReservation, Date dateFinReservation, 
			String nomClient, String prenomClient, String mailClient, int telephoneClient, int nbPersonnes,
			int idGite, int idUser) {
		super();
		this.idreservation = idreservation;
		this.dateDebutReservation = dateDebutReservation;
		this.dateFinReservation = dateFinReservation;
		this.nomClient = nomClient;
		this.prenomClient = prenomClient;
		this.mailClient = mailClient;
		this.telephoneClient = telephoneClient;
		this.nbPersonnes = nbPersonnes;
		this.idGite = idGite;
		this.idUser = idUser;
	}
	
	public Reservation(Date dateDebutReservation, Date dateFinReservation, 
			String nomClient, String prenomClient, String mailClient, int telephoneClient, int nbPersonnes,
			int idGite, int idUser) {
		super();
		this.idreservation = 0;
		this.dateDebutReservation = dateDebutReservation;
		this.dateFinReservation = dateFinReservation;
		this.nomClient = nomClient;
		this.prenomClient = prenomClient;
		this.mailClient = mailClient;
		this.telephoneClient = telephoneClient;
		this.nbPersonnes = nbPersonnes;
		this.idGite = idGite;
		this.idUser = idUser;
	}

	public int getIdreservation() {
		return idreservation;
	}

	public void setIdreservation(int idreservation) {
		this.idreservation = idreservation;
	}

	public Date getDateDebutReservation() {
		return dateDebutReservation;
	}

	public void setDateDebutReservation(Date dateDebutReservation) {
		this.dateDebutReservation = dateDebutReservation;
	}

	public Date getDateFinReservation() {
		return dateFinReservation;
	}

	public void setDateFinReservation(Date dateFinReservation) {
		this.dateFinReservation = dateFinReservation;
	}

	public String getNomClient() {
		return nomClient;
	}

	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}

	public String getPrenomClient() {
		return prenomClient;
	}

	public void setPrenomClient(String prenomClient) {
		this.prenomClient = prenomClient;
	}

	public String getMailClient() {
		return mailClient;
	}

	public void setMailClient(String mailClient) {
		this.mailClient = mailClient;
	}

	public int getTelephoneClient() {
		return telephoneClient;
	}

	public void setTelephoneClient(int telephoneClient) {
		this.telephoneClient = telephoneClient;
	}

	public int getNbPersonnes() {
		return nbPersonnes;
	}

	public void setNbPersonnes(int nbPersonnes) {
		this.nbPersonnes = nbPersonnes;
	}

	public int getIdGite() {
		return idGite;
	}

	public void setIdGite(int idGite) {
		this.idGite = idGite;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	
	
}
