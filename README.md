# Projet de Gestion des Stagiaires - Architecture Microservices

## Description

Projet de fin de module Spring Boot pour la gestion des stagiaires avec une architecture microservices complète.

## Architecture

### Services

- **Eureka Server** (Port 8762) - Service de découverte
- **API Gateway** (Port 9090) - Passerelle centralisée
- **Stagiaire Service** (Port 9091) - Gestion des stagiaires
- **Encadreur Service** (Port 9092) - Gestion des encadreurs
- **MySQL Database** (Port 3308) - Base de données

## Technologies Utilisées

- Spring Boot 3.4.1
- Spring Cloud 2024.0.1
- Spring Data JPA
- MySQL 8.0
- Docker & Docker Compose
- Swagger/OpenAPI 3
- Lombok
- Maven
- Configuration: application.properties

## Modèle de Données

### Stagiaire

- `id`: Long (Clé primaire, auto-générée)
- `nom`: String (Obligatoire)
- `prenom`: String (Obligatoire)
- `email`: String (Unique, obligatoire)
- `dateDebut`: LocalDate (Obligatoire)
- `dateFin`: LocalDate (Obligatoire)
- `encadreurId`: Long (Facultatif)

### Encadreur

- `id`: Long (Clé primaire, auto-générée)
- `nom`: String (Obligatoire)
- `prenom`: String (Obligatoire)
- `email`: String (Unique, obligatoire)
- `telephone`: String (Facultatif)

## Endpoints API

### Stagiaire Service (/api/stagiaires)

- `GET /api/stagiaires` - Récupérer tous les stagiaires
- `GET /api/stagiaires/{id}` - Récupérer un stagiaire par ID
- `POST /api/stagiaires` - Créer un nouveau stagiaire
- `PUT /api/stagiaires/{id}` - Mettre à jour un stagiaire
- `DELETE /api/stagiaires/{id}` - Supprimer un stagiaire
- `GET /api/stagiaires/encadreur/{encadreurId}` - Stagiaires par encadreur
- `GET /api/stagiaires/search?q={terme}` - Rechercher des stagiaires

### Encadreur Service (/api/encadreurs)

- `GET /api/encadreurs` - Récupérer tous les encadreurs
- `GET /api/encadreurs/{id}` - Récupérer un encadreur par ID
- `POST /api/encadreurs` - Créer un nouveau encadreur
- `PUT /api/encadreurs/{id}` - Mettre à jour un encadreur
- `DELETE /api/encadreurs/{id}` - Supprimer un encadreur
- `GET /api/encadreurs/search?q={terme}` - Rechercher des encadreurs

## Installation et Démarrage

### Prérequis

- Java 17+
- Maven 3.6+
- Docker & Docker Compose

### Démarrage avec Docker Compose (Recommandé)

1. **Cloner le projet**

```bash
git clone <repository-url>
cd intern_management
```

1. **Construire les applications**

```bash
mvn clean package -DskipTests
```

1. **Démarrer tous les services**

```bash
docker-compose up -d
```

1. **Vérifier le statut des services**

```bash
docker-compose ps
```

### Démarrage Manuel (Développement)

1. **Démarrer MySQL**

```bash
docker run -d --name mysql-intern \
  -e MYSQL_ROOT_PASSWORD=rootpassword \
  -e MYSQL_DATABASE=intern_management_db \
  -p 3308:3306 mysql:8.0
```

2. **Démarrer les services dans l'ordre**

```bash
# 1. Eureka Server
cd eureka-server && mvn spring-boot:run

# 2. API Gateway (nouveau terminal)
cd api-gateway && mvn spring-boot:run

# 3. Stagiaire Service (nouveau terminal)
cd stagiaire-service && mvn spring-boot:run

# 4. Encadreur Service (nouveau terminal)
cd encadreur-service && mvn spring-boot:run
```

## URLs d'Accès

- **Eureka Dashboard**: <http://localhost:8762>
- **API Gateway**: <http://localhost:9090>
- **Stagiaire Service**: <http://localhost:9091>
- **Encadreur Service**: <http://localhost:9092>
- **Swagger Stagiaire**: <http://localhost:9091/swagger-ui.html>
- **Swagger Encadreur**: <http://localhost:9092/swagger-ui.html>

## Accès via API Gateway

Tous les endpoints sont accessibles via l'API Gateway:

- **Stagiaires**: <http://localhost:9090/api/stagiaires>
- **Encadreurs**: <http://localhost:9090/api/encadreurs>

## Monitoring et Santé

Chaque service expose des endpoints Actuator:

- `/actuator/health` - État de santé du service
- `/actuator/info` - Informations sur le service
- `/actuator/metrics` - Métriques du service

## Tests

### Tester les APIs avec curl

**Lister tous les encadreurs:**

```bash
curl http://localhost:9090/encadreur-service/api/encadreurs
```

**Créer un encadreur:**

```bash
curl -X POST http://localhost:9090/encadreur-service/api/encadreurs \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Dupont",
    "prenom": "Jean",
    "email": "jean.dupont@technolab.com",
    "telephone": "0123456789"
  }'
```

**Créer un stagiaire:**

```bash
curl -X POST http://localhost:9090/stagiaire-service/api/stagiaires \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Nouveau",
    "prenom": "Stagiaire",
    "email": "nouveau.stagiaire@student.com",
    "dateDebut": "2025-01-01",
    "dateFin": "2025-06-01",
    "encadreurId": 1
  }'
```

## Arrêt des Services

```bash
# Avec Docker Compose
docker-compose down

# Supprimer les volumes (données)
docker-compose down -v
```

## Structure du Projet

```text
intern_management/
├── eureka-server/          # Service de découverte
├── api-gateway/            # Passerelle API
├── stagiaire-service/      # Service de gestion des stagiaires
├── encadreur-service/      # Service de gestion des encadreurs
├── docker-compose.yml      # Configuration Docker Compose
├── init-db.sql            # Script d'initialisation DB
├── start-services.sh       # Script de démarrage
├── stop-services.sh        # Script d'arrêt
└── README.md              # Documentation
```

## État Actuel du Projet

### ✅ Fonctionnalités Implémentées

- **Architecture Microservices Complète** : Eureka Server, API Gateway, Services métier
- **Découverte de Services** : Enregistrement automatique via Eureka
- **API Gateway** : Routage centralisé vers les microservices
- **APIs REST CRUD** : Opérations complètes pour Stagiaires et Encadreurs
- **Base de Données** : MySQL avec données de test pré-chargées
- **Documentation API** : Swagger UI disponible pour chaque service
- **Conteneurisation** : Docker et Docker Compose opérationnels
- **Configuration** : Migration vers application.properties

### 📊 Données de Test

- **Encadreurs** : 4 enregistrements disponibles
- **Stagiaires** : 7 enregistrements disponibles
- **Relations** : Stagiaires associés à leurs encadreurs

### 🚀 Services Opérationnels

- ✅ Eureka Server : <http://localhost:8762>
- ✅ API Gateway : <http://localhost:9090>
- ✅ Stagiaire Service : <http://localhost:9091>
- ✅ Encadreur Service : <http://localhost:9092>
- ✅ MySQL Database : localhost:3308

### 📝 Documentation API

- Swagger Stagiaire : <http://localhost:9091/swagger-ui/index.html>
- Swagger Encadreur : <http://localhost:9092/swagger-ui/index.html>

## Auteur

**Étudiant** : Amadou BATHILY
Projet réalisé dans le cadre du module Spring Boot - Microservices  
**Date limite** : Lundi 30/06/2025 à 23:59  
**Contact** : <ysalissou@gmail.com>
