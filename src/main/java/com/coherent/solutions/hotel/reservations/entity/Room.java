package com.coherent.solutions.hotel.reservations.entity;

import com.coherent.solutions.hotel.reservations.converter.AsientosMapaConverter;
import com.coherent.solutions.hotel.reservations.enums.SEAT_STATUS;
import com.coherent.solutions.hotel.reservations.enums.STATUS_SALA;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Salas")
public class Room {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private int idEvento;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private STATUS_SALA status;

    @Convert(converter = AsientosMapaConverter.class)
    @Column private Map<String, SEAT_STATUS> asientosMapa;
}