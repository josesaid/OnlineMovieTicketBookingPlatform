package com.coherent.solutions.hotel.reservations.entity;

import com.coherent.solutions.hotel.reservations.enums.SEAT_STATUS;
import com.coherent.solutions.hotel.reservations.enums.STATUS_SALA;

import java.util.Map;

public class Sala{
    private int id;
    private int idEvento;
    private Map<String, SEAT_STATUS> asientosMapa;
    private STATUS_SALA statusSala;
}