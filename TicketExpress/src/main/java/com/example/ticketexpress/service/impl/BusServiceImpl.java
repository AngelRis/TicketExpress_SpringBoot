package com.example.ticketexpress.service.impl;

import com.example.ticketexpress.dto.BusDTO;
import com.example.ticketexpress.exception.BusNotFoundException;
import com.example.ticketexpress.mapper.BusMapper;
import com.example.ticketexpress.model.Bus;
import com.example.ticketexpress.repository.BusRepository;
import com.example.ticketexpress.service.BusService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BusServiceImpl implements BusService {
    private final BusRepository busRepository;

    public BusServiceImpl(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    @Override
    public List<BusDTO> getAllBuses() {
        return busRepository.findAll()
                .stream()
                .map(BusMapper::mapToBusDTO).toList();
    }

    @Override
    public Optional<BusDTO> findBusById(Long id) {
        Optional<Bus> bus = busRepository.findById(id);
        return bus.map(BusMapper::mapToBusDTO);
    }

    @Override
    public void deleteBus(Long id) {
        busRepository.deleteById(id);
    }

    @Override
    public void updateBus(BusDTO busDTO,Long id) {
         Bus bus=busRepository.findById(id).orElseThrow(()->new BusNotFoundException(id));
         bus.setBrand(busDTO.getBrand());
         bus.setModel(busDTO.getModel());
         bus.setNumberOfSeats(busDTO.getNumberOfSeats());
         busRepository.save(bus);
    }

    @Override
    public Bus saveBus(BusDTO busDTO) {
        Bus bus = BusMapper.mapToBus(busDTO);
        return busRepository.save(bus);
    }
}
