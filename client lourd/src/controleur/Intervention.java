package controleur;

public class Intervention {
	private int idinter;
	private String panne, datedepot, prix;
	public Intervention(int idinter, String panne, String datedepot, String prix) {
		super();
		this.idinter = idinter;
		this.panne = panne;
		this.datedepot = datedepot;
		this.prix = prix;
	}
	
	public Intervention(String panne, String datedepot, String prix) {
		super();
		this.idinter = 0;
		this.panne = panne;
		this.datedepot = datedepot;
		this.prix = prix;
	}

	public int getIdinter() {
		return idinter;
	}

	public void setIdinter(int idinter) {
		this.idinter = idinter;
	}

	public String getPanne() {
		return panne;
	}

	public void setPanne(String panne) {
		this.panne = panne;
	}

	public String getDatedepot() {
		return datedepot;
	}

	public void setDatedepot(String datedepot) {
		this.datedepot = datedepot;
	}

	public String getPrix() {
		return prix;
	}

	public void setPrix(String prix) {
		this.prix = prix;
	}
	
	
	
	
}
