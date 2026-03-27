<?php
session_start();

require_once '../../config/Database.php';
require_once '../../models/Gite.php';

$database= new bdd();
$db=$database->getConn();
$unGite= new Gite($db);

//si l'user n'est pas proprietaire, ca part
if(!isset($_SESSION['role']) || $_SESSION['role'] !== 'proprietaire'){
    $_SESSION['error']="Accès refusé ❌";
    header('location: login.php');
    exit();
}


//Si aucun logement n'a été séléctionné
if(!isset($_POST['idGite'])){
    $_SESSION['error']="Aucun logement n'a été séléctionné";
    header('location: mesGites.php');
    exit();
}

$res=$unGite->selectGite($_POST['idGite']);

if (!$res) {
    $_SESSION['error'] = "Gîte introuvable ❌";
    header('Location: mesGites.php');
    exit();
}

//si ce n'est pas son logement
if($_SESSION['idUser']!==$res['idUser']){
    $_SESSION['error']="Accès refusé ❌";
    header('location: ../auth/login.php');
}

?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="../../public/css/global.css">
    <title>Neige & Soleil - Modifier votre logement</title>
</head>
<body>
    <div class='navbar'>
        <img class="logo" src="../images/logo.png" alt="">
        <ul>
            <li><a href="homeProprietaire.php">Accueil</a></li>
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

    <form action="../../controllers/proprietaire/updateGiteController.php" method="POST">
            
        <label for="">Nom du logement:</label><br>
        <input type="text" name="nom" value="<?php echo $res['nomGite']; ?>"><br><br>

        <label for="">Adresse :</label><br>
        <input type="text" name="adresse" value="<?php echo $res['adresseGite']; ?>"><br><br>

        <label for="">Ville :</label><br>
        <input type="text" name="ville" value="<?php echo $res['villeGite']; ?>"><br><br>

        <label for="">Code Postal :</label><br>
        <input type="text" name="codeP" value="<?php echo $res['codePostalGite']; ?>"><br><br>

        <label for="">Description :</label><br>
        <textarea name="desc"><?php echo $res['descriptionGite']; ?></textarea><br><br>

        <label for="">Capacité :</label><br>
        <input type="number" name="capacite" value="<?php echo $res['capaciteGite']; ?>"><br><br>

        <label for="">Prix nuitée :</label><br>
        <input type="number" name="prix" value="<?php echo $res['prixNuitGite']; ?>"><br><br>

        <label for="">Disponibilité</label><br>
        <input type="checkbox" name="dispo" <?php $check=$res['disponibiliteGite']==1 ? 'checked' : '' ;echo $check ?>><br><br>

        <input type="hidden" name="idGite" value="<?php echo $res['idGite']; ?>"><br><br>

        <input type="submit" name="valider" value="Modifier" onclick="return confirm('Êtes vous sûr de vouloir modifier ce gîte ?')">
            
    </form>


</body>
</html>