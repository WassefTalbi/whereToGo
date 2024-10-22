package controller;

import entity.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    @GetMapping("/current-user")
    public User getUser(){
        User user=new User();
        user.setId(1L);
        user.setEmail("user@gmail.com");
        user.setFirstName("user");
        user.setLastName("user");
        return user ;
    }
}
