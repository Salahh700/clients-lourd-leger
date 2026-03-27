<?php

class User {
    private $conn;

    public function __construct($database) {
        $this->conn=$database;
    }

    public function insertUser($data){
        
        $req = "INSERT INTO users (prenomUser, nomUser, usernameUser, passwordUser, mailUser, roleUser, latitudeUser, longitudeUser) 
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        $stmt = $this->conn->prepare($req);

        return $stmt->execute([
            $data['prenom'],
            $data['nom'],
            $data['username'],
            $data['password'],
            $data['mail'],
            $data['role'],
            $data['latitude'],
            $data['longitude']
        ]);
    }

    public function selectUser($username){
        $req="SELECT * from users where usernameUser = ? or mailUser = ?";

        $stmt=$this->conn->prepare($req);

        $stmt->execute([$username, $username]);

        return $res=$stmt->fetch(PDO::FETCH_ASSOC);

    }

    public function selectUserById($idUser){
        $req="SELECT * from users where idUser = ?";

        $stmt=$this->conn->prepare($req);

        $stmt->execute([$idUser]);

        return $stmt->fetch(PDO::FETCH_ASSOC);

    }

    public function updateUser($data){
        $req="UPDATE  users SET prenomUser=?, nomUser=?, usernameUser=?, mailUser=? where idUser=?";
        
        $stmt=$this->conn->prepare($req);

        return $stmt->execute([$data['prenom'], $data['nom'], $data['username'], $data['mail'], $data['idUser']]);
    }

    public function selectUserByMail($mail){
        $req="SELECT * from users where mailUser=?";

        $stmt=$this->conn->prepare($req);

        $stmt->execute([$mail]);

        return $stmt->fetch(PDO::FETCH_ASSOC);
    }

    public function deleteUserById($idUser){
        $req="DELETE from users where idUser = ?";

        $stmt=$this->conn->prepare($req);

        return $stmt->execute([$idUser]);
    }
}

?>