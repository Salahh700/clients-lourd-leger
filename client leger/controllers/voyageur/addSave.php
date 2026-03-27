<?php
session_start();

require_once '../../config/Database.php';
require_once '../../models/User.php';
require_once '../../models/Save.php';

if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
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
$unSave=new Save($bdd);

$location=$_GET['location'];
$idGite=$_GET['idGite'];
$isSave=$_GET['isSave'];

if(empty( $location) || empty($idGite)){
    $_SESSION['error']="Error: pas d'element a mettre en favoris";
    header('location: ../../views/voyageur/homeVoyageur.php');
    exit();
}

if($isSave=="false" && $location=='home'){
    $unSave->insertSave($idGite, $_SESSION['idUser']);
    header('location: ../../views/voyageur/homeVoyageur.php');
}elseif($isSave=="true" && $location=='home'){
    $unSave->deleteSave($idGite, $_SESSION['idUser']);
    header('location: ../../views/voyageur/homeVoyageur.php');
}

if($isSave=="false" && $location=='mesSaves'){
    $unSave->insertSave($idGite, $_SESSION['idUser']);
    header('location: ../../views/voyageur/mesSaves.php');
}elseif($isSave=="true" && $location=='mesSaves'){
    $unSave->deleteSave($idGite, $_SESSION['idUser']);
    header('location: ../../views/voyageur/mesSaves.php');
}


?>