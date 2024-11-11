package com.esprit.transportservice.DTO;

import lombok.Data;

@Data
public class TransportDTO {
    private Long id;
    private String vehicleNumber;
    private String driverName;
    private String type;
    private int capacity;
}
