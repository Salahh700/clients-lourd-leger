<?php

session_start();

require_once '../../config/Database.php';
require_once '../../models/User.php';

// Vérifier que c'est bien un POST
if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    header('Location: ../../views/auth/signup.php');
    exit();
}

$database = new bdd();
$db = $database->getConn();
$unUser = new User($db);

$username = $_POST['mail'];

// Géocodage de l'adresse de l'utilisateur
require_once __DIR__ . '/../../utils/Geocoder.php';
$geocoder = new Geocoder();
        
$latitude = null;
$longitude = null;
        
// Si l'utilisateur a fourni une ville
if (!empty($_POST['ville'])) {
    $codePostal = !empty($_POST['codePostal']) ? $_POST['codePostal'] : '';
    $coords = $geocoder->geocodeByCity($_POST['ville'], $codePostal);
            
    if ($coords !== null) {
        $latitude = $coords['latitude'];
        $longitude = $coords['longitude'];
        }
}

// Validation de la politique de mot de passe
$password = $_POST['password'];
$erreursPwd = [];

if (strlen($password) < 8)              $erreursPwd[] = "au moins 8 caractères";
if (!preg_match('/[A-Z]/', $password))  $erreursPwd[] = "une majuscule";
if (!preg_match('/[a-z]/', $password))  $erreursPwd[] = "une minuscule";
if (!preg_match('/[^A-Za-z0-9]/', $password)) $erreursPwd[] = "un caractère spécial";

if (!empty($erreursPwd)) {
    $_SESSION['error'] = "Mot de passe invalide : il faut " . implode(', ', $erreursPwd) . ".";
    header('Location: ../../views/auth/signup.php');
    exit();
}

$data = [
    'nom' => $_POST['nom'],
    'prenom' => $_POST['prenom'],
    'username' => $_POST['username'],
    'password' => $_POST['password'],
    'mail' => $username,
    'role' => $_POST['role'],
    'latitude' => $latitude,
    'longitude' => $longitude
];

$res = $unUser->selectUser($username);

if($res) {
    // Email ou username existe déjà donc directetion le signup pour qu'il réessaie un autre email
    $_SESSION['error'] = "Cet email ou ce nom d'utilisateur existe déjà";
    header('Location: ../../views/auth/signup.php'); 
    exit();

} elseif ($unUser->insertUser($data)) {
    // Inscription réussie donc direction login pour qu'il se connecte
    $_SESSION['success'] = "Inscription réussie ! Vous pouvez vous connecter.";
    header('Location: ../../views/auth/login.php'); 
    exit();

} else {
    // Erreur lors de l'insertion
    $_SESSION['error'] = "Erreur lors de l'inscription. Veuillez réessayer.";
    header('Location: ../../views/auth/signup.php'); // 
    exit();
}

?>