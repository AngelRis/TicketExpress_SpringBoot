package com.example.ticketexpress.web.controller;

import com.example.ticketexpress.dto.BusStationDTO;
import com.example.ticketexpress.exception.BusStationNotFoundException;
import com.example.ticketexpress.service.BusStationService;
import com.example.ticketexpress.service.CityService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("/busStations")
public class BusStationController {
    private final BusStationService busStationService;
    private final CityService cityService;

    public BusStationController(BusStationService busStationService, CityService cityService) {
        this.busStationService = busStationService;
        this.cityService = cityService;
    }
    @GetMapping
    public String index(@RequestParam(required = false)Long cityId, Model model) {
        Optional<BusStationDTO> busStationDTO=busStationService.findBusStationByCity(cityId);
        if(busStationDTO.isPresent()) {
            model.addAttribute("busStations", Collections.singletonList(busStationDTO.get()));
        } else if (cityId != null) {
            model.addAttribute("busStations", Collections.emptyList());
        } else{
            model.addAttribute("busStations", busStationService.getAllBusStations());
        }
        model.addAttribute("cities",cityService.getCities());
        model.addAttribute("bodyContent","bus-stations");
        model.addAttribute("title","Bus Stations");
        return "master-template";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("busStation",new BusStationDTO());
        model.addAttribute("cities",cityService.getCities());
        model.addAttribute("bodyContent","create-bus-station");
        model.addAttribute("title","Create Bus Station");

        return "master-template";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String create(@ModelAttribute BusStationDTO busStationDTO, @RequestParam(required = false) MultipartFile image) {
        busStationService.saveBusStation(busStationDTO,image);
        return "redirect:/busStations";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        busStationService.deleteBusStation(id);
        return "redirect:/busStations";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,Model model) {
        BusStationDTO busStationDTO=busStationService.findBusStationById(id).orElseThrow(()->new BusStationNotFoundException(id));
        model.addAttribute("busStation",busStationDTO);
        model.addAttribute("cities",cityService.getCities());
        model.addAttribute("bodyContent","edit-bus-station");
        model.addAttribute("title","Edit Bus Station");
        return "master-template";

    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute BusStationDTO busStationDTO,@RequestParam(required = false) MultipartFile image, @PathVariable("id") Long id) {
        busStationService.updateBusStation(busStationDTO,image,id);
        return "redirect:/busStations";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Long id,Model model) {
        BusStationDTO busStationDTO=busStationService.findBusStationById(id).orElseThrow(()->new BusStationNotFoundException(id));
        model.addAttribute("busStation",busStationDTO);
        model.addAttribute("bodyContent","details-bus-station");
        model.addAttribute("title","Details Bus Station");
        return "master-template";
    }
}
