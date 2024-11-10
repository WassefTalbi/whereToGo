package com.esprit.transportservice.controller;

import com.esprit.transportservice.entity.Transport;
import com.esprit.transportservice.service.TransportService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/transport")
@CrossOrigin("*")
public class TransportController {
    private final TransportService transportService;

    @GetMapping
    public List<Transport> getAllTransports() {
        return transportService.getAllTransports();
    }

    @GetMapping("/{id}")
    public Transport getTransportById(@PathVariable Long id) {
        return transportService.getTransportById(id);
    }

    @PostMapping
    public Transport addTransport(@RequestBody Transport transport) {
        return transportService.addTransport(transport);
    }

    @DeleteMapping("/{id}")
    public void deleteTransport(@PathVariable Long id) {
        transportService.deleteTransport(id);
    }
}
