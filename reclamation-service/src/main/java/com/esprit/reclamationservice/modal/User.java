package com.esprit.reclamationservice.modal;

import lombok.Data;

@Data
public class User {
    private Long idUser;
    private String email;
    private String firstName;
    private String lastName;
}
