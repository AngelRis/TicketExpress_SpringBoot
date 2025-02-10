package com.example.ticketexpress.web.controller;

import com.example.ticketexpress.dto.TicketDTO;
import com.example.ticketexpress.exception.FullBusException;
import com.example.ticketexpress.mapper.TicketMapper;
import com.example.ticketexpress.model.ShoppingCart;
import com.example.ticketexpress.service.ShoppingCartService;
import com.google.zxing.WriterException;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }
    @GetMapping
    public String index(Model model,@RequestParam(required = false) String error) {
        if(error != null) {
            model.addAttribute("error", error);
        }
        ShoppingCart shoppingCart=shoppingCartService.getActiveShoppingCart();
        if(!shoppingCart.getTickets().isEmpty()){
            List<TicketDTO> tickets=shoppingCart.getTickets().stream().map(TicketMapper::mapToTicketDTO).toList();
            model.addAttribute("tickets",tickets);
        }
        model.addAttribute("shoppingCart",shoppingCart);
        model.addAttribute("bodyContent","shopping-cart");
        model.addAttribute("title","Shopping Cart");
        return "master-template";
    }
    @GetMapping("/removeTicket/{id}")
    public String removeTicket(@PathVariable Long id){
        shoppingCartService.removeTicket(id);
        return "redirect:/shoppingCart";
    }
    @PostMapping("/checkout/{id}")
    public String checkout(@PathVariable Long id){
        try {
            shoppingCartService.checkout(id);
        }catch (MessagingException | WriterException | IOException | FullBusException e){
            return "redirect:/shoppingCart?error="+ URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
        }

        return "redirect:/shoppingCart";
    }


}
