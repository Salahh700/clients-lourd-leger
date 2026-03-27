<?php
class Save {

    private $conn;

    public function __construct($database) {
        $this->conn=$database;
    }

    public function selectSaveByUser($idUser){
        $req="SELECT * from favoris where idUser = ?";

        $stmt=$this->conn->prepare($req);

        $stmt->execute([$idUser]);

        return $stmt->fetchAll(PDO::FETCH_ASSOC);
    }

    public function insertSave($idGite, $idUser) {
        // ✅ Spécifie les colonnes explicitement
        $req = "INSERT INTO favoris (idGite, idUser) VALUES (?, ?)";
        
        $stmt = $this->conn->prepare($req);
        
        return $stmt->execute([$idGite, $idUser]);
    }

    public function deleteSave($idGite, $idUser){
        $req="DELETE from favoris where idGite= ? AND idUser= ?";

        $stmt=$this->conn->prepare($req);

        return $stmt->execute([$idGite, $idUser]);
    }

    public function selectCountSaveByGite($idGite){
        $req="SELECT COUNT(*) from favoris where idGite=? ";

        $stmt=$this->conn->prepare($req);

        $stmt->execute([$idGite]);

        return $stmt->fetch();  
    }
}

?>