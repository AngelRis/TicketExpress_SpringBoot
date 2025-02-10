package com.example.ticketexpress.mapper;

import com.example.ticketexpress.dto.TicketDTO;
import com.example.ticketexpress.model.Ticket;
import com.example.ticketexpress.model.Timetable;
import com.example.ticketexpress.model.User;

public class TicketMapper {
    public static Ticket mapToTicket(TicketDTO ticketDTO, Timetable timetable, User user) {
        return Ticket.builder()
                .id(ticketDTO.getId())
                .roundTripTicket(ticketDTO.isRoundTripTicket())
                .date(ticketDTO.getDate())
                .price(ticketDTO.getPrice())
                .seatNumber(ticketDTO.getSeatNumber())
                .term(timetable)
                .user(user)
                .build();

    }
    public static TicketDTO mapToTicketDTO(Ticket ticket) {
        return TicketDTO.builder()
                .id(ticket.getId())
                .startingCity(ticket.getTerm().getStartingBusStation().getCity().getName())
                .finalCity(ticket.getTerm().getFinalBusStation().getCity().getName())
                .busOperatorName(ticket.getTerm().getBusOperator().getName())
                .roundTripTicket(ticket.isRoundTripTicket())
                .seatNumber(ticket.getSeatNumber())
                .price(ticket.getPrice())
                .date(ticket.getDate())
                .startingTime(ticket.getTerm().getStartTime())
                .username(ticket.getUser().getUsername())
                .userId(ticket.getUser().getId())
                .timetableId(ticket.getTerm().getId())
                .build();
    }
}
