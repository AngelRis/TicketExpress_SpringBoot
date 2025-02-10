package com.example.ticketexpress.repository;

import com.example.ticketexpress.model.BusStation;
import com.example.ticketexpress.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusStationRepository extends JpaRepository<BusStation, Long> {
    Optional<BusStation> findBusStationByCity(City city);
}
