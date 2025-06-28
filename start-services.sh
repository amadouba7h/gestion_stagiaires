#!/bin/bash

echo "🚀 Démarrage du projet de gestion des stagiaires"
echo "================================================="

# Vérifier si Docker est installé
if ! command -v docker &> /dev/null; then
    echo "❌ Docker n'est pas installé. Veuillez installer Docker pour continuer."
    exit 1
fi

# Vérifier si Docker Compose est installé
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose n'est pas installé. Veuillez installer Docker Compose pour continuer."
    exit 1
fi

echo "📦 Construction des applications Spring Boot..."
./mvnw clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "❌ Erreur lors de la construction des applications"
    exit 1
fi

echo "🐳 Démarrage des services avec Docker Compose..."
docker-compose up -d

echo "⏳ Attente du démarrage des services..."
sleep 30

echo "🔍 Vérification du statut des services..."
docker-compose ps

echo ""
echo "✅ Services démarrés avec succès !"
echo ""
echo "🌐 URLs d'accès :"
echo "  - Eureka Dashboard: http://localhost:8762"
echo "  - API Gateway: http://localhost:9090"
echo "  - Stagiaire Service: http://localhost:9091"
echo "  - Encadreur Service: http://localhost:9092"
echo "  - Swagger Stagiaire: http://localhost:9091/swagger-ui.html"
echo "  - Swagger Encadreur: http://localhost:9092/swagger-ui.html"
echo ""
echo "📚 Exemples d'utilisation :"
echo "  - Lister tous les stagiaires: curl http://localhost:9090/api/stagiaires"
echo "  - Lister tous les encadreurs: curl http://localhost:9090/api/encadreurs"
echo ""
echo "🛑 Pour arrêter les services: docker-compose down"
