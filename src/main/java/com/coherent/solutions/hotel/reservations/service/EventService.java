package com.coherent.solutions.hotel.reservations.service;


import com.coherent.solutions.hotel.reservations.entity.Event;
import com.coherent.solutions.hotel.reservations.entity.User;
import com.coherent.solutions.hotel.reservations.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class EventService {
    private final EventRepository eventRepository;
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Iterable<Event> getAllEvents(){
        log.info("Retrieving all the events");
        return eventRepository.findAll();
    }

    public Event createEvent(Event event) {
        log.info("Creating a new event");
        Event eventCreated = eventRepository.save(event);
        return eventCreated;
    }


    public Optional<Event> getEvent(int id) {
        log.info("Retrieving an event");
        return eventRepository.findById(id);
    }

    public Event saveEvent(Event event) {
        log.info("Saving an event");
        return eventRepository.save(event);
    }

    public void removeEvent(int eventId) {
        log.info("Removing an event");
        eventRepository.deleteById(eventId);
    }

}
