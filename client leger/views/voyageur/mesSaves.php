<?php
session_start();

require_once '../../config/Database.php';
require_once '../../models/Gite.php';
require_once '../../models/User.php';
require_once '../../models/Reservation.php';
require_once '../../models/Save.php';

if(!isset($_SESSION['role']) || $_SESSION['role'] !== 'voyageur'){
    $_SESSION['error'] = "Accès refusé ❌";
    header('location: ../auth/login.php');
    exit();
}

$database = new bdd();
$db = $database->getConn();
$unSave = new Save($db);
$unGite = new Gite($db);
$unUser = new User($db);

$resSave = $unSave->selectSaveByUser($_SESSION['idUser']);

?>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../public/css/global.css">
    <link rel="stylesheet" href="../../public/css/voyageur/voyageur.css">
    <title>Neige & Soleil - Mes favoris</title>
</head>
<body>
    <div class='navbar'>
        <img class="logo" src="../../public/images/logo.png" alt="">
        <ul>
            <li><a href="homeVoyageur.php">Accueil</a></li>
            <li><a href="mesReservations.php">Mes réservations</a></li>
            <li><a href="mesSaves.php" class="active">Mes favoris</a></li>
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
        <h1>Mes favoris ❤️</h1>

        <?php if (empty($resSave)): ?>
            <div class="empty-state">
                <p>Vous n'avez pas encore de favoris 💔</p>
                <a href="homeVoyageur.php">Découvrir des logements</a>
            </div>
        <?php else: ?>
            <div class="reservations-grid">
                <?php foreach($resSave as $fav): ?>
                    <?php
                    $resGite = $unGite->selectGite($fav['idGite']);
                    $resProprietaire = $unUser->selectUserById($resGite['idUser']);
                    ?>
                    <div class="card-reservation">
                        <h5><?= htmlspecialchars($resGite['nomGite']) ?></h5>
                        <p><strong>Ville :</strong> <?= htmlspecialchars($resGite['villeGite']) ?></p>
                        <p><strong>Prix :</strong> <?= htmlspecialchars($resGite['prixNuitGite']) ?> €/nuit</p>
                        <p><strong>Propriétaire :</strong> <?= htmlspecialchars($resProprietaire['usernameUser']) ?></p>
                        <div style="display:flex; gap:0.75rem; margin-top:1rem; flex-wrap:wrap;">
                            <a href="detailGite.php?idGite=<?= $resGite['idGite'] ?>" style="font-weight:600; font-size:0.9rem;">Voir le logement →</a>
                            <a href="../../controllers/voyageur/addSave.php?idGite=<?= $resGite['idGite'] ?>&isSave=true&location=mesSaves" class="btn-unsave">
                                ❤️ Retirer des favoris
                            </a>
                        </div>
                    </div>
                <?php endforeach; ?>
            </div>
        <?php endif; ?>
    </div>

</body>
</html>
