package com.coherent.solutions.hotel.reservations.entity;

import com.coherent.solutions.hotel.reservations.converter.RoomConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Theatres")
public class Theatre {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    private String city;
    private String state;
    private String country;

    @Convert(converter = RoomConverter.class)
    private List<Room>Salas;
}




