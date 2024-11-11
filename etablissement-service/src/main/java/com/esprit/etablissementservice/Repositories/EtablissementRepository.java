package com.esprit.etablissementservice.Repositories;

import com.esprit.etablissementservice.Entities.Etablissement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtablissementRepository extends JpaRepository<Etablissement, Long> {
}
