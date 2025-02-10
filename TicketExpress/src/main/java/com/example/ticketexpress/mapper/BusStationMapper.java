package com.example.ticketexpress.mapper;

import com.example.ticketexpress.dto.BusStationDTO;
import com.example.ticketexpress.model.BusStation;
import com.example.ticketexpress.model.City;

public class BusStationMapper {
    public static BusStation mapToBusStation(BusStationDTO busStationDTO, City city) {
        return BusStation.builder()
                .id(busStationDTO.getId())
                .name(busStationDTO.getName())
                .phone(busStationDTO.getPhone())
                .address(busStationDTO.getAddress())
                .imageUrl(busStationDTO.getImageUrl())
                .latitude(busStationDTO.getLatitude())
                .longitude(busStationDTO.getLongitude())
                .city(city)
                .build();
    }
    public static BusStationDTO mapToBusStationDTO(BusStation busStation) {
        return BusStationDTO.builder()
                .id(busStation.getId())
                .name(busStation.getName())
                .address(busStation.getAddress())
                .latitude(busStation.getLatitude())
                .longitude(busStation.getLongitude())
                .phone(busStation.getPhone())
                .imageUrl(busStation.getImageUrl())
                .cityName(busStation.getCity().getName())
                .cityId(busStation.getCity().getId())
                .build();
    }
}
