package com.coherent.solutions.hotel.reservations.entity;

import com.coherent.solutions.hotel.reservations.enums.USER_STATUS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String cellPhone;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private USER_STATUS status;
}