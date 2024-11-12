package com.esprit.userservice.repository;

import com.esprit.userservice.entity.RoleType;
import com.esprit.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User>findByPhone(String mobileNumber);
    Optional<User>findByKeycloakId(String id);
    List<User> findByRoleRoleType(RoleType roleType);


}
