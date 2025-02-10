package com.example.ticketexpress.service.impl;

import com.example.ticketexpress.dto.BusStationDTO;
import com.example.ticketexpress.exception.BusStationNotFoundException;
import com.example.ticketexpress.exception.CityNotFoundException;
import com.example.ticketexpress.exception.FileStorageException;
import com.example.ticketexpress.mapper.BusStationMapper;
import com.example.ticketexpress.model.BusStation;
import com.example.ticketexpress.model.City;
import com.example.ticketexpress.repository.BusStationRepository;
import com.example.ticketexpress.repository.CityRepository;
import com.example.ticketexpress.service.BusStationService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusStationServiceImpl implements BusStationService {
    private final BusStationRepository busStationRepository;
    private final CityRepository cityRepository;

    public BusStationServiceImpl(BusStationRepository busStationRepository, CityRepository cityRepository) {
        this.busStationRepository = busStationRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public List<BusStationDTO> getAllBusStations() {
        return busStationRepository.findAll()
                .stream().map(BusStationMapper::mapToBusStationDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<BusStationDTO> findBusStationById(Long id) {
        return busStationRepository.findById(id).map(BusStationMapper::mapToBusStationDTO);
    }

    @Override
    public void deleteBusStation(Long id) {
        busStationRepository.deleteById(id);
    }

    @Override
    public void updateBusStation(BusStationDTO busStationDTO, MultipartFile image, Long id) {
         BusStation busStation=busStationRepository.findById(id).orElseThrow(()->new BusStationNotFoundException(id));
        if(image!=null && !image.isEmpty()) {
            try {
                String imageFileName=image.getOriginalFilename();
                String uploadDir="src/main/resources/static/images/";
                Path path= Paths.get(uploadDir+'/'+imageFileName);
                Files.copy(image.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
                busStation.setImageUrl("/images/"+imageFileName);
            }catch (IOException e){
                throw new FileStorageException(image.getOriginalFilename(),e);
            }
        }
        City city=cityRepository.findById(busStationDTO.getCityId()).
                orElseThrow(()->new CityNotFoundException(busStationDTO.getCityId()));
        busStation.setCity(city);
        busStation.setName(busStationDTO.getName());
        busStation.setAddress(busStationDTO.getAddress());
        busStation.setLatitude(busStationDTO.getLatitude());
        busStation.setLongitude(busStationDTO.getLongitude());
        busStation.setPhone(busStationDTO.getPhone());
        busStationRepository.save(busStation);
    }

    @Override
    public BusStation saveBusStation(BusStationDTO busStationDTO, MultipartFile image) {
        City city=cityRepository.findById(busStationDTO.getCityId()).
                orElseThrow(()->new CityNotFoundException(busStationDTO.getCityId()));
        BusStation busStation=BusStationMapper.mapToBusStation(busStationDTO,city);
        if(image!=null && !image.isEmpty()) {
            try {
                String imageFileName=image.getOriginalFilename();
                String uploadDir="src/main/resources/static/images/";
                Path path= Paths.get(uploadDir+'/'+imageFileName);
                Files.copy(image.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
                busStation.setImageUrl("/images/"+imageFileName);
            }catch (IOException e){
                throw new FileStorageException(image.getOriginalFilename(),e);
            }
        }
        return busStationRepository.save(busStation);
    }

    @Override
    public Optional<BusStationDTO> findBusStationByCity(Long cityId) {
        if(cityId==null)
            return Optional.empty();
        City city=cityRepository.findById(cityId).orElseThrow(()->new CityNotFoundException(cityId));
        Optional<BusStation> busStation=busStationRepository.findBusStationByCity(city);
        return busStation.map(BusStationMapper::mapToBusStationDTO);
    }
}
