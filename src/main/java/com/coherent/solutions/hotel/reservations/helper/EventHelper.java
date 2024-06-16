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
        MovieFunction movieFunction = new MovieFunction();
        movieFunction.setIdEvent(event.getId());
        movieFunction.setMovieName(event.getNombreEvento());
        movieFunction.setAudioIdiomaName(event.getAudioIdiomaEvento());
        movieFunction.setSubtitulosIdiomaName(event.getSubtitulosIdiomaEvento());
        movieFunction.setClassification(event.getClassification());
        movieFunction.setHorarioInicio(event.getHorarioInicio());
        movieFunction.setHorarioFin(event.getHorarioFin());
        return movieFunction;
    }

    public static List<MovieFunction> assembleMovieFunction(List<Event> eventList) {
        List<MovieFunction> movieFunctionList = new ArrayList<>();
        for(Event event : eventList){
            movieFunctionList.add(assembleMovieFunction(event));
        }
        return movieFunctionList;
    }

    public Iterable<Event> getEventsByCountry(String country) {
        List<Integer> idEvents = new ArrayList<>();
        //Filter theatres by country:
        List<Theatre> theatreList = theatreRepository.findByCountry(country);
        for (Theatre theare : theatreList) {
            //For each theatre, retrieve its rooms:
            List<Room> rooms = theare.getSalas();

            for (Room room : rooms) {
                //For each room, retrieve the event id and save it temporarily, to look for it at the next step...
                idEvents.add(room.getIdEvento());
                log.info("ID EVENT Detected for country:" + country + ": " + room.getIdEvento());
            }
        }
        //Now, with all the events that match for the given country, we can filter by anything we wish:
        //This might be, filtering by:
        //nombreEvento, tipo de Evento, horarios, audio idioma evento, classificacion del evento, subtitulos idioma evento

        //Let's filter the events, first to match the wished ones.
        return eventRepository.findAllById(idEvents);

    }

    public Iterable<Event> getEventsByCountryAndState(String countryName, String stateName) {
        List<Integer> idEvents = new ArrayList<>();

        //Filter theatres by country and state
        List<Theatre> theatreList = theatreRepository.findByCountryAndState(countryName, stateName);
        for (Theatre theare : theatreList) {
            //For each theatre, retrieve its rooms:
            List<Room> rooms = theare.getSalas();

            for (Room room : rooms) {
                //For each room, retrieve the event id and save it temporarily, to look for it at the next step...
                idEvents.add(room.getIdEvento());
                log.info("ID EVENT Detected: " + room.getIdEvento()+ " for country:" + countryName + " AND STATE: " + stateName);
            }
        }
        //Now, with all the events that match for the given country, we can filter by anything we wish:
        //This might be, filtering by:
        //nombreEvento, tipo de Evento, horarios, audio idioma evento, classificacion del evento, subtitulos idioma evento

        //Let's filter the events, first to match the wished ones.
        return eventRepository.findAllById(idEvents);
    }

    public Iterable<Event> getEventsByCountryAndStateAndCity(String countryName, String stateName, String cityName) {
        List<Integer> idEvents = new ArrayList<>();

        //Filter theatres by country, state and city:
        List<Theatre> theatreList = theatreRepository.findByCountryAndStateAndCity(countryName, stateName, cityName);
        for (Theatre theare : theatreList) {
            //For each theatre, retrieve its rooms:
            List<Room> rooms = theare.getSalas();

            for (Room room : rooms) {
                //For each room, retrieve the event id and save it temporarily, to look for it at the next step...
                idEvents.add(room.getIdEvento());
                log.info("ID EVENT Detected: " + room.getIdEvento()+ " for country:" + countryName + " AND STATE: " + stateName+" and City: " + cityName);
            }
        }
        //Now, with all the events that match for the given country, we can filter by anything we wish:
        //This might be, filtering by:
        //nombreEvento, tipo de Evento, horarios, audio idioma evento, classificacion del evento, subtitulos idioma evento

        //Let's filter the events, first to match the wished ones.
        return eventRepository.findAllById(idEvents);
    }

}
