package com.example.ticketexpress.service;

import com.example.ticketexpress.dto.BusStationDTO;
import com.example.ticketexpress.model.BusStation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface BusStationService {
    List<BusStationDTO> getAllBusStations();
    Optional<BusStationDTO> findBusStationById(Long id);
    void deleteBusStation(Long id);
    void updateBusStation(BusStationDTO busStationDTO, MultipartFile image, Long id);
    BusStation saveBusStation(BusStationDTO busStationDTO, MultipartFile image);
    Optional<BusStationDTO> findBusStationByCity(Long cityId);
}
