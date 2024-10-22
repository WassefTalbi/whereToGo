package com.esprit.userservice.controller;

import com.esprit.userservice.entity.User;
import com.esprit.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    private UserService userService;
    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable(value = "email") String email){
        return userService.getUserByEmail(email);
    }
    @GetMapping("/current-user")
    public User getUserByEmail(){
        User user=new User();
        user.setId(1L);
        user.setEmail("user@gmail.com");
        user.setFirstName("user");
        user.setLastName("user");
        return user ;
    }
}
