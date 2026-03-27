package controleur;

import java.util.ArrayList;

import modele.modele;

public class Controleur {
	/****************** Methodes Controles propriétaires ******************/
	public static Proprietaire selectWhereProprietaire (String email, String mdp) {
		// on controle email 
		
		// on control la complexite du mdp
		
		// on appelle le modele pour vérifier la presence du propriétaire
		Proprietaire unProprietaire = modele.selectWhereProprietaire(email, mdp);
		return unProprietaire;
	}
	
	public static void insertClient (String nom, String prenom, String adresse, String email, String statut) {
		
		
	}

	public static void insertClient(Client unClient) {
		modele.insertClient(unClient);
	}
	
	public static void updateClient(Client unClient) {
		modele.updateClient(unClient);
	}
	
	public static void deleteClient(int idclient) {
		modele.deleteClient(idclient);
	}
	
	public static ArrayList<Client> selectAllClients(String filtre){
		return modele.selectAllClients(filtre);
	}
	
	/****************** Methodes Controles propriétaires ******************/
	public static void insertProprietaire(Proprietaire unProprietaire) {
		modele.insertProprietaire(unProprietaire);
	}
	
	public static void updateProprietaire(Proprietaire unProprietaire) {
		modele.updateProprietaire(unProprietaire);
	}
	
	public static void deleteProprietaire(int idproprietaire) {
		modele.deleteProprietaire(idproprietaire);
	}
	
	public static ArrayList<Proprietaire> selectAllProprietaires(String filtre){
		return modele.selectAllProprietaires(filtre);
	}
	
	/****************** Methodes Controles gîtes ******************/
	public static void insertGite(Gite unGite) {
		modele.insertGite(unGite);
	}

	/****************** Compatibilité ancien panel objets ******************/
	public static void insertObjet(Objet unObjet) {
		// Méthode conservée pour compatibilité avec PanelObjets.
		// Le nouveau domaine utilise Gite/Reservation.
	}

	public static ArrayList<Gite> selectAllGites(String filtre) {
		return modele.selectAllGites(filtre);
	}

	/****************** Methodes Controles réservations ******************/
	public static void insertReservation(Reservation uneReservation) {
		modele.insertReservation(uneReservation);
	}

	public static void updateReservation(Reservation uneReservation) {
		modele.updateReservation(uneReservation);
	}

	public static void deleteReservation(int idreservation) {
		modele.deleteReservation(idreservation);
	}

	public static ArrayList<Reservation> selectAllReservations(String filtre) {
		return modele.selectAllReservations(filtre);
	}

	public static boolean reservationChevauche(int idGite, java.sql.Date dateDebut, java.sql.Date dateFin, int idReservationExclure) {
		return modele.reservationChevauche(idGite, dateDebut, dateFin, idReservationExclure);
	}

	/****************** Methodes Controles contrats ******************/
	public static void insertContrat(Contrat unContrat) {
		modele.insertContrat(unContrat);
	}

	public static Contrat selectContratByReservation(int idReservation) {
		return modele.selectContratByReservation(idReservation);
	}

	public static boolean hasContrat(int idReservation) {
		return modele.hasContrat(idReservation);
	}

	public static void updateContrat(Contrat unContrat) {
		modele.updateContrat(unContrat);
	}

	public static void deleteContrat(int idContrat) {
		modele.deleteContrat(idContrat);
	}

}
