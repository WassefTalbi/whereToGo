package com.esprit.evenementservice.Services;

import com.esprit.evenementservice.Entities.Evenement;
import com.esprit.evenementservice.Repositories.EvenementRepository;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Logger;

@Service
public class EventService  implements IEvent {
    @Autowired
    EvenementRepository erepo;

    @Override
    public Evenement addEvent(Evenement e) {
        // TODO Auto-generated method stub
        return erepo.save(e);
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
}
