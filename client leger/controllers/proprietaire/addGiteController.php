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

//Verificiation de la dispo si 1 = dispo ; si 0 = pas dispo
$disponibilite = isset($_POST['disponibilite']) ? 1 : 0;

// Géocodage de l'adresse de l'utilisateur
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

$data = [
    'nom'=> $_POST['nom'],
    'adresse'=> $_POST['adresse'],
    'ville'=> $_POST['ville'],
    'codePostal'=> $_POST['codeP'],
    'description'=> $_POST['description'],
    'prixNuit'=> $_POST['prix'],
    'capacite'=> $_POST['capacite'],
    'disponibilite'=> $disponibilite,
    'idUser'=>$_SESSION['idUser'],
    'latitude'=>$latitude,
    'longitude'=>$longitude
];

if($unGite->insertGite($data)){
    $_SESSION['success']="✅Appartement ajouté avec succès";
    header('location: ../../views/proprietaire/mesGites.php');
    exit();
}else{
    $_SESSION['error']="❌Erreur: Logement non ajouté";
    header('location: ../../views/proprietaire/addGite.php');
    exit();
}

?>