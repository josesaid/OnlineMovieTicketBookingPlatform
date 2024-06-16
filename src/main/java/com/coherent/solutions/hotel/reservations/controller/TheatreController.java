package com.coherent.solutions.hotel.reservations.controller;

import com.coherent.solutions.hotel.reservations.entity.Theatre;
import com.coherent.solutions.hotel.reservations.response.TheatreResponse;
import com.coherent.solutions.hotel.reservations.service.TheatreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
/*
* This class is used to manage CRUDL operations to a Theatre
* If you want to review function requirements, then, take a look at the CatalogController and MovieController classes.
* */
@RestController
@RequestMapping("/api/v1")
@Slf4j
@Tag(name = "Theatres", description = "Theatres API")
public class TheatreController {
    private final TheatreService theatreService;

    public TheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    @Operation(summary = "Get all the Theatres in the system", description = "This method lets to retrieve all the Theatres " +
            "from the MySQL AWS DB instance. By default, when the app starts up, it automatically inserts some Theatres " +
            "on the Theatres table to start to play with the app.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Theatres found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Theatre.class))})})
    @GetMapping(value={"/theatres", "/theatres/" })
    public ResponseEntity<Iterable<Theatre>> getAllTheatres() {
        Iterable<Theatre> theatreIterable = theatreService.getAllTheathres();
        return ResponseEntity.status(HttpStatus.OK).body(theatreIterable);
    }

    @Operation(summary = "Creates an Theatre in the database.",
            description = "This method generates an Theatre entry at the MySQL AWS database instance")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Theatre created",
            content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Theatre.class))})})
    @PostMapping("/theatres")
    public ResponseEntity<Theatre> create(@RequestBody Theatre theatre){
        Theatre theatreCreated = theatreService.createTheatre(theatre);
        return ResponseEntity.status(HttpStatus.CREATED).body(theatreCreated);
    }

    @Operation(summary = "Retrieves an Theatre", description = "This method lets to retrieve an Theatre from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Theatre gotten",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Theatre.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request is returned if the info has a wrong format.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Theatre.class)) })
    })
    @GetMapping("/theatres/{id}")
    public ResponseEntity<Object> getTheatre(@PathVariable String id) {
        try{
            Integer.parseInt(id);
        }catch(NumberFormatException e){
            String message = "Theatre ID has an incorrect format: " + id;
            log.error(message);
            return new ResponseEntity<>(new TheatreResponse("ERROR", message), HttpStatus.BAD_REQUEST);
        }
        Optional<Theatre> TheatreOptional = theatreService.getTheatre(Integer.parseInt(id));
        if(TheatreOptional.isPresent())
            return new ResponseEntity<>(TheatreOptional.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(new TheatreResponse("ERROR", "Theatre ID: " + id + " was NOT found on the DB"), HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Updates an Theatre",
            description = "This method updates an Theatre object from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Theatre updated",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Theatre.class)) }),
            @ApiResponse(responseCode = "404", description = "Theatre not found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Theatre.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request is returned if the info has a wrong format.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Theatre.class)) })
    })
    @PutMapping("/theatres/{id}")
    public ResponseEntity<Object> updateTheatre(@PathVariable("id") String id, @RequestBody Theatre theatre) {
        String message = null;
        int theatreId = -1;
        try{
            theatreId = Integer.parseInt(id);
        }catch(NumberFormatException e){
            message = "Theatre ID has an incorrect format: " + theatreId;
            log.error(message);
            return new ResponseEntity<>(new TheatreResponse("ERROR", message), HttpStatus.BAD_REQUEST);
        }

        // Retrieve the resource from the database
        Optional<Theatre> theatreOptional = theatreService.getTheatre(Integer.parseInt(id));

        // If the resource is not found, return a 404 (not found) status code
        if (!theatreOptional.isPresent()) {
            message = "Theatre ID: " + id + " was found found on the DB";
            log.error(message);
            return new ResponseEntity<>(new TheatreResponse("ERROR", message), HttpStatus.NOT_FOUND);
        }

        // Update the resource
        theatre.setId(theatreId);
        log.info("Theatre incoming: " + theatre);
        Theatre theatreUpdated = theatreService.saveTheatre(theatre);
        log.info("TheatreUpdated: " + theatreUpdated);

        // Return the updated resource with a 200 (OK) status code
        return ResponseEntity.status(HttpStatus.OK).body(theatreUpdated);
    }


    @Operation(summary = "Deletes an Theatre", description = "This method deletes an Theatre from database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Theatre removed",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Theatre.class)) }),
            @ApiResponse(responseCode = "404", description = "Theatre not found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Theatre.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request is returned if the info has a wrong format.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Theatre.class)) })
    })
    @DeleteMapping("/theatres/{id}")
    public ResponseEntity<Object> deleteTheatre(@PathVariable String id){
        String message = "Theatre ID: "+  id + " deleted correctly.";
        int theatreId = -1;
        try{
            theatreId = Integer.parseInt(id);
        }catch(NumberFormatException e){
            message = "Theatre ID has an incorrect format: " + theatreId;
            log.error(message);
            return new ResponseEntity<>(new TheatreResponse("ERROR", message), HttpStatus.BAD_REQUEST);
        }
        Optional<Theatre> theatreOptional = theatreService.getTheatre(Integer.parseInt(id));
        if(!theatreOptional.isPresent()) {
            message = "Theatre ID: " + id + " was not found on the DB";
            log.error(message);
            return new ResponseEntity<>(new TheatreResponse("ERROR", message), HttpStatus.NOT_FOUND);
        }else {
            message = "Theatre ID: " + id + " removed from the DB";
            theatreService.removeTheatre(theatreId);
            log.info(message);
            // Return the info about the removed resource with a 202 (ACCEPTED) status code
            return new ResponseEntity<>(new TheatreResponse("SUCCESS", message), HttpStatus.ACCEPTED);
        }
    }

}
