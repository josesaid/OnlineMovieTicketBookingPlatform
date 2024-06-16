package com.coherent.solutions.hotel.reservations.controller;

import com.coherent.solutions.hotel.reservations.entity.BodyResponse;
import com.coherent.solutions.hotel.reservations.entity.MovieFunction;
import com.coherent.solutions.hotel.reservations.entity.Theatre;
import com.coherent.solutions.hotel.reservations.service.CatalogService;
import com.coherent.solutions.hotel.reservations.service.MovieService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@Slf4j
@Tag(name = "Movie", description = "Current Movies API")
public class MovieController {
    private final MovieService movieService;

    private BodyResponse bodyResponse;
    private   List<MovieFunction>movieFunctionList;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(value = {"/movies", "/movies/", "/movies/{countryName}", "/movies/{countryName}/"
    })
    public Object getMovie(@PathVariable(required = false) String countryName) {
        bodyResponse = new BodyResponse();
        movieFunctionList = new ArrayList<>();

        if (countryName != null) {
            System.out.println("countryName !=null");
            movieFunctionList = movieService.getMoviesByCountry(countryName);
            bodyResponse.setNumberOfRecords(movieFunctionList.size());
            bodyResponse.setData(movieFunctionList);
            return bodyResponse;
        }
        movieFunctionList = movieService.getAllMovies();
        bodyResponse.setNumberOfRecords(movieFunctionList.size());
        bodyResponse.setData(movieFunctionList);
        return bodyResponse;

    }

}
