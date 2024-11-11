package com.esprit.userservice;

import com.esprit.userservice.entity.Role;
import com.esprit.userservice.entity.RoleType;
import com.esprit.userservice.entity.User;
import com.esprit.userservice.repository.RoleRepository;
import com.esprit.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
@EnableFeignClients
@RequiredArgsConstructor
public class UserServiceApplication implements CommandLineRunner {
    private  final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
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
            User admin=new User();
            Role role=roleRepository.findByRoleType(RoleType.admin).get();
            admin.setFirstName("admin");
            admin.setLastName("admin");
            admin.setEmail("admin@1waydev.tn");
            admin.setPhone("28598395");
            admin.setRoles(List.of(role));
            admin.setPassword(passwordEncoder.encode("adminADMIN#1919"));
            userRepository.save(admin);

        }

    }
}
