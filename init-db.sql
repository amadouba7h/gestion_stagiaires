-- Script d'initialisation de la base de données
CREATE DATABASE IF NOT EXISTS intern_management_db;
USE intern_management_db;

-- Création de la table encadreurs
CREATE TABLE IF NOT EXISTS encadreurs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    telephone VARCHAR(20)
);

-- Création de la table stagiaires
CREATE TABLE IF NOT EXISTS stagiaires (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    prenom VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    encadreur_id BIGINT,
    FOREIGN KEY (encadreur_id) REFERENCES encadreurs(id) ON DELETE SET NULL
);

-- Insertion de données de test pour les encadreurs
INSERT INTO encadreurs (nom, prenom, email, telephone) VALUES
('Dupont', 'Jean', 'jean.dupont@technolab.com', '0123456789'),
('Martin', 'Marie', 'marie.martin@technolab.com', '0987654321'),
('Bernard', 'Pierre', 'pierre.bernard@technolab.com', '0147258369');

-- Insertion de données de test pour les stagiaires
INSERT INTO stagiaires (nom, prenom, email, date_debut, date_fin, encadreur_id) VALUES
('Doe', 'John', 'john.doe@student.com', '2025-01-15', '2025-06-15', 1),
('Smith', 'Jane', 'jane.smith@student.com', '2025-02-01', '2025-07-01', 1),
('Johnson', 'Bob', 'bob.johnson@student.com', '2025-01-20', '2025-06-20', 2),
('Williams', 'Alice', 'alice.williams@student.com', '2025-03-01', '2025-08-01', 2),
('Brown', 'Charlie', 'charlie.brown@student.com', '2025-02-15', '2025-07-15', 3);
