package com.example.ticketexpress.dto;

import com.example.ticketexpress.enumeration.DayOfWeek;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class TimetableDTO {
    private Long id;
    private Long startingBusStationId;
    private String startingCityName;
    private Long finalBusStationId;
    private String finalCityName;
    private Long busOperatorId;
    private String busOperatorName;
    private Long busId;
    private int numberOfSeatsBus;
    private int oneWayTicketPrice;
    private int twoWayTicketPrice;
    private LocalTime startTime;
    private List<DayOfWeek> daysOfWeek;

}