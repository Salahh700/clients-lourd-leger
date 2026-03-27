<?php
session_start();

require_once '../../models/Gite.php';
require_once '../../models/User.php';
require_once '../../config/Database.php';

if(!isset($_SESSION['role']) || $_SESSION['role'] !== 'voyageur'){
    $_SESSION['error']="Accès refusé ❌";
    header('location: ../auth/login.php');
    exit();
}

$database= new bdd();
$db=$database->getConn();
$unUser= new User($db);
$unGite = new Gite($db);

//récupération des infos du gite
$results=$unGite->selectGite($_GET['idGite']);

//récuperation des infos du proprio du gite
$resultsProprio=$unUser->selectUserById($results['idUser']);

?>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../public/css/global.css">
    <link rel="stylesheet" href="../../public/css/voyageur/voyageur.css">
    <title>Neige & Soleil - <?= htmlspecialchars($results['nomGite']) ?></title>
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

    <div class="detail-wrapper">

        <div class="detail-card">
            <h1><?= htmlspecialchars($results['nomGite']) ?></h1>
            <div class="detail-info">
                <p><span class="label">Adresse</span> <?= htmlspecialchars($results['adresseGite']) ?></p>
                <p><span class="label">Ville</span> <?= htmlspecialchars($results['villeGite']) ?></p>
                <p><span class="label">Capacité</span> <?= htmlspecialchars($results['capaciteGite']) ?> personnes</p>
                <p><span class="label">Propriétaire</span> <?= htmlspecialchars($resultsProprio['usernameUser']) ?></p>
            </div>
            <div class="detail-description">
                <?= htmlspecialchars($results['descriptionGite']) ?>
            </div>
            <p class="detail-price"><?= htmlspecialchars($results['prixNuitGite']) ?> € / nuit</p>
        </div>

        <div class="detail-card reservation-form">
            <h2>Réserver ce logement</h2>
            <form action="../../controllers/voyageur/addReservation.php?idGite=<?= $results['idGite']?>" method="POST">
                <div class="form-group">
                    <label>Date de début</label>
                    <input type="date" name="dateDebut" id="dateDebut">
                </div>
                <div class="form-group">
                    <label>Date de fin</label>
                    <input type="date" name="dateFin" id="dateFin">
                </div>

                <div id="prix-total-box" style="display:none;">
                    <div class="prix-total-detail">
                        <span id="prix-detail-text"></span>
                    </div>
                    <div class="prix-total-amount">
                        Total : <strong id="prix-total-value"></strong>
                    </div>
                </div>

                <div class="form-group">
                    <label>Nombre de personnes</label>
                    <input type="number" name="capacite" min="1" max="<?= $results['capaciteGite'] ?>">
                </div>
                <div class="form-group">
                    <label>Message pour le propriétaire</label>
                    <textarea name="message" placeholder="Présentez-vous, indiquez vos horaires d'arrivée..."></textarea>
                </div>
                <input type="submit" name="valider" value="Confirmer la réservation">
            </form>
        </div>

    </div>

<script>
    const prixNuit = <?= (float)$results['prixNuitGite'] ?>;
    const dateDebut = document.getElementById('dateDebut');
    const dateFin = document.getElementById('dateFin');
    const box = document.getElementById('prix-total-box');
    const detail = document.getElementById('prix-detail-text');
    const total = document.getElementById('prix-total-value');

    function getSaison(date) {
        const mois = date.getMonth() + 1; // 1-12
        if (mois >= 3 && mois <= 8) {
            return { label: 'Haute saison 🌸☀️', modificateur: 1.10, classe: 'saison-haute', info: '+10%' };
        } else {
            return { label: 'Basse saison 🍂❄️', modificateur: 0.90, classe: 'saison-basse', info: '-10%' };
        }
    }

    function calculerTotal() {
        const d1 = new Date(dateDebut.value);
        const d2 = new Date(dateFin.value);

        if (dateDebut.value && dateFin.value && d2 > d1) {
            const nuits = Math.round((d2 - d1) / (1000 * 60 * 60 * 24));
            const saison = getSaison(d1);
            const prixAjuste = prixNuit * saison.modificateur;
            const montant = nuits * prixAjuste;

            box.className = 'prix-total-box ' + saison.classe;

            detail.innerHTML =
                '<span class="saison-badge ' + saison.classe + '">' + saison.label + ' <em>' + saison.info + '</em></span>' +
                '<span class="saison-calcul">' +
                    nuits + ' nuit' + (nuits > 1 ? 's' : '') +
                    ' × ' + prixAjuste.toFixed(2) + ' € ' +
                    '<small>(base ' + prixNuit.toFixed(2) + ' €)</small>' +
                '</span>';

            total.textContent = montant.toFixed(2) + ' €';
            box.style.display = 'block';
        } else {
            box.style.display = 'none';
        }
    }

    dateDebut.addEventListener('change', calculerTotal);
    dateFin.addEventListener('change', calculerTotal);
</script>
</body>
</html>
