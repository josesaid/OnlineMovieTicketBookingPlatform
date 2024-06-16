package com.coherent.solutions.hotel.reservations.entity;

import com.coherent.solutions.hotel.reservations.enums.PAYMENT_STATUS;
import com.coherent.solutions.hotel.reservations.enums.PAYMENT_TYPE;
import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    @Column(name="tipoPago")
    private PAYMENT_TYPE tipoPago;

    private String description;
    private String referenceDetails;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private PAYMENT_STATUS status;
}
