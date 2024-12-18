package com.esprit.userservice.service;


import com.esprit.userservice.dto.*;
import com.esprit.userservice.entity.Role;
import com.esprit.userservice.entity.RoleType;
import com.esprit.userservice.entity.User;
import com.esprit.userservice.exception.EmailExistsExecption;
import com.esprit.userservice.repository.RoleRepository;
import com.esprit.userservice.repository.UserRepository;
import com.esprit.userservice.securityconfig.KeycloakConfig;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${keycloak.client-secret}")
    private String clientSecret;
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final FileService fileService;
    private final PasswordEncoder passwordEncoder;
    public String login(AuthenticationRequest authenticationRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String body = "grant_type=password&username=" + authenticationRequest.getUsername() + "&password=" + authenticationRequest.getPassword() +
                "&client_id=login-app&client_secret="+clientSecret;
        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "http://localhost:9090/realms/whereToGo/protocol/openid-connect/token",
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        String responseBody = responseEntity.getBody();

        return responseBody;
    }

    public String register(RegisterRequest userDto) {

        UserRepresentation userRep= mapUserRep(userDto);
        Keycloak keycloak = KeycloakConfig.getInstance();
       List<UserRepresentation> usernameRepresentations = keycloak.realm("whereToGo").users().searchByUsername(userDto.getEmail(),true);
        List<UserRepresentation> emailRepresentations = keycloak.realm("whereToGo").users().searchByEmail(userDto.getEmail(),true);

        if(!(usernameRepresentations.isEmpty() && emailRepresentations.isEmpty())){
            throw new EmailExistsExecption("username or email already exists");
        }
        Response response = keycloak.realm("whereToGo").users().create(userRep);


        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed to create user");
        }
        String userId = CreatedResponseUtil.getCreatedId(response);
        User userEntity = convertUserToEntity(userDto);
        userEntity.setKeycloakId(userId);
        userRepository.save(userEntity);

        roleService.getRole(RoleType.user);
        roleService.assignRole(userId,RoleType.user);


        return "User created";




    }

    public String createOwner(OwnerRequest ownerRequest) {
        UserRepresentation userRep= mapOwnerRep(ownerRequest);

        Keycloak keycloak = KeycloakConfig.getInstance();
        List<UserRepresentation> usernameRepresentations = keycloak.realm("whereToGo").users().searchByUsername(ownerRequest.getEmail(),true);
        List<UserRepresentation> emailRepresentations = keycloak.realm("whereToGo").users().searchByEmail(ownerRequest.getEmail(),true);

        if(!(usernameRepresentations.isEmpty() && emailRepresentations.isEmpty())){
            throw new EmailExistsExecption("username or email already exists");
        }
        Response response = keycloak.realm("whereToGo").users().create(userRep);

        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed to create owner");
        }

        String userId = CreatedResponseUtil.getCreatedId(response);
        Role role=roleRepository.findByRoleType(RoleType.owner).get();
        User user=new User();
        user.setFirstName(ownerRequest.getName());
        user.setAddress(ownerRequest.getAddress());
        user.setDescription(ownerRequest.getDescription());
        user.setSinceYear(ownerRequest.getSinceYear());
        user.setPhone(ownerRequest.getMobileNumber());
        user.setPassword(passwordEncoder.encode(ownerRequest.getPassword()));
        user.setEmail(ownerRequest.getEmail());
        String photoName= fileService.uploadFile(ownerRequest.getLogo());
        user.setPhotoprofile(photoName);
        user.setRole(role);
        user.setKeycloakId(userId);
        userRepository.save(user);
        roleService.getRole(RoleType.owner);
        roleService.assignRole(userId,RoleType.owner);


        return "Owner created";




    }
    private UserRepresentation mapUserRep(RegisterRequest userDto) {
        UserRepresentation userRep = new UserRepresentation();
        userRep.setFirstName(userDto.getFirstName());
        userRep.setUsername(userDto.getEmail());
        userRep.setLastName(userDto.getLastName());
        userRep.setEmail(userDto.getEmail());
        userRep.setEnabled(true);
        userRep.setEmailVerified(true);

        Map<String, List<String>> attributes = new HashMap<>();
        if (userDto.getMobileNumber() != null) {
            List<String> mobileNumber = new ArrayList<>();
            mobileNumber.add(userDto.getMobileNumber());
            attributes.put("mobileNumber", mobileNumber);
        }
        userRep.setAttributes(attributes);

        if (userDto.getPassword() != null) {
            List<CredentialRepresentation> creds = new ArrayList<>();
            CredentialRepresentation cred = new CredentialRepresentation();
            cred.setTemporary(false);
            cred.setType(CredentialRepresentation.PASSWORD);
            cred.setValue(userDto.getPassword());
            creds.add(cred);
            userRep.setCredentials(creds);
        }
        return userRep;
    }
    private UserRepresentation mapOwnerRep(OwnerRequest ownerRequest) {
        UserRepresentation userRep = new UserRepresentation();
        userRep.setFirstName(ownerRequest.getName());
        userRep.setUsername(ownerRequest.getEmail());
        userRep.setEmail(ownerRequest.getEmail());
        userRep.setEnabled(true);
        userRep.setEmailVerified(true);

          Map<String, List<?>> attributes = new HashMap<>();

            List<String> mobileNumber = new ArrayList<>();
            mobileNumber.add(ownerRequest.getMobileNumber());
            attributes.put("mobileNumber", mobileNumber);

            List<String> description = new ArrayList<>();
            description.add(ownerRequest.getDescription());
            attributes.put("description", description);

            List<String> address = new ArrayList<>();
            address.add(ownerRequest.getAddress());
            attributes.put("address", address);

         /*   List<Integer> sinceYear = new ArrayList<>();
            sinceYear.add(ownerRequest.getSinceYear());
            attributes.put("sinceYear", sinceYear);
             userRep.setAttributes(attributes);*/


            List<CredentialRepresentation> creds = new ArrayList<>();
            CredentialRepresentation cred = new CredentialRepresentation();
            cred.setTemporary(false);
            cred.setType(CredentialRepresentation.PASSWORD);
            cred.setValue(ownerRequest.getPassword());
            creds.add(cred);
            userRep.setCredentials(creds);

        return userRep;
    }
    public void forgotPassword(String email) {
        Keycloak keycloak = KeycloakConfig.getInstance();
        List<UserRepresentation> emailRepresentations = keycloak.realm("whereToGo").users().searchByEmail(email,true);
        if (!emailRepresentations.isEmpty()) {
            UserRepresentation userRepresentation = emailRepresentations.get(0);

            try {
                UserResource userResource = keycloak.realm("whereToGo").users().get(userRepresentation.getId());
                List<String> actions = new ArrayList<>();
                actions.add("UPDATE_PASSWORD");
                userResource.executeActionsEmail(actions);

                System.out.println("Password reset email sent successfully.");
            } catch (Exception e) {
                System.err.println("Error resetting password: " + e.getMessage());
                throw new RuntimeException("Failed to reset password.");
            }
        } else {
            System.err.println("User with username '" + email + "' not found.");
            throw new RuntimeException("User not found.");
        }
    }


    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->  new RuntimeException("User not found with email " + email));
    }


    public List<User>findAllOwner( ){
        List<User> agencies = userRepository.findByRoleRoleType(RoleType.owner);
        return agencies;
    }
    public List<User>findAllClient( ){
        List<User> clients = userRepository.findByRoleRoleType(RoleType.user);
        return clients;
    }


    public User findUserByIdKeycloak(String idUser){
        Optional<User> user= userRepository.findByKeycloakId(idUser);
        if (!user.isPresent()){
            throw new NotFoundException("no user found");
        }
        return user.get();
    }

    public void deleteUser(Long idUser){
        User user= userRepository.findById(idUser).orElseThrow(()->new NotFoundException(" no user found"));
        userRepository.deleteById(idUser);
    }

    public User profileManage(ProfileRequest profileRequest, String email) {

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

            user.setFirstName(profileRequest.getFirstName());
            user.setLastName(profileRequest.getLastName());
            user.setPhone(profileRequest.getMobileNumber());
            user.setEmail(profileRequest.getEmail());
            return userRepository.save(user);

    }
    public User profileOwnerManage(ProfileOwnerRequest profileRequest, String email) {

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

            user.setName(profileRequest.getName());
            user.setPhone(profileRequest.getMobileNumber());
            user.setEmail(profileRequest.getEmail());
            return userRepository.save(user);

    }

    public void uploadProfilePicture(String email,String filePath){
        User user = getUserByEmail(email);
        user.setPhotoprofile(filePath);
        userRepository.save(user);
    }

    private User convertUserToEntity(RegisterRequest userDto) {
        Role role  = roleRepository.findByRoleType(RoleType.user).get();
        User user=new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getMobileNumber());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        String photoName= fileService.uploadFile(userDto.getPhotoProfile());
        user.setPhotoprofile(photoName);
        user.setRole(role);
        return user;
    }
}




