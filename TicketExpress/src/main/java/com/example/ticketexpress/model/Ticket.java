package com.example.ticketexpress.model;

import com.example.ticketexpress.enumeration.TicketStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Timetable term;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private boolean roundTripTicket;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private User user;
    private int seatNumber;
    @Column(nullable = false)
    private int price;
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
}
