package com.example.ticketexpress.service.impl;


import com.example.ticketexpress.model.City;
import com.example.ticketexpress.repository.CityRepository;
import com.example.ticketexpress.service.CityService;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;


@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {

        this.cityRepository = cityRepository;
    }

    @Override
    public List<City> getCities() {
        return cityRepository.findAll();
    }

    @PostConstruct
    @Override
    public void loadCitiesFromCSV() {
        if(cityRepository.count() == 0) {
            try (Reader reader = new InputStreamReader(new ClassPathResource("data/cities.csv").getInputStream())) {
                CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
                List<String[]> rows = csvReader.readAll();
                for (String[] row : rows) {
                    String [] values = row[0].split(";");
                    String name = values[0];
                    String country = values[1];
                    City city=City.builder().name(name).country(country).build();
                    cityRepository.save(city);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

}
