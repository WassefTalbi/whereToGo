package com.esprit.userservice.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "app_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    private String keycloakId;
    private String firstName;
    private String lastName;
    private String name;
    private String email;
    private String username;
    private String photoprofile;
    private String phone;
    private String address;
    private Integer sinceYear;
    private String description;
    @JsonIgnore
    private String password;
    @ManyToOne
    @JsonIgnore
    private Role role;
}

