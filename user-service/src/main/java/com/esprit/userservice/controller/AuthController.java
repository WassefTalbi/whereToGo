package com.esprit.userservice.controller;

import com.esprit.userservice.dto.AuthenticationRequest;
import com.esprit.userservice.dto.RegisterRequest;
import com.esprit.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("auth")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AuthController {

   private final UserService userService ;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthenticationRequest authenticationRequest) {
        String response = userService.login(authenticationRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/singup")
    public ResponseEntity<?> signup(  @ModelAttribute @Valid RegisterRequest userDto){

        return new ResponseEntity<>(userService.register(userDto), HttpStatus.CREATED);
    }
    @GetMapping("/test")
    public String getUserData() {
        return "This is user data.";
    }


}
