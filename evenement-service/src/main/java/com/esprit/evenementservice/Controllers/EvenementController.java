package com.esprit.evenementservice.Controllers;

import com.esprit.evenementservice.Entities.Evenement;
import com.esprit.evenementservice.Services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evenement")
@CrossOrigin("*")
public class EvenementController {
    @Autowired
    private EventService sevent;


    @PostMapping("/add")
    public Evenement addEvent(@RequestBody Evenement e){
        String image = e.getImage();

        e.setImage("event.jpg");
        return sevent.addEvent(e);

    }
    @GetMapping("/paged")
    public Page<Evenement> getEventsPaged(@RequestParam(defaultValue = "1") int page,
                                          @RequestParam(defaultValue = "10") int pageSize) {
        return sevent.getEvents(page, pageSize);
    }




    @PutMapping("/updateEvent/{id}")
    public Evenement updateEvent(@RequestBody Evenement e,@PathVariable("id")Long id){

        return sevent.updateEvent(e,id);
    }


    @DeleteMapping("/delete/{id}")
    public String deleteEvent(@PathVariable("id")Long id ){
        sevent.deleteEvent(id);
        return "this event was deleted with success";
    }
    @PutMapping("/updateRating/{id}")
    public Evenement updateEventRating(@PathVariable("id") Long id, @RequestParam("rating") int rating) {
        return sevent.updateEventRating(id, rating);
    }

    @GetMapping("/getbyid/{id}")
    public Evenement getEvent(@PathVariable("id")Long id){
        return sevent.getEvent(id);
    }


    @GetMapping("/getall")
    public List<Evenement> getEvents(){
        return sevent.getEvents();
    }

}
