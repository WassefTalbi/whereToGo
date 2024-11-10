package com.esprit.evenementservice.Services;

import com.esprit.evenementservice.Entities.Evenement;
import com.esprit.evenementservice.Repositories.EvenementRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;

@Service
public class EventService  implements IEvent {
    @Autowired
    EvenementRepository erepo;


    @Override
    public Evenement addEvent(Evenement e) {
        // TODO Auto-generated method stub
        return erepo.save(e);
    }
    // Déclaration du logger
    private static final Logger logger = LoggerFactory.getLogger(EventService.class);
    @Scheduled(fixedRate = 60000)
    public void verifierEtSupprimerEvenementsExpires() {
        logger.info("Début de la vérification et suppression des événements expirés...");
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        List<Evenement> evenements = getEvents();
        logger.info("Nombre d'événements récupérés: {}", evenements.size()); // Log pour afficher le nombre d'événements récupérés
        for (Evenement evenement : evenements) {
            logger.info("Événement récupéré: {}", evenement); // Log pour afficher chaque événement récupéré

            // Si getEventDate() renvoie un java.util.Date
            LocalDateTime eventDateTime = evenement.getEventDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
            LocalDate eventDate = eventDateTime.toLocalDate();
            LocalTime eventTime = evenement.getHour();

            // Comparaison en ignorant les secondes
            if (eventDate.isEqual(currentDate) &&
                    eventTime.getHour() == currentTime.getHour() &&
                    eventTime.getMinute() == currentTime.getMinute()) {
                deleteEvent(evenement.getIdEvent());
                logger.info("Événement expiré supprimé: {}", evenement.getIdEvent());
            }
        }
        logger.info("Fin de la vérification et suppression des événements expirés.");
    }






    @Override
    public Evenement updateEvent(Evenement e, Long id) {
        Evenement ee= erepo.findById(id).get();
        ee.setAddress(e.getAddress());
        ee.setEventDate(e.getEventDate());
        ee.setHour(e.getHour());
        ee.setNbPlace(e.getNbPlace());
        ee.setName(e.getName());
        ee.setDescription(e.getDescription());
        ee.setPrice(e.getPrice());
        ee.setImage(e.getImage().substring(12));
        return erepo.save(ee);
    }

    public Evenement updateEventRating(Long id, int rating) {
        Evenement event = erepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Event not found"));

        // Mettre à jour la somme des évaluations et le nombre total d'évaluations
        int totalRating = event.getTotalRating() + rating;
        int numberOfRatings = event.getNumberOfRatings() + 1;

        // Calculer le rating moyen de l'événement
        double averageRating = (double) totalRating / numberOfRatings;

        // Arrondir le rating moyen au demi-entier le plus proche
        double roundedRating = Math.round(averageRating * 2) / 2.0;

        // Mettre à jour les propriétés de l'événement
        event.setTotalRating(totalRating);
        event.setNumberOfRatings(numberOfRatings);
        event.setRating(roundedRating);

        return erepo.save(event);
    }



    @Override
    public void deleteEvent(Long id) {
        // TODO Auto-generated method stub
        erepo.deleteById(id);

    }

    @Override
    public Evenement getEvent(Long id) {
        // TODO Auto-generated method stub
        return erepo.findById(id).get();
    }

    @Override
    public List<Evenement> getEvents() {
        // TODO Auto-generated method stub
        return erepo.findAll();
    }
    public Page<Evenement> getEvents(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return erepo.findAll(pageable);
    }
}