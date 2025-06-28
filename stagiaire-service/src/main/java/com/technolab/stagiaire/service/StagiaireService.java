package com.technolab.stagiaire.service;

import com.technolab.stagiaire.entity.Stagiaire;
import com.technolab.stagiaire.repository.StagiaireRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StagiaireService {

    private final StagiaireRepository stagiaireRepository;

    public List<Stagiaire> getAllStagiaires() {
        return stagiaireRepository.findAll();
    }

    public Optional<Stagiaire> getStagiaireById(Long id) {
        return stagiaireRepository.findById(id);
    }

    public Stagiaire createStagiaire(Stagiaire stagiaire) {
        if (stagiaireRepository.existsByEmail(stagiaire.getEmail())) {
            throw new RuntimeException("Un stagiaire avec cet email existe déjà");
        }
        return stagiaireRepository.save(stagiaire);
    }

    public Stagiaire updateStagiaire(Long id, Stagiaire stagiaireDetails) {
        return stagiaireRepository.findById(id)
                .map(stagiaire -> {
                    // Vérifier si l'email est déjà utilisé par un autre stagiaire
                    if (!stagiaire.getEmail().equals(stagiaireDetails.getEmail()) &&
                        stagiaireRepository.existsByEmail(stagiaireDetails.getEmail())) {
                        throw new RuntimeException("Un stagiaire avec cet email existe déjà");
                    }
                    
                    stagiaire.setNom(stagiaireDetails.getNom());
                    stagiaire.setPrenom(stagiaireDetails.getPrenom());
                    stagiaire.setEmail(stagiaireDetails.getEmail());
                    stagiaire.setDateDebut(stagiaireDetails.getDateDebut());
                    stagiaire.setDateFin(stagiaireDetails.getDateFin());
                    stagiaire.setEncadreurId(stagiaireDetails.getEncadreurId());
                    
                    return stagiaireRepository.save(stagiaire);
                })
                .orElseThrow(() -> new RuntimeException("Stagiaire non trouvé avec l'id : " + id));
    }

    public void deleteStagiaire(Long id) {
        if (!stagiaireRepository.existsById(id)) {
            throw new RuntimeException("Stagiaire non trouvé avec l'id : " + id);
        }
        stagiaireRepository.deleteById(id);
    }

    public List<Stagiaire> getStagiairesByEncadreur(Long encadreurId) {
        return stagiaireRepository.findByEncadreurId(encadreurId);
    }

    public List<Stagiaire> searchStagiaires(String searchTerm) {
        return stagiaireRepository.findByNomOrPrenomContaining(searchTerm);
    }

}
