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
$unUser= new User($db);
$unGite= new Gite($db);
$uneResa= new Reservation($db);

$resResa = $uneResa->selectReservationByUser($_SESSION['idUser']);

?>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../public/css/global.css">
    <link rel="stylesheet" href="../../public/css/voyageur/voyageur.css">
    <title>Neige & Soleil - Mes réservations</title>
</head>
<body>

    <div class='navbar'>
        <img class="logo" src="../../public/images/logo.png" alt="">
        <ul>
            <li><a href="homeVoyageur.php">Accueil</a></li>
            <li><a href="mesReservations.php" class="active">Mes réservations</a></li>
            <li><a href="mesSaves.php">Mes favoris</a></li>
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

    <div class="reservations-all">
        <h1>Mes réservations</h1>

        <?php if (empty($resResa)): ?>
            <div class="empty-state">
                <p>Vous n'avez aucune réservation pour le moment.</p>
                <a href="homeVoyageur.php">Trouver un logement</a>
            </div>
        <?php else: ?>
            <div class="reservations-grid">
                <?php foreach($resResa as $resa): ?>
                    <?php
                    $resGite = $unGite->selectGite($resa['idGite']);
                    $resProprietaire = $unUser->selectUserById($resGite['idUser']);
                    ?>
                    <div class="card-reservation">
                        <h5><?= htmlspecialchars($resGite['nomGite']) ?></h5>
                        <p>Du <strong><?= htmlspecialchars($resa['dateDebutReservation']) ?></strong> au <strong><?= htmlspecialchars($resa['dateFinReservation']) ?></strong></p>
                        <p>Vous recevrez un message du propriétaire <strong><?= htmlspecialchars($resProprietaire['nomUser']) ?> <?= htmlspecialchars($resProprietaire['prenomUser']) ?></strong> pour les infos d'accès au logement.</p>
                    </div>
                <?php endforeach; ?>
            </div>
        <?php endif; ?>
    </div>

</body>
</html>
