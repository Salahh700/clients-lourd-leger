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
$unGite= new Gite($db);
$uneResa= new Reservation($db);

//verif pour savoir si on recoit bien l'id et s'il est bien un chiffre 
if (!isset($_GET['idGite']) || !is_numeric($_GET['idGite'])) {
    $_SESSION['error'] = "Gîte invalide";
    header('location: ../../views/voyageur/homeVoyageur.php');
    exit();
}

$idGite=$_GET['idGite'];

$resUser=$unUser->selectUserById($_SESSION['idUser']);

$resGite=$unGite->selectGite($idGite);

//verif que le bien est dispo
if($resGite['disponibiliteGite']==0){
    $_SESSION['error']="Le logement n'est pas disponible à la résérvation";
    header("location: ../../views/voyageur/detailGite.php?idGite=".$idGite);
    exit();
}

//vérification que la date de debut est avant la date de fin 
if (strtotime($_POST['dateDebut']) >= strtotime($_POST['dateFin'])) {
    $_SESSION['error'] = "La date de fin doit être après la date de début";
    header("location: ../../views/voyageur/detailGite.php?idGite=".$idGite);
    exit();
}

//verif que la date choisie est bien dispo 
$isReservation=$uneResa->isGiteDispoByDate($idGite, $_POST['dateDebut'], $_POST['dateFin']);
if($isReservation){
    $_SESSION['error'] = "Le logement n'est pas disponible pour cette période";
    header("location: ../../views/voyageur/detailGite.php?idGite=".$idGite);
    exit();
}

//verif que la capacite du logement est assez pour la résa 
if($resGite['capaciteGite']<$_POST['capacite']){
    $_SESSION['error']="Le nombre de personnes est plus élevé que la capacité du logement";
    header("location: ../../views/voyageur/detailGite.php?idGite=".$idGite);
    exit();    
}

// Calcul de la saison et du prix total
$moisDebut = (int)date('n', strtotime($_POST['dateDebut']));
$nuits = (int)round((strtotime($_POST['dateFin']) - strtotime($_POST['dateDebut'])) / 86400);
$prixBase = (float)$resGite['prixNuitGite'];

if ($moisDebut >= 3 && $moisDebut <= 8) {
    $saison        = 'haute';
    $labelSaison   = 'Haute saison 🌸☀️';
    $modificateur  = 1.10;
    $infoPrix      = '+10%';
} else {
    $saison        = 'basse';
    $labelSaison   = 'Basse saison 🍂❄️';
    $modificateur  = 0.90;
    $infoPrix      = '-10%';
}

$prixAjuste = round($prixBase * $modificateur, 2);
$prixTotal  = round($nuits * $prixAjuste, 2);

$_SESSION['reservationPrix'] = [
    'nuits'       => $nuits,
    'prixBase'    => $prixBase,
    'prixAjuste'  => $prixAjuste,
    'prixTotal'   => $prixTotal,
    'saison'      => $saison,
    'labelSaison' => $labelSaison,
    'infoPrix'    => $infoPrix,
];

$data=[
    "dateDebut"=>$_POST['dateDebut'],
    "dateFin"=>$_POST['dateFin'],
    "nomClient"=>$resUser['nomUser'],
    "prenomClient"=>$resUser['prenomUser'],
    "mailClient"=>$resUser['mailUser'],
    "usernameClient"=>$resUser['usernameUser'],
    "messageReservation"=>$_POST['message'],
    "idGite"=>$idGite,
    "idUser"=>$_SESSION['idUser']
];

$idReservation=$uneResa->insertReservation($data);

if($idReservation){
    $_SESSION['success']="La réservation a bien été enregistrée";
    header("location: ../../views/voyageur/successReservation.php?idReservation=".$idReservation);
    exit();
}else{
    $_SESSION['error']="La réservation n'a pas pû etre effectuée, réessayez plus tard";
    header("location: ../../views/voyageur/detailGite.php?idGite=".$idGite);
    exit(); 
}

?>