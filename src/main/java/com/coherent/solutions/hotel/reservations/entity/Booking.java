package com.coherent.solutions.hotel.reservations.entity;

import com.coherent.solutions.hotel.reservations.enums.BOOKING_STATUS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Bookings")
public class Booking{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private int idTheatre;
    private int idSala;
    private int idUsuario;
    private int idPayment;
    private String comments;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private BOOKING_STATUS status;
}