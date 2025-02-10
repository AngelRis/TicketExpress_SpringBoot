package com.example.ticketexpress.web.controller;

import com.example.ticketexpress.dto.BusDTO;
import com.example.ticketexpress.exception.BusNotFoundException;
import com.example.ticketexpress.service.BusService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/buses")
public class BusController {
    private final BusService busService;

    public BusController(BusService busService) {
        this.busService = busService;
    }
    @GetMapping
    public String index(Model model) {
        model.addAttribute("buses",busService.getAllBuses());
        model.addAttribute("bodyContent","buses");
        model.addAttribute("title","Buses");
        return "master-template";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("bus",new BusDTO());
        model.addAttribute("bodyContent","create-bus");
        model.addAttribute("title","Create Bus");

        return "master-template";
    }
    @PostMapping("/create")
    public String create(@ModelAttribute BusDTO bus) {
        busService.saveBus(bus);
        return "redirect:/buses";
    }
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        busService.deleteBus(id);
        return "redirect:/buses";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,Model model) {
       BusDTO busDTO = busService.findBusById(id).orElseThrow(()->new BusNotFoundException(id));
            model.addAttribute("bus",busDTO);
            model.addAttribute("bodyContent","edit-bus");
            model.addAttribute("title","Edit Bus");
            return "master-template";

    }
    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute BusDTO bus, @PathVariable("id") Long id) {
        busService.updateBus(bus, id);
        return "redirect:/buses";
    }
}
