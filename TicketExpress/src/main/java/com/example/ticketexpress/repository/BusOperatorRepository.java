package com.example.ticketexpress.repository;

import com.example.ticketexpress.model.BusOperator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusOperatorRepository extends JpaRepository<BusOperator,Long> {
    List<BusOperator> findBusOperatorsByNameContainingIgnoreCase(String name);
}
