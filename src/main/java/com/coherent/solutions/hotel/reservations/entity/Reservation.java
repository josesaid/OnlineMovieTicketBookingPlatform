package com.coherent.solutions.hotel.reservations.entity;

import com.coherent.solutions.hotel.reservations.converter.ReservationConverter;
import jakarta.persistence.*;
import jakarta.persistence.Convert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String clientFullName;

    private int roomNumber;

    @Convert(converter = ReservationConverter.class)
    private List<LocalDate> reservationDates;

}
