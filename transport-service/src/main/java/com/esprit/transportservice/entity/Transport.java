package com.esprit.transportservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transport {
    public enum TransportType {
        BUS, TRUCK, VAN, TAXI
    }

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(nullable = false, unique = true)
    private String vehicleNumber;

    @NotNull
    @Size(max = 100)
    private String driverName;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TransportType type;

    @Min(1)
    private int capacity;

    // Foreign keys to other entities
    private Long userId; // Reference to a User entity in the User Service
    private Long reclamationId; // Reference to a Reclamation entity in the Reclamation Service
    private Long evenementId; // Reference to an Evenement entity in the Evenement Service

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Version
    private int version;

    private boolean deleted = false;
}
