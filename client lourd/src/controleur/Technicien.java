package controleur;

public class Technicien {
	private int idtechnicient; 
	private String nom, prenom, specialite, email, mdp, droit;
	
	public Technicien(int idtechnicient, String nom, String prenom, String specialite, String email, String mdp,
			String droit) {
		super();
		this.idtechnicient = idtechnicient;
		this.nom = nom;
		this.prenom = prenom;
		this.specialite = specialite;
		this.email = email;
		this.mdp = mdp;
		this.droit = droit;
	}
	
	public Technicien(String nom, String prenom, String specialite, String email, String mdp,
			String droit) {
		super();
		this.idtechnicient = 0;
		this.nom = nom;
		this.prenom = prenom;
		this.specialite = specialite;
		this.email = email;
		this.mdp = mdp;
		this.droit = droit;
	}

	public int getIdtechnicient() {
		return idtechnicient;
	}

	public void setIdtechnicient(int idtechnicient) {
		this.idtechnicient = idtechnicient;
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

	public String getSpecialite() {
		return specialite;
	}

	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public String getDroit() {
		return droit;
	}

	public void setDroit(String droit) {
		this.droit = droit;
	}
	
	
	
}
