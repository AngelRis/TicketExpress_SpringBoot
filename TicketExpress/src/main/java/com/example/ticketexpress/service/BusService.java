package com.example.ticketexpress.service;

import com.example.ticketexpress.dto.BusDTO;
import com.example.ticketexpress.model.Bus;

import java.util.List;
import java.util.Optional;

public interface BusService {
    List<BusDTO> getAllBuses();
    Optional<BusDTO> findBusById(Long id);
    void deleteBus(Long id);
    void updateBus(BusDTO busDTO, Long id);
    Bus saveBus(BusDTO busDTO);

}
