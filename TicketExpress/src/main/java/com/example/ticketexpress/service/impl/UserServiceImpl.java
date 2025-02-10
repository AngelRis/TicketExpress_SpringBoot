package com.example.ticketexpress.service.impl;

import com.example.ticketexpress.dto.UserDTO;
import com.example.ticketexpress.enumeration.ShoppingCartStatus;
import com.example.ticketexpress.exception.PasswordsDoNotMatchException;
import com.example.ticketexpress.exception.UsernameAlreadyExistsException;
import com.example.ticketexpress.model.ShoppingCart;
import com.example.ticketexpress.model.User;
import com.example.ticketexpress.repository.RoleRepository;
import com.example.ticketexpress.repository.ShoppingCartRepository;
import com.example.ticketexpress.repository.UserRepository;
import com.example.ticketexpress.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ShoppingCartRepository shoppingCartRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }


    @Override
    public User register(UserDTO userDTO) {
        if(userRepository.findByUsername(userDTO.getUsername())!=null)
        {
            throw new UsernameAlreadyExistsException();
        }
        if(!userDTO.getPassword().equals(userDTO.getRepeatPassword()))
        {
            throw new PasswordsDoNotMatchException();
        }
        User user=User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .roles(roleRepository.findAllById(userDTO.getRolesId()))
                .shoppingCarts(new ArrayList<>())
                .build();
        user=userRepository.save(user);
        ShoppingCart cart=ShoppingCart.builder()
                .user(user)
                .totalPrice(0.0)
                .status(ShoppingCartStatus.ACTIVE)
                .build();
        shoppingCartRepository.save(cart);
        user.getShoppingCarts().add(cart);
        return userRepository.save(user);
    };

    @Override
    public UserDetails loadUserByUsername(String username){
        User user=userRepository.findByUsername(username);
        if(user!=null)
        {
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList())
            );
        }
        throw new UsernameNotFoundException(username);
    }
}
