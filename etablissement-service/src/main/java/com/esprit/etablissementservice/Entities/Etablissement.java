package com.esprit.etablissementservice.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "etablissement")
public class Etablissement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String adresse;
    private String type;
    private String image;

    // Constructeurs, getters et setters

    public Etablissement() {}

    public Etablissement(String nom, String adresse, String type, String image) {
        this.nom = nom;
        this.adresse = adresse;
        this.type = type;
        this.image = image;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}
