<?php

session_start();

require_once '../../models/Gite.php';
require_once '../../models/User.php';
require_once '../../models/Reservation.php';
require_once '../../models/Save.php';
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
$uneResa= new Reservation($db);
$unSave=new Save($db);

$gites=[];
$rayon = 50;
$resGites=[];
$mode="nearby";

//récupération des infos de l'user connecté
$resUser=$unUser->selectUserById($_SESSION['idUser']);

// selection du mode "recherche logements" ou du mode "afficher gites proches"
if(isset($_GET['search'])){
    $search=$_GET['search'];
    $resGites=$unGite->selectGitesBySearch($search);
    $mode="search";
}
//si il n'y a pas de recherche qui a été faite bah affichage des gites les plus proches (changement du rayon via filtre bientôt)
elseif(!empty($resUser) && $resUser['latitudeUser']!= null && $resUser['longitudeUser']!= null){
    $gites=$unGite->selectGitesNearbyUser($resUser['latitudeUser'], $resUser['longitudeUser'], $rayonn = 50);
    $mode="nearby";

}

$resSave=$unSave->selectSaveByUser($_SESSION['idUser']);
//prends les id des logements en saves et les mets dans un tableau
$savedGitesIds = array_column($resSave, 'idGite');
?>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../public/css/global.css">
    <link rel="stylesheet" href="../../public/css/voyageur/voyageur.css">
    <title>Neige & Soleil - Accueil</title>
</head>
<body>

    <div class='navbar'>
        <img class="logo" src="../../public/images/logo.png" alt="">
        <ul>
            <li><a href="homeVoyageur.php" class="active">Accueil</a></li>
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

    <div class="search-logement">
        <label>🔍</label>
        <form action="../../controllers/voyageur/searchLogementController.php" method="POST">
            <input type="text" name="element" placeholder="Recherchez par ville, nom, code postal...">
            <input type="submit" value="Rechercher" name="searchLogement">
        </form>
    </div>

    <div class="page-content">

        <?php if ($mode == 'nearby'): ?>
            <h1>Logements près de chez vous <small style="font-size:1rem; color:var(--medium-gray); font-weight:400;">(rayon <?= $rayon ?> km)</small></h1>

            <?php if (empty($gites)): ?>
                <div class="empty-state">
                    <p>Aucun logement disponible près de chez vous pour le moment.</p>
                </div>
            <?php else: ?>
                <div class="gites-list">
                    <?php foreach ($gites as $gite): ?>
                        <div class="gite-card">
                            <h3><?= htmlspecialchars($gite['nomGite']) ?></h3>
                            <p><strong>Ville :</strong> <?= htmlspecialchars($gite['villeGite']) ?></p>
                            <p><strong>Distance :</strong> <?= round($gite['distance'], 1) ?> km</p>
                            <p><strong>Capacité :</strong> <?= $gite['capaciteGite'] ?> personnes</p>
                            <p><?= htmlspecialchars($gite['descriptionGite']) ?></p>
                            <p class="gite-price"><?= $gite['prixNuitGite'] ?> €/nuit</p>
                            <div class="card-actions">
                                <a href="detailGite.php?idGite=<?= $gite['idGite'] ?>">Voir le logement →</a>
                                <?php if(in_array($gite['idGite'], $savedGitesIds)): ?>
                                    <a href="../../controllers/voyageur/addSave.php?idGite=<?= $gite['idGite']?>&isSave=true&location=home">
                                        <button class="btn-save" title="Retirer des favoris">❤️</button>
                                    </a>
                                <?php else: ?>
                                    <a href="../../controllers/voyageur/addSave.php?idGite=<?= $gite['idGite']?>&isSave=false&location=home">
                                        <button class="btn-save" title="Ajouter aux favoris">♡</button>
                                    </a>
                                <?php endif; ?>
                            </div>
                        </div>
                    <?php endforeach; ?>
                </div>
            <?php endif; ?>

        <?php elseif($mode == "search"): ?>
            <?php if(!empty($resGites)): ?>
                <h1>Résultats pour "<?= htmlspecialchars($search) ?>"</h1>
                <div class="results">
                    <?php foreach ($resGites as $gi): ?>
                        <div class="gite-card">
                            <h3><?= htmlspecialchars($gi['nomGite']) ?></h3>
                            <p><strong>Ville :</strong> <?= htmlspecialchars($gi['villeGite']) ?></p>
                            <p><strong>Capacité :</strong> <?= $gi['capaciteGite'] ?> personnes</p>
                            <p><?= htmlspecialchars($gi['descriptionGite']) ?></p>
                            <p class="gite-price"><?= $gi['prixNuitGite'] ?> €/nuit</p>
                            <div class="card-actions">
                                <a href="detailGite.php?idGite=<?= $gi['idGite'] ?>">Voir le logement →</a>
                            </div>
                        </div>
                    <?php endforeach; ?>
                </div>
            <?php else: ?>
                <div class="empty-state">
                    <p>Aucun logement trouvé pour "<?= htmlspecialchars($search) ?>".</p>
                </div>
            <?php endif; ?>
        <?php endif; ?>

    </div>

</body>
</html>
