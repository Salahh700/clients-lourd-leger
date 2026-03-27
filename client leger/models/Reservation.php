<?php

class Reservation {
    private $conn;

    public function __construct($database) {
        $this->conn=$database;
    }

    public function selectReservationsForProprietaire($idUser){
        $req = "SELECT * from reservations r  
                inner join gites g on r.idGite = g.idGite
                where g.idUser=?";

        $stmt=$this->conn->prepare($req);

        $stmt->execute([$idUser]);

        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function insertReservation($data){
        $req="INSERT into reservations values (null, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        $stmt=$this->conn->prepare($req);

        $stmt->execute([
            $data['dateDebut'],
            $data['dateFin'],
            $data['nomClient'],
            $data['prenomClient'],
            $data['mailClient'],
            $data['usernameClient'],
            $data['messageReservation'],
            $data['idGite'],
            $data['idUser']
        ]);

        return $this->conn->lastInsertId();
    }

    public function isGiteDispoByDate($idGite, $dateDebut, $dateFin){
        $req="SELECT * from reservations WHERE idGite = ? 
            AND NOT (dateFinReservation < ? OR dateDebutReservation > ?)";
        
        $stmt=$this->conn->prepare($req);

        $stmt->execute([$idGite, $dateDebut, $dateFin]);

        return $stmt->fetch();
    }

    public function selectReservationById($idResa){
        $req="SELECT * from reservations where idReservation = ?";

        $stmt=$this->conn->prepare($req);

        $stmt->execute([$idResa]);

        return $stmt->fetch(PDO::FETCH_ASSOC);

    }

    public function selectReservationByUser($idUser){
        $req="SELECT * From reservations where idUser= ?";

        $stmt=$this->conn->prepare($req);

        $stmt->execute([$idUser]);

        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function selectReservationCurrently($idUser, $today){
        $req="SELECT * from reservations where idUser= ? and (dateDebutReservation <= ? and dateFinReservation >= ?) ";

        $stmt=$this->conn->prepare($req);

        $stmt->execute([$idUser, $today, $today]);

        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function selectReservationForDelete($idUser) {
        $today = date('Y-m-d');
        
        // Sélectionne les réservations EN COURS ou FUTURES
        $req = "SELECT * FROM reservations 
                WHERE idUser = ? 
                AND dateFinReservation >= ?
                ORDER BY dateDebutReservation ASC";
        
        $stmt = $this->conn->prepare($req);
        $stmt->execute([$idUser, $today]);
        
        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

}





?>