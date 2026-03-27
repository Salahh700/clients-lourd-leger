package controleur;

public class Gite {
	private int idgite;
	private String nomGite, adresseGite, villeGite, descriptionGite;
	private int codePostalGite, capaciteGite;
	private double prixNuitGite;
	private boolean disponibiliteGite;
	private int idUser;
	
	public Gite(int idgite, String nomGite, String adresseGite, String villeGite, int codePostalGite, 
			String descriptionGite, int capaciteGite, double prixNuitGite, boolean disponibiliteGite, int idUser) {
		super();
		this.idgite = idgite;
		this.nomGite = nomGite;
		this.adresseGite = adresseGite;
		this.villeGite = villeGite;
		this.codePostalGite = codePostalGite;
		this.descriptionGite = descriptionGite;
		this.capaciteGite = capaciteGite;
		this.prixNuitGite = prixNuitGite;
		this.disponibiliteGite = disponibiliteGite;
		this.idUser = idUser;
	}
	
	public Gite(String nomGite, String adresseGite, String villeGite, int codePostalGite, 
			String descriptionGite, int capaciteGite, double prixNuitGite, boolean disponibiliteGite, int idUser) {
		super();
		this.idgite = 0;
		this.nomGite = nomGite;
		this.adresseGite = adresseGite;
		this.villeGite = villeGite;
		this.codePostalGite = codePostalGite;
		this.descriptionGite = descriptionGite;
		this.capaciteGite = capaciteGite;
		this.prixNuitGite = prixNuitGite;
		this.disponibiliteGite = disponibiliteGite;
		this.idUser = idUser;
	}

	public int getIdgite() {
		return idgite;
	}

	public void setIdgite(int idgite) {
		this.idgite = idgite;
	}

	public String getNomGite() {
		return nomGite;
	}

	public void setNomGite(String nomGite) {
		this.nomGite = nomGite;
	}

	public String getAdresseGite() {
		return adresseGite;
	}

	public void setAdresseGite(String adresseGite) {
		this.adresseGite = adresseGite;
	}

	public String getVilleGite() {
		return villeGite;
	}

	public void setVilleGite(String villeGite) {
		this.villeGite = villeGite;
	}

	public int getCodePostalGite() {
		return codePostalGite;
	}

	public void setCodePostalGite(int codePostalGite) {
		this.codePostalGite = codePostalGite;
	}

	public String getDescriptionGite() {
		return descriptionGite;
	}

	public void setDescriptionGite(String descriptionGite) {
		this.descriptionGite = descriptionGite;
	}

	public int getCapaciteGite() {
		return capaciteGite;
	}

	public void setCapaciteGite(int capaciteGite) {
		this.capaciteGite = capaciteGite;
	}

	public double getPrixNuitGite() {
		return prixNuitGite;
	}

	public void setPrixNuitGite(double prixNuitGite) {
		this.prixNuitGite = prixNuitGite;
	}

	public boolean isDisponibiliteGite() {
		return disponibiliteGite;
	}

	public void setDisponibiliteGite(boolean disponibiliteGite) {
		this.disponibiliteGite = disponibiliteGite;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	
	
}
