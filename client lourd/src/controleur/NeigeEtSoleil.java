package controleur;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import vue.VueConnexion;
import vue.VueGeneral;

public class NeigeEtSoleil {

	private static VueConnexion uneVueConnexion;
	private static VueGeneral uneVueGeneral;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// Forcer les couleurs des tableaux pour éviter texte blanc sur fond blanc
			UIManager.put("TableHeader.background", new java.awt.Color(52, 73, 94));
			UIManager.put("TableHeader.foreground", new java.awt.Color(248, 252, 255));
			UIManager.put("Table.foreground", new java.awt.Color(44, 62, 80));
			UIManager.put("Table.background", new java.awt.Color(248, 252, 255));
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// Utiliser le look par défaut en cas d'erreur
		}
		uneVueConnexion = new VueConnexion();
	}
	
	public static void rendreVisibleVueConnexion (boolean action) {
		uneVueConnexion.setVisible(action);
		
	}

	public static void creerDetruireVueGeneral (boolean action) {
		if (action == true) {
			uneVueGeneral = new VueGeneral();
		}else {
			uneVueGeneral.dispose();
		}
	}
	

}
