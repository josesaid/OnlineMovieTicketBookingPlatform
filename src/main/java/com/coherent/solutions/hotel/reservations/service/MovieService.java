package com.coherent.solutions.hotel.reservations.service;

import com.coherent.solutions.hotel.reservations.entity.Event;
import com.coherent.solutions.hotel.reservations.entity.MovieFunction;
import com.coherent.solutions.hotel.reservations.entity.Room;
import com.coherent.solutions.hotel.reservations.entity.Theatre;
import com.coherent.solutions.hotel.reservations.enums.TIPO_EVENTO;
import com.coherent.solutions.hotel.reservations.helper.EventHelper;
import com.coherent.solutions.hotel.reservations.repository.EventRepository;
import com.coherent.solutions.hotel.reservations.repository.TheatreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service
public class MovieService {
    @Autowired
    private TheatreRepository theatreRepository;
    @Autowired
    private EventRepository eventRepository;

    public List<MovieFunction> getMoviesByCountry(String country) {
        List<MovieFunction> movieFunctionList = new ArrayList<>();
        //Filter theatres by country:
        List<Theatre> theatreList = theatreRepository.findByCountry(country);
        for(Theatre theare : theatreList){
            //For each theatre, retrieve its rooms:
            List<Room> rooms = theare.getSalas();
            List<Integer> idEvents = new ArrayList<>();
            for(Room room : rooms){
                //For each room, retrieve the event id and save it temporarily, to look for it at the next step...
                idEvents.add(room.getIdEvento());
                log.info("ID EVENT Detected for country:"+country+": "+room.getIdEvento());
            }

            //Now, with all the events that match for the given country, we can filter by anything we wish:
            //This might be, filtering by:
            //nombreEvento, tipo de Evento, horarios, audio idioma evento, classificacion del evento, subtitulos idioma evento

            //Let's filter the events, first to match the wished ones.
            Iterable<Event> events = eventRepository.findAllById(idEvents);

            Iterator<Event> eventIterator = events.iterator();
            while(eventIterator.hasNext()){
                Event event = eventIterator.next();
                TIPO_EVENTO tipoEvento = event.getTipoEvento();
                TIPO_EVENTO movie = TIPO_EVENTO.PELICULA;
                    if (tipoEvento.equals(movie)){
                    log.info("Evento PELICULA, found.");
                    movieFunctionList.add( EventHelper.assembleMovieFunction(event) );
                }
            }
        }
        return movieFunctionList;
    }

    public List<MovieFunction> getAllMovies() {
        log.info("Retrieving all the list of movies, making some magic...");
        return EventHelper.assembleMovieFunction(eventRepository.findByTipoEvento(TIPO_EVENTO.PELICULA));
    }

}
