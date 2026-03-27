package controleur;

public class Objet {
	private int idobjet;
	private String designation, dataAchat, etat;
	public Objet(String etat, String designation, String dataAchat, int idClient) {
		super();
		this.designation = designation;
		this.dataAchat = dataAchat;
		this.etat = etat;
	}
	
	public Objet(String designation, String dataAchat, String etat) {
		super();
		this.idobjet = 0;
		this.designation = designation;
		this.dataAchat = dataAchat;
		this.etat = etat;
	}

	public int getIdobjet() {
		return idobjet;
	}

	public void setIdobjet(int idobjet) {
		this.idobjet = idobjet;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDataAchat() {
		return dataAchat;
	}

	public void setDataAchat(String dataAchat) {
		this.dataAchat = dataAchat;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}
	
	
	
	
}
