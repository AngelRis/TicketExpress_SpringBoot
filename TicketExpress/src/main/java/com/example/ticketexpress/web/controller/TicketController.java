package com.example.ticketexpress.web.controller;

import com.example.ticketexpress.dto.TicketDTO;
import com.example.ticketexpress.exception.FullBusException;
import com.example.ticketexpress.exception.WrongTermException;
import com.example.ticketexpress.service.BusStationService;
import com.example.ticketexpress.service.CityService;
import com.example.ticketexpress.service.TicketService;
import com.example.ticketexpress.service.TimetableService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;

@Controller
@RequestMapping("tickets")
public class TicketController {
    private final BusStationService busStationService;
    private final TicketService ticketService;
    private final TimetableService timetableService;
    private final CityService cityService;


    public TicketController(BusStationService busStationService, TicketService ticketService, TimetableService timetableService, CityService cityService) {
        this.busStationService = busStationService;
        this.ticketService = ticketService;
        this.timetableService = timetableService;
        this.cityService = cityService;
    }

    @GetMapping
    public String createTicket(Model model,
                               @RequestParam(required = false) String error,
                               @RequestParam(required = false) Long startingCityId,
                               @RequestParam(required = false) Long finishCityId,
                               @RequestParam(required = false)LocalDate date)
    {
       if(error != null) {
           model.addAttribute("error", error);
       }
       model.addAttribute("cities",cityService.getCities());
       if(startingCityId!=null&&finishCityId!=null&&date!=null) {
           model.addAttribute("timetables",timetableService.filterTimetables(startingCityId,finishCityId,date));
       }
       else {
           model.addAttribute("timetables",new ArrayList<>());
       }
       model.addAttribute("selectedDate",date);
       model.addAttribute("ticket",new TicketDTO());
       model.addAttribute("startingCityId",startingCityId);
       model.addAttribute("finishCityId",finishCityId);
       model.addAttribute("bodyContent","create-ticket");
       model.addAttribute("title","Ticket");
       return "master-template";
    }
    @PostMapping
    public String saveTicket(@ModelAttribute TicketDTO ticketDTO)
    {
       try {
             ticketService.createTicket(ticketDTO);
       } catch (FullBusException| WrongTermException e) {
           return "redirect:/tickets?error="+ URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
       }
       return "redirect:/shoppingCart";
    }
}
