package com.coherent.solutions.hotel.reservations.helper;

import com.coherent.solutions.hotel.reservations.entity.Event;
import com.coherent.solutions.hotel.reservations.entity.MovieFunction;

import java.util.ArrayList;
import java.util.List;

public class EventHelper {
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

}
