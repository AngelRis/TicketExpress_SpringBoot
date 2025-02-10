package com.example.ticketexpress.config;

import com.example.ticketexpress.enumeration.ERole;
import com.example.ticketexpress.model.Role;
import com.example.ticketexpress.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer {
    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @PostConstruct
    public void init() {
         initializeRoles();
    }
    public void initializeRoles(){
        if(roleRepository.count() == 0) {
            roleRepository.save(Role.builder().name(ERole.ROLE_USER).build());
            roleRepository.save(Role.builder().name(ERole.ROLE_ADMIN).build());
        }
    }
}
