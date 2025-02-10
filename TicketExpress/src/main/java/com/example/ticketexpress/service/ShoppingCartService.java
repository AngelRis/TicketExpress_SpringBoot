package com.example.ticketexpress.service;

import com.example.ticketexpress.model.ShoppingCart;
import com.google.zxing.WriterException;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface ShoppingCartService {
    ShoppingCart getActiveShoppingCart();
    void removeTicket(Long ticketId);
    void checkout(Long cartId) throws MessagingException, WriterException, IOException;
}
