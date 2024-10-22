package com.esprit.reclamationservice.Services;

import com.esprit.reclamationservice.DTO.ReclamationDTO;
import com.esprit.reclamationservice.Entities.Reclamation;
import com.esprit.reclamationservice.Repositories.ReclamationRepository;
import com.esprit.reclamationservice.modal.User;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReclamationService {

    private final ReclamationRepository reclamationRepository;

    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }

    public Optional<Reclamation> getReclamationById(Long id) {
        return reclamationRepository.findById(id);
    }

    public Reclamation createReclamation(ReclamationDTO reclamationDTO, User user) {
        Reclamation reclamation=new Reclamation();
        reclamation.setDescription(reclamationDTO.getDescription());
        reclamation.setTitre(reclamationDTO.getTitre());

        reclamation.setIdUser(user.getIdUser());

        return reclamationRepository.save(reclamation);
    }
    public Reclamation updateReclamation(Long id, ReclamationDTO reclamationDTO){
        if(id==null){
            throw new NotFoundException(" id must not be null");
        }
        Reclamation existingReclamation= reclamationRepository.findById(id).orElseThrow(()->new NotFoundException("interview not found with id :"+id));
        existingReclamation.setDescription(reclamationDTO.getDescription());
        existingReclamation.setIdUser(reclamationDTO.getIdUser());
        existingReclamation.setTitre(reclamationDTO.getTitre());
        return reclamationRepository.save(existingReclamation);
    }

    public void deleteReclamation(Long id) {
        Reclamation reclamation= reclamationRepository.findById(id).orElseThrow(()->new NotFoundException("reclamation not found"));
        reclamationRepository.deleteById(id);
    }

    private Long getCurrentUser(){
        return 1L;
    }
}
