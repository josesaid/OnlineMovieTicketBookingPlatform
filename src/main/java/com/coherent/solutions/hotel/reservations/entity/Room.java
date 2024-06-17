package com.coherent.solutions.hotel.reservations.entity;

import com.coherent.solutions.hotel.reservations.converter.SeatsMapConverter;
import com.coherent.solutions.hotel.reservations.enums.SEAT_STATUS;
import com.coherent.solutions.hotel.reservations.enums.ROOM_STATUS;
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
@Entity(name = "Rooms")
public class Room {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private int idEvent;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private ROOM_STATUS status;

    @Convert(converter = SeatsMapConverter.class)
    @Column private Map<String, SEAT_STATUS> seatsMap;
}