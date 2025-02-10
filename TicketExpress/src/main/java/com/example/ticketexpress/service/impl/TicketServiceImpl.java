package com.example.ticketexpress.service.impl;

import com.example.ticketexpress.dto.TicketDTO;
import com.example.ticketexpress.enumeration.ShoppingCartStatus;
import com.example.ticketexpress.enumeration.TicketStatus;
import com.example.ticketexpress.exception.FullBusException;
import com.example.ticketexpress.exception.TimetableNotFoundException;
import com.example.ticketexpress.exception.WrongTermException;
import com.example.ticketexpress.mapper.TicketMapper;
import com.example.ticketexpress.model.ShoppingCart;
import com.example.ticketexpress.model.Ticket;
import com.example.ticketexpress.model.Timetable;
import com.example.ticketexpress.model.User;
import com.example.ticketexpress.repository.*;
import com.example.ticketexpress.service.TicketService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final TimetableRepository timetableRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, UserRepository userRepository, TimetableRepository timetableRepository, ShoppingCartRepository shoppingCartRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.timetableRepository = timetableRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public Ticket createTicket(TicketDTO ticketDTO) {
        Timetable timetable=timetableRepository.findById(ticketDTO.getTimetableId()).orElseThrow(()->new TimetableNotFoundException(ticketDTO.getTimetableId()));
        User user=userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        ticketDTO.setPrice(ticketDTO.isRoundTripTicket()?timetable.getTwoWayTicketPrice():timetable.getOneWayTicketPrice());
        if(ticketDTO.getDate().equals(LocalDate.now()) && timetable.getStartTime().isBefore(LocalTime.now())){
             throw new WrongTermException(timetable.getStartTime());
        }
        if(ticketRepository.findAllByStatusAndDateAndTerm(TicketStatus.CONFIRMED,ticketDTO.getDate(),timetable).size()<timetable.getBus().getNumberOfSeats())
        {
            Ticket ticket= TicketMapper.mapToTicket(ticketDTO,timetable,user);
            ticket.setStatus(TicketStatus.RESERVED);
            ticket=ticketRepository.save(ticket);
            ShoppingCart shoppingCart=user.getShoppingCarts().stream()
                            .filter(s->s.getStatus()== ShoppingCartStatus.ACTIVE)
                            .findFirst().get();
            shoppingCart.setTotalPrice(shoppingCart.getTotalPrice()+ticket.getPrice());
            shoppingCart.getTickets().add(ticket);
            shoppingCartRepository.save(shoppingCart);
            return ticket;
        }
        throw new FullBusException("Автобусот е полн, ве молиме изберете друго време.");
    }

}
