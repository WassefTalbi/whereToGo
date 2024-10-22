package com.esprit.actualiteservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDate dateComment;
    private Long idUser;
    @PrePersist
    protected void onCreate() {
        this.dateComment = LocalDate.now();
    }
    @ManyToOne
    @JsonIgnore
    private Post post;
}

