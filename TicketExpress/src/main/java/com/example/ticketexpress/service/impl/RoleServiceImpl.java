package com.example.ticketexpress.service.impl;

import com.example.ticketexpress.model.Role;
import com.example.ticketexpress.repository.RoleRepository;
import com.example.ticketexpress.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

}
