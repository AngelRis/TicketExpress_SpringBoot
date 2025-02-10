package com.example.ticketexpress.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BusDTO {
    private Long id;
    private String brand;
    private String model;
    private int numberOfSeats;
}
