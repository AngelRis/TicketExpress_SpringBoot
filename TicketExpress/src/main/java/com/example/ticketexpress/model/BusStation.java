package com.example.ticketexpress.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "bus_stations")
public class BusStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private double latitude;
    @Column(nullable = false)
    private double longitude;
    @Column(nullable = false)
    private String phone;
    private String imageUrl;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private City city;
    @OneToMany(mappedBy = "startingBusStation",cascade = CascadeType.ALL)
    private List<Timetable> starting=new ArrayList<>();
    @OneToMany(mappedBy = "finalBusStation",cascade = CascadeType.ALL)
    private List<Timetable> ending=new ArrayList<>();
}
