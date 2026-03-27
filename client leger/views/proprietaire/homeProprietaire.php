<?php
session_start();

require_once '../../models/Reservation.php';
require_once '../../config/Database.php';

$database= new bdd();
$db=$database->getConn();
$uneReservation= new Reservation($db);

if(!isset($_SESSION['role']) || $_SESSION['role'] !== 'proprietaire'){
    $_SESSION['error']="Accès refusé ❌";
    header('location: ../auth/login.php');
    exit();
}

$res=$uneReservation->selectReservationsForProprietaire($_SESSION['idUser']);

?>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../public/css/global.css">
    <link rel="stylesheet" href="../../public/css/proprietaire/homeProprietaire.css">
    <title>Neige & Soleil - Accueil</title>
</head>
<body>
    <div class='navbar'>
        <img class="logo" src="../../public/images/logo.png" alt="">
        <ul>
            <li><a href="homeProprietaire.php" class="active">Accueil</a></li>
            <li><a href="mesGites.php">Mes logements</a></li>
            <li><a href="profilProprietaire.php">Mon compte</a></li>
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

    <div class="page-content">
        <h3>Bienvenue, <?php echo htmlspecialchars($_SESSION['username']) ?> 👋</h3>

        <div class="tab-last-reservation">
            <h4>Mes dernières réservations</h4>
            <table>
                <thead>
                    <tr>
                        <th>Logement</th>
                        <th>Voyageur</th>
                        <th>Dates</th>
                    </tr>
                </thead>
                <tbody>
                    <?php if (empty($res)): ?>
                        <tr>
                            <td colspan="3" class="empty-state">Aucune réservation pour le moment.</td>
                        </tr>
                    <?php else: ?>
                        <?php foreach ($res as $each): ?>
                            <tr>
                                <td><?php echo htmlspecialchars($each['nomGite']) ?></td>
                                <td>
                                    <?= htmlspecialchars($each['nomClient']) ?>
                                    <?= htmlspecialchars($each['prenomClient']) ?>
                                    <span style="color: var(--medium-gray);">(<?= htmlspecialchars($each['usernameClient']) ?>)</span>
                                </td>
                                <td>
                                    Du <?= htmlspecialchars($each['dateDebutReservation']) ?>
                                    au <?= htmlspecialchars($each['dateFinReservation']) ?>
                                </td>
                            </tr>
                        <?php endforeach; ?>
                    <?php endif; ?>
                </tbody>
            </table>
        </div>
    </div>

</body>
</html>
