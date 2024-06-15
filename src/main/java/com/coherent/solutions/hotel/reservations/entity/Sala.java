package com.coherent.solutions.hotel.reservations.entity;

import com.coherent.solutions.hotel.reservations.enums.SEAT_STATUS;
import com.coherent.solutions.hotel.reservations.enums.STATUS_SALA;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Salas")
public class Sala{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column private int idEvento;

    @Column @JdbcTypeCode(SqlTypes.JSON) private Map<String, SEAT_STATUS> asientosMapa;
    @Column private STATUS_SALA statusSala;
}