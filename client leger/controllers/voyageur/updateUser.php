<?php
session_start();

require_once '../../config/Database.php';
require_once '../../models/Gite.php';
require_once '../../models/User.php';

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    header('location: ../../views/voyageur/homeVoyageur.php');
    exit();
}

if(!isset($_SESSION['role']) || $_SESSION['role'] !== 'voyageur'){
    $_SESSION['error']="Accès refusé ❌";
    header('location: login.php');
    exit();
}

$database= new bdd();
$db=$database->getConn();
$unUser= new User($db);

$data=[
    'nom'=>$_POST['nom'],
    'prenom'=>$_POST['prenom'],
    'username'=>$_POST['username'],
    'mail'=>$_POST['mail'],
    'idUser'=>$_SESSION['idUser']
];

if(empty($_POST['nom']) || empty($_POST['prenom']) || empty($_POST['username']) || empty($_POST['mail'])){
    $_SESSION['success']="Merci de bien remplir entièrement le formulaire";
    header('location: ../../views/voyageur/profilVoyageur.php');
    exit();
}

$resUser=$unUser->selectUserById($_SESSION['idUser']);

$unUser->selectUserByMail($data['mail']) ? $existMail=true : $existMail=false;

if($existMail==true && $data['mail'] != $resUser['mailUser']){
    $_SESSION['error']="L'email choisi est déja utilisé par un compte";
    header('location: ../../views/voyageur/profilVoyageur.php');
    exit();    
}

if($unUser->updateUser($data)){
    $_SESSION['success']="La modification a bien été effectuée";
    header('location: ../../views/voyageur/profilVoyageur.php');
    exit();
}else{
    $_SESSION['error']="La modification n'a pas pu être faite, merci de réessayer plus tard ou d'envoyer un message à notre support si cela persiste";
    header('location: ../../views/voyageur/profilVoyageur.php');
    exit();
}
?>