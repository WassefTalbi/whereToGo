package com.esprit.reclamationservice.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReclamationDTO {
    @Valid

    @NotBlank(message = "titre  is required and cannot be blank.")
    @Size(min=3,max = 25,message = "titre length min is 3 and max is 25")
    private String titre;
    @NotBlank(message = "description is required and cannot be blank.")
    @Size(min=12,message = "description length min is 3 and max is 25")
    private String description;
    private Long idUser;
}
