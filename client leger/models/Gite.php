<?php

class Gite  {
    private $conn;

    public function __construct($database){
        $this->conn=$database;
    }

    public function insertGite($data){
        $req="INSERT into gites 
        values (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        $stmt=$this->conn->prepare($req);

        return $stmt->execute([
            $data['nom'],
            $data['adresse'],
            $data['ville'],
            $data['codePostal'],
            $data['description'],
            $data['capacite'],
            $data['prixNuit'],
            $data['disponibilite'],
            $data['idUser'],
            $data['latitude'],
            $data['longitude']
        ]);
    }

    public function selectAllGites(){

        $req="SELECT * from gites";

        $stmt=$this->conn->prepare($req);

        $stmt->execute();

        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function selectGitesByUser($idUser){

        $req="SELECT * from gites where idUser = ?";

        $stmt=$this->conn->prepare($req);

        $stmt->execute([$idUser]);

        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function selectGite($idGite){

        $req="SELECT * from gites where idGite = ?";

        $stmt=$this->conn->prepare($req);

        $stmt->execute([$idGite]);

        return $stmt->fetch(PDO::FETCH_ASSOC);
    }

    public function deleteGite($idGite){
        $req="DELETE from gites where idGite = ?";

        $stmt=$this->conn->prepare($req);

        return $stmt->execute([$idGite]);
    } 

    public function updateGite ($data, $idGite){
        $req="UPDATE gites SET nomGite=?,adresseGite=?,villeGite=?,codePostalGite=?,descriptionGite=?,capaciteGite=?,
        prixNuitGite=?,disponibiliteGite=?, latitudeGite=?, longitudeGite=? 
        WHERE idGite=?";

        $stmt=$this->conn->prepare($req);

        return $stmt->execute([
            $data['nom'],
            $data['adresse'],
            $data['ville'],
            $data['codeP'],
            $data['description'],
            $data['capacite'],
            $data['prix'],
            $data['dispo'],
            $data['latitude'],
            $data['longitude'],
            $idGite
        ]);
    }

    public function selectGitesNearbyUser($latitudeUser, $longitudeUser, $rayon){
        // Formule de Haversine pour calculer la distance en km
        $req = "SELECT *, 
            (6371 * acos(
                cos(radians(?)) * cos(radians(latitudeGite)) 
                * cos(radians(longitudeGite) - radians(?)) 
                + sin(radians(?)) * sin(radians(latitudeGite))
            )) AS distance
            FROM gites
            WHERE latitudeGite IS NOT NULL 
            AND longitudeGite IS NOT NULL
            AND disponibiliteGite = 1
            HAVING distance < ?
            ORDER BY distance ASC";
    
         $stmt = $this->conn->prepare($req);
        $stmt->execute([$latitudeUser, $longitudeUser, $latitudeUser, $rayon]);
    
        return $stmt->fetchAll(PDO::FETCH_ASSOC);

    }

    public function selectGitesBySearch($search){
        $req = "SELECT * 
                FROM gites 
                WHERE disponibiliteGite = 1
                AND (
                    codePostalGite LIKE ? 
                    OR villeGite LIKE ? 
                    OR nomGite LIKE ?
                )";

        // prreparer avec % , ( tout caractere accompagné de $search avec tout caractere )
        $searchParam = '%' . $search . '%';

        $stmt = $this->conn->prepare($req);

        $stmt->execute([$searchParam, $searchParam, $searchParam]);

        return $stmt->fetchAll(PDO::FETCH_ASSOC);

    }


}
?>