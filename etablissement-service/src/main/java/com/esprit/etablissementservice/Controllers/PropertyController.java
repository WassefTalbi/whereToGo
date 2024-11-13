package com.esprit.etablissementservice.Controllers;

import com.esprit.etablissementservice.Entities.Etablissement;
import com.esprit.etablissementservice.Services.EtablissementService;
import com.esprit.etablissementservice.dto.PropertyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("etablissement")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EtablissementController {
    private final EtablissementService propertyService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllProperties() {
        List<Etablissement> properties = propertyService.getAllProperties();
        return new ResponseEntity<>(properties, HttpStatus.OK);
    }
    @GetMapping("/owner")
    public ResponseEntity<?> getPropertiesByOwner(@AuthenticationPrincipal Jwt jwt) {
        String idOwner = jwt.getSubject();

        return new ResponseEntity<>(propertyService.getPropertiesByAgency(idOwner), HttpStatus.OK);
    }
    @GetMapping("/findById/{id}")
    public ResponseEntity<?> getPropertyById(@PathVariable Long id) {
        return new ResponseEntity<>(propertyService.getPropertyById(id), HttpStatus.OK);

    }

    @PostMapping("/add")
    public ResponseEntity<?> saveProperty(@ModelAttribute PropertyDTO propertyDTO, @AuthenticationPrincipal Jwt jwt) {
        return new ResponseEntity<>(propertyService.saveProperty(propertyDTO, jwt.getSubject()), HttpStatus.CREATED);
    }



    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProperty(@PathVariable Long id, @ModelAttribute PropertyDTO propertyDTO) {
        Etablissement updatedProperty = propertyService.updateProperty(id, propertyDTO);
        if (updatedProperty != null) {
            return new ResponseEntity<>(updatedProperty, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Property not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>  deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
