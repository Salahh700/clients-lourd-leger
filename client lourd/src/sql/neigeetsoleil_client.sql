-- Script pour ajouter la table client à la base de données NeigeEtSoleil
USE NeigeEtSoleil;

-- Création de la table client si elle n'existe pas
CREATE TABLE IF NOT EXISTS client (
    idclient INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    adresse VARCHAR(150) NOT NULL,
    email VARCHAR(100) NOT NULL,
    statut VARCHAR(50) NOT NULL
);
