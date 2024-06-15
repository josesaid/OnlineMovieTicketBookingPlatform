package com.coherent.solutions.hotel.reservations.repository;

import com.coherent.solutions.hotel.reservations.entity.Payment;
import com.coherent.solutions.hotel.reservations.entity.Sala;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Integer> {

}

