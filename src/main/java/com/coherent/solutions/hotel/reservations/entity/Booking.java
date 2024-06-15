package com.coherent.solutions.hotel.reservations.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
}