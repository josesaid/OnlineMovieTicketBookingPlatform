package com.coherent.solutions.hotel.reservations.helper;

import com.coherent.solutions.hotel.reservations.entity.Event;
import com.coherent.solutions.hotel.reservations.entity.MovieFunction;
import com.coherent.solutions.hotel.reservations.entity.Room;
import com.coherent.solutions.hotel.reservations.entity.Theatre;
import com.coherent.solutions.hotel.reservations.repository.EventRepository;
import com.coherent.solutions.hotel.reservations.repository.TheatreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class EventHelper {

    public final  TheatreRepository theatreRepository;
    private final EventRepository eventRepository;
    public EventHelper(TheatreRepository theatreRepository,EventRepository eventRepository ) {
        this.theatreRepository = theatreRepository;
        this.eventRepository = eventRepository;
    }
    public static MovieFunction assembleMovieFunction(Event event) {
        return MovieFunctionHelper.assembleMovieFunction(event);
    }
    public static List<MovieFunction> assembleMovieFunction(List<Event> eventList) {
        List<MovieFunction> movieFunctionList = new ArrayList<>();
        for(Event event : eventList){
            movieFunctionList.add(assembleMovieFunction(event));
        }
        return movieFunctionList;
    }
    public Iterable<Event> getEventsByCountry(String country) {
        List<Theatre> theatreList = theatreRepository.findByCountry(country);
        return method(theatreList, new ArrayList<>());
    }

    public Iterable<Event> getEventsByCountryAndState(String countryName, String stateName) {
        List<Theatre> theatreList = theatreRepository.findByCountryAndState(countryName, stateName);
        return method(theatreList, new ArrayList<>());
    }

    public Iterable<Event> getEventsByCountryAndStateAndCity(String countryName, String stateName, String cityName) {
        List<Theatre> theatreList = theatreRepository.findByCountryAndStateAndCity(countryName, stateName, cityName);
        return method(theatreList, new ArrayList<>());
    }

    private Iterable<Event> method(List<Theatre> theatreList, List<Integer> idEvents){
        //For each theatre, retrieve its rooms. For each room, retrieve the event id and save it temporarily;
        // look for it at the next step...
        theatreList.stream().map(Theatre::getRooms).flatMap(Collection::stream).forEachOrdered(room -> {
            idEvents.add(room.getIdEvent());
            log.info("ID EVENT Detected: " + room.getIdEvent());
        });
        //Now, with all the events that match for the given country, we can filter by anything we wish:
        //This might be, filtering by:
        //eventName, eventType, initTime, classification, etc.
        //Let's filter the events, first to match the wished ones.
        return eventRepository.findAllById(idEvents);
    }

}
