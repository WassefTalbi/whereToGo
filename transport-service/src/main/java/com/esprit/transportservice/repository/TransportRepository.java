package com.esprit.transportservice.repository;

import com.esprit.transportservice.entity.Transport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransportRepository extends JpaRepository<Transport,Long> {

}
