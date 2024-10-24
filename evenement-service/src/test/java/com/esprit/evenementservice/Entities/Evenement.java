package com.esprit.evenementservice.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Evenement {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idEvent;
    private String address;
    private String description;
    private Date eventDate; // Utilisez java.sql.Date
    private String name;
    private Long nbPlace;
    private String image;
    private long price;
    private LocalTime hour;
    private double rating;
    private int totalRating; // Somme des évaluations
    private int numberOfRatings; // Nombre total d'évaluations
    private double averageRating; // Rating moyen

}
