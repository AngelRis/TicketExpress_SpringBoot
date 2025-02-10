package com.example.ticketexpress.model;

import com.example.ticketexpress.enumeration.DayOfWeek;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "timetables")
public class Timetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private BusStation startingBusStation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private BusStation finalBusStation;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private BusOperator busOperator;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Bus bus;
    @Column(nullable = false)
    private int oneWayTicketPrice;
    @Column(nullable = false)
    private int twoWayTicketPrice;
    @Column(nullable = false)
    private LocalTime startTime;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<DayOfWeek> dayOfWeek=new ArrayList<>();

}
