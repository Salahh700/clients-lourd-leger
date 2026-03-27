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
$uneResa= new Reservation($db);
$unGite= new Gite($db);

$idResa=$_GET['idReservation'];

$resResa=$uneResa->selectReservationById($idResa);
$resGite=$unGite->selectGite($resResa['idGite']);

$prix = $_SESSION['reservationPrix'] ?? null;
unset($_SESSION['reservationPrix']);

?>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../public/css/global.css">
    <link rel="stylesheet" href="../../public/css/voyageur/voyageur.css">
    <title>Neige & Soleil - Réservation confirmée</title>
</head>
<body>

    <div class='navbar'>
        <img class="logo" src="../../public/images/logo.png" alt="">
        <ul>
            <li><a href="homeVoyageur.php">Accueil</a></li>
            <li><a href="mesReservations.php">Mes réservations</a></li>
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

    <div class="success-wrapper">
        <div class="cardSuccessResa">
            <p style="font-size:3rem; margin-bottom:1rem;">🎉</p>
            <h1>Félicitations <?= htmlspecialchars($resResa['prenomClient']) ?> !</h1>
            <p>Votre réservation pour <strong><?= htmlspecialchars($resGite['nomGite']) ?></strong> est confirmée.</p>
            <p>Du <strong><?= htmlspecialchars($resResa['dateDebutReservation']) ?></strong> au <strong><?= htmlspecialchars($resResa['dateFinReservation']) ?></strong></p>

            <?php if ($prix): ?>
            <div class="success-prix-recap">
                <span class="saison-badge saison-<?= $prix['saison'] ?>">
                    <?= $prix['labelSaison'] ?> <em><?= $prix['infoPrix'] ?></em>
                </span>
                <div class="success-prix-detail">
                    <?= $prix['nuits'] ?> nuit<?= $prix['nuits'] > 1 ? 's' : '' ?>
                    × <?= number_format($prix['prixAjuste'], 2, ',', ' ') ?> €
                    <small>(base <?= number_format($prix['prixBase'], 2, ',', ' ') ?> €/nuit)</small>
                </div>
                <div class="success-prix-total">
                    Total : <strong><?= number_format($prix['prixTotal'], 2, ',', ' ') ?> €</strong>
                </div>
            </div>
            <?php endif; ?>

            <a href="homeVoyageur.php" class="btn-home">Revenir à l'accueil</a>
        </div>
    </div>

</body>
</html>
