<?php
session_start();

require_once '../../config/Database.php';
require_once '../../models/Gite.php';

$database= new bdd();
$db=$database->getConn();
$unGite= new Gite($db);

if(!isset($_SESSION['role']) || $_SESSION['role'] !== 'proprietaire'){
    $_SESSION['error']="Accès refusé ❌";
    header('location: login.php');
    exit();
}

$res=$unGite->selectGitesByUser($_SESSION['idUser']);

?>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../public/css/global.css">
    <link rel="stylesheet" href="../../public/css/proprietaire/mesGites.css">
    <title>Neige & Soleil - Mes Logements</title>
</head>
<body>
    <div class='navbar'>
        <img class="logo" src="../../public/images/logo.png" alt="">
        <ul>
            <li><a href="homeProprietaire.php">Accueil</a></li>
            <li><a href="mesGites.php" class="active">Mes logements</a></li>
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
        <div class="page-header">
            <h3>Mes logements</h3>
            <a href="addGite.php" class="add-gite-btn">➕ Ajouter un logement</a>
        </div>

        <div class="gites-grid">
            <?php foreach($res as $each): ?>
            <div class="each-gite">
                <p class="gite-name"><?php echo htmlspecialchars($each['nomGite']) ?></p>

                <div class="gite-info">
                    <p><span class="label">Adresse</span> <?php echo htmlspecialchars($each['adresseGite']) ?></p>
                    <p><span class="label">Ville</span> <?php echo htmlspecialchars($each['villeGite']) ?></p>
                    <p><span class="label">Code postal</span> <?php echo htmlspecialchars($each['codePostalGite']) ?></p>
                    <p><span class="label">Capacité</span> <?php echo htmlspecialchars($each['capaciteGite']) ?> personnes</p>
                    <p><span class="label">Description</span> <?php echo htmlspecialchars($each['descriptionGite']) ?></p>
                </div>

                <p class="gite-price"><?php echo htmlspecialchars($each['prixNuitGite']) ?> € / nuit</p>

                <?php if($each['disponibiliteGite'] == 1): ?>
                    <span class="badge badge-success">✅ Disponible</span>
                <?php else: ?>
                    <span class="badge badge-danger">❌ Indisponible</span>
                <?php endif; ?>

                <div class="gite-actions">
                    <form method="POST" action="../../controllers/proprietaire/deleteGiteController.php">
                        <input type="hidden" name="id" value="<?php echo $each['idGite']?>">
                        <button type="submit" class="btn-delete" onclick="return confirm('Confirmer la suppression ?')">
                            🗑️ Supprimer
                        </button>
                    </form>
                    <form method="POST" action="updateGite.php">
                        <input type="hidden" name="idGite" value="<?php echo $each['idGite']?>">
                        <button type="submit" class="btn-edit">
                            📝 Modifier
                        </button>
                    </form>
                </div>
            </div>
            <?php endforeach; ?>
        </div>
    </div>

</body>
</html>
