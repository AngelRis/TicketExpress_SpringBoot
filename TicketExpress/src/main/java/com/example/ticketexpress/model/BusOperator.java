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
@Table(name = "bus_operators")
public class BusOperator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    private String logoUrl;
    @Column(nullable = false)
    private String phone;
    @OneToMany(mappedBy = "busOperator",cascade = CascadeType.ALL)
    private List<Timetable> timetableList=new ArrayList<>();
}
