package com.esprit.transportservice.service;


import com.esprit.transportservice.entity.Transport;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.esprit.transportservice.repository.TransportRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TransportService {
    private final TransportRepository transportRepository;

    public List<Transport> getAllTransports() {
        return transportRepository.findAll();
    }

    public Transport getTransportById(Long id) {
        return transportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transport not found with id: " + id));
    }

    public Transport addTransport(Transport transport) {
        return transportRepository.save(transport);
    }

    public void deleteTransport(Long id) {
        transportRepository.deleteById(id);
    }
}