package com.coherent.solutions.hotel.reservations.helper;

import com.coherent.solutions.hotel.reservations.entity.Event;
import com.coherent.solutions.hotel.reservations.entity.MovieFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MovieFunctionHelper {
    public static MovieFunction assembleMovieFunction(Event event) {
        MovieFunction movieFunction = new MovieFunction();
        movieFunction.setIdEvent(event.getId());
        movieFunction.setMovieName(event.getEventName());
        movieFunction.setAudioIdiomaName(event.getEventAudioLanguage());
        movieFunction.setSubtitulosIdiomaName(event.getEventSubtitleLanguage());
        movieFunction.setClassification(event.getClassification());
        movieFunction.setHorarioInicio(event.getInitTime());
        movieFunction.setHorarioFin(event.getFinishTime());
        return movieFunction;
    }

}
