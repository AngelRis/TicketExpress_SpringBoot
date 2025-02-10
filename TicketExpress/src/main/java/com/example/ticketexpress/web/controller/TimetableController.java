package com.example.ticketexpress.web.controller;

import com.example.ticketexpress.dto.TimetableDTO;
import com.example.ticketexpress.enumeration.DayOfWeek;
import com.example.ticketexpress.exception.TimetableNotFoundException;
import com.example.ticketexpress.service.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;


@Controller
@RequestMapping("/timetables")
public class TimetableController {
    private final TimetableService timetableService;
    private final BusStationService busStationService;
    private final BusOperatorService busOperatorService;
    private final BusService busService;
    private final CityService cityService;

    public TimetableController(TimetableService timetableService, BusStationService busStationService, BusOperatorService busOperatorService, BusService busService, CityService cityService) {
        this.timetableService = timetableService;
        this.busStationService = busStationService;
        this.busOperatorService = busOperatorService;
        this.busService = busService;
        this.cityService = cityService;
    }

    @GetMapping
    public String index(Model model,
                        @RequestParam(required = false)Long startingCityId,
                        @RequestParam(required = false)Long finalCityId,
                        @RequestParam(required = false)Long busOperatorId) {

        model.addAttribute("timetables",timetableService.filterTimetables(startingCityId, finalCityId, busOperatorId));
        model.addAttribute("cities",cityService.getCities());
        model.addAttribute("startCity",startingCityId);
        model.addAttribute("finalCity",finalCityId);
        model.addAttribute("operator",busOperatorId);
        model.addAttribute("busOperators",busOperatorService.getAllBusOperators());
        model.addAttribute("bodyContent","timetables");
        model.addAttribute("title","Timetables");
        return "master-template";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("timetable",new TimetableDTO());
        model.addAttribute("busStations",busStationService.getAllBusStations());
        model.addAttribute("busOperators",busOperatorService.getAllBusOperators());
        model.addAttribute("buses",busService.getAllBuses());
        model.addAttribute("daysOfWeek", DayOfWeek.values());
        model.addAttribute("bodyContent","create-timetable");
        model.addAttribute("title","Create Timetable");

        return "master-template";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String create(@ModelAttribute TimetableDTO timetableDTO) {
        timetableService.saveTimetable(timetableDTO);
        return "redirect:/timetables";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        timetableService.deleteTimetable(id);
        return "redirect:/timetables";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,Model model) {
        TimetableDTO timetableDTO=timetableService.findTimetableById(id).orElseThrow(()->new TimetableNotFoundException(id));

        model.addAttribute("timetable",timetableDTO);
        model.addAttribute("busStations",busStationService.getAllBusStations());
        model.addAttribute("busOperators",busOperatorService.getAllBusOperators());
        model.addAttribute("buses",busService.getAllBuses());
        model.addAttribute("daysOfWeek", DayOfWeek.values());
        model.addAttribute("bodyContent","edit-timetable");
        model.addAttribute("title","Edit Timetable");
        return "master-template";

    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute TimetableDTO timetableDTO, @PathVariable("id") Long id) {
        timetableService.updateTimetable(timetableDTO,id);
        return "redirect:/timetables";
    }

}
