package com.example.ticketexpress.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/home","/"})
public class HomeController {

    @GetMapping
    public String home(Model model) {
        model.addAttribute("bodyContent","home");
        model.addAttribute("title","Home Page");
        return "master-template";
    }
}
