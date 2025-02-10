package com.example.ticketexpress.mapper;

import com.example.ticketexpress.dto.BusDTO;
import com.example.ticketexpress.model.Bus;

public class BusMapper {
    public static Bus mapToBus(BusDTO busDTO){
        return Bus.builder().
                id(busDTO.getId()).
                brand(busDTO.getBrand()).
                model(busDTO.getModel()).
                numberOfSeats(busDTO.getNumberOfSeats()).
                build();
    }
    public static BusDTO mapToBusDTO(Bus bus){
        return BusDTO.builder().
                id(bus.getId()).
                brand(bus.getBrand()).
                model(bus.getModel()).
                numberOfSeats(bus.getNumberOfSeats()).
                build();
    }
}
