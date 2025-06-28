package com.technolab.stagiaire.controller;

import com.technolab.stagiaire.entity.Stagiaire;
import com.technolab.stagiaire.service.StagiaireService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stagiaires")
@RequiredArgsConstructor
@Tag(name = "Stagiaire", description = "API de gestion des stagiaires")
public class StagiaireController {

    private final StagiaireService stagiaireService;

    @GetMapping
    @Operation(summary = "Récupérer tous les stagiaires", description = "Retourne la liste de tous les stagiaires")
    public ResponseEntity<List<Stagiaire>> getAllStagiaires() {
        List<Stagiaire> stagiaires = stagiaireService.getAllStagiaires();
        return ResponseEntity.ok(stagiaires);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un stagiaire par ID", description = "Retourne un stagiaire spécifique par son ID")
    public ResponseEntity<Stagiaire> getStagiaireById(
            @Parameter(description = "ID du stagiaire") @PathVariable Long id) {
        return stagiaireService.getStagiaireById(id)
                .map(stagiaire -> ResponseEntity.ok(stagiaire))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau stagiaire", description = "Crée un nouveau stagiaire")
    public ResponseEntity<Stagiaire> createStagiaire(@Valid @RequestBody Stagiaire stagiaire) {
        try {
            Stagiaire createdStagiaire = stagiaireService.createStagiaire(stagiaire);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStagiaire);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un stagiaire", description = "Met à jour un stagiaire existant")
    public ResponseEntity<Stagiaire> updateStagiaire(
            @Parameter(description = "ID du stagiaire") @PathVariable Long id,
            @Valid @RequestBody Stagiaire stagiaireDetails) {
        try {
            Stagiaire updatedStagiaire = stagiaireService.updateStagiaire(id, stagiaireDetails);
            return ResponseEntity.ok(updatedStagiaire);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un stagiaire", description = "Supprime un stagiaire par son ID")
    public ResponseEntity<Void> deleteStagiaire(
            @Parameter(description = "ID du stagiaire") @PathVariable Long id) {
        try {
            stagiaireService.deleteStagiaire(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/encadreur/{encadreurId}")
    @Operation(summary = "Récupérer les stagiaires par encadreur", description = "Retourne tous les stagiaires d'un encadreur spécifique")
    public ResponseEntity<List<Stagiaire>> getStagiairesByEncadreur(
            @Parameter(description = "ID de l'encadreur") @PathVariable Long encadreurId) {
        List<Stagiaire> stagiaires = stagiaireService.getStagiairesByEncadreur(encadreurId);
        return ResponseEntity.ok(stagiaires);
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher des stagiaires", description = "Recherche des stagiaires par nom ou prénom")
    public ResponseEntity<List<Stagiaire>> searchStagiaires(
            @Parameter(description = "Terme de recherche") @RequestParam String q) {
        List<Stagiaire> stagiaires = stagiaireService.searchStagiaires(q);
        return ResponseEntity.ok(stagiaires);
    }

}
