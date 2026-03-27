package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class bdd {
	private String serveur, bdd, user, mdp;
	private Connection maConnexion;

	public bdd(String serveur, String bdd, String user, String mdp) {
		this.serveur = serveur;
		this.bdd = bdd;
		this.user = user;
		this.mdp = mdp;
		this.maConnexion = null;
	}
	
	public void chargerPilote() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException exp) {
			System.out.println("Absence du prilote JDBC");
		}
	}
	public void seConnecter() {
	    this.chargerPilote();
	    String url = "jdbc:mysql://"+this.serveur+":3308/"+this.bdd+"?useSSL=false&serverTimezone=Europe/Paris&allowPublicKeyRetrieval=true";
	    try {
	        System.out.println("Tentative de connexion à : " + url);
	        System.out.println("User : " + this.user);
	        this.maConnexion = DriverManager.getConnection(url, this.user, this.mdp);
	        System.out.println("Connexion réussie !");
	        
	    }catch(SQLException exp) {
	        System.out.println("Impossible de se connecter à : " + url);
	        System.out.println("Détails de l'erreur : " + exp.getMessage());
	        exp.printStackTrace();
	    }
	}
	public void seDeconnecter() {
		
		try {
			if (this.maConnexion != null) {
				this.maConnexion.close();
			}
		}catch (SQLException exp) {
			System.out.println("Impossible de ferme la connexion à la bdd");
		}
		
	}
	public Connection getMacConnection() {
		return this.maConnexion;
	}
	
	
		
}
