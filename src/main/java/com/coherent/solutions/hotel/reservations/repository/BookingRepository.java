package com.coherent.solutions.hotel.reservations.repository;

import com.coherent.solutions.hotel.reservations.entity.Booking;
import com.coherent.solutions.hotel.reservations.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer> {

}

