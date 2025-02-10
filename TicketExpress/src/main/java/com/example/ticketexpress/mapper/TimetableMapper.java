package com.example.ticketexpress.mapper;

import com.example.ticketexpress.dto.TimetableDTO;
import com.example.ticketexpress.model.Bus;
import com.example.ticketexpress.model.BusOperator;
import com.example.ticketexpress.model.BusStation;
import com.example.ticketexpress.model.Timetable;

public class TimetableMapper {

    public static Timetable mapToTimetable(TimetableDTO timetableDTO, BusStation startingBusStation, BusStation finalBusStation, BusOperator busOperator, Bus bus) {
          return Timetable.builder()
                  .id(timetableDTO.getId())
                  .startingBusStation(startingBusStation)
                  .finalBusStation(finalBusStation)
                  .busOperator(busOperator)
                  .bus(bus)
                  .oneWayTicketPrice(timetableDTO.getOneWayTicketPrice())
                  .twoWayTicketPrice(timetableDTO.getTwoWayTicketPrice())
                  .dayOfWeek(timetableDTO.getDaysOfWeek())
                  .startTime(timetableDTO.getStartTime())
                  .build();
    }
    public static TimetableDTO mapToTimetableDTO(Timetable timetable) {
        return TimetableDTO.builder()
                .id(timetable.getId())
                .startingBusStationId(timetable.getStartingBusStation().getId())
                .startingCityName(timetable.getStartingBusStation().getCity().getName())
                .finalBusStationId(timetable.getFinalBusStation().getId())
                .finalCityName(timetable.getFinalBusStation().getCity().getName())
                .busId(timetable.getBus().getId())
                .numberOfSeatsBus(timetable.getBus().getNumberOfSeats())
                .busOperatorId(timetable.getBusOperator().getId())
                .busOperatorName(timetable.getBusOperator().getName())
                .daysOfWeek(timetable.getDayOfWeek())
                .oneWayTicketPrice(timetable.getOneWayTicketPrice())
                .twoWayTicketPrice(timetable.getTwoWayTicketPrice())
                .startTime(timetable.getStartTime())
                .build();
    }
}
