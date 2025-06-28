package com.technolab.encadreur.repository;

import com.technolab.encadreur.entity.Encadreur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EncadreurRepository extends JpaRepository<Encadreur, Long> {

    Optional<Encadreur> findByEmail(String email);

    @Query("SELECT e FROM Encadreur e WHERE e.nom LIKE %?1% OR e.prenom LIKE %?1%")
    List<Encadreur> findByNomOrPrenomContaining(String searchTerm);

    boolean existsByEmail(String email);

}
