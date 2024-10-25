package com.esprit.reclamationservice.Controllers;

import com.esprit.reclamationservice.DTO.ReclamationDTO;
import com.esprit.reclamationservice.Entities.Reclamation;
import com.esprit.reclamationservice.Entities.ReclamationStatus;
import com.esprit.reclamationservice.Services.ReclamationService;
import com.esprit.reclamationservice.feignClient.UserClient;
import com.esprit.reclamationservice.modal.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reclamations")
@RequiredArgsConstructor
public class ReclamationController {

        private final ReclamationService reclamationService;
        private final UserClient userClient;

        @GetMapping("/all")
        public List<Reclamation> getAllReclamations() {
            return reclamationService.getAllReclamations();
        }

        @GetMapping("/findbyid/{id}")
        public ResponseEntity<?> getReclamationById(@PathVariable Long id) {
            Optional<Reclamation> reclamation = reclamationService.getReclamationById(id);
            return reclamation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

        @PostMapping("/create")
        public ResponseEntity<?> createReclamation(@RequestBody @Valid ReclamationDTO reclamationDTO,@AuthenticationPrincipal Jwt jwt) {
            try {
                String idUser=jwt.getSubject();
                return new ResponseEntity<>( reclamationService.createReclamation(reclamationDTO,idUser), HttpStatus.OK);
            }
            catch (Exception e) {
                System.out.println("testing of create");
                System.out.println(e.getMessage());
                return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

        @PutMapping("/update/{idReclamation}")
        public ResponseEntity<?> updateReclamation(@PathVariable Long idReclamation, @RequestBody @Valid ReclamationDTO reclamationDTO) {
            try {
                return new ResponseEntity<>( reclamationService.updateReclamation(idReclamation,reclamationDTO), HttpStatus.OK);
            }
            catch (Exception e) {
                return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    @PutMapping("/update/status/{idReclamation}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> updateReclamationStatus(@PathVariable Long idReclamation, @RequestParam ReclamationStatus newStatus) {
       try {
            return new ResponseEntity<>(reclamationService.updateReclamationStatus(idReclamation, newStatus), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Reclamation not found", HttpStatus.NOT_FOUND);
        }
    }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<?> deleteReclamation(@PathVariable Long id) {
            reclamationService.deleteReclamation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

}
