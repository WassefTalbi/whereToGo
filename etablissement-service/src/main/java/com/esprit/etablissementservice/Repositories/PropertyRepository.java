package com.esprit.etablissementservice.Repositories;

import com.esprit.etablissementservice.Entities.Etablissement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EtablissementRepository extends JpaRepository<Etablissement, Long> {
    List<Etablissement> findByIdOwner(String idOwner);
}
