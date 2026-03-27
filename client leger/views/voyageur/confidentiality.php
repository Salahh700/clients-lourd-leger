<?php 
session_start();

require_once '../../models/Gite.php';
require_once '../../models/User.php';
require_once '../../models/Reservation.php';
require_once '../../config/Database.php';

if(!isset($_SESSION['role']) || $_SESSION['role'] !== 'voyageur'){
    $_SESSION['error']="Accès refusé ❌";
    header('location: ../auth/login.php');
    exit();
}

$database= new bdd();
$db=$database->getConn();

?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="../../public/css/global.css">
    <title>Confidentialité - Neige & Soleil</title>
</head>
<body>

    <div class='navbar'>
        <img class="logo" src="../../public/images/logo.png" alt="">
        <ul>
            <li><a href="homeVoyageur.php">Accueil</a></li>
            <li><a href="mesSaves.php">Mes Logements favoris</a></li>
            <li><a href="profilVoyageur.php">Mon compte</a></li>
            <li><a href="../../controllers/auth/LogoutController.php">Déconnexion</a></li>
        </ul>
    </div>

    <div class="temporaly-message">
        <?php if (isset($_SESSION['success'])): ?>
            <p class="success">✅ <?= $_SESSION['success'] ?></p>
            <?php unset($_SESSION['success']); ?>
        <?php endif; ?>

        <?php if (isset($_SESSION['error'])): ?>
            <p class="error">❌ <?= $_SESSION['error'] ?></p>
            <?php unset($_SESSION['error']); ?>
        <?php endif; ?>
    </div>

    <div class="passwordSettings">
        <H3>Mot de passe</H3>
            <details>
                <Summary>Modifier votre mot de passe</Summary>
                
                <form action="../../controllers/editPassword.php" method="POST">
                <label for="">Votre ancien mot de passe</label>
                <input type="text" name="oldPassword" placeholder="Ancien mot de passe">

                <label for="">Nouveau mot de passe</label>
                <input type="text" name="newPass1" placeholder="Nouveau">

                <label for="">Confirmer le mot de passe</label>
                <input type="text" name="newPass2" placeholder="Confirmer">

                <input type="submit" value="Valider" onclick="return confirm('Êtes vous sûr(e) ? (Cette action est irréversible)'">
                </form>
            </details>
    </div>
</body>
</html>