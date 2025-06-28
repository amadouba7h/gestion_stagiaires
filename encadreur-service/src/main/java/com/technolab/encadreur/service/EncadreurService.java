package com.technolab.encadreur.service;

import com.technolab.encadreur.entity.Encadreur;
import com.technolab.encadreur.repository.EncadreurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EncadreurService {

    private final EncadreurRepository encadreurRepository;

    public List<Encadreur> getAllEncadreurs() {
        return encadreurRepository.findAll();
    }

    public Optional<Encadreur> getEncadreurById(Long id) {
        return encadreurRepository.findById(id);
    }

    public Encadreur createEncadreur(Encadreur encadreur) {
        if (encadreurRepository.existsByEmail(encadreur.getEmail())) {
            throw new RuntimeException("Un encadreur avec cet email existe déjà");
        }
        return encadreurRepository.save(encadreur);
    }

    public Encadreur updateEncadreur(Long id, Encadreur encadreurDetails) {
        return encadreurRepository.findById(id)
                .map(encadreur -> {
                    // Vérifier si l'email est déjà utilisé par un autre encadreur
                    if (!encadreur.getEmail().equals(encadreurDetails.getEmail()) &&
                        encadreurRepository.existsByEmail(encadreurDetails.getEmail())) {
                        throw new RuntimeException("Un encadreur avec cet email existe déjà");
                    }
                    
                    encadreur.setNom(encadreurDetails.getNom());
                    encadreur.setPrenom(encadreurDetails.getPrenom());
                    encadreur.setEmail(encadreurDetails.getEmail());
                    encadreur.setTelephone(encadreurDetails.getTelephone());
                    
                    return encadreurRepository.save(encadreur);
                })
                .orElseThrow(() -> new RuntimeException("Encadreur non trouvé avec l'id : " + id));
    }

    public void deleteEncadreur(Long id) {
        if (!encadreurRepository.existsById(id)) {
            throw new RuntimeException("Encadreur non trouvé avec l'id : " + id);
        }
        encadreurRepository.deleteById(id);
    }

    public List<Encadreur> searchEncadreurs(String searchTerm) {
        return encadreurRepository.findByNomOrPrenomContaining(searchTerm);
    }

}
