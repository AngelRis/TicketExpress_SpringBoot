package com.example.ticketexpress.repository;

import com.example.ticketexpress.enumeration.DayOfWeek;
import com.example.ticketexpress.model.BusOperator;
import com.example.ticketexpress.model.BusStation;
import com.example.ticketexpress.model.City;
import com.example.ticketexpress.model.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;

public interface TimetableRepository extends JpaRepository<Timetable, Long> {
    List<Timetable> findAllByStartingBusStation_CityAndFinalBusStation_CityAndBusOperator(City startingCity, City finalCity, BusOperator busOperator);
    List<Timetable> findAllByStartingBusStation_CityAndFinalBusStation_City(City startingCity, City finalCity);
    List<Timetable> findAllByStartingBusStation_CityAndFinalBusStation_CityAndDayOfWeekContaining (City starting, City finish, DayOfWeek day);
}
