package com.coherent.solutions.hotel.reservations.repository;

import com.coherent.solutions.hotel.reservations.entity.Reservation;
import com.coherent.solutions.hotel.reservations.entity.Theatre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheatreRepository extends CrudRepository<Theatre, Integer> {

}

