package com.technolab.encadreur.controller;

import com.technolab.encadreur.entity.Encadreur;
import com.technolab.encadreur.service.EncadreurService;
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
@RequestMapping("/api/encadreurs")
@RequiredArgsConstructor
@Tag(name = "Encadreur", description = "API de gestion des encadreurs")
public class EncadreurController {

    private final EncadreurService encadreurService;

    @GetMapping
    @Operation(summary = "Récupérer tous les encadreurs", description = "Retourne la liste de tous les encadreurs")
    public ResponseEntity<List<Encadreur>> getAllEncadreurs() {
        List<Encadreur> encadreurs = encadreurService.getAllEncadreurs();
        return ResponseEntity.ok(encadreurs);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un encadreur par ID", description = "Retourne un encadreur spécifique par son ID")
    public ResponseEntity<Encadreur> getEncadreurById(
            @Parameter(description = "ID de l'encadreur") @PathVariable Long id) {
        return encadreurService.getEncadreurById(id)
                .map(encadreur -> ResponseEntity.ok(encadreur))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau encadreur", description = "Crée un nouveau encadreur")
    public ResponseEntity<Encadreur> createEncadreur(@Valid @RequestBody Encadreur encadreur) {
        try {
            Encadreur createdEncadreur = encadreurService.createEncadreur(encadreur);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEncadreur);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un encadreur", description = "Met à jour un encadreur existant")
    public ResponseEntity<Encadreur> updateEncadreur(
            @Parameter(description = "ID de l'encadreur") @PathVariable Long id,
            @Valid @RequestBody Encadreur encadreurDetails) {
        try {
            Encadreur updatedEncadreur = encadreurService.updateEncadreur(id, encadreurDetails);
            return ResponseEntity.ok(updatedEncadreur);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un encadreur", description = "Supprime un encadreur par son ID")
    public ResponseEntity<Void> deleteEncadreur(
            @Parameter(description = "ID de l'encadreur") @PathVariable Long id) {
        try {
            encadreurService.deleteEncadreur(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher des encadreurs", description = "Recherche des encadreurs par nom ou prénom")
    public ResponseEntity<List<Encadreur>> searchEncadreurs(
            @Parameter(description = "Terme de recherche") @RequestParam String q) {
        List<Encadreur> encadreurs = encadreurService.searchEncadreurs(q);
        return ResponseEntity.ok(encadreurs);
    }

}
