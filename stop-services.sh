#!/bin/bash

echo "🛑 Arrêt des services de gestion des stagiaires"
echo "==============================================="

echo "🐳 Arrêt des conteneurs Docker..."
docker-compose down

echo "🧹 Nettoyage des volumes (optionnel)..."
read -p "Voulez-vous supprimer les données de la base de données ? (y/N): " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    docker-compose down -v
    echo "✅ Volumes supprimés"
else
    echo "✅ Volumes conservés"
fi

echo "✅ Services arrêtés avec succès !"
