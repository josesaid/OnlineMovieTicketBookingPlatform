package com.coherent.solutions.hotel.reservations.entity;

import com.coherent.solutions.hotel.reservations.enums.TIPO_PAGO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Payments")
public class Payment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private float amount;
    private LocalDateTime timestamp;
    private TIPO_PAGO tipoPago;
    private String description;
    private String referenceDetails;
    private String processed;
}
