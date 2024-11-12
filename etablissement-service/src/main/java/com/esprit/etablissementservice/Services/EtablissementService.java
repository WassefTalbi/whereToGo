package com.esprit.etablissementservice.Services;

import com.esprit.etablissementservice.Entities.Etablissement;
import com.esprit.etablissementservice.Entities.Feature;
import com.esprit.etablissementservice.Entities.FeatureType;
import com.esprit.etablissementservice.Repositories.EtablissementRepository;
import com.esprit.etablissementservice.Repositories.FeatureRepository;
import com.esprit.etablissementservice.dto.PropertyDTO;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EtablissementService {
    private final EtablissementRepository propertyRepository;
    private final FeatureRepository featureRepository;
    private final FileService fileService;


    public List<Etablissement> getAllProperties() {
        return propertyRepository.findAll();
    }
    public List<Etablissement> getPropertiesByAgency(String idOwner) {
        return propertyRepository.findByIdOwner(idOwner);
    }
    public Etablissement getPropertyById(Long id) {
        return propertyRepository.findById(id).orElseThrow(()->new NotFoundException("property not found"));
    }

    public Etablissement saveProperty(PropertyDTO propertyDTO, String idOwner) {

        Etablissement property = new Etablissement();
        property.setTitle(propertyDTO.getTitle());
        property.setType(propertyDTO.getType());
        property.setBedroom(propertyDTO.getBedroom());
        property.setBathroom(propertyDTO.getBathroom());
        property.setArea(propertyDTO.getArea());
        property.setPrice(propertyDTO.getPrice());
        property.setIdOwner(idOwner);
        property.setRequirement(propertyDTO.getRequirement());
        property.setLocation(propertyDTO.getLocation());
        List<Feature> features = propertyDTO.getAdditionalFeatures().stream()
                .map(feature -> featureRepository.findByFeatureName(FeatureType.valueOf(feature))
                        .orElseThrow(() -> new NotFoundException("Feature not found: " + feature)))
                .collect(Collectors.toList());
        property.setFeatures(features);
        String img= fileService.uploadFile(propertyDTO.getImage());
        property.setImage(img);
        return propertyRepository.save(property);
    }


    public Etablissement updateProperty(Long id, PropertyDTO propertyDTO) {
        Etablissement property = propertyRepository.findById(id).orElseThrow(()->new NotFoundException("property not found"));
        property.setTitle(propertyDTO.getTitle());
        property.setType(propertyDTO.getType());
        property.setBedroom(propertyDTO.getBedroom());
        property.setBathroom(propertyDTO.getBathroom());
        property.setArea(propertyDTO.getArea());
        property.setPrice(propertyDTO.getPrice());
        property.setRequirement(propertyDTO.getRequirement());
        property.setLocation(propertyDTO.getLocation());
        List<Feature> features = propertyDTO.getAdditionalFeatures().stream()
                .map(featureName -> featureRepository.findByFeatureName(FeatureType.valueOf(featureName))
                        .orElseThrow(() -> new NotFoundException("Feature not found: " + featureName)))
                .collect(Collectors.toList());
        property.setFeatures(features);
        if (propertyDTO.getImage() != null && !propertyDTO.getImage().isEmpty()) {
            String img= fileService.uploadFile(propertyDTO.getImage());
            property.setImage(img);
        }
        return propertyRepository.save(property);
    }

    public void deleteProperty(Long id) {
        propertyRepository.findById(id).orElseThrow(()->new NotFoundException("property not found"));
        propertyRepository.deleteById(id);
    }


}
