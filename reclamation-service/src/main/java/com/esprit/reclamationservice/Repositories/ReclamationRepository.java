package com.esprit.reclamationservice.Repositories;

import com.esprit.reclamationservice.Entities.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
    List<Reclamation> findByIdUser(String idUser);
}
