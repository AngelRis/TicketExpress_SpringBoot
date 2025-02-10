package com.example.ticketexpress.service;

import com.example.ticketexpress.dto.TimetableDTO;
import com.example.ticketexpress.model.Timetable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TimetableService {
    List<TimetableDTO> getAllTimetable();
    Optional<TimetableDTO> findTimetableById(Long id);
    void deleteTimetable(Long id);
    void updateTimetable(TimetableDTO timetableDTO, Long id);
    Timetable saveTimetable(TimetableDTO timetableDTO);
    List<TimetableDTO> filterTimetables(Long startingCityId, Long finalCityId,Long busOperatorId);
    List<TimetableDTO> filterTimetables(Long startingCityId, Long finalCityId, LocalDate date);
}
