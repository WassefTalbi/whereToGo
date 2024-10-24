package com.esprit.evenementservice.Controllers;

import com.esprit.evenementservice.Entities.Evenement;
import com.esprit.evenementservice.Services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@CrossOrigin(origins = "http://localhost:4200")

public class EvenementController {
    @Autowired
    EventService sevent;


@PostMapping("/add")
public Evenement addEvent(@RequestBody Evenement e){
String image = e.getImage();

e.setImage(image.substring(12));
return sevent.addEvent(e);

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


@GetMapping("/getbyid/{id}")
  public Evenement getEvent(@PathVariable("id")Long id){
 return sevent.getEvent(id);
  }
  @GetMapping("/getall")
  public List<Evenement> getEvents(){
 return sevent.getEvents();
}
}
