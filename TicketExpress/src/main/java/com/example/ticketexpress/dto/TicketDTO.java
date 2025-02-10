package com.example.ticketexpress.dto;

import com.example.ticketexpress.enumeration.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class TicketDTO {
    private Long id;
    private Long timetableId;
    private String startingCity;
    private String finalCity;
    private LocalTime startingTime;
    private String busOperatorName;
    private int price;
    private LocalDate date;
    private boolean roundTripTicket;
    private Long userId;
    private String username;
    private int seatNumber;
}
