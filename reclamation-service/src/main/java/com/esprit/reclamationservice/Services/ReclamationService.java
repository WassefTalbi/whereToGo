package com.esprit.reclamationservice.Services;

import com.esprit.reclamationservice.DTO.ReclamationDTO;
import com.esprit.reclamationservice.Entities.Reclamation;
import com.esprit.reclamationservice.Entities.ReclamationStatus;
import com.esprit.reclamationservice.Repositories.ReclamationRepository;
import com.esprit.reclamationservice.modal.User;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReclamationService {

    private final ReclamationRepository reclamationRepository;
    private final MailService mailService;

    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }
    public List<Reclamation> MyReclamations(String idUser) {
        return reclamationRepository.findByIdUser(idUser);
    }

    public Optional<Reclamation> getReclamationById(Long id) {
        return reclamationRepository.findById(id);
    }

    public Reclamation createReclamation(ReclamationDTO reclamationDTO, String idUser) {
        Reclamation reclamation=new Reclamation();
        reclamation.setDescription(reclamationDTO.getDescription());
        reclamation.setTitre(reclamationDTO.getTitre());
        reclamation.setStatus(ReclamationStatus.EN_COURS);
        System.out.println("display the id of user"+idUser);
        reclamation.setIdUser(idUser);
        mailService.sendEmail("meryem.trabelsi@esprit.tn", "Nouvelle réclamation", "Votre réclamation a été créée.");

        return reclamationRepository.save(reclamation);
    }

    public Reclamation updateReclamation(Long id, ReclamationDTO reclamationDTO){
        if(id==null){
            throw new NotFoundException(" id must not be null");
        }
        Reclamation existingReclamation= reclamationRepository.findById(id).orElseThrow(()->new NotFoundException("interview not found with id :"+id));
        existingReclamation.setDescription(reclamationDTO.getDescription());
        existingReclamation.setTitre(reclamationDTO.getTitre());
        return reclamationRepository.save(existingReclamation);
    }
    public Reclamation updateReclamationStatus(Long idReclamation, ReclamationStatus newStatus) {
        if(idReclamation==null){
            throw new NotFoundException(" id must not be null");
        }
        Reclamation existingReclamation= reclamationRepository.findById(idReclamation).orElseThrow(()->new NotFoundException("interview not found with id :"+idReclamation));

        existingReclamation.setStatus(newStatus);
        return reclamationRepository.save(existingReclamation);
    }

    public void deleteReclamation(Long id) {
        Reclamation reclamation= reclamationRepository.findById(id).orElseThrow(()->new NotFoundException("reclamation not found"));
        reclamationRepository.deleteById(id);
    }

    private User convertDTO(User userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        return user;
    }

}
