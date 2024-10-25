package com.esprit.reclamationservice.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data

public class Reclamation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String description;
    private LocalDate createDate;
    private LocalDate updateDate;
    private String idUser;
    @Enumerated(EnumType.STRING)
    private ReclamationStatus status;
    @PrePersist
    public void prePersist() {
        this.createDate = LocalDate.now();
    }
    @PreUpdate
    public void preUpdate() {
        this.updateDate = LocalDate.now();
    }

}
