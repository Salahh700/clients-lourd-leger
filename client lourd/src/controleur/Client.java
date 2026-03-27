package controleur;
 
public class Client {
	private int idclient;
	private String nom, prenom, adresse, email, statut, mdp;
	
	
	// constructeur : lecteur de la BDD
	public Client(int idclient, String nom, String prenom, String adresse, String email, String statut, String mdp) {
		this.idclient = idclient;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.email = email;
		this.statut = statut;
		this.mdp = mdp;
	}
	
	// constructeur : lecteur du formulaire de saisie (pas d'id dans le form, il s'ajoute seul dans la BDD)
	public Client(String nom, String prenom, String adresse, String email, String statut, String mdp) {
		this.idclient = 0;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.email = email;
		this.statut = statut;
		this.mdp = mdp;
	}
 
	public int getIdclient() {
		return idclient;
	}
 
	public void setIdclient(int idclient) {
		this.idclient = idclient;
	}
 
	public String getNom() {
		return nom;
	}
 
	public void setNom(String nom) {
		this.nom = nom;
	}
 
	public String getPrenom() {
		return prenom;
	}
 
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
 
	public String getAdresse() {
		return adresse;
	}
 
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
 
	public String getEmail() {
		return email;
	}
 
	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatut() {
		return statut;
	}
	
	public void setStatut(String statut) {
		this.statut = statut;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	
}