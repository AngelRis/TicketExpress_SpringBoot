package com.example.ticketexpress.service;

import com.example.ticketexpress.dto.UserDTO;
import com.example.ticketexpress.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register(UserDTO userDTO);
}
