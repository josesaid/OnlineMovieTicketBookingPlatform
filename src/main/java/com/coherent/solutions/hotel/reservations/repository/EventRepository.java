package com.coherent.solutions.hotel.reservations.repository;

import com.coherent.solutions.hotel.reservations.entity.Event;
import com.coherent.solutions.hotel.reservations.enums.EVENT_TIPE;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {

    List<Event> findByTipoEvento(EVENT_TIPE tipoEvento);

}

