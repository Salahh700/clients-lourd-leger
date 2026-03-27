<?php

session_start();

require_once '../../config/Database.php';
require_once '../../models/User.php';

if($_SERVER['REQUEST_METHOD'] !== 'POST' ){
    header('location: ../../views/auth/login.php');
    exit();
}

$database= new bdd();
$db=$database->getConn();
$unUser= new User($db);

//données du form
$username=$_POST["username"];
$password=$_POST["password"];

//données de la bdd à partir de l'username
$res=$unUser->selectUser($username);

//Si rien n'a été SELECT quitter direct
if(!$res){
    $_SESSION['error']="Nom d'utilisateur ou mot de passe incorrect ❌";
    header('location: ../../views/auth/login.php');

    exit();
}

//If pour vérifier si le mdp de la bdd et celui du form sont similaires
if($password==$res['passwordUser']){
    $_SESSION['success']='Connexion réussie ✅';

    //passage des variables importantes dans la SESSION
    $_SESSION['idUser']=$res['idUser'];
    $_SESSION['username']=$res['usernameUser'];
    $_SESSION['role']=$res['roleUser'];
    if($_SESSION['role']=='voyageur'){
        header('location: ../../views/voyageur/homeVoyageur.php');
        exit();
    }else{
        header('location: ../../views/proprietaire/homeProprietaire.php');
        exit();
    }

}else{
    $_SESSION['error']="Nom d'utilisateur ou mot de passe incorrect ❌";
    header('location: ../../views/auth/login.php');
    exit();
}


?>