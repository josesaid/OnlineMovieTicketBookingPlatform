package com.coherent.solutions.hotel.reservations.controller;

import com.coherent.solutions.hotel.reservations.entity.Reservation;
import com.coherent.solutions.hotel.reservations.response.ReservationResponse;
import com.coherent.solutions.hotel.reservations.service.ReservationService;
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

@RestController
@RequestMapping("/hotel")
@Slf4j
@Tag(name = "Reservation", description = "Reservation API")
public class ReservationController {
    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "Get all the reservations",
            description = "This method lets to retrieve all the recorded Reservations objects from the HSQL in memory database. By default, when the app starts up, it automatically inserts 3 reservations on the reservations table to start to play with the app.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservations found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Reservation.class)) }
            )})
    @GetMapping("/reservations")
    public ResponseEntity<Iterable<Reservation>> getAllReservations(){
        Iterable<Reservation> reservationIterable= reservationService.getAllReservations();
        return ResponseEntity.status(HttpStatus.OK).body(reservationIterable);
    }

    @Operation(summary = "Generates a reservation without validation (If you want to generate a valid reservation, then you must use /hotel/reservations/reserve/ POST Method call).",
            description = "This method generates a reservation in the HSQL in memory database with no validation (The one which validates the information, is the /reserve http post endpoint [Below]).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reservation created",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Reservation.class)) }
            )})
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation reservation){
        Reservation reservationCreated = reservationService.createReservation(reservation);
        log.info("reservationCreated: " + reservationCreated);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationCreated);
    }

    @Operation(summary = "Retrieves a reservation",
            description = "This method lets to retrieve a reservation object from the HSQL in memory database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation gotten",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Reservation.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request is returned if the info has a wrong format.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Reservation.class)) })
            })
    @GetMapping("/reservations/{id}")
    public ResponseEntity<Object> getReservation(@PathVariable String id) {
        try{
            Integer.parseInt(id);
        }catch(NumberFormatException e){
            String message = "Reservation ID has an incorrect format: " + id;
            log.error(message);
            return new ResponseEntity<>(new ReservationResponse("ERROR", message), HttpStatus.BAD_REQUEST);
        }
        Optional<Reservation> reservationOptional = reservationService.getReservation(Integer.parseInt(id));
        if(reservationOptional.isPresent())
            return new ResponseEntity<>(reservationOptional.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(new ReservationResponse("ERROR", "Reservation ID: " + id + " was found found on the DB"), HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Updates a reservation",
            description = "This method updates a reservation object from the HSQL in memory database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation updated",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Reservation.class)) }),
            @ApiResponse(responseCode = "404", description = "Reservation not found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request is returned if the info has a wrong format.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationResponse.class)) })
    })
    @PutMapping("/reservations/{id}")
    public ResponseEntity<Object> updateReservation(@PathVariable("id") String id, @RequestBody Reservation reservation) {
        String message = null;
        int reservationId = -1;
        try{
            reservationId = Integer.parseInt(id);
        }catch(NumberFormatException e){
            message = "Reservation ID has an incorrect format: " + reservationId;
            log.error(message);
            return new ResponseEntity<>(new ReservationResponse("ERROR", message), HttpStatus.BAD_REQUEST);
        }

        // Retrieve the resource from the database
        Optional<Reservation> reservationOptional = reservationService.getReservation(Integer.parseInt(id));

        // If the resource is not found, return a 404 (not found) status code
        if (!reservationOptional.isPresent()) {
            message = "Reservation ID: " + id + " was found found on the DB";
            log.error(message);
            return new ResponseEntity<>(new ReservationResponse("ERROR", message), HttpStatus.NOT_FOUND);
        }

        // Update the resource
        reservation.setId(reservationId);
        log.info("reservation incoming: " + reservation);
        Reservation updatedReservation = reservationService.saveReservation(reservation);
        log.info("updatedReservation: " + updatedReservation);

        // Return the updated resource with a 200 (OK) status code
        return ResponseEntity.status(HttpStatus.OK).body(updatedReservation);
    }

    @Operation(summary = "Deletes a reservation",
            description = "This method deletes an existing reservation object from the HSQL in memory database. You need to provide an ID number and the API will let you know the result of the operation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reservation removed",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Reservation.class)) }),
            @ApiResponse(responseCode = "404", description = "Reservation not found",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request is returned if the info has a wrong format.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationResponse.class)) })
    })
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Object> deleteReservation(@PathVariable String id){
        String message = "Reservation ID: "+  id + " deleted correctly.";
        int reservationId = -1;
        try{
            reservationId = Integer.parseInt(id);
        }catch(NumberFormatException e){
            message = "Reservation ID has an incorrect format: " + reservationId;
            log.error(message);
            return new ResponseEntity<>(new ReservationResponse("ERROR", message), HttpStatus.BAD_REQUEST);
        }
        Optional<Reservation> reservationOptional = reservationService.getReservation(Integer.parseInt(id));
        if(!reservationOptional.isPresent()) {
            message = "Reservation ID: " + id + " was not found on the DB";
            log.error(message);
            return new ResponseEntity<>(new ReservationResponse("ERROR", message), HttpStatus.NOT_FOUND);
        }else {
            reservationService.removeReservation(reservationId);
            log.info(message);
            return new ResponseEntity<>(new ReservationResponse("SUCCESS", message), HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Creates a validated reservation, by considering if the room number and dates are free(ready to be reserved). ",
            description = "This method generates a reservation on the HSQL memory database. This is the main part of the assignment of the Coherent solutions team. This endpoint takes the information and ensures the room number given as well as the set of the dates are available according the existing information on the DB.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reservation created",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Reservation.class)) }),
            @ApiResponse(responseCode = "500", description = "Reservation error (The dates were already selected for that room number).",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ReservationResponse.class)) })
            })
    @PostMapping("/reservations/reserve")
    public ResponseEntity<Object> reserve(@RequestBody Reservation reservation){
        Reservation reservationCreated;
        boolean successfulVerification = reservationService.verifyReservationInfo(reservation);
        if(successfulVerification){
            reservationCreated = reservationService.createReservation(reservation);
            log.info("reservationCreated: " + reservationCreated);
            return new ResponseEntity<>(new ReservationResponse("SUCCESS", "Reservation created: " +reservationCreated), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(new ReservationResponse("ERROR", "Reservation not created"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
