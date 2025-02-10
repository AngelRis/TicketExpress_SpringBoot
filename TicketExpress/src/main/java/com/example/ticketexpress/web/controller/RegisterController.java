package com.example.ticketexpress.web.controller;

import com.example.ticketexpress.dto.UserDTO;
import com.example.ticketexpress.exception.PasswordsDoNotMatchException;
import com.example.ticketexpress.exception.UsernameAlreadyExistsException;
import com.example.ticketexpress.service.RoleService;
import com.example.ticketexpress.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final UserService userService;
    private final RoleService roleService;
    public RegisterController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping
    public String register(Model model) {
        model.addAttribute("user",new UserDTO());
        model.addAttribute("roles",roleService.getAllRoles());
        model.addAttribute("bodyContent","register");
        model.addAttribute("title","Register");
        return "master-template";
    }
    @PostMapping
    public String register(UserDTO userDTO,Model model) {
        try {
            userService.register(userDTO);
            return "redirect:/login";
        }catch (PasswordsDoNotMatchException| UsernameAlreadyExistsException exception)
        {
            model.addAttribute("error",exception.getMessage());
            model.addAttribute("title","Register");
            model.addAttribute("user",userDTO);
            model.addAttribute("bodyContent","register");
            model.addAttribute("roles",roleService.getAllRoles());
            return "master-template";
        }
    }
}
