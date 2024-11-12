package com.esprit.userservice;

import com.esprit.userservice.dto.RegisterRequest;
import com.esprit.userservice.entity.Role;
import com.esprit.userservice.entity.RoleType;
import com.esprit.userservice.entity.User;
import com.esprit.userservice.exception.EmailExistsExecption;
import com.esprit.userservice.repository.RoleRepository;
import com.esprit.userservice.repository.UserRepository;
import com.esprit.userservice.securityconfig.KeycloakConfig;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Stream;

@SpringBootApplication
@EnableFeignClients
@RequiredArgsConstructor
public class UserServiceApplication implements CommandLineRunner {
    private  final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Keycloak keycloakInstance = KeycloakConfig.getInstance();
    private static final String REALM_NAME = "whereToGo";
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        if (roleRepository.count() == 0) {
            Stream.of(RoleType.admin, RoleType.user,RoleType.owner)
                    .forEach(roleType -> {
                        Role role= new Role();
                        role.setRoleType(roleType);
                        roleRepository.save(role);
                    });
            Role role=roleRepository.findByRoleType(RoleType.admin).get();
            User admin=new User();
            admin.setFirstName("admin");
            admin.setLastName("admin");
            admin.setEmail("admin@wheretogo.tn");
            admin.setPhone("28598395");
            admin.setRole(role);
            admin.setPassword("adminADMIN#1920");
            UserRepresentation userRep= mapUserRep(admin);
            Keycloak keycloak = KeycloakConfig.getInstance();
            List<UserRepresentation> usernameRepresentations = keycloak.realm("whereToGo").users().searchByUsername(admin.getEmail(),true);
            List<UserRepresentation> emailRepresentations = keycloak.realm("whereToGo").users().searchByEmail(admin.getEmail(),true);

            if(!(usernameRepresentations.isEmpty() && emailRepresentations.isEmpty())){
                throw new EmailExistsExecption("username or email already exists");
            }
            Response response = keycloak.realm("whereToGo").users().create(userRep);


            if (response.getStatus() != 201) {
                throw new RuntimeException("Failed to create user");
            }
            String userId = CreatedResponseUtil.getCreatedId(response);
            admin.setKeycloakId(userId);
            admin.setPassword(passwordEncoder.encode("adminADMIN#1920"));
            userRepository.save(admin);
            assignRole(userId,RoleType.admin);




        }
    }

    private UserRepresentation mapUserRep(User user) {
        UserRepresentation userRep = new UserRepresentation();
        userRep.setFirstName(user.getFirstName());
        userRep.setUsername(user.getEmail());
        userRep.setLastName(user.getLastName());
        userRep.setEmail(user.getEmail());
        userRep.setEnabled(true);
        userRep.setEmailVerified(true);

        Map<String, List<String>> attributes = new HashMap<>();
        if (user.getPhone() != null) {
            List<String> mobileNumber = new ArrayList<>();
            mobileNumber.add(user.getPhone());
            attributes.put("mobileNumber", mobileNumber);
        }
        userRep.setAttributes(attributes);

        if (user.getPassword() != null) {
            List<CredentialRepresentation> creds = new ArrayList<>();
            CredentialRepresentation cred = new CredentialRepresentation();
            cred.setTemporary(false);
            cred.setType(CredentialRepresentation.PASSWORD);
            cred.setValue(user.getPassword());
            creds.add(cred);
            userRep.setCredentials(creds);
        }
        return userRep;
    }
    private void assignRole(String userId, RoleType roleName) {
        try {
            String roleNameStr = roleName.toString();

            UserResource userResource = keycloakInstance.realm(REALM_NAME).users().get(userId);
            RolesResource rolesResource = keycloakInstance.realm(REALM_NAME).roles();
            RoleRepresentation representation = rolesResource.get(roleNameStr).toRepresentation();
            userResource.roles().realmLevel().add(Collections.singletonList(representation));

        } catch (Exception e) {
            System.out.println("Error assigning role '{}' to user '{}'"+ e);
        }
    }

}
