<?php
    
class bdd {

    private $host = "localhost";
    private $name = "neigeetsoleil";
    private $username = "root";
    private $mdp = "";
    private $conn;

    public function getConn() {
        $this->conn = null;
        try {
            $dsn = "mysql:host=" . $this->host . ";dbname=" . $this->name . ";charset=utf8";
            $this->conn = new PDO($dsn, $this->username, $this->mdp);
        } catch(PDOException $e) {
            logger('Echec Connexion Database: ' . $e->getMessage(), 'erreur');
        }

        return $this->conn;
    }
}

?>