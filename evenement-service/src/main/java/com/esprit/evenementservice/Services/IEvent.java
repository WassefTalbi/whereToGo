package com.esprit.evenementservice.Services;

import com.esprit.evenementservice.Entities.Evenement;

import java.util.List;

public interface IEvent {
    public Evenement addEvent(Evenement e);

public Evenement updateEvent(Evenement e , Long id);
public void deleteEvent(Long id);
public Evenement getEvent(Long id );
public List<Evenement> getEvents();
}
