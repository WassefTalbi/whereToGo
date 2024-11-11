package com.esprit.userservice.controller;

import com.esprit.userservice.dto.*;
import com.esprit.userservice.entity.User;
import com.esprit.userservice.service.FileService;
import com.esprit.userservice.service.UserService;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/user")

@CrossOrigin("*")

@RequiredArgsConstructor
public class UserController {

   private final UserService userService ;

   private final FileService fileService;

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable(value = "email") String email){
        return userService.getUserByEmail(email);
    }
    @GetMapping("/findById/{idUser}")
    public User findUserById(@PathVariable(value = "idUser") Long idUser){
        return userService.findUserById(idUser);
    }
    @PostMapping("/register-Owner")
    public ResponseEntity<?> addOwner(  @ModelAttribute @Valid OwnerRequest ownerRequest){

        return new ResponseEntity<>(userService.createOwner(ownerRequest), HttpStatus.CREATED);
    }
    @GetMapping("/test")
    public String test() {
        return "This is user data.";

    }
    @GetMapping("/user-only")
    @PreAuthorize("hasRole('user')")
    public String getUserData() {
        return "This is user data.";
    }

    @GetMapping("/admin-only")
    @PreAuthorize("hasRole('admin')")
    public String getAdminData() {
        return "This is admin data.";
    }

    @GetMapping("/current-user-connected")
    public ResponseEntity<String> currentUserConnected(@AuthenticationPrincipal Jwt jwt) {

        return ResponseEntity.ok("response of testing current user auth "+jwt.getSubject());
    }

    @GetMapping("/all-owner")
    public List<User> getAllOwner(){
        return userService.findAllOwner();
    }
    @GetMapping("/all-client")
    public List<User> getAllClient(){
        return userService.findAllClient();
    }
    @DeleteMapping("/delete/{idUser}")
    public ResponseEntity<Map<String, String>> removeCategorie(@PathVariable Long idUser) {
        Map<String, String> response = new HashMap<>();
        try {
            userService.deleteUser(idUser);
            response.put("message", "user dropped successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NotFoundException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

    @PutMapping("/profile-manage")
    public ResponseEntity<?> profileManage(@ModelAttribute @Valid ProfileRequest profileRequest, Principal principal){
            return new ResponseEntity<>(userService.profileManage(profileRequest,principal.getName()), HttpStatus.OK);
    }
    @PutMapping("/owner-profile-manage")
    public ResponseEntity<?> profileAgencyManage(@ModelAttribute @Valid ProfileOwnerRequest profileRequest, Principal principal){
            return new ResponseEntity<>(userService.profileOwnerManage(profileRequest,principal.getName()), HttpStatus.OK);
    }
    @PostMapping("/upload-profile-picture")
    public ResponseEntity<Map<String, String>> uploadProfilePicture(@RequestParam("file") MultipartFile file, @RequestParam("email") String email) {
        String filePath = fileService.uploadFile(file);
       userService.uploadProfilePicture(email,filePath);

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Profile picture updated successfully");

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }


    @GetMapping("/image/{imageName}")
    public ResponseEntity<?> getImage(@PathVariable String imageName) {
        Path imagePath = Paths.get("src/main/resources/upload").resolve(imageName);
        try {
            org.springframework.core.io.Resource resource = new UrlResource(imagePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
