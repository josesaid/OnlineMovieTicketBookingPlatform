package com.coherent.solutions.hotel.reservations.entity;

import com.coherent.solutions.hotel.reservations.enums.TIPO_EVENTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Events")
public class Evento {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private TIPO_EVENTO tipoEvento;
    private String nombreEvento;
    private LocalDateTime horarioInicio;
    private LocalDateTime horarioFin;
    private String audioIdiomaEvento;
    private String subtitulosIdiomaEvento;
    private String classification;
    private String comments;
}
