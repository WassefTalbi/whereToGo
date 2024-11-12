package com.esprit.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    @OneToMany(mappedBy = "role")
    private List<User> users;

}
