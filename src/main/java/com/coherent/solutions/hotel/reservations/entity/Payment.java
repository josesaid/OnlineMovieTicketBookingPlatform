package com.coherent.solutions.hotel.reservations.entity;

import com.coherent.solutions.hotel.reservations.enums.TIPO_PAGO;

import java.time.LocalDateTime;

public class Payment {
    private int id;
    private float amount;
    private LocalDateTime timestamp;
    private TIPO_PAGO tipoPago;
    private String description;
    private String referenceDetails;
    private String processed;
}
