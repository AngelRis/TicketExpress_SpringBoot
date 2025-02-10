package com.example.ticketexpress.service;

import com.example.ticketexpress.dto.TicketDTO;
import com.example.ticketexpress.model.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketService {
    Ticket createTicket(TicketDTO ticketDTO);

}
