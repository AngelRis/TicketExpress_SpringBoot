package com.example.ticketexpress.service;

import com.example.ticketexpress.model.City;

import java.util.List;

public interface CityService {
    List<City> getCities();
    void loadCitiesFromCSV();
}
