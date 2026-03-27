package controleur;

import javax.swing.table.AbstractTableModel;

public class Tableau extends AbstractTableModel{

	private Object [][] donnees; // matrice des données
	private String entetes[]; // entetes des colonnes 
	
	
	public Tableau(Object[][] donnees, String[] entetes) {
		super();
		this.donnees = donnees;
		this.entetes = entetes;
	}

	@Override
	public int getRowCount() {
		return this.donnees.length; // nombre de ligne 
	}

	@Override
	public int getColumnCount() {
		return this.entetes.length; // nombre de colonnes
	}

	@Override
	public Object getValueAt(int i, int j) {
		return this.donnees[i][j]; // valeur qui ce trouve sur ligne i colonne j 
	}

	@Override
	public String getColumnName(int j) {
		return this.entetes[j]; // retourne le nomd de la colonne
	}
	
	public void setDonnees(Object [][] matrice) {
		this.donnees = matrice; 
		//actualiser les données changées
		this.fireTableDataChanged();
	}
	
	
	
}
