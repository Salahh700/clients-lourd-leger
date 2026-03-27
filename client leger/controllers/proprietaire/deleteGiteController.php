<?php  

session_start();

require_once '../../config/Database.php';
require_once '../../models/Gite.php';

if(!isset($_SESSION['role']) || $_SESSION['role'] !== 'proprietaire'){
    $_SESSION['error']="Accès refusé ❌";
    header('location: login.php');
    exit();
}

$database= new bdd();
$db=$database->getConn();
$unGite= new Gite($db);

$idGite=$_POST['id'];

$res=$unGite->selectGite($idGite);

if (!$res) {
    $_SESSION['error'] = "Gîte introuvable ❌";
    header('Location: ../../views/proprietaire/mesGites.php');
    exit();
}

if($_SESSION['idUser']==$res['idUser']){

    if($unGite->deleteGite($idGite)){
        $_SESSION['success']="✅ Logement bien supprimé";
        header('location: ../../views/proprietaire/mesGites.php');
        exit();

    }else{
        $_SESSION['error']="❌ Echec de la suppression";
        header('location: ../../views/proprietaire/mesGites.php');
        exit();

    }

}else{
    $_SESSION['error']="❌ Accès refusé, vous n'êtes pas le propriétaire de ce logement ";
        header('location: ../../views/proprietaire/mesGites.php');
        exit();

}

?>