package modele;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import controleur.Client;
import controleur.Contrat;
import controleur.Proprietaire;
import controleur.Gite;
import controleur.Reservation;

public class modele {
	private static bdd unebdd = new bdd ("localhost", "NeigeEtSoleil", "root", "");
	
	static {
		initialiserSchema();
	}
	
	private static void initialiserSchema() {
		// Tables principales du projet NeigeEtSoleil
		executerRequete("CREATE TABLE IF NOT EXISTS Users ("
				+ "idUser INT AUTO_INCREMENT PRIMARY KEY,"
				+ "prenomUser VARCHAR(50) NOT NULL,"
				+ "nomUser VARCHAR(50) NOT NULL,"
				+ "usernameUser VARCHAR(50) NOT NULL,"
				+ "passwordUser VARCHAR(255) NOT NULL,"
				+ "mailUser VARCHAR(100) NOT NULL,"
				+ "roleUser VARCHAR(50) NOT NULL"
				+ ");");
		
		executerRequete("CREATE TABLE IF NOT EXISTS Gite ("
				+ "idGite INT AUTO_INCREMENT PRIMARY KEY,"
				+ "nomGite VARCHAR(100) NOT NULL,"
				+ "adresseGite VARCHAR(150) NOT NULL,"
				+ "villeGite VARCHAR(100) NOT NULL,"
				+ "codePostalGite INT NOT NULL,"
				+ "descriptionGite TEXT NOT NULL,"
				+ "capaciteGite INT NOT NULL,"
				+ "prixNuitGite DECIMAL(10,2) NOT NULL,"
				+ "disponibiliteGite BOOLEAN NOT NULL,"
				+ "idUser INT,"
				+ "FOREIGN KEY (idUser) REFERENCES Users(idUser)"
				+ ");");
		
		executerRequete("CREATE TABLE IF NOT EXISTS Reservation ("
				+ "idReservation INT AUTO_INCREMENT PRIMARY KEY,"
				+ "dateDebutReservation DATE NOT NULL,"
				+ "dateFinReservation DATE NOT NULL,"
				+ "nomClient VARCHAR(100) NOT NULL,"
				+ "prenomClient VARCHAR(100) NOT NULL,"
				+ "mailClient VARCHAR(100) NOT NULL,"
				+ "telephoneClient INT NOT NULL,"
				+ "nbPersonnes INT NOT NULL DEFAULT 1,"
				+ "idGite INT,"
				+ "idUser INT,"
				+ "FOREIGN KEY (idGite) REFERENCES Gite(idGite),"
				+ "FOREIGN KEY (idUser) REFERENCES Users(idUser)"
				+ ");");
		executerRequete("ALTER TABLE Reservation ADD COLUMN IF NOT EXISTS nbPersonnes INT NOT NULL DEFAULT 1;");
		
		executerRequete("CREATE TABLE IF NOT EXISTS Contrat ("
				+ "idContrat INT AUTO_INCREMENT PRIMARY KEY,"
				+ "idReservation INT NOT NULL,"
				+ "dateCreation DATE NOT NULL,"
				+ "titre VARCHAR(200) NOT NULL,"
				+ "contenu TEXT NOT NULL,"
				+ "FOREIGN KEY (idReservation) REFERENCES Reservation(idReservation) ON DELETE CASCADE"
				+ ");");
		
		// Compatibilité avec l'ancien panel client
		executerRequete("CREATE TABLE IF NOT EXISTS client ("
				+ "idclient INT AUTO_INCREMENT PRIMARY KEY,"
				+ "nom VARCHAR(50) NOT NULL,"
				+ "prenom VARCHAR(50) NOT NULL,"
				+ "adresse VARCHAR(150) NOT NULL,"
				+ "email VARCHAR(100) NOT NULL,"
				+ "statut VARCHAR(50) NOT NULL,"
				+ "mdp VARCHAR(255) NOT NULL"
				+ ");");
		executerRequete("ALTER TABLE client ADD COLUMN IF NOT EXISTS mdp VARCHAR(255) NOT NULL DEFAULT '';");
	}
	
	/**************** Requetes Propriétaires ******************/
	public static Proprietaire selectWhereProprietaire(String email, String mdp) {
		Proprietaire unProprietaire = null;
		String requete = "select * from Users where mailUser ='" + email + "' and passwordUser ='" + mdp + "' and roleUser = 'proprietaire';";
 		try {
 			unebdd.seConnecter();
 			Statement unStat = unebdd.getMacConnection().createStatement(); //preparation de la requete
			ResultSet unRes = unStat.executeQuery(requete);//fetch
				if(unRes.next()) {
					unProprietaire = new Proprietaire(unRes.getInt("idUser"), 
												  unRes.getString("nomUser"), unRes.getString("prenomUser"),
												  unRes.getString("mailUser"),
												  unRes.getString("passwordUser"), unRes.getString("roleUser"));
					
				}
 				unStat.close();
 				
 			unebdd.seDeconnecter();
 			
		}catch(SQLException exp) {
			System.out.println("Erreur execution requete : " + requete);
			System.out.println("Détails de l'erreur : " + exp.getMessage());
			exp.printStackTrace();
		}
		return unProprietaire;
	}
	
	public static void insertProprietaire(Proprietaire unProprietaire) {
		String requete = "INSERT INTO Users (prenomUser, nomUser, usernameUser, passwordUser, mailUser, roleUser) VALUES ('"
				+ unProprietaire.getPrenom() + "','" + unProprietaire.getNom() + "','" + unProprietaire.getEmail() 
				+ "','" + unProprietaire.getMdp() + "','" + unProprietaire.getEmail() + "','proprietaire');";
		executerRequete(requete);
	}
	
	public static void updateProprietaire(Proprietaire unProprietaire) {
		String requete = "UPDATE Users SET nomUser ='" + unProprietaire.getNom()
		+ "', prenomUser='" + unProprietaire.getPrenom() + "', mailUser= '" + unProprietaire.getEmail()
		+ "', passwordUser ='" + unProprietaire.getMdp() + "' WHERE idUser = "
		+ unProprietaire.getIdproprietaire() + " AND roleUser = 'proprietaire';";
		
		executerRequete(requete);
	}
	
	public static void deleteProprietaire(int idproprietaire) {
		String requete = "DELETE FROM Users WHERE idUser = " + idproprietaire + " AND roleUser = 'proprietaire';";
		executerRequete(requete);
	}
	
	
	public static ArrayList<Proprietaire> selectAllProprietaires(String filtre){
		String requete ;
		if (filtre.equals("")) {
			requete="SELECT * FROM Users WHERE roleUser = 'proprietaire';";
		}else {
			requete="SELECT * FROM Users WHERE roleUser = 'proprietaire' AND (nomUser like '%"+filtre+"%' OR prenomUser like'%"+filtre+"%' OR mailUser like '%"+filtre+"%');";
		}
		ArrayList<Proprietaire> lesProprietaires = new ArrayList<Proprietaire>();
		
		try {
			unebdd.seConnecter();
			Statement unStat = unebdd.getMacConnection().createStatement();
			ResultSet desResultats = unStat.executeQuery(requete);
			while(desResultats.next()) {
				Proprietaire unProp = new Proprietaire(
						desResultats.getInt("idUser"), desResultats.getString("nomUser"), 
						desResultats.getString("prenomUser"), 
						desResultats.getString("mailUser"), desResultats.getString("passwordUser"), 
						desResultats.getString("roleUser"));
				lesProprietaires.add(unProp);
			
			}
			unStat.close();
			unebdd.seDeconnecter();
		}catch(SQLException exp) {
			System.out.println("Erreur execution requete : "+requete);
		}
		
		return lesProprietaires;
		
	}
		

	
	public static void insertClient(Client unClient) {
		
		
		String requete = "INSERT INTO client (nom, prenom, adresse, email, statut, mdp) VALUES ('"
				+ unClient.getNom() + "','" + unClient.getPrenom() + "','" + unClient.getAdresse() + "','"
				+ unClient.getEmail() + "','" + unClient.getStatut() + "','" + unClient.getMdp() + "');";
		executerRequete(requete);
		}
	
	public static void deleteClient(int idclient) {
		String requete = "DELETE FROM client WHERE idclient = " + idclient + ";";
		executerRequete(requete);
	}
	
	public static void updateClient(Client unClient) {
		String requete = "update client set nom ='" + unClient.getNom()
		+ "', prenom='" + unClient.getPrenom() + "', adresse= '" + unClient.getAdresse()
		+ "', email ='" + unClient.getEmail() + "', statut = '" + unClient.getStatut()
		+ "', mdp = '" + unClient.getMdp() + "' where idclient = "
		+ unClient.getIdclient() 
		+ ";";
		
		executerRequete(requete);
		}
	
	
	public static ArrayList<Client> selectAllClients(String filtre){
		String requete ;
		if (filtre.equals("")) {
			requete="SELECT * FROM client;";
		}else {
			requete="SELECT * FROM client where nom like '%"+filtre+"%'or prenom like'%"+filtre+"%' or adresse like '%"+filtre+"%' or email like '%"+filtre+"%';";
		}
		ArrayList<Client> lesClients = new ArrayList<Client>();
		
		try {
			unebdd.seConnecter();
			Statement unStat = unebdd.getMacConnection().createStatement();
			ResultSet desResultats = unStat.executeQuery(requete);
			while(desResultats.next()) {
				Client unClient = new Client(
						desResultats.getInt("idclient"), desResultats.getString("nom"), 
						desResultats.getString("prenom"), desResultats.getString("adresse"), 
						desResultats.getString("email"), desResultats.getString("statut"),
						desResultats.getString("mdp"));
			lesClients.add(unClient);
			
			}
			unStat.close();
			unebdd.seDeconnecter();
		}catch(SQLException exp) {
			System.out.println("Erreur execution requete : "+requete);
		}
		
		return lesClients;
		
	}
	
	/**************** Requetes Réservations ******************/
	public static void insertReservation(Reservation uneReservation) {
		String requete = "INSERT INTO Reservation (dateDebutReservation, dateFinReservation, nomClient, prenomClient, mailClient, telephoneClient, nbPersonnes, idGite, idUser) VALUES ('"
				+ uneReservation.getDateDebutReservation() + "','" + uneReservation.getDateFinReservation() + "','"
				+ uneReservation.getNomClient() + "','" + uneReservation.getPrenomClient() + "','"
				+ uneReservation.getMailClient() + "'," + uneReservation.getTelephoneClient() + "," + uneReservation.getNbPersonnes() + ","
				+ uneReservation.getIdGite() + "," + uneReservation.getIdUser() + ");";
		executerRequete(requete);
	}

	public static void updateReservation(Reservation uneReservation) {
		String requete = "UPDATE Reservation SET dateDebutReservation='" + uneReservation.getDateDebutReservation()
				+ "', dateFinReservation='" + uneReservation.getDateFinReservation() + "', nomClient='"
				+ uneReservation.getNomClient() + "', prenomClient='" + uneReservation.getPrenomClient()
				+ "', mailClient='" + uneReservation.getMailClient() + "', telephoneClient="
				+ uneReservation.getTelephoneClient() + ", nbPersonnes=" + uneReservation.getNbPersonnes()
				+ ", idGite=" + uneReservation.getIdGite() + ", idUser="
				+ uneReservation.getIdUser() + " WHERE idReservation=" + uneReservation.getIdreservation() + ";";
		executerRequete(requete);
	}

	public static void deleteReservation(int idreservation) {
		String requete = "DELETE FROM Reservation WHERE idReservation = " + idreservation + ";";
		executerRequete(requete);
	}

	/**
	 * Vérifie si une réservation chevauche une autre pour le même gîte.
	 * @param idGite l'id du gîte
	 * @param dateDebut date de début de la nouvelle réservation
	 * @param dateFin date de fin de la nouvelle réservation
	 * @param idReservationExclure id de la réservation à exclure (0 ou -1 pour insertion)
	 * @return true si chevauchement détecté
	 */
	public static boolean reservationChevauche(int idGite, java.sql.Date dateDebut, java.sql.Date dateFin, int idReservationExclure) {
		String requete = "SELECT COUNT(*) as nb FROM Reservation WHERE idGite = " + idGite
				+ " AND idReservation != " + idReservationExclure
				+ " AND dateDebutReservation <= '" + dateFin + "'"
				+ " AND dateFinReservation >= '" + dateDebut + "';";
		try {
			unebdd.seConnecter();
			Statement unStat = unebdd.getMacConnection().createStatement();
			ResultSet res = unStat.executeQuery(requete);
			if (res.next() && res.getInt("nb") > 0) {
				unStat.close();
				unebdd.seDeconnecter();
				return true;
			}
			unStat.close();
			unebdd.seDeconnecter();
		} catch (SQLException exp) {
			System.out.println("Erreur execution requete : " + requete);
		}
		return false;
	}

	public static ArrayList<Reservation> selectAllReservations(String filtre) {
		String requete;
		if (filtre.equals("")) {
			requete = "SELECT * FROM Reservation;";
		} else {
			requete = "SELECT * FROM Reservation WHERE nomClient LIKE '%" + filtre + "%' OR prenomClient LIKE '%" + filtre
					+ "%' OR mailClient LIKE '%" + filtre + "%';";
		}
		ArrayList<Reservation> lesReservations = new ArrayList<Reservation>();
		try {
			unebdd.seConnecter();
			Statement unStat = unebdd.getMacConnection().createStatement();
			ResultSet desResultats = unStat.executeQuery(requete);
			while (desResultats.next()) {
				Reservation uneRes = new Reservation(desResultats.getInt("idReservation"),
						desResultats.getDate("dateDebutReservation"), desResultats.getDate("dateFinReservation"),
						desResultats.getString("nomClient"), desResultats.getString("prenomClient"),
						desResultats.getString("mailClient"), desResultats.getInt("telephoneClient"), desResultats.getInt("nbPersonnes"),
						desResultats.getInt("idGite"), desResultats.getInt("idUser"));
				lesReservations.add(uneRes);
			}
			unStat.close();
			unebdd.seDeconnecter();
		} catch (SQLException exp) {
			System.out.println("Erreur execution requete : " + requete);
		}
		return lesReservations;
	}

	/**************** Requetes Contrats ******************/
	public static void insertContrat(Contrat unContrat) {
		String contenuEscaped = (unContrat.getContenu() != null ? unContrat.getContenu() : "").replace("'", "''");
		String titreEscaped = (unContrat.getTitre() != null ? unContrat.getTitre() : "").replace("'", "''");
		String requete = "INSERT INTO Contrat (idReservation, dateCreation, titre, contenu) VALUES ("
				+ unContrat.getIdReservation() + ",'" + unContrat.getDateCreation() + "','" + titreEscaped + "','"
				+ contenuEscaped + "');";
		executerRequete(requete);
	}

	public static Contrat selectContratByReservation(int idReservation) {
		String requete = "SELECT * FROM Contrat WHERE idReservation = " + idReservation + " LIMIT 1;";
		Contrat unContrat = null;
		try {
			unebdd.seConnecter();
			Statement unStat = unebdd.getMacConnection().createStatement();
			ResultSet res = unStat.executeQuery(requete);
			if (res.next()) {
				unContrat = new Contrat(res.getInt("idContrat"), res.getInt("idReservation"),
						res.getDate("dateCreation"), res.getString("titre"), res.getString("contenu"));
			}
			unStat.close();
			unebdd.seDeconnecter();
		} catch (SQLException exp) {
			System.out.println("Erreur execution requete : " + requete);
		}
		return unContrat;
	}

	public static boolean hasContrat(int idReservation) {
		return selectContratByReservation(idReservation) != null;
	}

	public static void updateContrat(Contrat unContrat) {
		String contenuEscaped = (unContrat.getContenu() != null ? unContrat.getContenu() : "").replace("'", "''");
		String titreEscaped = (unContrat.getTitre() != null ? unContrat.getTitre() : "").replace("'", "''");
		String requete = "UPDATE Contrat SET titre='" + titreEscaped + "', contenu='" + contenuEscaped
				+ "' WHERE idContrat=" + unContrat.getIdContrat() + ";";
		executerRequete(requete);
	}

	public static void deleteContrat(int idContrat) {
		String requete = "DELETE FROM Contrat WHERE idContrat=" + idContrat + ";";
		executerRequete(requete);
	}

	/**************** Requetes Gîtes ******************/
	public static void insertGite(Gite unGite) {
		String requete = "INSERT INTO Gite (nomGite, adresseGite, villeGite, codePostalGite, descriptionGite, capaciteGite, prixNuitGite, disponibiliteGite, idUser) VALUES ('"
				+ unGite.getNomGite() + "','" + unGite.getAdresseGite() + "','" + unGite.getVilleGite() + "',"
				+ unGite.getCodePostalGite() + ",'" + unGite.getDescriptionGite() + "'," + unGite.getCapaciteGite() 
				+ "," + unGite.getPrixNuitGite() + "," + (unGite.isDisponibiliteGite() ? 1 : 0) + "," + unGite.getIdUser() + ");";
		executerRequete(requete);
	}

	public static ArrayList<Gite> selectAllGites(String filtre) {
		String requete = filtre.equals("") ? "SELECT * FROM Gite;" : "SELECT * FROM Gite WHERE nomGite LIKE '%" + filtre + "%' OR villeGite LIKE '%" + filtre + "%';";
		ArrayList<Gite> lesGites = new ArrayList<Gite>();
		try {
			unebdd.seConnecter();
			Statement unStat = unebdd.getMacConnection().createStatement();
			ResultSet desResultats = unStat.executeQuery(requete);
			while (desResultats.next()) {
				Gite unGite = new Gite(desResultats.getInt("idGite"), desResultats.getString("nomGite"),
						desResultats.getString("adresseGite"), desResultats.getString("villeGite"),
						desResultats.getInt("codePostalGite"), desResultats.getString("descriptionGite"),
						desResultats.getInt("capaciteGite"), desResultats.getDouble("prixNuitGite"),
						desResultats.getBoolean("disponibiliteGite"), desResultats.getInt("idUser"));
				lesGites.add(unGite);
			}
			unStat.close();
			unebdd.seDeconnecter();
		} catch (SQLException exp) {
			System.out.println("Erreur execution requete : " + requete);
		}
		return lesGites;
	}

	private static void executerRequete(String requete) {
		try {
			unebdd.seConnecter();
			Statement unStat = unebdd.getMacConnection().createStatement();
			unStat.execute(requete);
			unStat.close();
			unebdd.seDeconnecter();
		}catch(SQLException exp) {
			System.out.println("Erreur execution requete : "+requete);
			System.out.println("Détails de l'erreur : " + exp.getMessage());
			exp.printStackTrace();
		}
	}

}
