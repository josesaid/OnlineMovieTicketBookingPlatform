package com.coherent.solutions.hotel.reservations.entity;

import com.coherent.solutions.hotel.reservations.enums.TIPO_EVENTO;

import java.time.LocalDateTime;
import java.util.List;

public class Evento {
    private int id;
    private TIPO_EVENTO tipoEvento;
    private String nombreEvento;
    private List<LocalDateTime> horarioInicio;
    private List<LocalDateTime> horarioFin;
    private String audioIdiomaEvento;
    private String subtitulosIdiomaEvento;
    private String comments;
}
