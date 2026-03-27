<?php
session_start();

require_once '../../config/Database.php';
require_once '../../models/Gite.php';
require_once '../../models/User.php';
require_once '../../models/Reservation.php';


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
$uneResa=new Reservation($db);

//verif si le mot entré est bon 
if($_POST['confirm'] !== "DELETE FOR SURE"){
    $_SESSION['error']="Merci de bien écrire la confirmation ;)";
    header('location: ../../views/voyageur/profilVoyageur.php');
    exit();
}

//verif si l'user a des résas en cours 
$existResa=$uneResa->selectReservationForDelete($_SESSION['iduser']);
if(!empty($existResa)){
    $_SESSION['error']="Il vous reste des réservations en cours, merci de les annuler";
    header('location: ../../views/profilVoyageur.php?currently=true');
    exit();
}

if($unUser->deleteUserById($_SESSION['idUser'])){
    session_destroy();
    header("location: ../../views/auth/signup.php");
    exit();
}else{
    $_SESSION['error']="La suppression du compte a échoué, veuillez réessayer ou contacter un administrateur si cela persiste";
    header('location: ../../views/voyageur/profilVoyageur.php');
    exit();
}
?>