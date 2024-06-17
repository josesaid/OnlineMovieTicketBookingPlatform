package com.coherent.solutions.hotel.reservations.service;

import com.coherent.solutions.hotel.reservations.entity.Event;
import com.coherent.solutions.hotel.reservations.entity.MovieFunction;
import com.coherent.solutions.hotel.reservations.enums.EVENT_TIPE;
import com.coherent.solutions.hotel.reservations.helper.EventHelper;
import com.coherent.solutions.hotel.reservations.repository.EventRepository;
import com.coherent.solutions.hotel.reservations.repository.TheatreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/*
    This Movie Service lets retrieve all the movies, by taking into account the next criterias:
    1. Filter the movies by Country.
    2. Filter the movies by Country and State.
    3. Filter the movies by Country, State and City.
    4. No filter anything, it returns all the existing movies.
* */
@Slf4j
@Service
public class MovieService {
    @Autowired
    private TheatreRepository theatreRepository;
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventHelper eventHelper;

    public List<MovieFunction> getMoviesByCountryAndStateAndCity(String countryName, String stateName, String cityName) {
        List<MovieFunction> movieFunctionList = new ArrayList<>();

        Iterable<Event> eventsByCountryStateCity = eventHelper.getEventsByCountryAndStateAndCity(countryName, stateName, cityName);
        Iterator<Event> eventIterator = eventsByCountryStateCity.iterator();

        while (eventIterator.hasNext()) {
            Event event = eventIterator.next();
            EVENT_TIPE tipoEvento = event.getEventType();
            EVENT_TIPE movie = EVENT_TIPE.PELICULA;
            if (tipoEvento.equals(movie)) {
                log.info("Evento PELICULA, found.");
                movieFunctionList.add(EventHelper.assembleMovieFunction(event));
            }
        }

        return movieFunctionList;
    }
    public List<MovieFunction> getMoviesByCountryAndState(String countryName, String stateName) {
        List<MovieFunction> movieFunctionList = new ArrayList<>();

        Iterable<Event> eventsByCountryAndState = eventHelper.getEventsByCountryAndState(countryName, stateName);
        Iterator<Event> eventIterator = eventsByCountryAndState.iterator();

        while (eventIterator.hasNext()) {
            Event event = eventIterator.next();
            EVENT_TIPE tipoEvento = event.getEventType();
            EVENT_TIPE movie = EVENT_TIPE.PELICULA;
            if (tipoEvento.equals(movie)) {
                log.info("Evento PELICULA, found.");
                movieFunctionList.add(EventHelper.assembleMovieFunction(event));
            }
        }

        return movieFunctionList;
    }


    public List<MovieFunction> getMoviesByCountry(String country) {
        List<MovieFunction> movieFunctionList = new ArrayList<>();

        Iterable<Event> eventsByCountry = eventHelper.getEventsByCountry(country);
        Iterator<Event> eventIterator = eventsByCountry.iterator();

        while (eventIterator.hasNext()) {
            Event event = eventIterator.next();
            EVENT_TIPE tipoEvento = event.getEventType();
            EVENT_TIPE movie = EVENT_TIPE.PELICULA;
            if (tipoEvento.equals(movie)) {
                log.info("Evento PELICULA, found.");
                movieFunctionList.add(EventHelper.assembleMovieFunction(event));
            }
        }

        return movieFunctionList;
    }

    public List<MovieFunction> getAllMovies() {
        log.info("Retrieving all the list of movies, making some magic...");
        return EventHelper.assembleMovieFunction(eventRepository.findByEventType(EVENT_TIPE.PELICULA));
    }




}
