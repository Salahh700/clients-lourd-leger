<?php
session_start();

// Détruire toutes les variables de session
$_SESSION = array();

// Détruire la session
session_destroy();

session_start();
$_SESSION['success']="Déconnexion réussie ✅";
// Rediriger vers la page de connexion
header("Location: ../../views/auth/login.php");
exit();
?>