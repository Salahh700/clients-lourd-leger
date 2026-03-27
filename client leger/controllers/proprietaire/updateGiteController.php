<?php


session_start();

require_once '../../config/Database.php';
require_once '../../models/Gite.php';

if($_SERVER['REQUEST_METHOD'] !== 'POST' ){
    header('location: ../../views/auth/login.php');
    exit();
}

$database= new bdd();
$db=$database->getConn();
$unGite= new Gite($db);

if(!isset($_SESSION['role']) || $_SESSION['role'] !== 'proprietaire'){
    $_SESSION['error']="Accès refusé ❌";
    header('location: login.php');
    exit();
}

//pour avoir la longitude et latitude de chaque ville pour donner des distances entre chaque ville
require_once __DIR__ . '/../../utils/Geocoder.php';
$geocoder = new Geocoder();

$latitude = null;
$longitude = null;

if (!empty($_POST['ville'])) {
    // Version simplifiée : juste ville + code postal
    $coords = $geocoder->geocodeByCity($_POST['ville'], $_POST['codeP']);
    
    if ($coords !== null) {
        $latitude = $coords['latitude'];
        $longitude = $coords['longitude'];
    }
}

$dispo=isset($_POST['dispo'])? 1 : 0;

$idGite=$_POST['idGite'];

if(!isset($idGite)){
    $_SESSION['error']="Aucun Logement n'a été séléctionné pour la modification";
    header ('location : ../../views/proprietaire/mesGites.php');
    exit();
}

$data=[
    'nom'=>$_POST['nom'],
    'adresse'=>$_POST['adresse'],
    'ville'=>$_POST['ville'],
    'codeP'=>$_POST['codeP'],
    'description'=>$_POST['desc'],
    'capacite'=>$_POST['capacite'],
    'prix'=>$_POST['prix'],
    'dispo'=>$dispo,
    'latitude'=>$latitude,
    'longitude'=>$longitude
];

if ($unGite->updateGite($data,$idGite)){
    $_SESSION['success']="✅ Logement modifié avec succès !";
    header('location: ../../views/proprietaire/mesGites.php');
    exit();
}else{
    $_SESSION['error']="❌ Erreur lors de la modification";
    header('location: ../../views/proprietaire/mesGites.php');
    exit();
}

?>