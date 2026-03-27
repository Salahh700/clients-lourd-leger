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

$database=new bdd();
$bdd=$database->getConn();
$unUser=new User($bdd);

$resUser=$unUser->selectUserById($_SESSION['idUser']);

if($_POST['newPass1'] !== $_POST['newPass2'] && $_POST['oldPass'] !== $resUser['passwordUser' ]){
    $_SESSION['error']="L'ancien mot de passe ou la validation de mot passe est erronée";
    header('location: ../../views/voyageur/confidentiality.php');
    exit();   
}

if($_POST['oldPass']== $_POST['newPass1']){
    $_SESSION['error']="Vous ne pouvez pas mettre le même mot de passe";
    header('location: ../../views/voyageur/confidentiality.php');
    exit();  
}

if($unUser->updatePasswordById($_POST['newPass2'], $_SESSION['idUser'])){
    $_SESSION['success']="Le changement de mot de passe a bien été effectuée !";
    header('location: ../../views/voyageur/profilVoyageur.php');
    exit();
}else{
    $_SESSION['error']="Le changement de mot de passe n'a pas pu être effectuée, veuillez réessayer";
    header('location: ../../views/voyageur/confidentiality.php');
    exit();   
}

?>