<?php

session_start();

require_once '../../models/Gite.php';
require_once '../../models/User.php';
require_once '../../config/Database.php';
require_once '../../models/Reservation.php';

if(!isset($_SESSION['role']) || $_SESSION['role'] !== 'voyageur'){
    $_SESSION['error']="Accès refusé ❌";
    header('location: ../auth/login.php');
    exit();
}

$edit = (isset($_GET['edit']) && $_GET['edit'] == 'true') ? true : false;

$database= new bdd();
$db=$database->getConn();
$unUser= new User($db);
$uneResa= new Reservation($db);

$resUser=$unUser->selectUserById($_SESSION['idUser']);

$currently = isset($_GET['currently']);

if($currently == true){
    $today=date('Y-m-d');
    $resCurrent=$uneResa->selectReservationCurrently($_SESSION['idUser'], $today);
}

?>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../public/css/global.css">
    <link rel="stylesheet" href="../../public/css/voyageur/voyageur.css">
    <title>Neige & Soleil - Mon profil</title>
</head>
<body>

    <div class='navbar'>
        <img class="logo" src="../../public/images/logo.png" alt="">
        <ul>
            <li><a href="homeVoyageur.php">Accueil</a></li>
            <li><a href="mesReservations.php">Mes réservations</a></li>
            <li><a href="mesSaves.php">Mes favoris</a></li>
            <li><a href="profilVoyageur.php" class="active">Mon compte</a></li>
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

    <?php if($currently == true): ?>
        <div class="alert-reservation">
            <h4>⚠️ Vous avez des <strong>réservations en cours</strong> ! Contactez le propriétaire et annulez ces réservations.</h4>
            <?php foreach($resCurrent as $one): ?>
                <p><strong><?= htmlspecialchars($one['nomGite']) ?></strong> — Du <?= htmlspecialchars($one['dateDebutReservation']) ?> au <?= htmlspecialchars($one['dateFinReservation']) ?></p>
            <?php endforeach; ?>
            <p>Rdv sur votre page <a href="mesReservations.php">Réservations</a> pour procéder aux changements.</p>
        </div>
    <?php endif; ?>

    <div class="sett-compte">

        <details class="info-compte" <?= $edit ? 'open' : '' ?>>
            <summary>Mes informations</summary>
            <form action="../../controllers/voyageur/updateUser.php" method="POST">
                <div class="form-group">
                    <label>Prénom</label>
                    <input type="text" name="prenom" value="<?= htmlspecialchars($resUser['prenomUser']) ?>" <?= $edit ? '' : 'disabled' ?>>
                </div>
                <div class="form-group">
                    <label>Nom</label>
                    <input type="text" name="nom" value="<?= htmlspecialchars($resUser['nomUser']) ?>" <?= $edit ? '' : 'disabled' ?>>
                </div>
                <div class="form-group">
                    <label>Nom d'utilisateur</label>
                    <input type="text" name="username" value="<?= htmlspecialchars($resUser['usernameUser']) ?>" <?= $edit ? '' : 'disabled' ?>>
                </div>
                <div class="form-group">
                    <label>Email</label>
                    <input type="text" name="mail" value="<?= htmlspecialchars($resUser['mailUser']) ?>" <?= $edit ? '' : 'disabled' ?>>
                </div>
                <div class="form-actions">
                    <?php if($edit): ?>
                        <button type="submit" class="btn-save">💾 Enregistrer</button>
                        <a href="profilVoyageur.php" class="btn-edit-profil">❌ Annuler</a>
                    <?php else: ?>
                        <a href="profilVoyageur.php?edit=true" class="btn-edit-profil">✏️ Modifier mes informations</a>
                    <?php endif; ?>
                </div>
            </form>
        </details>

        <details>
            <summary>Paramètres</summary>
            <div class="settings-links">
                <a href="confidentiality.php">🔒 Confidentialité (mot de passe, informations personnelles…)</a>
                <a href="affichage.php">🎨 Affichage</a>
            </div>
        </details>

        <details>
            <summary>Supprimer mon compte</summary>
            <div class="danger-zone">
                <label>Pour confirmer la suppression, saisissez <strong>"DELETE FOR SURE"</strong></label>
                <form action="../../controllers/voyageur/deleteUser.php" method="POST">
                    <input type="text" name="confirm" placeholder="DELETE FOR SURE">
                    <input type="submit" value="🗑️ Supprimer mon compte" onclick="return confirm('Êtes-vous sûr ? Cette action est irréversible.')">
                </form>
            </div>
        </details>

    </div>

</body>
</html>
