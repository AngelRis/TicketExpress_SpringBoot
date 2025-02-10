package com.example.ticketexpress.dto;

import com.example.ticketexpress.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String username;
    private String password;
    private String repeatPassword;
    private String email;
    private List<Long>rolesId;
}
