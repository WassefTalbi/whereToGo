package com.esprit.etablissementservice.Repositories;

import com.esprit.etablissementservice.Entities.Etablissement;
import com.esprit.etablissementservice.Entities.Feature;
import com.esprit.etablissementservice.Entities.FeatureType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeatureRepository extends JpaRepository<Etablissement, Long> {
    Optional<Feature> findByFeatureName(FeatureType featureType);
}
