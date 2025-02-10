package com.example.ticketexpress.web.controller;

import com.example.ticketexpress.dto.BusOperatorDTO;
import com.example.ticketexpress.exception.BusOperatorNotFoundException;
import com.example.ticketexpress.service.BusOperatorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/busOperators")
public class BusOperatorController {
   private final BusOperatorService busOperatorService;


    public BusOperatorController(BusOperatorService busOperatorService) {
        this.busOperatorService = busOperatorService;
    }
    @GetMapping
    public String index(@RequestParam(required = false)String text,Model model) {
        model.addAttribute("busOperators",busOperatorService.searchBusOperators(text));
        model.addAttribute("bodyContent","bus-operators");
        model.addAttribute("title","Bus Operators");
        return "master-template";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("busOperator",new BusOperatorDTO());
        model.addAttribute("bodyContent","create-bus-operator");
        model.addAttribute("title","Create Bus Operator");

        return "master-template";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String create(@ModelAttribute BusOperatorDTO busOperatorDTO, @RequestParam(required = false) MultipartFile logo) {
        busOperatorService.saveBusOperator(busOperatorDTO,logo);
        return "redirect:/busOperators";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        busOperatorService.deleteBusOperator(id);
        return "redirect:/busOperators";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,Model model) {
        BusOperatorDTO busOperatorDTO= busOperatorService.findBusOperatorById(id).orElseThrow(()->new BusOperatorNotFoundException(id));
        model.addAttribute("busOperator",busOperatorDTO);
        model.addAttribute("bodyContent","edit-bus-operator");
        model.addAttribute("title","Edit Bus Operator");
        return "master-template";

    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute BusOperatorDTO busOperatorDTO,@RequestParam(required = false) MultipartFile logo, @PathVariable("id") Long id) {
        busOperatorService.updateBusOperator(busOperatorDTO,logo,id);
        return "redirect:/busOperators";
    }
}
