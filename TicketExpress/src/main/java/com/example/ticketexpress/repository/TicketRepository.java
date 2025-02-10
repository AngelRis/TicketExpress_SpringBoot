package com.example.ticketexpress.repository;

import com.example.ticketexpress.enumeration.TicketStatus;
import com.example.ticketexpress.model.Ticket;

import com.example.ticketexpress.model.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByStatusAndDateAndTerm(TicketStatus status,LocalDate date, Timetable term);
}
