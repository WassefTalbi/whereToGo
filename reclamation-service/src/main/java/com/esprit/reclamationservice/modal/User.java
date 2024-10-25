package com.esprit.reclamationservice.modal;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
}
