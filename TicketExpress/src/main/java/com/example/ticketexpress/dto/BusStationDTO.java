package com.example.ticketexpress.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BusStationDTO {
    private Long id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String phone;
    private String imageUrl;
    private String cityName;
    private Long cityId;
}
