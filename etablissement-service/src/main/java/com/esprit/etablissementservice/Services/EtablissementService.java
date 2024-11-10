package com.esprit.etablissementservice.Services;

import com.esprit.etablissementservice.Entities.Etablissement;
import com.esprit.etablissementservice.Repositories.EtablissementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtablissementService {
    @Autowired
    private EtablissementRepository etablissementRepository;

    public List<Etablissement> getAllEtablissements() {
        return etablissementRepository.findAll();
    }

    public Optional<Etablissement> getEtablissementById(Long id) {
        return etablissementRepository.findById(id);
    }

    public Etablissement createEtablissement(Etablissement etablissement) {
        return etablissementRepository.save(etablissement);
    }

    public Etablissement updateEtablissement(Long id, Etablissement updatedEtablissement) {
        Optional<Etablissement> existingEtablissement = etablissementRepository.findById(id);
        if (existingEtablissement.isPresent()) {
            Etablissement etablissement = existingEtablissement.get();
            etablissement.setNom(updatedEtablissement.getNom());
            etablissement.setAdresse(updatedEtablissement.getAdresse());
            etablissement.setType(updatedEtablissement.getType());
            etablissement.setImage(updatedEtablissement.getImage());
            return etablissementRepository.save(etablissement);
        }
        return null;
    }

    public void deleteEtablissement(Long id) {
        etablissementRepository.deleteById(id);
    }
}
