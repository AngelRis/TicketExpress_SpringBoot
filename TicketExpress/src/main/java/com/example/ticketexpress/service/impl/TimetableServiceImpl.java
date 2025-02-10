package com.example.ticketexpress.service.impl;

import com.example.ticketexpress.dto.TimetableDTO;
import com.example.ticketexpress.enumeration.DayOfWeek;
import com.example.ticketexpress.exception.*;
import com.example.ticketexpress.mapper.TimetableMapper;
import com.example.ticketexpress.model.*;
import com.example.ticketexpress.repository.*;
import com.example.ticketexpress.service.TimetableService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TimetableServiceImpl implements TimetableService {
    private final TimetableRepository timetableRepository;
    private final BusStationRepository busStationRepository;
    private final BusRepository busRepository;
    private final BusOperatorRepository busOperatorRepository;
    private final CityRepository cityRepository;

    public TimetableServiceImpl(TimetableRepository timetableRepository, BusStationRepository busStationRepository, BusRepository busRepository, BusOperatorRepository busOperatorRepository, CityRepository cityRepository) {
        this.timetableRepository = timetableRepository;
        this.busStationRepository = busStationRepository;
        this.busRepository = busRepository;
        this.busOperatorRepository = busOperatorRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public List<TimetableDTO> getAllTimetable() {
        return timetableRepository.findAll().stream()
                .map(TimetableMapper::mapToTimetableDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<TimetableDTO> findTimetableById(Long id) {
        return timetableRepository.findById(id).map(TimetableMapper::mapToTimetableDTO);
    }

    @Override
    public void deleteTimetable(Long id) {
        timetableRepository.deleteById(id);
    }

    @Override
    public void updateTimetable(TimetableDTO timetableDTO, Long id) {
         Timetable timetable = timetableRepository.findById(id).orElseThrow(()->new TimetableNotFoundException(id));
         BusStation startingBusStation=busStationRepository.
                 findById(timetableDTO.getStartingBusStationId()).orElseThrow(()->new BusStationNotFoundException(timetableDTO.getStartingBusStationId()));
         if(!Objects.equals(timetableDTO.getStartingBusStationId(), timetable.getStartingBusStation().getId())){
             timetable.getStartingBusStation().getStarting().remove(timetable);
             busStationRepository.save(timetable.getStartingBusStation());
             startingBusStation.getStarting().add(timetable);
         }
         timetable.setStartingBusStation(startingBusStation);
         BusStation finalBusStation=busStationRepository.
                 findById(timetableDTO.getFinalBusStationId()).orElseThrow(()->new BusStationNotFoundException(timetableDTO.getFinalBusStationId()));
         if(!Objects.equals(timetableDTO.getFinalBusStationId(), timetable.getFinalBusStation().getId())){
             timetable.getFinalBusStation().getEnding().remove(timetable);
             busStationRepository.save(timetable.getFinalBusStation());
             finalBusStation.getStarting().add(timetable);
         }
         timetable.setFinalBusStation(finalBusStation);
         Bus bus=busRepository.findById(timetableDTO.getBusId()).orElseThrow(()->new BusNotFoundException(timetableDTO.getBusId()));
         if(!Objects.equals(timetableDTO.getBusId(), timetable.getBus().getId())){
             timetable.getBus().getTimetables().remove(timetable);
             busRepository.save(timetable.getBus());
             bus.getTimetables().add(timetable);
         }
         timetable.setBus(bus);
         BusOperator busOperator=busOperatorRepository.findById(timetableDTO.getBusOperatorId()).orElseThrow(()->new BusOperatorNotFoundException(timetableDTO.getBusOperatorId()));
         if(!Objects.equals(timetableDTO.getBusOperatorId(), timetable.getBusOperator().getId())){
             timetable.getBusOperator().getTimetableList().remove(timetable);
             busOperatorRepository.save(timetable.getBusOperator());
             busOperator.getTimetableList().add(timetable);
         }
         timetable.setBusOperator(busOperator);
         timetable.setOneWayTicketPrice(timetableDTO.getOneWayTicketPrice());
         timetable.setTwoWayTicketPrice(timetableDTO.getTwoWayTicketPrice());
         timetable.setDayOfWeek(timetableDTO.getDaysOfWeek());
         timetable.setStartTime(timetableDTO.getStartTime());

         busStationRepository.save(startingBusStation);
         busStationRepository.save(finalBusStation);
         busRepository.save(bus);
         busOperatorRepository.save(busOperator);
         timetableRepository.save(timetable);
    }

    @Override
    public Timetable saveTimetable(TimetableDTO timetableDTO) {
        BusStation startingBusStation=busStationRepository.
                findById(timetableDTO.getStartingBusStationId()).orElseThrow(()->new BusStationNotFoundException(timetableDTO.getStartingBusStationId()));
        BusStation finalBusStation=busStationRepository.
                findById(timetableDTO.getFinalBusStationId()).orElseThrow(()->new BusStationNotFoundException(timetableDTO.getFinalBusStationId()));
        Bus bus=busRepository.findById(timetableDTO.getBusId()).orElseThrow(()->new BusNotFoundException(timetableDTO.getBusId()));
        BusOperator busOperator=busOperatorRepository.findById(timetableDTO.getBusOperatorId()).orElseThrow(()->new BusOperatorNotFoundException(timetableDTO.getBusOperatorId()));
        Timetable timetable=TimetableMapper.mapToTimetable(timetableDTO,startingBusStation,finalBusStation,busOperator,bus);
        timetableRepository.save(timetable);
        startingBusStation.getStarting().add(timetable);
        finalBusStation.getEnding().add(timetable);
        busOperator.getTimetableList().add(timetable);
        bus.getTimetables().add(timetable);
        busStationRepository.save(startingBusStation);
        busStationRepository.save(finalBusStation);
        busOperatorRepository.save(busOperator);
        busRepository.save(bus);
        return timetable;
    }

    @Override
    public List<TimetableDTO> filterTimetables(Long startingCityId, Long finalCityId, Long busOperatorId) {
        if(startingCityId!=null&&finalCityId!=null&&busOperatorId!=null) {
            City startingCity=cityRepository.findById(startingCityId).orElseThrow(()->new CityNotFoundException(startingCityId));
            City finalCity=cityRepository.findById(finalCityId).orElseThrow(()->new CityNotFoundException(finalCityId));
            BusOperator busOperator=busOperatorRepository.findById(busOperatorId).orElseThrow(()->new BusOperatorNotFoundException(busOperatorId));
            return timetableRepository.findAllByStartingBusStation_CityAndFinalBusStation_CityAndBusOperator(startingCity,finalCity,busOperator)
                    .stream().map(TimetableMapper::mapToTimetableDTO).collect(Collectors.toList());
        } else if (startingCityId!=null && finalCityId!=null) {
            City startingCity=cityRepository.findById(startingCityId).orElseThrow(()->new CityNotFoundException(startingCityId));
            City finalCity=cityRepository.findById(finalCityId).orElseThrow(()->new CityNotFoundException(finalCityId));
            return timetableRepository.findAllByStartingBusStation_CityAndFinalBusStation_City(startingCity,finalCity)
                    .stream().map(TimetableMapper::mapToTimetableDTO).collect(Collectors.toList());
        }else return getAllTimetable();
    }

    @Override
    public List<TimetableDTO> filterTimetables(Long startingCityId, Long finalCityId, LocalDate date) {
        City startingCity=cityRepository.findById(startingCityId).orElseThrow(()->new CityNotFoundException(startingCityId));
        City finalCity=cityRepository.findById(finalCityId).orElseThrow(()->new CityNotFoundException(finalCityId));
        String dayOfWeek=date.getDayOfWeek().getDisplayName(TextStyle.FULL,new Locale("mk","MK"));
        return timetableRepository.findAllByStartingBusStation_CityAndFinalBusStation_CityAndDayOfWeekContaining(startingCity,finalCity,DayOfWeek.valueOf(dayOfWeek)).
                stream().map(TimetableMapper::mapToTimetableDTO).collect(Collectors.toList());
    }
}
