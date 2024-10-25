package com.esprit.reclamationservice.Repositories;

import com.esprit.reclamationservice.Entities.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
}
