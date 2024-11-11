package com.esprit.actualiteservice.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class ActualitéDTO {
    @Valid

    @NotBlank(message = "content is required and cannot be blank.")
    @Size(min=12,message = "content length min is 12 ")
    private String content;

}
