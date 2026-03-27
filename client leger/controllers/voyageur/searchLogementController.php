<?php
session_start();

require_once '../../config/Database.php';
require_once '../../models/Gite.php';

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    header('location: ../../views/voyageur/homeVoyageur.php');
    exit();
}

if(!isset($_SESSION['role']) || $_SESSION['role'] !== 'voyageur'){
    $_SESSION['error']="Accès refusé ❌";
    header('location: login.php');
    exit();
}

$search=$_POST['element'];

//sécurité si ce qu'il a recherché est vide 
if(empty($search)){
    header ('location: ../../views/voyageur/homeVoyageur.php');
    exit();
}

if(!empty($search)){
    header ('location: ../../views/voyageur/homeVoyageur.php?search='.urlencode($search));
    exit();
}

?>