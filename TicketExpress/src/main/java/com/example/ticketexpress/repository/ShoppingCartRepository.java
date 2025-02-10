package com.example.ticketexpress.repository;

import com.example.ticketexpress.model.ShoppingCart;
import com.example.ticketexpress.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
