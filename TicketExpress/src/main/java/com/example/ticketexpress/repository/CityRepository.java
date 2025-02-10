package com.example.ticketexpress.repository;

import com.example.ticketexpress.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
