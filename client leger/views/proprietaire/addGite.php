<?php

session_start();

if(!isset($_SESSION['role']) || $_SESSION['role'] !== 'proprietaire'){
    $_SESSION['error']="Accès refusé ❌";
    header('location: ../auth/login.php');
    exit();
}

?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../../public/css/global.css">
    <title>Neige & Soleil - Ajoutez un logement</title>
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
    

    <h1>Ajouter un nouveau logement</h1>
    <form action="../../controllers/proprietaire/addGiteController.php" method="POST">

        <label for="nom">Nom du logement:</label>
        <input type="text" id="nom" name="nom" required
                        placeholder="Ex: Coin de paradis à Paris ..."><br><br>

        <label for="adresse">Adresse:</label>
        <input type="text" id="adresse" name="adresse" required
                        placeholder="Ex: 8 impasse des Deux Cousins"><br><br>

        <label for="ville">Ville:</label>
        <input id="ville" name="ville" required
                        placeholder="Ex: Paris, Drancy, Sartrouville ..."></textarea><br><br>

        <label for="codePostal">Code Postal:</label>
        <input type="text" id="codePostal" name="codeP" required
                    placeholder="Ex: 93700, 75000"><br><br>

        <label for="description">Description:</label>
        <textarea id="description" name="description" required></textarea><br><br>

        <label for="prix">Prix par nuit:</label>
        <input type="number" id="prix" name="prix" required
                    placeholder="65"><br><br>

        <label for="capacite">Capacité du logement:</label>
        <input type="number" id="capacite" name="capacite" required
                    placeholder="5" max=15><br><br>

        <label for="disponibilite">Voulez vous le rendre disponible au lancement ?</label>
        <input type="checkbox" id="disponibilite" name="disponibilite" checked><br><br>


        <input type="submit" value="Ajouter le logement" name="add">
    </form>
</body>
</html>