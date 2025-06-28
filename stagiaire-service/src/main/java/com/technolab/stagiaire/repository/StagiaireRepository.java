package com.technolab.stagiaire.repository;

import com.technolab.stagiaire.entity.Stagiaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StagiaireRepository extends JpaRepository<Stagiaire, Long> {

    Optional<Stagiaire> findByEmail(String email);

    List<Stagiaire> findByEncadreurId(Long encadreurId);

    @Query("SELECT s FROM Stagiaire s WHERE s.nom LIKE %?1% OR s.prenom LIKE %?1%")
    List<Stagiaire> findByNomOrPrenomContaining(String searchTerm);

    boolean existsByEmail(String email);

}
