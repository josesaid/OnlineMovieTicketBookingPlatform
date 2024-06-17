package com.coherent.solutions.hotel.reservations.controller;

import com.coherent.solutions.hotel.reservations.entity.BodyResponse;
import com.coherent.solutions.hotel.reservations.entity.Booking;
import com.coherent.solutions.hotel.reservations.entity.MovieFunction;
import com.coherent.solutions.hotel.reservations.entity.Theatre;
import com.coherent.solutions.hotel.reservations.service.CatalogService;
import com.coherent.solutions.hotel.reservations.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@Tag(name = "Movie", description = "Current Movies API")
public class MovieController {
    private final MovieService movieService;
    private BodyResponse bodyResponse;
    private List<MovieFunction> movieFunctionList;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @Operation(summary = "Retrieves a movies data structure", description = "This method lets to retrieve a information from movies from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movies gotten",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MovieFunction.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request is returned if the info has a wrong format.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = MovieFunction.class)) })
    })
    @GetMapping(value = {"/movies", "/movies/", "/movies/{countryName}", "/movies/{countryName}/",
            "/movies/{countryName}/states", "/movies/{countryName}/states/", "/movies/{countryName}/states/{stateName}",
            "/movies/{countryName}/states/{stateName}/", "/movies/{countryName}/states/{stateName}/",
            "/movies/{countryName}/states/{stateName}/cities", "/movies/{countryName}/states/{stateName}/cities/",
            "/movies/{countryName}/states/{stateName}/cities/{cityName}", "/movies/{countryName}/states/{stateName}/cities/{cityName}/"
    })
    public Object getMovies(@PathVariable(required = false) String countryName,
                            @PathVariable(required = false) String stateName,
                            @PathVariable(required = false) String cityName) {
        bodyResponse = new BodyResponse();
        movieFunctionList = new ArrayList<>();

        if (cityName != null && stateName != null && countryName != null) {
            movieFunctionList = movieService.getMoviesByCountryAndStateAndCity(countryName, stateName, cityName);
            bodyResponse.setNumberOfRecords(movieFunctionList.size());
            bodyResponse.setData(movieService);
            return bodyResponse;
        }

        if (stateName != null && countryName != null) {
            movieFunctionList = movieService.getMoviesByCountryAndState(countryName, stateName);
            bodyResponse.setNumberOfRecords(movieFunctionList.size());
            bodyResponse.setData(movieFunctionList);
            return bodyResponse;
        }

        if (countryName != null) {
            movieFunctionList = movieService.getMoviesByCountry(countryName);
            bodyResponse.setNumberOfRecords(movieFunctionList.size());
            bodyResponse.setData(movieFunctionList);
            return bodyResponse;
        }

        //If none of the previous criteria got selected, then return all the movies at the DB.
        movieFunctionList = movieService.getAllMovies();
        bodyResponse.setNumberOfRecords(movieFunctionList.size());
        bodyResponse.setData(movieFunctionList);
        return bodyResponse;

    }

}
